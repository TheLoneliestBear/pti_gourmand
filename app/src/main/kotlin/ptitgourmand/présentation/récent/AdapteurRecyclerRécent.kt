package ptitgourmand.présentation.récent
/*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ptitgourmand.R
import ptitgourmand.domaine.entité.Recette
import ptitgourmand.présentation.Modèle
import ptitgourmand.présentation.modèle
import ptitgourmand.présentation.récent.AdapteurRecyclerRécent

class AdapteurRecyclerRécent(private val listeRecette: Array<Recette>) : RecyclerView.Adapter<AdapteurRecyclerRécent.ViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class ViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view){

        val nomRecette : TextView
        val nomUtilisateur : TextView

        init {
            nomRecette = itemView.findViewById(R.id.nom_recette)
            nomUtilisateur = itemView.findViewById(R.id.nom_utilisateur)
            nomRecette.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.recette_view,viewGroup,false)
        return  ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val élémentActuel = modèle.obtenirUneRecette(position)
        viewHolder.nomRecette.text = élémentActuel.nom
        viewHolder.nomUtilisateur.text = élémentActuel.utilisateur.nom
    }

    override fun getItemCount(): Int {
        return listeRecette.size
    }


}*/