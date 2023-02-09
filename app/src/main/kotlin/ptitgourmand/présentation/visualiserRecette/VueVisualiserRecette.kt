package ptitgourmand.présentation.visualiserRecette

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ptitgourmand.R
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ptitgourmand.présentation.GestionnairePréférences
import ptitgourmand.présentation.Modèle
import ptitgourmand.présentation.modèle
import ptitgourmand.présentation.visualiserRecette.ContratVuePrésentateurVisualiserRecette.*

class VueVisualiserRecette : Fragment(), IVueVisualiserRecette {

    lateinit var navController : NavController
    lateinit var présentateur : IPrésentateurVisualiserRecette
    lateinit var txtIngrédients : TextView
    lateinit var txtDurée : TextView
    lateinit var tempÉtapes : TextView
    lateinit var txtNomRecette : TextView
    lateinit var txtNomUtilisateur : TextView
    lateinit var imgRecette : ImageView
    lateinit var gestionnaire: GestionnairePréférences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vue = inflater.inflate(R.layout.fragment_visualiser_recette, container, false)
        présentateur = PrésentateurVisualiserRecette(this)
        gestionnaire = GestionnairePréférences(this.context as Context)

        return vue
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtIngrédients = view.findViewById(R.id.champListeIngredients)
        txtDurée = view.findViewById(R.id.txtDurée)
        tempÉtapes = view.findViewById(R.id.tempÉtapes)
        txtNomRecette = view.findViewById(R.id.txtNomRecette)
        txtNomUtilisateur = view.findViewById(R.id.txtUtilisateur)
        imgRecette = view.findViewById(R.id.imgRecette)

        viderLesChamps()

        navController = Navigation.findNavController(view)
        présentateur.traiterDébut()
        lifecycleScope.launch {
            save(modèle.recetteIndex)
        }

    }

    override fun afficherIndicateurDeChargement(){
        tempÉtapes.text = "\n\n\n\n_____________\n____________________________\n\n" +
                "\tUn instant SVP... Le chargement des données est en cours\n____________________________\n" +
                "_____________"
        imgRecette.setImageResource(R.drawable.loader)
    }

    override fun chargerDonnées( nomRecette: String, nomUtilisateur: String, listIngrédient: MutableList<String>, durée: String, listÉtapes: MutableList<String> ) {
        var i = 1
        txtIngrédients.text = "$listIngrédient"
        txtDurée.text = "$durée"
        listÉtapes.forEach {
            tempÉtapes.text = tempÉtapes.text.toString() + "\t$i - $it\n___________\n____\n\n"
            i++
        }
        txtNomRecette.text = "$nomRecette"
        txtNomUtilisateur.text = "$nomUtilisateur"
        imgRecette.setImageResource(R.drawable.defaultrecette)
    }

    override fun viderLesChamps(){
        txtIngrédients.text = ""
        txtDurée.text = ""
        tempÉtapes.text = ""
        txtNomRecette.text = ""
        txtNomUtilisateur.text = ""
    }

    private suspend fun save(value: Int){
        gestionnaire.updateDataStoreId(value)
    }
}