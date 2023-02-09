package ptitgourmand.accèsAuxDonnées

import ptitgourmand.domaine.entité.Recette
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.io.StringReader

import android.util.JsonReader;
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import ptitgourmand.domaine.entité.Utilisateur
import kotlin.math.log


class SourceDeDonnéesPostman (var ctx: Context, var  urlSource: URL):ISourceDeDonnées{

    /*var ingrédients = listOf<String>("Patate frite","Sauce","Fromage")
    var étapes = listOf<String>("Frire les patate","Tout mélanger","Manger")

    var recette = Recette("Poutine","50 minutes", Utilisateur("nico", "megane", "email"),
        ingrédients as MutableList<String>, 4, étapes as MutableList<String>)*/
    var motPasse = mutableListOf<String>()
    var listeRecetteTemporaire = mutableListOf<Recette>()
    var listeUtilisateurTemporaire = mutableListOf<Utilisateur>()
    var listeMotPasseTemporaire = mutableListOf<String>()




    override fun obtenirToutesLesRecettes(): MutableList<Recette> {
        val queue = Volley.newRequestQueue(ctx)

        val promesse: RequestFuture<String> = RequestFuture.newFuture()

        var url : String = urlSource.toString()+"recette"

        val requête = StringRequest(Request.Method.GET, url, promesse, promesse)

        queue.add(requête)

        try{

            var liste:MutableList<Recette> = réponseJSONToRecettes(JsonReader(StringReader(promesse.get())))

            var grandeListe = liste
            grandeListe += listeRecetteTemporaire
            for (i in 0 until grandeListe.size){
                Log.d("liste", grandeListe[i].nom)
            }
            return grandeListe
        }
        catch (e: InterruptedException){
            e.printStackTrace()
            return mutableListOf<Recette>()
        }
        catch (e: ExecutionException){
            throw AccèsRessourcesException(e)
        }

    }

    override fun obtenirUneRecette(indice: Int): Recette {
        val queue = Volley.newRequestQueue(ctx)

        val promesse: RequestFuture<String> = RequestFuture.newFuture()

        var url : String = urlSource.toString()+"Recette/"+indice

        val requête = StringRequest(Request.Method.GET, url, promesse, promesse)

        queue.add(requête)

        try{
            Log.d("url", "test")
            var liste:MutableList<Recette> = réponseJSONToRecettes(JsonReader(StringReader(promesse.get())))
            Log.d("url", "se rend")
            /*var utilisateur = Utilisateur("","","")
            var ingrédients = mutableListOf<String>()
            var étapes = mutableListOf<String>()
            var recette = Recette("","",utilisateur,ingrédients,0,étapes)*/
            var grandeListe = liste
            grandeListe += listeRecetteTemporaire
            var recette = grandeListe[indice]
            return recette
        }
        catch (e: InterruptedException){
            e.printStackTrace()
            return Recette("","", Utilisateur("","",""), mutableListOf(), 0, mutableListOf())
        }
        catch (e: ExecutionException){
            throw AccèsRessourcesException(e)
        }
    }

    override fun ajouterRecette(recette: Recette) {
        listeRecetteTemporaire.add(recette)
    }

    override fun rechercheUtilisateur(email: String, mdp: String): Utilisateur {
        motPasse.clear()
        val queue = Volley.newRequestQueue(ctx)

        val promesse: RequestFuture<String> = RequestFuture.newFuture()
        var url : String = urlSource.toString()+"utilisateur"

        val requête = StringRequest(Request.Method.GET, url, promesse, promesse)

        queue.add(requête)

        try{

            var liste:MutableList<Utilisateur> = réponseJSONToUtilisateurs(JsonReader(StringReader(promesse.get())))
            var utilisateur = Utilisateur("","","")
            motPasse += listeMotPasseTemporaire
            liste += listeUtilisateurTemporaire
            for(i in 0 until liste.size){
                if (liste[i].email == email && motPasse[i] == mdp){
                    utilisateur = liste[i]
                }
            }
            return utilisateur
        }
        catch (e: InterruptedException){
            e.printStackTrace()
            return Utilisateur("","","")
        }
        catch (e: ExecutionException){
            throw AccèsRessourcesException(e)
        }
    }

    override fun ajouterUtilisateur(utilisateur: Utilisateur, mdp: String) {
        Log.d("test", "rentre")
        listeUtilisateurTemporaire.add(utilisateur)
        listeMotPasseTemporaire.add(mdp)
    }

    private fun réponseJSONToRecettes(jsonReader: JsonReader): MutableList<Recette>{
        var recettes = mutableListOf<Recette>()
        Log.d("url", "test1")
        jsonReader.beginObject()
        Log.d("url", "test2")
        while (jsonReader.hasNext()){
            val clé = jsonReader.nextName()

            when(clé){
                "recettes" -> {
                    jsonReader.beginArray()

                    while (jsonReader.hasNext()){
                        recettes.add(recetteJSONToRecette(jsonReader))
                    }

                    jsonReader.endArray()
                }

                else -> {
                    jsonReader.skipValue()
                }
            }
        }

        jsonReader.endObject()

        return recettes
    }
    private fun réponseJSONToUtilisateurs(jsonReader: JsonReader): MutableList<Utilisateur>{
        var recettes = mutableListOf<Utilisateur>()

        jsonReader.beginObject()
        while (jsonReader.hasNext()){
            val clé = jsonReader.nextName()

            when(clé){
                "utilisateurs" -> {
                    jsonReader.beginArray()

                    while (jsonReader.hasNext()){
                        recettes.add(utilisateurPourRecette(jsonReader))
                    }

                    jsonReader.endArray()
                }

                else -> {
                    jsonReader.skipValue()
                }
            }
        }

        jsonReader.endObject()

        return recettes
    }

    private fun recetteJSONToRecette (jsonReader: JsonReader): Recette{
        var nom : String? = null
        var durée : String? = null
        var utilisateur : Utilisateur? = null
        var ingrédients = mutableListOf<String>()
        var nombre_portion : Int? = null
        var étapes = mutableListOf<String>()


        jsonReader.beginObject()
        while (jsonReader.hasNext()){

            val  clé = jsonReader.nextName()

            if (clé == "nom"){
                nom = jsonReader.nextString()
            }
            if (clé == "durée"){
                durée = jsonReader.nextString()
            }
            if (clé == "nombre_portion"){
                nombre_portion = jsonReader.nextInt()
            }
            if (clé == "étapes"){
                jsonReader.beginArray()
                while (jsonReader.hasNext()) {
                    var etape = jsonReader.nextString()

                    étapes.add(etape.toString())
                }
                jsonReader.endArray()
            }
            if (clé == "ingrédients"){

                jsonReader.beginArray()
                while (jsonReader.hasNext()) {
                    var ingrédient = jsonReader.nextString()
                    ingrédients.add(ingrédient.toString())
                }
                jsonReader.endArray()
            }
            if (clé == "utilisateur"){
                utilisateur = utilisateurPourRecette(jsonReader)
            }

        }
        jsonReader.endObject()
        var utilisateurNull = Utilisateur("Anonyme","Anonyme","Anonyme")
        var listeNull = mutableListOf("Anonyme")
        return Recette(nom?: "Anonyme",durée?: "Anonyme", utilisateur?: utilisateurNull, ingrédients ?: listeNull, nombre_portion?: 0, étapes?: listeNull)
    }

    private fun utilisateurPourRecette (jsonReader: JsonReader): Utilisateur{
        var nom : String? = null
        var prénom : String? = null
        var email : String? = null
        Log.d("entre", "1")
        jsonReader.beginObject()
        while (jsonReader.hasNext()) {
            val  clé = jsonReader.nextName()

            if (clé == "nom"){

                nom = jsonReader.nextString()
            }
            if (clé == "prénom"){
                prénom = jsonReader.nextString()
            }
            if (clé == "email"){
                email = jsonReader.nextString()
            }
            if (clé == "mdp"){
                motPasse.add(jsonReader.nextString())
            }

        }
        jsonReader.endObject()
        Log.d("Sortie", "2")

        return Utilisateur(nom?: "Anonyme", prénom?: "Anonyme", email?: "Anonyme")
    }


}