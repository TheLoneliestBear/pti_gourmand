
using service_recette.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Net;
using System.Net.Http;
using System.Web.Http;


namespace service_recette.Controllers
{
    
    public class RecetteController : ApiController
    {
        /*private readonly RecetteDataProvider _recetteDataProvider;

        public RecetteController(RecetteDataProvider recetteDataProvider)
        {
            _recetteDataProvider = recetteDataProvider;
        }*/
        [HttpGet]
        [Route("api/Recettes")]
        public IEnumerable<Recette> FindAll()
        {
            return RecetteDataProvider.FindAll();
        }

        //[HttpGet]
        [Route("api/Recette/{id}")]
        public Recette GetParId(int id)
        {
            Recette resultat = RecetteDataProvider.FindById(id);
            return resultat;
        }

        //public ActionResult FindById(int id)
        //{
        //    Recette recette = RecetteDataProvider.FindById(id);
        //    if (recette != null)
        //    {
        //        return this.Ok(recette);
        //    }
        //    else
        //    {
        //        return this.NotFound();
        //    }
        //}

        [HttpPost]
        public bool Post(Recette recette)
        {
            return RecetteDataProvider.AddRecette(recette);
        }

        //[HttpPut]
        //public bool Put(Recette recette)
        //{
        //    return RecetteDataProvider.Modifie(recette);
        //}

        //[HttpDelete("{id}")]
        //public bool Delete(int id)
        //{
        //    return RecetteDataProvider.Supprime(id);
        //}
    }
}
