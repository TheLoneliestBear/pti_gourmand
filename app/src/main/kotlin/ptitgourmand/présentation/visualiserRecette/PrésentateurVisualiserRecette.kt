package ptitgourmand.présentation.visualiserRecette

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import ptitgourmand.présentation.visualiserRecette.ContratVuePrésentateurVisualiserRecette.*
import ptitgourmand.présentation.Modèle
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import ptitgourmand.domaine.entité.Recette
import ptitgourmand.présentation.GestionnairePréférences
import ptitgourmand.présentation.modèle

class PrésentateurVisualiserRecette(var vue : IVueVisualiserRecette ) : IPrésentateurVisualiserRecette {


    override fun traiterDébut() {
        // [ GUI ]
        GlobalScope.launch(Dispatchers.Main) {

            // [ IO ]
            var job = async(Dispatchers.IO) {
                // Opération longue
                modèle.obtenirUneRecette(modèle.recetteIndex)
            }

            //en attendant la fin de la tâche,
            //l'exécution de cette coroutine est suspendue
            var recette = job.await()

            //lorsque la tâche est terminée, la coroutine
            //reprend et on met à jour l'interface utilisateur

            //recette  = r[index!!]
            vue.viderLesChamps()
            vue.chargerDonnées(recette.nom, recette.utilisateur.nom, recette.ingrédient, recette.durée, recette.étapes)
        }

        vue.viderLesChamps()
        vue.afficherIndicateurDeChargement()
    }

}