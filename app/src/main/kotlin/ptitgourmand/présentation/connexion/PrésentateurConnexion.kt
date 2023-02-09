package ptitgourmand.présentation.connexion

import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ptitgourmand.accèsAuxDonnées.AccèsRessourcesException
import ptitgourmand.présentation.Modèle
import ptitgourmand.présentation.connexion.ContratVuePrésentateurConnexion.*
import ptitgourmand.présentation.modèle


class PrésentateurConnexion (var vue: IVueConnexion = VueConnexion(), val _modèle : Modèle = modèle) : IPrésentateurConnexion {

    override fun obtenirUnUtilisateur(email: String, mdp: String): Boolean {
        var présent = false
        //modèle.testRequete()
        var utilisateur = modèle.rechercheUtilisateur(email, mdp)
        Log.d("url", "apres")
        //for(utilisateur in modèle.obtenirToutesLesRecettes())
        //    if(utilisateur.utilisateur.email == email && utilisateur.utilisateur.motPasse == mdp) présent = true

        if (utilisateur.email != "")
            présent = true

        return présent;


        return présent
    }

    override fun traiterRequêteDécouverte(email: String, mdp: String) {
        GlobalScope.launch(Dispatchers.Main) {

            var job = async(Dispatchers.IO) {
                _modèle.rechercheUtilisateur(email, mdp)
            }
            try {
                var utilisateur = job.await()
                if (utilisateur.email != "") {
                    vue.naviguerVersPageDecouverte()
                }
            } catch (e: AccèsRessourcesException) {
                Log.d("Erreur", "erreur")
            }

        }
    }

    override fun traiterRequêteInscription() {
        vue.naviguerVersPageInscription()
    }

}