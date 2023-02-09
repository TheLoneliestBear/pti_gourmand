package ptitgourmand.présentation.découverte

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ptitgourmand.R
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ptitgourmand.domaine.entité.Recette
import ptitgourmand.présentation.GestionnairePréférences
import ptitgourmand.présentation.découverte.ContratVuePrésentateurDécouverte.IVueDécouverte
import ptitgourmand.présentation.découverte.ContratVuePrésentateurDécouverte.IPrésentateurDécouverte
import ptitgourmand.présentation.modèle

class VueDécouverte : Fragment(), IVueDécouverte {

    lateinit var navController : NavController

    lateinit var btnVisualiseRecette : Button
    lateinit var btnPageAjouter : Button
    private lateinit var btnRéinitialiser : Button

    private lateinit var adapteur : AdapteurRecycler
    private lateinit var recyclerView: RecyclerView

    private lateinit var présentateur : IPrésentateurDécouverte

    lateinit var gestionnaire: GestionnairePréférences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vue = inflater.inflate(R.layout.fragment_page_decouverte,container,false)
        présentateur = PrésentateurDécouverte(this)
        gestionnaire = GestionnairePréférences(this.context as Context)


        btnVisualiseRecette = vue.findViewById<Button>(R.id.btnMesRecettes)
        btnPageAjouter = vue.findViewById<Button>(R.id.btnAjouter)

        attacherÉcouteursNavigation( )


        return vue
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.rv_tableau)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        présentateur.traiterDébuter()

        navController = Navigation.findNavController(view)
    }

    private fun attacherÉcouteursNavigation( ) {
        btnVisualiseRecette.setOnClickListener {
            lifecycleScope.launch {
                val index = gestionnaire.preferencesFlow.first()
                if (index!=-1) {
                    présentateur.traiterRequêteVisualiserUneRecette(index)

                } else {
                    Toast.makeText(context, "aucune recette en mémoire", Toast.LENGTH_SHORT).show()
                }
            }

        }


        btnPageAjouter.setOnClickListener(){
            présentateur.traiterRequêteAfficherPageCréation()
        }


    }

    override fun naviguerVersRecette() {
        navController.navigate( R.id.action_découverte_to_descriptionUneRecette )
    }


    override fun naviguerVersAjouter() {
        navController.navigate(R.id.action_pageDecouverte_to_vueCréationRecette)
    }

    override fun afficherRecette(liste: MutableList<Recette>) {
        adapteur = AdapteurRecycler(liste)
        recyclerView.adapter = adapteur
        adapteur.setOnItemClickListener(object : AdapteurRecycler.onItemClickListener{
            override fun onItemClick(position: Int) {
                présentateur.traiterRequêteVisualiserUneRecette(position)
            }
        })


    }

}