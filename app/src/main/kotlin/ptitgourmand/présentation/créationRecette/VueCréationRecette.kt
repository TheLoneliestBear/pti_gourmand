package ptitgourmand.présentation.créationRecette

import android.app.Activity
import android.util.Base64
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.provider.FontsContractCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.ptitgourmand.R
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import ptitgourmand.MainActivity
import ptitgourmand.domaine.entité.Recette
import ptitgourmand.domaine.entité.Utilisateur
import ptitgourmand.présentation.créationRecette.ContratVuePrésentateurCréationRecette.IVueCréationRecette
import ptitgourmand.présentation.créationRecette.ContratVuePrésentateurCréationRecette.IPrésentateurCréationRecette
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class VueCréationRecette : Fragment(), IVueCréationRecette{
    private lateinit var EditNomRecette : EditText
    private lateinit var EditDuréeRecette : EditText
    private lateinit var EditPremierIngrédientRecette : EditText
    private lateinit var EditPremièreÉtapeRecette : EditText
    private var PhotoEncodée : String? = null
    private lateinit var présentateur : IPrésentateurCréationRecette

    private lateinit var ImageRecette : ImageView

    private val PRENDRE_IMAGE = 1
    private val PRENDRE_PHOTO = 2
    private lateinit var Médias : Button


    private lateinit var fichierPhoto : File
     var CheminPhotoActuel : String? = null


    val REQUEST_IMAGE_CAPTURE = 100

    private lateinit var btnAjouter : Button
    private lateinit var btnAnnuler : Button
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vue = inflater.inflate(R.layout.fragment_page_creation_recette,container,false)
        présentateur = PrésentateurCréationRecette(this)


        EditNomRecette = vue.findViewById<EditText>(R.id.nomRecetteForm)
        EditDuréeRecette = vue.findViewById<EditText>(R.id.duréeRecetteForm)
        EditPremierIngrédientRecette = vue.findViewById<EditText>(R.id.PremierIngrédientRecetteForm)
        EditPremièreÉtapeRecette = vue.findViewById<EditText>(R.id.PremièreÉtapeRecetteForm)

        EditNomRecette.addTextChangedListener(VérificateurDeChamps)
        EditDuréeRecette.addTextChangedListener(VérificateurDeChamps)
        EditPremierIngrédientRecette.addTextChangedListener(VérificateurDeChamps)
        EditPremièreÉtapeRecette.addTextChangedListener(VérificateurDeChamps)

        btnAjouter = vue.findViewById<Button>(R.id.Créer)
        btnAnnuler = vue.findViewById<Button>(R.id.Annuler)
        Médias = vue.findViewById<Button>(R.id.btnGallery)
        ImageRecette = vue.findViewById<ImageView>(R.id.vueImageView)


        attacherÉcouteursTraitement()
        attacherÉcouteursMédias()
        return vue
    }

    private val VérificateurDeChamps = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val nom = EditNomRecette.text.toString().trim()
            val durée = EditDuréeRecette.text.toString().trim()
            val ingrédients = EditPremierIngrédientRecette.text.toString().trim()
            val étapes = EditPremièreÉtapeRecette.text.toString().trim()

            btnAjouter.isEnabled = nom.isNotEmpty() &&
                    durée.isNotEmpty() && ingrédients.isNotEmpty() && étapes.isNotEmpty()
        }

        override fun afterTextChanged(p0: Editable?) {

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun attacherÉcouteursTraitement( ) {
        btnAjouter.setOnClickListener {
            présentateur.traiterAjoutRecette()
            présentateur.traiterRequêteVisualiserDécouverte()
        }

        btnAnnuler.setOnClickListener(){
            présentateur.traiterRequêteVisualiserDécouverte()
        }

    }


    private fun attacherÉcouteursMédias(){
        Médias.setOnClickListener{
            val items = arrayOf("Choisir photo", "Prendre photo")
            AlertDialog.Builder(context)
                .setTitle("Choisissez entre:")
                .setItems(items) { dialog: DialogInterface?, which: Int ->
                    when (which) {
                        0 -> this.pickPhoto(PRENDRE_IMAGE)
                        1 -> context?.let { context -> takePhoto(PRENDRE_PHOTO, context) }
                    }
                }
                .show()
        }
    }

    private fun takePhoto(code: Int, context: Context) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(context.packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile(context)
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        context,
                        "com.ptitgourmand.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, code)
                }
            }
        }
    }

    private fun pickPhoto(code: Int) {
        Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        ).apply {
            startActivityForResult(Intent.createChooser(this, getString(R.string.pick_image)), code)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PRENDRE_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val imageUri = data.data
                if (imageUri != null) {
                    cropRequest(imageUri)
                    var bitmap : Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver,imageUri)
                    var stream : ByteArrayOutputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
                    var bytes = stream.toByteArray()
                    PhotoEncodée = Base64.encodeToString(bytes, URL_SAFE)

                }
            }
        } else if (requestCode == PRENDRE_PHOTO && resultCode == Activity.RESULT_OK) {
            if (CheminPhotoActuel != null) {
                val MonfichierImage = File(CheminPhotoActuel as String)
                val imgUri = Uri.fromFile(MonfichierImage)
                cropRequest(imgUri)
                var bitmap : Bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver,imgUri)
                var stream : ByteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
                var bytes = stream.toByteArray()
                PhotoEncodée = Base64.encodeToString(bytes, URL_SAFE)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun cropRequest(uri: Uri) {
        CropImage.activity(uri).setCropMenuCropButtonTitle("Choisir")
            .setGuidelines(CropImageView.Guidelines.ON)
            .setMultiTouchEnabled(true)
            .start(activity as MainActivity)

    }

    fun addPhoto(uri: Uri) {
        context?.let {
            Glide.with(it)
                .load(uri)
                .into(ImageRecette)
        }
    }

    private fun createImageFile(context: Context): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            CheminPhotoActuel = absolutePath
        }
    }


    override fun enregistrerUneRecette() {

        var nouveaux_ingrédients = EditPremierIngrédientRecette.text.toString().split(","," ",";")
        var nouvelles_étapes = EditPremièreÉtapeRecette.text.toString().split(",",";")

            présentateur.ajouterRecette(Recette(EditNomRecette.text.toString(),
                EditDuréeRecette.text.toString(),
                Utilisateur("Bidon","Je suis","jesuisbidon@gmail.com"),
                nouveaux_ingrédients as MutableList<String>,
                2,
                nouvelles_étapes as MutableList<String>,
                PhotoEncodée

            ))

    }

    override fun naviguerVersDécouverte() {

            navController.navigate(R.id.action_vueCréationRecette_to_pageDecouverte)


    }



}
