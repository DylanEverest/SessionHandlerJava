package databaseAccess;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SessionModel {
    private int pk;
    private String cryptedIDSession;
    private String key;
    private String value;
    private Timestamp dateb;

    // Constructors, getters, and setters
    // ...

    public static SessionModel create(String cryptedIDSession, String key, String value) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        SessionModel session = new SessionModel();
        session.setCryptedIDSession(cryptedIDSession);
        session.setKey(key);
        session.setValue(value);

        try 
        {
            PosgtreConnection posgtreConnection = new PosgtreConnection("javasession","postgres" , "", "jdbc:postgresql://localhost:5432/");
            conn = posgtreConnection.connectToDataBase();
            String sql = "INSERT INTO sessions (cryptedsessionid,key ,value) VALUES (?, ?,?) RETURNING sessionsidpk";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cryptedIDSession);
            stmt.setString(2, key);
            stmt.setString(3, value);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                session.setPk(rs.getInt("sessionsidpk"));
                return session;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    public static SessionModel read(String key) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        try 
        {
            PosgtreConnection posgtreConnection = new PosgtreConnection("javasession","postgres" , "", "jdbc:postgresql://localhost:5432/");
            conn = posgtreConnection.connectToDataBase();
            String sql = "SELECT * FROM sessions WHERE key = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, key);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) 
            {

                SessionModel session = new SessionModel();
                session.setPk(rs.getInt("sessionsidpk"));
                session.setCryptedIDSession(rs.getString("cryptedsessionid"));
                session.setKey(rs.getString("key"));
                session.setValue(rs.getString("value"));
                session.setDateb(rs.getTimestamp("dateb"));
                return session;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
         }
        return null;
    }
    public static boolean deleteBySessionId(String sessionId) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
    
        try 
        {
            PosgtreConnection posgtreConnection = new PosgtreConnection("javasession","postgres" , "", "jdbc:postgresql://localhost:5432/");
            conn = posgtreConnection.connectToDataBase();
            String sql = "DELETE FROM sessions WHERE cryptedsessionid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, sessionId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            // Close resources (stmt, conn)
            if (stmt != null) 
            {
                stmt.close();
            }
            if (conn != null) 
            {
                conn.close();
            }
        }
        return false;
    }
    


    public static boolean updateSessionData(String cryptedsessionid ,String key, String valueToInsert) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
    
        try 
        {
            PosgtreConnection posgtreConnection = new PosgtreConnection("javasession","postgres" , "", "jdbc:postgresql://localhost:5432/");
            conn = posgtreConnection.connectToDataBase();
            String sql = "UPDATE sessions SET value = ?  WHERE cryptedsessionid = ? AND key= ? ";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, valueToInsert);
            stmt.setString(2, cryptedsessionid);
            stmt.setString(3, key);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources (stmt, conn)
        }
        return false;
    }
    

    
    

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getCryptedIDSession() {
        return cryptedIDSession;
    }

    public void setCryptedIDSession(String cryptedIDSession) {
        this.cryptedIDSession = cryptedIDSession;
    }


    public Timestamp getDateb() {
        return dateb;
    }

    public void setDateb(Timestamp dateb) {
        this.dateb = dateb;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // Additional methods, getters, setters, and other members
    // ...
}
