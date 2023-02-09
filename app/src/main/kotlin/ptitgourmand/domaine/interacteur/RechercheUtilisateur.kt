package ptitgourmand.domaine.interacteur

import ptitgourmand.accèsAuxDonnées.ISourceDeDonnées
import ptitgourmand.domaine.entité.Utilisateur

class RechercheUtilisateur(var sourceDeDonnées: ISourceDeDonnées) {

    fun rechercheUtililisateur(email : String, mdp: String): Utilisateur{
        return sourceDeDonnées.rechercheUtilisateur(email, mdp)
    }
}