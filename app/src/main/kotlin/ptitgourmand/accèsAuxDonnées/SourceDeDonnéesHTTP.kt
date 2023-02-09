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


class SourceDeDonnéesHTTP (var ctx: Context, var  urlSource: URL):ISourceDeDonnées{

    override fun obtenirToutesLesRecettes(): MutableList<Recette> {
        val queue = Volley.newRequestQueue(ctx)

        val promesse: RequestFuture<String> = RequestFuture.newFuture()

        var url : String = urlSource.toString()+"Recette"

        val requête = StringRequest(Request.Method.GET, url, promesse, promesse)

        queue.add(requête)

        try{

            var liste:MutableList<Recette> = réponseJSONToRecettes(JsonReader(StringReader(promesse.get())))

            return liste
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
            return recetteJSONToRecette(JsonReader(StringReader(promesse.get())))
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

        val queue = Volley.newRequestQueue(ctx)

        val promesse: RequestFuture<String> = RequestFuture.newFuture()

        var url : String = urlSource.toString()+"Recette/"+recette

        val requête = StringRequest(Request.Method.POST, url, promesse, promesse)

        queue.add(requête)
    }


    override fun rechercheUtilisateur(email: String, mdp: String): Utilisateur {

     val queue = Volley.newRequestQueue(ctx)

        val promesse: RequestFuture<String> = RequestFuture.newFuture()

        var url : String = urlSource.toString()+"Utilisateur/"+email



        val requête = StringRequest(Request.Method.GET, url, promesse, promesse)

        queue.add(requête)

        try{

            return utilisateurPourRecette(JsonReader(StringReader(promesse.get())))
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
        val queue = Volley.newRequestQueue(ctx)

        val promesse: RequestFuture<String> = RequestFuture.newFuture()

        var url : String = urlSource.toString()+"Recette/"+utilisateur+"/"+mdp

        val requête = StringRequest(Request.Method.POST, url, promesse, promesse)

        queue.add(requête)

    }

    private fun réponseJSONToRecettes(jsonReader: JsonReader): MutableList<Recette>{
        var recettes = mutableListOf<Recette>()

        jsonReader.beginObject()

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

        }
        jsonReader.endObject()


        return Utilisateur(nom?: "Anonyme", prénom?: "Anonyme", email?: "Anonyme")
    }

}