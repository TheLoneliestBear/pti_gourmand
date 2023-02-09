using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace service_recette.Models
{
    public class Utilisateur
    {
        public int Id { get; set; }
        public string Nom { get; set; }
        public string Prénom { get; set; }
        public string Email { get; set; }
        public string Mdp { get; set; }
        
    }
}
