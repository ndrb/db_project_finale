package venteflash;

public class VenteFlash {
    
    public static Database database;
    
    public static void main(String[] args) throws Exception {
        
        database = new Database();
        database.connect();
        
        // Vérifier que l'utilisateur a fourni un argument.
        if(args.length == 0)
        {
            throw new Exception("Aucun argument n'a été fourni !");
        }
        
        // Ce switch permettra d'effectuer la requête SQL choisie par l'utilisateur
        // lorsqu'il utilisera java -jar VenteFlash.jar NUMÉRO_DE_REQUÊTE
        // en ligne de commande !
        // Par exemple, "java -jar VenteFlash.jar 1" activera la requête #1
        // dans case "1" : ...
        
        switch(args[0]) {
            case "1" : 
            	database.requeteUn();
            	break; 
            case "2" : 
            	database.requeteDeux();
            	break;
            case "3" : 
            	database.requeteTrois();
            	break; 
            case "4" : 
            	database.requeteQuatre();
            	break;
            case "5" : 
            	database.requeteCinque();
            	break;
            case "6" : 
            	database.requeteSix();
            	break;
            case "7" : 
            	database.requeteSept();
            	break;
            case "8" : 
            	database.requeteHuit();
            	break;
        }

    }

}
