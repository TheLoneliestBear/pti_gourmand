package ptitgourmand.présentation.créationUtilisateur

import ptitgourmand.domaine.entité.Utilisateur
import ptitgourmand.présentation.créationUtilisateur.ContratVuePrésentateurCréationUtilisateur.*
import ptitgourmand.présentation.modèle

class PrésentateurCréationUtilisateur (var vue : IVueNouveauUtilisateur = VueCréationUtilisateur()) : IPrésentateurNouveauUtilisateur {

    override fun traiterRequêteConnexion() {
        vue.naviguerVersConnexion()
    }

    override fun ajouterUtilisateur(utilisateur: Utilisateur,mdp :String) {
        modèle.ajouterUtilisateur(utilisateur,mdp)
    }

    override fun utilisateurDejainscrit(courriel : String) : Boolean {
        var présent = false
        if(modèle.rechercheUtilisateur(courriel,"").email.length !=0) présent = true
        return présent
    }
}