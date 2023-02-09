using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace service_recette.Models
{
    public class Recette
    {
        public int Id { get; set; }
        public string Nom { get; set; }
        public string Durée { get; set; }
        public Utilisateur utilisateur { get; set; }
        public string Ingredient { get; set; }
        public int Nb_portion { get; set; }
        public string Etapes { get; set; }
    }
}
