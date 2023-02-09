package ptitgourmand.domaine.interacteur

import ptitgourmand.accèsAuxDonnées.ISourceDeDonnées
import ptitgourmand.domaine.entité.Recette

class AjoutRecette(var sourceDeDonnées: ISourceDeDonnées) {

    fun ajouterRecette(recette: Recette){
        sourceDeDonnées.ajouterRecette(recette)
    }
}