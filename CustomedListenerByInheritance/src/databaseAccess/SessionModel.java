package databaseAccess;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SessionModel {
    PosgtreConnection pg ;
    private int pk;
    private String cryptedIDSession;
    private String key;
    private String value;
    private Timestamp dateb;

    // Constructors, getters, and setters
    // ...
    public SessionModel(PosgtreConnection pg)
    {
        this.pg= pg;
    }

    public boolean create() throws Exception 
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        SessionModel session = new SessionModel(pg);
        session.setCryptedIDSession(cryptedIDSession);
        session.setKey(key);
        session.setValue(value);

        try 
        {
            conn = pg.connectToDataBase();
            String sql = "INSERT INTO sessions (cryptedsessionid,key ,value) VALUES (?, ?,?) RETURNING sessionsidpk";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cryptedIDSession);
            stmt.setString(2, key);
            stmt.setString(3, value);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } 
        catch (SQLException e) 
        {
            return false;
        } 
        finally 
        {
            if (stmt != null) 
            {
                stmt.close();
            }
            if (conn != null) 
            {
                conn.close();
            }
        }

    }

    public SessionModel read() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        try 
        {
            conn = pg.connectToDataBase();
            String sql = "SELECT * FROM sessions WHERE key = ? and cryptedsessionid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, key);
            stmt.setString(2, cryptedIDSession);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) 
            {

                SessionModel session = new SessionModel(pg);
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
            if (stmt != null) 
            {
                stmt.close();
            }
            if (conn != null) 
            {
                conn.close();
            }
         }
        return null;
    }

    public SessionModel [] getAllBySessionID() throws Exception 
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        try 
        {
            conn = pg.connectToDataBase();
            String sql = "SELECT * FROM sessions WHERE cryptedsessionid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, key);
            stmt.setString(2, cryptedIDSession);
            ResultSet rs = stmt.executeQuery();
            ArrayList<SessionModel> list = new ArrayList<SessionModel>();
            while (rs.next()) 
            {

                SessionModel session = new SessionModel(pg);
                session.setPk(rs.getInt("sessionsidpk"));
                session.setCryptedIDSession(rs.getString("cryptedsessionid"));
                session.setKey(rs.getString("key"));
                session.setValue(rs.getString("value"));
                session.setDateb(rs.getTimestamp("dateb"));
                list.add(session);

            }
            return list.toArray(new SessionModel[list.size()]);

        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            if (stmt != null) 
            {
                stmt.close();
            }
            if (conn != null) 
            {
                conn.close();
            }
         }
        return null;   
    }

    public boolean deleteBySessionId() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
    
        try 
        {
            conn = pg.connectToDataBase();
            String sql = "DELETE FROM sessions WHERE cryptedsessionid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cryptedIDSession);
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
    


    public boolean updateSessionData() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
    
        try 
        {
            conn = pg.connectToDataBase();
            String sql = "UPDATE sessions SET value = ?  WHERE cryptedsessionid = ? AND key= ? ";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, value);
            stmt.setString(2, cryptedIDSession);
            stmt.setString(3, key);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        } 
        finally
        {
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

}
