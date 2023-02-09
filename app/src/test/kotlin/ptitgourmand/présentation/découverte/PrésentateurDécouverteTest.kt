package ptitgourmand.présentation.découverte

import android.util.Log
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Test
//import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import ptitgourmand.domaine.entité.Recette
import ptitgourmand.domaine.entité.Utilisateur
import ptitgourmand.présentation.Modèle
import ptitgourmand.présentation.découverte.ContratVuePrésentateurDécouverte.IVueDécouverte
import ptitgourmand.présentation.modèle

internal class PrésentateurDécouverteTest{

    val dispatcher = newSingleThreadContext("UI")

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Étant donné l'écran decouverte, lorsqu'on se trouve sur la vue découverte, on obtient la liste des recettes `(){

        val vueMock = Mockito.mock(IVueDécouverte::class.java)
        val modèleTest = Mockito.mock(Modèle::class.java)
        val présentateurTest = PrésentateurDécouverte(vueMock, modèleTest)
        var utilisateur2 = Utilisateur("Lazarre","Ricardo","therealricardo@gmail.com")
        var ingrédients2 = listOf<String>("Pomme","Orange","Banane")
        var étapes2 = listOf<String>("Trancher les pommes","Trancher les Orange","Trancher les Banane")

        var recette2 = Recette("Salade de fruit","10 minutes", utilisateur2,
            ingrédients2 as MutableList<String>, 8, étapes2 as MutableList<String>
        )
        var listeRecettes = mutableListOf<Recette>(recette2)

        Mockito.`when`(modèleTest.obtenirToutesLesRecettes()).thenReturn(listeRecettes)

        présentateurTest.traiterDébuter()

        Thread.sleep(5500)
        Mockito.verify(modèleTest).obtenirToutesLesRecettes()
        Mockito.verify(vueMock).afficherRecette(listeRecettes)
    }

    @Test
    fun `Étant donné l'écran decouverte, lorsqu'on appuie sur une recette, la recette s'affiche dans la vue visualiserRecette `(){

        val vueMock = Mockito.mock(IVueDécouverte::class.java)
        val modèleTest = Mockito.mock(Modèle::class.java)
        val présentateurTest = PrésentateurDécouverte(vueMock, modèleTest)

        présentateurTest.traiterRequêteVisualiserUneRecette(1)


        Mockito.verify(modèleTest).recetteIndex = 1
        Mockito.verify(vueMock).naviguerVersRecette()
    }

    @Test
    fun `Étant donné l'écran decouverte, lorsqu'on appuie sur le bouton création de recette, la vue créationRecette apparait `(){

        val vueMock = Mockito.mock(IVueDécouverte::class.java)
        val présentateurTest = PrésentateurDécouverte(vueMock)

        présentateurTest.traiterRequêteAfficherPageCréation()

        Mockito.verify(vueMock).naviguerVersAjouter()
    }

}