package ptitgourmand.présentation.visualiserRecette



interface ContratVuePrésentateurVisualiserRecette {

    interface IVueVisualiserRecette {
        fun viderLesChamps()
        fun afficherIndicateurDeChargement()
        fun chargerDonnées( nomRecette: String, nomUtilisateur: String, listIngrédient: MutableList<String>, durée: String, listÉtapes: MutableList<String> )
    }

    interface IPrésentateurVisualiserRecette {
        fun traiterDébut()
    }
}