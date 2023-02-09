package ptitgourmand.présentation.récent
/*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ptitgourmand.R
import ptitgourmand.domaine.entité.Recette
import ptitgourmand.présentation.Modèle
import ptitgourmand.présentation.découverte.AdapteurRecycler
import ptitgourmand.présentation.découverte.ContratVuePrésentateurDécouverte
import ptitgourmand.présentation.récent.AdapteurRecyclerRécent
import ptitgourmand.présentation.récent.ContratVuePrésentateurRécent

class VueRécent : Fragment(), ContratVuePrésentateurRécent.IVueRécent {

    lateinit var navController : NavController
    private lateinit var adapteur : AdapteurRecyclerRécent
    private lateinit var recyclerView: RecyclerView
    private lateinit var listeRecettes : Array<Recette>
    private lateinit var présentateur : ContratVuePrésentateurRécent.IPrésentateurRécent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vue = inflater.inflate(R.layout.fragment_page_recent, container, false)
        présentateur = PrésentateurRécent(this)

        return vue
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        afficherRecette()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.rv_tableau)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapteur = AdapteurRecyclerRécent(listeRecettes)
        recyclerView.adapter = adapteur
        navController = Navigation.findNavController(view)
        adapteur.setOnItemClickListener(object : AdapteurRecyclerRécent.onItemClickListener{
            override fun onItemClick(position: Int) {
                Modèle.recetteIndex = position
                naviguerVersRecette()
            }
        })
    }

    override fun naviguerVersRecette() {
        navController.navigate( R.id.action_vueRécent_to_layoutVisualiserRecette )
    }

    override fun afficherRecette() {
        listeRecettes = présentateur.obtenirLesRecettes()
    }

}*/