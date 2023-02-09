package ptitgourmand.domaine.entité

import junit.framework.Assert
import org.junit.Test


internal class UtilisateurTest{

    @Test
    fun `test étant donné Nicolas Therrien avec ntherrien@icloud.com comme email, lorsque j'instancie un utilisateur, j'obtiens un objet Score avec pseudonyme Bob et 77 points`() {

        //Mise en place

        var nom = "Therrien"
        var prénom = "Nicolas"
        var email = "ntherrien@icloud.com"

        var utilisateur = Utilisateur(nom, prénom, email)


        //Vérification
        Assert.assertEquals("Nicolas", utilisateur.prénom)
        Assert.assertEquals("Therrien", utilisateur.nom)
        Assert.assertEquals("ntherrien@icloud.com", utilisateur.email)
    }

}