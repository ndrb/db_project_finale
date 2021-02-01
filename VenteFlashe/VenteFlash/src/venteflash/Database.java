package venteflash;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * Servez-vous de cette classe pour établir la connexion et pour
 * effectuer vos requêtes à la base postgresql.
 */

public class Database {
    
    /*
     * Ces trois variables permettront à votre application de se connecter à votre base postgresql
     * du DIRO.
     * 
     * -> Mettez votre nom d'usager du DIRO à la fin de l'url.
     * -> Mettez votre nom d'usager avec _app en suffixe pour la variable user.
     * -> Votre mot de passe est forcément le suivant :
     *     <> Les trois dernières lettres, en minuscule, de votre nom d'usager du DIRO.
     *     <> Suivi des quatre premiers caractères de votre login Studium en minuscule.
     *     <> Suivi de la première lettre de votre nom de famille en majuscule.
     *     
     * Par exemple, pour un étudiant nommé Simon Tremblay dont le nom d'usager du DIRO est trembsim
     * et dont le login Studium est p1110293, le mot de passe sera simp111T. D'ailleurs, il devra
     * mettre trembsim_app pour la variable user et trembsin à la fin de l'url.
     */
    
    private final String url = "jdbc:postgresql://postgres.iro.umontreal.ca:5432/baydounn";
    private final String user = "baydounn_app";
    private final String password = "unnp113B";
    
    private Connection connect = null;
 
    /*
     * La fonction ci-dessous est un exemple de connexion à la base avec un exemple de requête.
     * Évidemment, la fonction ne sera pas utilisable dans l'état car la requête ne correspond
     * pas à votre base de données. Ce n'est qu'un exemple.
     * 
     * Assurez-vous d'avoir installé JDBC et d'avoir importé son .jar dans votre projet !
     * Télécharger JDBC : https://jdbc.postgresql.org/download.html
     * 
     * De plus, assurez-vous d'être connecté au VPN de l'université avant de lancer votre programme !
     * Instructions VPN : https://bib.umontreal.ca/public/bib/soutien-informatique/S13-VPN.pdf
     * 
     * Assurez-vous d'avoir donné les droits à votre base postgreSQL pour faire des requêtes !
     * Instructions pour donner les droits : https://tableplus.com/blog/2018/04/postgresql-how-to-grant-access-to-users.html
     */
    
    public void connect() {
        //Connection c = null;
        try {
           // Établissement de la connexion avec les variables fournies plus haut
        	connect = DriverManager.getConnection(url, user, password);
           
           // Utilisation de la requête-exemple décrite dans la fonction plus bas
           //requeteExemple();
          
        } catch (Exception e) {
           e.printStackTrace();
           System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        
    }
    
    // Exemple de requête
    public void requeteExemple() 
    {
        // Notre requête SQL entière ici. Remarquez que le schéma et la table sont précisés
        String requete1 = "select * from projectyolo.product";
        //tring requete1 = "select * from demo10e1.constitution";
        
        try 
        {
            Statement requete = connect.createStatement();
            
            // Récupération de la table résultante
            ResultSet rs = requete.executeQuery(requete1);

            // Simple ligne d'enquête pour présenter les tuples.
            System.out.println("id_n | id_b");
            
            // Cette boucle permet d'itérer les tuples qui correspondent au résultat.
            while(rs.next())
            {
                // Par exemple, ici, si on s'attendait à avoir une colonne nommée
                // "id_n" dans nos résultats, on peut récupérer sa valeur pour le
                // tuple courant avec rs.getInt("NOM_DE_LA_COLONNE")
                int id_n = rs.getInt("id_prod");
                String id_b = rs.getString("titre");
                System.out.println(id_n + " |  " + id_b);

            }
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
    }
    
    private String formatter(String lol)
    {
    	return String.format( "%15s %-8s", lol, "" );
    }
    
    private String formatterLongText(String lol)
    {
    	return String.format( "%50s %-8s", lol, "" );
    }
    
    //Info sur annonceurs
    public void requeteUn()
    {
        String requete1 = "select * from projectyolo.annonceur";       
        try 
        {
            Statement requete = connect.createStatement();
            ResultSet rs = requete.executeQuery(requete1);

            System.out.println(formatter("ID Utilisateur") + " |  " + formatter("Prenom") + " |  " + formatter("Nom") + " |  " + formatterLongText("Email") + " |  " + formatter("Code Postal") + " |  " + formatter("Numero telephone") + "\n");
            
            while(rs.next())
            {
                int id_util = rs.getInt("id_util");
                String id_util_string = "" + id_util;
                String prenom = rs.getString("prenom");
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                String postal = rs.getString("code_postal");
                String telephone = rs.getString("num_telephone");
                System.out.println(formatter(id_util_string) + " |  " + formatter(prenom) + " |  " + formatter(nom) + " |  " + formatterLongText(email) + " |  " + formatter(postal) + " |  " + formatter(telephone));

            }
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
    }

    //le nombre de produits qu'ils vendent actuellement, on verifie les produit qui sont vendu et les produit qui sont retirer a cause d'une modification de prix
	public void requeteDeux() 
	{
		//with modif_accepter as (select * from projectyolo.modification_prix where accepter = 'true'), max_date_table as (select distinct on (id_prod) id_prod, id_mod, id_emp, new_price, accepter, date_modif, temp_modif from modif_accepter order by id_prod, date_modif  DESC),  max_time_table as (select distinct on (id_prod) id_prod, id_mod, id_emp, new_price, accepter, date_modif, temp_modif from max_date_table order by id_prod, temp_modif DESC), non_vendu as (select * from projectyolo.product where vendu_status = 'false') select count(*) from non_vendu left outer join max_time_table on non_vendu.id_prod = max_time_table.id_prod;	
		//with modif_non_accepter as (select * from projectyolo.modification_prix where accepter = 'false'), non_vendu as (select * from projectyolo.product where vendu_status = 'false') select count(*) from non_vendu t1 left join modif_non_accepter t2 on t1.id_prod = t2.id_prod where t2.id_prod IS NULL;
		
		
		
        String requete1 = "with modif_non_accepter as (select id_prod from projectyolo.modification_prix where accepter = 'false'), non_vendu as (select id_prod from projectyolo.product where vendu_status = 'false') select count(*) as compte from non_vendu t1 left join modif_non_accepter t2 on t1.id_prod = t2.id_prod where t2.id_prod IS NULL;";       
        try 
        {
            Statement requete = connect.createStatement();
            ResultSet rs = requete.executeQuery(requete1);
            
            while(rs.next())
            {
                System.out.println("Nombre d'object en vente actuellement: " + rs.getInt("compte"));
            }
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
	}

	//retourner une table produit avec la modification de prix accepter la plus recente
	public void requeteTrois() 
	{
		/*Cela va retourner une table produit avec la modification de prix accepter la plus recente
		with modif_accepter as (select * from projectyolo.modification_prix where accepter = 'true'), max_date_table as (select distinct on (id_prod) id_prod, id_mod, id_emp, new_price, accepter, date_modif, temp_modif from modif_accepter order by id_prod, date_modif  DESC),  max_time_table as (select distinct on (id_prod) id_prod, id_mod, id_emp, new_price, accepter, date_modif, temp_modif from max_date_table order by id_prod, temp_modif DESC) select * from max_time_table right outer join projectyolo.product on projectyolo.product.id_prod = max_time_table.id_prod;			
		*/
		
		String requete2 = "with modif_accepter as (select id_prod, new_price, date_modif, temp_modif from projectyolo.modification_prix where accepter = 'true'), "
				+ "max_date_table as (select distinct on (id_prod) id_prod, new_price, date_modif, temp_modif from modif_accepter order by id_prod, date_modif  DESC),  "
				+ "max_time_table as (select distinct on (id_prod) id_prod, new_price, temp_modif from max_date_table order by id_prod, temp_modif DESC) "
				+ "select * from max_time_table right join projectyolo.product on projectyolo.product.id_prod = max_time_table.id_prod";
        
		try 
        {
            Statement requete = connect.createStatement();
            ResultSet rs = requete.executeQuery(requete2);
            String prix;
            String produitNom;
            
            while(rs.next())
            {
            	produitNom = rs.getString("titre");
            	prix = rs.getString("new_price");
            	if(prix == null)
            	{
            		prix = rs.getString("prix");
            	}
            	System.out.println("Le prix du produit: '" + produitNom + "' est " + prix + "");
            }     
        } 
        
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
	}

	//le nombre maximal de propositions de modification de prix faites par un expert
	public void requeteQuatre() 
	{
		String requete2 =
				"WITH number_mods AS (Select count(id_mod) as count from projectyolo.modification_prix natural join projectyolo.admin group by id_emp)"
				+ "select max(count) as maximus from number_mods";      
        
		try 
        {
            Statement requete = connect.createStatement();
            ResultSet rs = requete.executeQuery(requete2);

            while(rs.next())
            {
            	System.out.println("Nombre maximal de modification fait par un expert: " + rs.getInt("maximus"));
            }     
        } 
        
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
	}

	//Nombre de modification accepter et refuser
	public void requeteCinque() 
	{
		String requete2 = "select count(accepter) as ctr from projectyolo.modification_prix where accepter = 'true'";
		String requete3 = "select count(id_mod) as totalctr from projectyolo.modification_prix";  
        
		try 
        {
            Statement requete = connect.createStatement();
            ResultSet rs = requete.executeQuery(requete2);
            int falses = 0;
            int total = 0;
            
            
            while(rs.next())
            {
            	falses = rs.getInt("ctr");
            }
            
            ResultSet rs2 = requete.executeQuery(requete3);
            while(rs2.next())
            {
            	total = rs2.getInt("totalctr");
            }
            
            int trues = total-falses;
            System.out.println("Modification totale: " + total);
            System.out.println("Modification non accepter: " + falses);
            System.out.println("Modification accepter: " + trues);
        } 
        
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
	}

	//Tous les produit de type "V�hicule"
	public void requeteSix() 
	{
		
		String requete2 = "with modif_accepter as (select id_prod, new_price, date_modif, temp_modif from projectyolo.modification_prix where accepter = 'true'), "
				+ "max_date_table as (select distinct on (id_prod) id_prod, new_price, date_modif, temp_modif from modif_accepter order by id_prod, date_modif  DESC),  "
				+ "max_time_table as (select distinct on (id_prod) id_prod, new_price, temp_modif from max_date_table order by id_prod, temp_modif DESC),"
				+ "cat_table as (select id_prod, titre, name, prix from projectyolo.product natural join projectyolo.categorie where categorie.name = 'Vehicule') "
				+ "select * from cat_table t1 left outer join max_time_table t2 on t1.id_prod = t2.id_prod";
        
		try 
        {
            Statement requete = connect.createStatement();
            ResultSet rs = requete.executeQuery(requete2);
            String prix;
            String produitNom;
            
            while(rs.next())
            {
            	produitNom = rs.getString("titre");
            	prix = rs.getString("new_price");
            	if(prix == null)
            	{
            		prix = rs.getString("prix");
            	}
            	System.out.println("Le prix du produit '" + produitNom + "' est '" + prix + "' - " + rs.getString("name") );
            }     
        } 
        
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
	}

	//maximum nombre de modification sur un produit effectuer dans une journee
	public void requeteSept() 
	{
		String requete3 = "with bridge_table as (select id_prod, count(id_prod) as changement from projectyolo.modification_prix group by id_prod, date_modif), max_val as (select MAX(changement) as changement from bridge_table), final_table as (select * from bridge_table natural join max_val) select * from final_table natural join projectyolo.product";

		try 
        {
            Statement requete = connect.createStatement();
            ResultSet rs = requete.executeQuery(requete3);
            
            while(rs.next())
            {
            	System.out.println("Maximum nombre de modification sur un produit effectuer dans une journee: " + rs.getInt("changement") + " - Sur le produit: " + rs.getString("titre"));
            }     
        } 
        
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
	}

	//La valeur moyenne de modification de prix sur tous les produit
	public void requeteHuit() 
	{
		String requete3 = "with bridge_table as (select id_prod, avg(new_price::numeric) as moyenne from projectyolo.modification_prix group by id_prod) select avg(moyenne) as moyenne from bridge_table";

		try 
        {
            Statement requete = connect.createStatement();
            ResultSet rs = requete.executeQuery(requete3);
            
            while(rs.next())
            {
            	double moyenne = rs.getDouble("moyenne");
            	String strDouble = String.format("%.2f", moyenne); 
            	System.out.println("La valeur moyenne de modification de prix sur tous les produit: " + strDouble);
            }     
        } 
        
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
	}
    
    
    

}
