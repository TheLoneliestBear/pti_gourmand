package ptitgourmand.présentation.découverte

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ptitgourmand.R
import ptitgourmand.domaine.entité.Recette

class AdapteurRecycler(private val listeRecette: MutableList<Recette>) : RecyclerView.Adapter<AdapteurRecycler.ViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }


    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun getItemCount(): Int {
        return listeRecette.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.recette_view,viewGroup,false)
        return  ViewHolder(view, mListener)
    }

    class ViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view){

        val nomRecette : TextView
        val nomUtilisateur : TextView
        val vueImage : ImageView

        init {
            nomRecette = itemView.findViewById(R.id.nom_recette)
            nomUtilisateur = itemView.findViewById(R.id.nom_utilisateur)
            vueImage = view.findViewById(R.id.imageRecetteView)
            val vueImage : ImageView
            nomRecette.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val élémentActuel = listeRecette.get(position)
        viewHolder.nomRecette.text = élémentActuel.nom
        viewHolder.nomUtilisateur.text = élémentActuel.utilisateur.nom

        if (élémentActuel.uriString == null){
            viewHolder.vueImage.setImageResource(R.drawable.defaultrecette)
        } else {
            var imageString : ByteArray = Base64.decode(élémentActuel.uriString, Base64.URL_SAFE)
            var imageDécodé : Bitmap = BitmapFactory.decodeByteArray(imageString,0,imageString.size)
            viewHolder.vueImage.setImageBitmap(imageDécodé)
        }



    }
}




