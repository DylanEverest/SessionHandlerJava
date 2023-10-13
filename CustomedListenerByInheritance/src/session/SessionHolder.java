package session;

public class SessionHolder 
{

    private static ThreadLocal<String> sessionValue = new ThreadLocal<String>();

    public static String getSessionValue() 
    {
        return sessionValue.get();
    }

    public static void setSessionValue(String value) 
    {
        sessionValue.set(value);
    }

    
    
}
