package ptitgourmand.présentation.découverte

import ptitgourmand.domaine.entité.Recette

interface ContratVuePrésentateurDécouverte {

    interface IPrésentateurDécouverte{
        fun obtenirLesRecettes(): MutableList<Recette>
        fun traiterRequêteAfficherPageCréation()
        fun traiterDébuter();
        fun traiterRequêteVisualiserUneRecette(index: Int)
    }

    interface IVueDécouverte{
        fun afficherRecette(liste: MutableList<Recette>)
        fun naviguerVersRecette()
        fun naviguerVersAjouter()
    }
}