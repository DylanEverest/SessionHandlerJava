package databaseAccess;

public class ConnectionHolder {
    private static ThreadLocal<PosgtreConnection> connection = new ThreadLocal<>();

    public static void setConnection(String database,String user , String passw, String ipaddr)
    {
        connection.set(new PosgtreConnection(database,user,passw,ipaddr));
    }
    public static PosgtreConnection getConnection()
    {
        return connection.get();
    }
}
