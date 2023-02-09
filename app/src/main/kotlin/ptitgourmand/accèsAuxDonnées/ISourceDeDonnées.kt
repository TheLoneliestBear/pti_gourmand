package ptitgourmand.accèsAuxDonnées

import ptitgourmand.domaine.entité.Recette
import ptitgourmand.domaine.entité.Utilisateur

interface ISourceDeDonnées {

    fun obtenirToutesLesRecettes() : MutableList<Recette>

    fun obtenirUneRecette(indice: Int): Recette

    fun ajouterRecette(recette: Recette)

    fun rechercheUtilisateur(email: String, mdp: String): Utilisateur

    fun ajouterUtilisateur(utilisateur: Utilisateur, mdp:String)


}