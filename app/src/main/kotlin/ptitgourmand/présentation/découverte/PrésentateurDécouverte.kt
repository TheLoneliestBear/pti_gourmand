package ptitgourmand.présentation.découverte

import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ptitgourmand.accèsAuxDonnées.AccèsRessourcesException
import ptitgourmand.domaine.entité.Recette
import ptitgourmand.domaine.entité.Utilisateur
import ptitgourmand.présentation.Modèle
import ptitgourmand.présentation.découverte.ContratVuePrésentateurDécouverte.IVueDécouverte
import ptitgourmand.présentation.découverte.ContratVuePrésentateurDécouverte.IPrésentateurDécouverte
import ptitgourmand.présentation.modèle

class PrésentateurDécouverte (var vue : IVueDécouverte = VueDécouverte(), val _modèle : Modèle = modèle) : IPrésentateurDécouverte{
    override fun obtenirLesRecettes(): MutableList<Recette> {

        var listeRecette = _modèle.obtenirToutesLesRecettes()
        return listeRecette
    }

    override fun traiterRequêteVisualiserUneRecette(index : Int) {
        _modèle.recetteIndex = index
        vue.naviguerVersRecette()
    }

    override fun traiterRequêteAfficherPageCréation() {
        vue.naviguerVersAjouter()
    }


    override fun traiterDébuter() {

        GlobalScope.launch(Dispatchers.Main) {

            var job = async(Dispatchers.IO) {
                _modèle.obtenirToutesLesRecettes()
            }

            try{
                var recettes = job.await()
                vue.afficherRecette(recettes)
            }
            catch(e: AccèsRessourcesException){
                Log.d("Erreur", "erreur")
            }

        }


    }
}