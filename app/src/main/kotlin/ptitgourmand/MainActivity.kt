package ptitgourmand


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.view.OneShotPreDrawListener.add
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ptitgourmand.R

import ptitgourmand.accèsAuxDonnées.AccèsRessourcesException
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_main.*
import ptitgourmand.accèsAuxDonnées.SourceDeDonnéesHTTP
import ptitgourmand.accèsAuxDonnées.SourceDeDonnéesPostman
import ptitgourmand.présentation.Modèle
import ptitgourmand.présentation.créationRecette.VueCréationRecette
import ptitgourmand.présentation.découverte.PrésentateurDécouverte
import ptitgourmand.présentation.découverte.VueDécouverte
import ptitgourmand.présentation.modèle
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.ExecutionException

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    var context: Context = this@MainActivity

    private lateinit var curFragment: Fragment

    private var userConnected: Boolean = false
    //lateinit var fragmentManager : FragmentManager

    lateinit var fragmentManager : FragmentManager


    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController



        modèle.sourceDeDonnées = SourceDeDonnéesPostman(this, URL("https://80aa5b2c-cc37-435a-a610-fd355e4f0c6f.mock.pstmn.io/"))

    }


/*
    fun activerNavBar(){
        var bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottom_navigation.setOnNavigationItemReselectedListener {
            when (it.itemId){
                R.id.ic_home -> navController.navigate( R.id.action_global_pageDecouverte )
                R.id.ic_recent -> navController.navigate( R.id.action_global_vueRécent )
                //R.id.ic_deco ->
            }
            true
        }
    }
    */




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            val inputStream: InputStream
            if (resultCode == Activity.RESULT_OK) {
                try {
                    contentResolver.openInputStream(result.uri)?.let {
                        inputStream = it
                        result.uri?.let { uri ->
                            val fragment =
                                nav_host_fragment.childFragmentManager.fragments[0] as VueCréationRecette

                            fragment.addPhoto(uri)
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, R.string.unable_to_open_image, Toast.LENGTH_SHORT).show()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}


