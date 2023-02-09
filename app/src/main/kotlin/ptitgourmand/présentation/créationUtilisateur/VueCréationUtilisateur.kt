package ptitgourmand.présentation.créationUtilisateur

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ptitgourmand.R
import ptitgourmand.domaine.entité.Utilisateur
import ptitgourmand.présentation.GestionnairePréférences
import ptitgourmand.présentation.créationUtilisateur.ContratVuePrésentateurCréationUtilisateur.*

class VueCréationUtilisateur  : Fragment(), IVueNouveauUtilisateur {

    lateinit var navController : NavController
    private lateinit var txt_nom : EditText
    private lateinit var txt_prenom : EditText
    private lateinit var txt_courriel : EditText
    private lateinit var txt_mdp : EditText
    private lateinit var btn_confirmation : Button

    private lateinit var présentateur : PrésentateurCréationUtilisateur
    lateinit var gestionnaire: GestionnairePréférences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vue = inflater.inflate(R.layout.fragment_nouveau_utilisateur,container,false)
        présentateur = PrésentateurCréationUtilisateur(this)
        gestionnaire = GestionnairePréférences(this.context as Context)

        txt_prenom = vue.findViewById(R.id.txt_prenom)
        txt_nom = vue.findViewById(R.id.txt_mdp)
        txt_courriel = vue.findViewById(R.id.txt_courriel)
        txt_mdp = vue.findViewById(R.id.txt_mdp)
        btn_confirmation = vue.findViewById(R.id.btn_confirmation)

        attacherÉcouteursConnexion( )

        return vue
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun attacherÉcouteursConnexion( ) {
        btn_confirmation.setOnClickListener {
            if( txt_prenom.length()!= 0 && txt_nom.length()!=0 && txt_courriel.length()!=0 && txt_mdp.length()!=0 )
                présentateur.traiterRequêteConnexion()
        }
    }

    override fun naviguerVersConnexion() {

        ajoutUtilisateur(Utilisateur( txt_nom.text.toString(), txt_prenom.text.toString(), txt_courriel.text.toString() ),txt_mdp.text.toString())
        navController.navigate(R.id.action_vueInscription_to_connexion)

    }



    override fun ajoutUtilisateur(utilisateur: Utilisateur,mdp :String) {
        présentateur.ajouterUtilisateur(utilisateur, mdp)
    }

}