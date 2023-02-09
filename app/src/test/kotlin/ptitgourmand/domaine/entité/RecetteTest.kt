package ptitgourmand.domaine.entité

import junit.framework.Assert.assertEquals
import org.junit.Test

internal class RecetteTest{

    @Test
    fun `test étant donné "Bob" son score de 77 points, lorsque j'instancie son Score, j'obtiens un objet Score avec pseudonyme Bob et 77 points`() {

        //Mise en place
        var utilisateur = Utilisateur("Therrien", "Nicolas", "ntherrien@icloud.com")
        var ingrédients = mutableListOf<String>("oeuf", "farine")
        var étapes = mutableListOf<String>("1- Ajouter les ingrédients", "2- Mélanger")
        var recette = Recette("tarte", "2h", utilisateur, ingrédients,4, étapes)



        //Vérification
        assertEquals( "tarte", recette.nom )
        assertEquals( "2h", recette.durée)
        assertEquals( "ntherrien@icloud.com", recette.utilisateur.email )
        assertEquals( "oeuf", recette.ingrédient[0])
        assertEquals( 4, recette.nombre_portion)
        assertEquals( "1- Ajouter les ingrédients", recette.étapes[0] )
    }
}

