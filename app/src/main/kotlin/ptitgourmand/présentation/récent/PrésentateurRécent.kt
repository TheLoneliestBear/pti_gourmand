package ptitgourmand.présentation.récent
/*
import ptitgourmand.domaine.entité.Recette
import ptitgourmand.présentation.Modèle
import ptitgourmand.présentation.récent.ContratVuePrésentateurRécent

class PrésentateurRécent (var modèle: Modèle,
                          var vue : ContratVuePrésentateurRécent.IVueRécent = VueRécent()
) : ContratVuePrésentateurRécent.IPrésentateurRécent {
    override fun obtenirLesRecettes(): Array<Recette> {
        var recettesMemoire : Array<Recette> = emptyArray()
        val lesRecettes : MutableList<Recette> = modèle.obtenirToutesLesRecettes()
        recettesMemoire += lesRecettes[2]
        return recettesMemoire
    }

    override fun traiterRequêteVisualiserUneRecette(index : Int) {
        //modèle.recetteIndex = index
        vue.naviguerVersRecette()

    }

}*/