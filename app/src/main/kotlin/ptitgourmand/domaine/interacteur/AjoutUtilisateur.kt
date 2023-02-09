package ptitgourmand.domaine.interacteur

import ptitgourmand.accèsAuxDonnées.ISourceDeDonnées
import ptitgourmand.domaine.entité.Utilisateur

class AjoutUtilisateur(var sourceDeDonnées: ISourceDeDonnées) {

    fun ajouterUtilisateur(utilisateur: Utilisateur, mdp:String){
        sourceDeDonnées.ajouterUtilisateur(utilisateur,mdp)
    }

}