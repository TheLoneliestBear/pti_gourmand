package ptitgourmand.présentation.connexion

import ptitgourmand.domaine.entité.Utilisateur

interface ContratVuePrésentateurConnexion {
    interface IPrésentateurConnexion {
        fun obtenirUnUtilisateur(courriel : String, motPasse : String) : Boolean
        fun traiterRequêteDécouverte(courriel : String, motPasse : String)
        fun traiterRequêteInscription()
    }

    interface IVueConnexion {
        fun naviguerVersPageDecouverte()
        fun naviguerVersPageInscription()
    }
}