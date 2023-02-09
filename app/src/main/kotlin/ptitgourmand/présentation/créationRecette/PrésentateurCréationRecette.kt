package ptitgourmand.présentation.créationRecette

import android.util.Log
import kotlinx.coroutines.*
import ptitgourmand.accèsAuxDonnées.AccèsRessourcesException
import ptitgourmand.domaine.entité.Recette
import ptitgourmand.présentation.Modèle
import ptitgourmand.présentation.créationRecette.ContratVuePrésentateurCréationRecette.IVueCréationRecette
import ptitgourmand.présentation.créationRecette.ContratVuePrésentateurCréationRecette.IPrésentateurCréationRecette
import ptitgourmand.présentation.modèle

class PrésentateurCréationRecette(var vue : IVueCréationRecette = VueCréationRecette(),var _modèle : Modèle = modèle) : IPrésentateurCréationRecette {



    override fun ajouterRecette(laRecette: Recette) {
        _modèle.ajouterRecette(laRecette)
    }

    override fun traiterRequêteVisualiserDécouverte() {
        vue.naviguerVersDécouverte()
    }

    override fun traiterAjoutRecette() {

        GlobalScope.launch(Dispatchers.Main){

            var job = async(SupervisorJob() + Dispatchers.IO){
                vue.enregistrerUneRecette()
            }

            try {
                job.await()
            } catch (e: AccèsRessourcesException){
                Log.d("Erreur", "impossible d'enregistrer la recette")
            }
        }
    }
}