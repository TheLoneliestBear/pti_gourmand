using System.Net;
using System.Net.Http;
using System.Web.Http;
using service_recette.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;


namespace service_recette.Controllers
{



    public class UtilisateurController : ApiController
    {
        [HttpGet]
        [Route("api/Utilisateur/{email}")]
        public Utilisateur RechercheParEmail(string email)
        {
            Utilisateur utilisateur = UtilisateurDataProvider.RechercheParEmail(email);
            return utilisateur;
        }

        [HttpPost]
        public bool Post(Utilisateur utilisateur)
        {
            return UtilisateurDataProvider.AjouterUtilisateur(utilisateur);
        }
    }

    
}