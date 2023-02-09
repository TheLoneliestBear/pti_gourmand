package ptitgourmand.domaine.entité


class Recette (var nom : String,
               var durée : String,
               var utilisateur : Utilisateur,
               var ingrédient: MutableList<String>,
               var nombre_portion : Int,
               var étapes : MutableList<String>,var uriString: String? = null){


    fun ajouterIngrédient(x : String){ingrédient.add(x)}
    fun ajouterÉtape(x : String){étapes.add(x)}

}