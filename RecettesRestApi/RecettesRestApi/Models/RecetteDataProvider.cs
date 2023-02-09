using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Linq;
using System.Threading.Tasks;

namespace service_recette.Models
{
    public class RecetteDataProvider
    {
        private static string connectionString = "Server=127.0.0.1;Uid=root;Pwd=root;Database=recettes;Port=3306";
        //private static string connectionString = "Server=(loc127aldb)\\MSSQLLocalDB;Database=books;Trusted_Connection=True;Uid=root;Pwd=root;Database=recettes";


        public static List<Recette> FindAll()
        {
            List<Recette> liste = new List<Recette>();
            Recette recette;
            DbConnection cnx = new MySqlConnection();
            cnx.ConnectionString = connectionString;
            cnx.Open();
            DbCommand cmd = cnx.CreateCommand();
            cmd.CommandText = "SELECT * FROM recette";
            DbDataReader dr = cmd.ExecuteReader();
            while (dr.Read())
            {
                System.Diagnostics.Debug.WriteLine(dr["utilisateur"]);
                recette = new Recette
                {
                    Id = Int32.Parse(dr["id_recette"].ToString()),
                    Nom = dr["nom_recette"].ToString(),
                    Durée = dr["duree"].ToString(),
                    utilisateur = UtilisateurDataProvider.RechercheParEmail(dr["utilisateur"].ToString()),
                    Ingredient = dr["ingredients"].ToString(),
                    Nb_portion = Int32.Parse(dr["nombre_portion"].ToString()),
                    Etapes = dr["etapes"].ToString()
                };
                liste.Add(recette);
            }
            cnx.Close();
            return liste;
        }

        public static Recette FindById(int id)
        {
            List<Recette> liste = new List<Recette>();
            Recette recette;
            DbConnection cnx = new MySqlConnection();
            cnx.ConnectionString = connectionString;
            cnx.Open();
            DbCommand cmd = cnx.CreateCommand();
            cmd.CommandText = "SELECT * FROM recette WHERE id_recette = "+id;
            DbDataReader dr = cmd.ExecuteReader();
            if (dr.Read())
            {
              
                recette = new Recette
                {
                    Id = Int32.Parse(dr["id_recette"].ToString()),
                    Nom = dr["nom_recette"].ToString(),
                    Durée = dr["duree"].ToString(),
                    utilisateur = UtilisateurDataProvider.RechercheParEmail(dr["utilisateur"].ToString()),
                    Ingredient = dr["ingredients"].ToString(),
                    Nb_portion = Int32.Parse(dr["nombre_portion"].ToString()),
                    Etapes = dr["etapes"].ToString()
                };
                return recette;
            }
            cnx.Close();
            return null;
        }

        public static bool AddRecette(Recette recette)
        {
            MySqlConnection cnx = new MySqlConnection();
            cnx.ConnectionString = connectionString;
            cnx.Open();
            MySqlCommand cmd = new MySqlCommand();
            cmd.Connection = cnx;
            cmd.CommandText = "INSERT INTO recette(nom_recette, duree, utilisateur, ingredients, nombre_portion, etapes) VALUES (@nom,@duree,@utilisateur,@ingredients,@nombre_portion,@etapes)";
            
            cmd.Parameters.AddWithValue("nom", recette.Nom);
            cmd.Parameters.AddWithValue("duree", recette.Durée);
            cmd.Parameters.AddWithValue("utilisateur", recette.utilisateur.Email);
            cmd.Parameters.AddWithValue("ingredients", recette.Ingredient);
            cmd.Parameters.AddWithValue("nombre_portion", recette.Nb_portion);
            cmd.Parameters.AddWithValue("etapes", recette.Etapes);
            
            bool res = cmd.ExecuteNonQuery() > 0;
            cnx.Close();
            return res;
        }

        public static bool Modifie(Recette recette)
        {
            MySqlConnection cnx = new MySqlConnection();
            cnx.ConnectionString = connectionString;
            cnx.Open();
            MySqlCommand cmd = new MySqlCommand();
            cmd.Connection = cnx;
            cmd.CommandText = "UPDATE recette SET nom_recette=@nom, ingredient=@ingredient, nb_portion=@nb_portion, etapes=@etapes WHERE id_recette=@id";

            cmd.Parameters.AddWithValue("id", recette.Id);
            cmd.Parameters.AddWithValue("nom", recette.Nom);
            cmd.Parameters.AddWithValue("ingredient", recette.Ingredient);
            cmd.Parameters.AddWithValue("nb_portion", recette.Nb_portion);
            cmd.Parameters.AddWithValue("etapes", recette.Etapes);

            bool res = cmd.ExecuteNonQuery() > 0;
            cnx.Close();
            return res;
        }

        internal static bool Supprime(int id)
        {
            DbConnection cnx = new MySqlConnection();
            cnx.ConnectionString = connectionString;
            cnx.Open();
            DbCommand cmd = cnx.CreateCommand();
            cmd.CommandText = "DELETE FROM recette WHERE id_recette="+id;

            bool res = cmd.ExecuteNonQuery() > 0;
            cnx.Close();
            return res;
        }
    }
}
