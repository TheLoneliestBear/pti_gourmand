package ptitgourmand.accèsAuxDonnées

import android.util.Log
import ptitgourmand.domaine.entité.Recette
import ptitgourmand.domaine.entité.Utilisateur

class SourceDeDonnéesBidon : ISourceDeDonnées {

    var utilisateur = Utilisateur("Jean", "Guy", "jean.guy@hotmail.com")
    var ingrédients = listOf<String>("oeuf", "farine")
    var étapes = listOf<String>("1- Ajouter les ingrédients", "2- Mélanger")

    var recette1 = Recette("Gateau", "2h30", utilisateur,
        ingrédients as MutableList<String>, 8, étapes as MutableList<String>
    )

    var utilisateur2 = Utilisateur("Lazarre","Ricardo","therealricardo@gmail.com")
    var ingrédients2 = listOf<String>("Pomme","Orange","Banane")
    var étapes2 = listOf<String>("Trancher les pommes","Trancher les Orange","Trancher les Banane")

    var recette2 = Recette("Salade de fruit","10 minutes", utilisateur2,
        ingrédients2 as MutableList<String>, 8, étapes2 as MutableList<String>
    )

    var utilisateur3 = Utilisateur("Beaubrun","Michel","michelbeaubrun@gmail.com")
    var ingrédients3 = listOf<String>("Patate","Ail","Sel")
    var étapes3 = listOf<String>("Trancher les patate","Ajouter l'ail","Ajouter du sel")

    var recette3 = Recette("Frites faites maison","50 minutes", utilisateur3,
        ingrédients3 as MutableList<String>, 4, étapes3 as MutableList<String>
    )


    var ingrédients4 = listOf<String>("Patate frite","Sauce","Fromage")
    var étapes4 = listOf<String>("Frire les patate","Tout mélanger","Manger")

    var recette4 = Recette("Poutine","50 minutes", utilisateur2,
        ingrédients4 as MutableList<String>, 4, étapes4 as MutableList<String>
    )

    var listeRecette = mutableListOf<Recette>(recette1,recette2,recette3, recette4)
    var listeUtilisateur = mutableListOf<Utilisateur>(utilisateur,utilisateur2,utilisateur3)

    override fun obtenirToutesLesRecettes() : MutableList<Recette> {
        Thread.sleep(5000)
        return listeRecette
    }


    override fun ajouterRecette(recette: Recette) {
        listeRecette.add(recette)

    }

    override fun obtenirUneRecette(indice: Int): Recette {
        return listeRecette.get(indice)
    }

    override fun rechercheUtilisateur(email: String, mdp: String): Utilisateur {
        var utilisateur = Utilisateur("", "", "")
        for (i in 0 until listeUtilisateur.size){
            if(listeUtilisateur[i].email == email){
                utilisateur = listeUtilisateur[i]
            }
        }
        return utilisateur
    }

    override fun ajouterUtilisateur(utilisateur: Utilisateur, mdp: String) {
        listeUtilisateur.add(utilisateur)
    }
}