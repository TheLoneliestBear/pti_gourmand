using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MySql.Data.MySqlClient;

namespace service_recette.Models
{
    internal class UtilisateurDataProvider
    {
        private static string connectionString = "Server=127.0.0.1;Uid=root;Pwd=root;Database=recettes;Port=3306";

        public static Utilisateur RechercheParEmail(string email)
        {
            List<Recette> liste = new List<Recette>();
            Utilisateur utillisateur;
            DbConnection cnx = new MySqlConnection();
            cnx.ConnectionString = connectionString;
            cnx.Open();
            DbCommand cmd = cnx.CreateCommand();
            cmd.CommandText = "SELECT * FROM utilisateur  WHERE email = '" + email+"';";
            DbDataReader dr = cmd.ExecuteReader();
            if (dr.Read())
            {
                utillisateur = new Utilisateur
                {
                    Id = Int32.Parse(dr["id_utilisateur"].ToString()),
                    Nom = dr["nom_utilisateur"].ToString(),
                    Prénom = dr["prenom"].ToString(),
                    Email = dr["email"].ToString(),
                    Mdp = dr["mdp"].ToString()
                };
                return utillisateur;
            }
            cnx.Close();
            return null;
        }

        public static bool AjouterUtilisateur(Utilisateur utilisateur)
        {
            MySqlConnection cnx = new MySqlConnection();
            cnx.ConnectionString = connectionString;
            cnx.Open();
            MySqlCommand cmd = new MySqlCommand();
            cmd.Connection = cnx;
            cmd.CommandText = "INSERT INTO utilisateur(nom_utilisateur, prenom, email, mdp) VALUES (@nom,@prénom,@email,@mdp)";
 
            
            cmd.Parameters.AddWithValue("nom", utilisateur.Nom);
            cmd.Parameters.AddWithValue("prénom", utilisateur.Prénom);
            cmd.Parameters.AddWithValue("email", utilisateur.Email);
            cmd.Parameters.AddWithValue("mdp", utilisateur.Mdp);

            bool res = cmd.ExecuteNonQuery() > 0;
            cnx.Close();
            return res;
        }
    }
}
