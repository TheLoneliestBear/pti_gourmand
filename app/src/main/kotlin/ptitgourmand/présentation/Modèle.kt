package ptitgourmand.présentation

import ptitgourmand.accèsAuxDonnées.ISourceDeDonnées
import ptitgourmand.accèsAuxDonnées.SourceDeDonnéesBidon
import ptitgourmand.domaine.entité.Recette
import ptitgourmand.domaine.entité.Utilisateur
import ptitgourmand.domaine.interacteur.AjoutRecette
import ptitgourmand.domaine.interacteur.AjoutUtilisateur
import ptitgourmand.domaine.interacteur.ObtenirRecette


class Modèle (var sourceDeDonnées: ISourceDeDonnées = SourceDeDonnéesBidon()){

    var recetteIndex : Int = -1
    lateinit var utilisateur : Utilisateur

    fun obtenirToutesLesRecettes() : MutableList<Recette>{
        return ObtenirRecette(sourceDeDonnées).obtenirToutesLesRecettes()
    }

    fun obtenirUneRecette(indice : Int) : Recette{
        return ObtenirRecette(sourceDeDonnées).obtenirUneRecette(indice)
    }

    fun ajouterRecette(recette: Recette){
        AjoutRecette(sourceDeDonnées).ajouterRecette(recette)

    }
    fun ajouterUtilisateur(utilisateur: Utilisateur,mdp: String){
        AjoutUtilisateur(sourceDeDonnées).ajouterUtilisateur(utilisateur,mdp)

    }

    fun rechercheUtilisateur(email: String, mdp: String) : Utilisateur {
        utilisateur = sourceDeDonnées.rechercheUtilisateur(email,mdp)
        return utilisateur
    }
}

val modèle =Modèle();
