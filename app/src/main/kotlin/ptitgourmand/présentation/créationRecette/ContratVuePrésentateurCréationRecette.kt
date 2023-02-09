package ptitgourmand.présentation.créationRecette

import ptitgourmand.domaine.entité.Recette

interface ContratVuePrésentateurCréationRecette {

    interface IPrésentateurCréationRecette {
        fun ajouterRecette(laRecette: Recette)
        fun traiterRequêteVisualiserDécouverte()
        fun traiterAjoutRecette()

    }

    interface IVueCréationRecette {
        fun enregistrerUneRecette()
        fun naviguerVersDécouverte()

    }
}