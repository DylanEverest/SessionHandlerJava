package databaseAccess;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SessionModel {
    private int pk;
    private String cryptedIDSession;
    private String sessions;
    private Timestamp dateb;

    // Constructors, getters, and setters
    // ...

    public static SessionModel create(String cryptedIDSession, String sessions) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        try 
        {
            PosgtreConnection posgtreConnection = new PosgtreConnection("clustering","postgre" , "", "jdbc:postgresql://localhost:5432/");
            conn = posgtreConnection.connectToDataBase();
            String sql = "INSERT INTO session (cryptedIDSession, sessions) VALUES (?, ?) RETURNING pk";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cryptedIDSession);
            stmt.setString(2, sessions);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                SessionModel session = new SessionModel();
                session.setPk(rs.getInt("pk"));
                session.setCryptedIDSession(cryptedIDSession);
                session.setSessions(sessions);
                return session;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources (stmt, conn)
        }
        return null;
    }

    public static SessionModel read(int pk) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        try 
        {
            PosgtreConnection posgtreConnection = new PosgtreConnection("clustering","postgre" , "", "jdbc:postgresql://localhost:5432/");
            conn = posgtreConnection.connectToDataBase();
            String sql = "SELECT * FROM session WHERE pk = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pk);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                SessionModel session = new SessionModel();
                session.setPk(rs.getInt("pk"));
                session.setCryptedIDSession(rs.getString("cryptedIDSession"));
                session.setSessions(rs.getString("sessions"));
                session.setDateb(rs.getTimestamp("dateb"));
                return session;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources (stmt, conn)
        }
        return null;
    }

    public boolean update() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        try 
        {
            PosgtreConnection posgtreConnection = new PosgtreConnection("clustering","postgre" , "", "jdbc:postgresql://localhost:5432/");
            conn = posgtreConnection.connectToDataBase();
            String sql = "UPDATE session SET cryptedIDSession = ?, sessions = ? WHERE pk = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, this.cryptedIDSession);
            stmt.setString(2, this.sessions);
            stmt.setInt(3, this.pk);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources (stmt, conn)
        }
        return false;
    }
    public static boolean updateSessionData(int pk ,String key, String valueToInsert) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
    
        try 
        {
            PosgtreConnection posgtreConnection = new PosgtreConnection("clustering","postgre" , "", "jdbc:postgresql://localhost:5432/");
            conn = posgtreConnection.connectToDataBase();
            String sql = "UPDATE session SET sessions = array_to_string(array_append(string_to_array(sessions, ','), ?), ',') WHERE pk = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, key + "=" + valueToInsert);
            stmt.setInt(2, pk);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources (stmt, conn)
        }
        return false;
    }
    

    public static int exists(String sessionId, String key) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        int pk = -1; // Initialize with a default value
    
        try {
            PosgtreConnection posgtreConnection = new PosgtreConnection("clustering", "postgre", "", "jdbc:postgresql://localhost:5432/");
            conn = posgtreConnection.connectToDataBase();
            String sql = "SELECT pk FROM session WHERE cryptedidsession = ? AND ? = ANY(string_to_array(sessions, ','))";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, sessionId);
            stmt.setString(2, key);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                pk = rs.getInt("pk"); // Get the primary key value
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            conn.close();
        }
        
        return pk;
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

    public String getSessions() {
        return sessions;
    }

    public void setSessions(String sessions) {
        this.sessions = sessions;
    }

    public Timestamp getDateb() {
        return dateb;
    }

    public void setDateb(Timestamp dateb) {
        this.dateb = dateb;
    }

    // Additional methods, getters, setters, and other members
    // ...
}
