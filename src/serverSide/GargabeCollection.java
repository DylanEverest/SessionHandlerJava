package serverSide;

import java.sql.Connection;
import java.sql.PreparedStatement;

import databaseAccess.PosgtreConnection;

public class GargabeCollection {
    public static void main(String[] args) throws Exception {
        PosgtreConnection pg = new PosgtreConnection("javasession","postgres","","jdbc:postgresql://localhost:5432/");

        Connection c = pg.connectToDataBase() ;

        String deleteSQL = "DELETE FROM sessions WHERE NOW() - createdat > interval '3 hours'";

        // Créer un objet PreparedStatement pour exécuter la requête
        PreparedStatement preparedStatement = c.prepareStatement(deleteSQL);
    
        // Exécuter la suppression
        int rowsAffected = preparedStatement.executeUpdate();   
    }
}
