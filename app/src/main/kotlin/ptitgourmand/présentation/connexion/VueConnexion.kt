package ptitgourmand.présentation.connexion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ptitgourmand.R
import ptitgourmand.présentation.connexion.ContratVuePrésentateurConnexion.*

class VueConnexion : Fragment(), IVueConnexion {

    lateinit var navController : NavController
    private lateinit var btnDecouverte : Button
    private lateinit var présentateur : IPrésentateurConnexion
    private lateinit var mot_passe : EditText
    private lateinit var courriel : EditText
    private lateinit var nouveauCompte : TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vue = inflater.inflate( R.layout.fragment_connexion, container, false )
        présentateur = PrésentateurConnexion( this )

        mot_passe = vue.findViewById( R.id.txt_mot_passe )
        courriel = vue.findViewById( R.id.txt_courriel )
        btnDecouverte = vue.findViewById( R.id.btn_connexion )
        nouveauCompte = vue.findViewById( R.id.txt_nouveau_compte)
        attacherÉcouteurDécouverte( )
        attacherÉcouteurInscription( )

        return vue
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun naviguerVersPageDecouverte() {
        navController.navigate( R.id.action_connexion_to_découverte )
    }

    override fun naviguerVersPageInscription() {
        navController.navigate( R.id.action_connexion_to_inscription)
    }

    private fun attacherÉcouteurDécouverte( ) {
        btnDecouverte.setOnClickListener {
            présentateur?.traiterRequêteDécouverte( courriel.text.toString(), mot_passe.text.toString() )
        }
    }

    private fun attacherÉcouteurInscription( ) {
        nouveauCompte.setOnClickListener {
            présentateur.traiterRequêteInscription()
        }
    }

}