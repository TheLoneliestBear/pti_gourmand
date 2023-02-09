package ptitgourmand.présentation.créationUtilisateur

import ptitgourmand.domaine.entité.Utilisateur

interface ContratVuePrésentateurCréationUtilisateur {

    interface IPrésentateurNouveauUtilisateur {
        fun traiterRequêteConnexion()
        fun ajouterUtilisateur(unUtilisateur : Utilisateur, mdp: String)
        fun utilisateurDejainscrit(email : String) : Boolean
    }

    interface IVueNouveauUtilisateur {
        fun naviguerVersConnexion()
        fun ajoutUtilisateur(utilisateur: Utilisateur, mdp:String)
    }
}