package databaseAccess;

import java.sql.Connection;
import java.sql.DriverManager;

public class PosgtreConnection {
    String database;
    String user ;
    String password;
    String url="jdbc:postgresql://localhost:5432/";
    
    public PosgtreConnection(String database, String user, String password, String url) 
    {
        this.database = database;
        this.user = user;
        this.password = password;
        this.url = url;
    }

    public Connection connectToDataBase() throws Exception
    {
        Class.forName("org.postgresql.Driver");
        Connection con  = DriverManager.getConnection(getUrl()+getDatabase(), getUser(), getPassword());
        return con ;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }    
}
