package ptitgourmand.domaine.interacteur

import ptitgourmand.accèsAuxDonnées.ISourceDeDonnées
import ptitgourmand.domaine.entité.Recette

class ObtenirRecette (var sourceDeDonnées: ISourceDeDonnées){

    fun obtenirToutesLesRecettes() : MutableList<Recette>{
        return sourceDeDonnées.obtenirToutesLesRecettes()
    }

    fun obtenirUneRecette(indice : Int) : Recette{
        return sourceDeDonnées.obtenirUneRecette(indice)
    }
}