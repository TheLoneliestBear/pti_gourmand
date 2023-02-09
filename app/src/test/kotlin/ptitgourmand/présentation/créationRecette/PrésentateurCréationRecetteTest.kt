package ptitgourmand.présentation.créationRecette

//import org.junit.jupiter.api.Assertions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import ptitgourmand.domaine.entité.Recette
import ptitgourmand.domaine.entité.Utilisateur
import ptitgourmand.présentation.Modèle
import ptitgourmand.présentation.modèle
import ptitgourmand.présentation.créationRecette.ContratVuePrésentateurCréationRecette.IVueCréationRecette
import ptitgourmand.présentation.créationRecette.ContratVuePrésentateurCréationRecette.IPrésentateurCréationRecette
import ptitgourmand.présentation.créationRecette.PrésentateurCréationRecette
import ptitgourmand.présentation.créationRecette.VueCréationRecette

internal class PrésentateurCréationRecetteTest {

    val dispatcher = newSingleThreadContext("UI")
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `étant donné un PrésentateurCréationRecette, lorsqu'on reçoit une requête «Naviger Vers Découverte», la vue passe à l'écran VueDécouverte»`(){

        val mockVue = Mockito.mock(IVueCréationRecette::class.java)
        val présentateur = PrésentateurCréationRecette(mockVue)

        présentateur.traiterRequêteVisualiserDécouverte()

        Mockito.verify(mockVue).naviguerVersDécouverte()
    }

}