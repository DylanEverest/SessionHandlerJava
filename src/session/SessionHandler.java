package session;



import cookie.CookieRetriever;
import databaseAccess.ConnectionHolder;
import databaseAccess.SessionModel;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import requestHandler.RequestHolder;

public class SessionHandler {

    public static void session_start() {
        // Check if the session ID exists in the current cookie of the user

        String sessionIdFromCookie = CookieRetriever.retrieveSessionIdFromCookie(RequestHolder.getRequest());

    
        if (sessionIdFromCookie == null) {
            // Session ID does not exist in the current cookie, generate a new one
            String sessionId = SessionIdGenerator.generateSessionId();
            
            // add the cookie for the client
            Cookie sessionCookie = new Cookie("sessionIdDylan", sessionId);

            RequestHolder.getResponse().addCookie(sessionCookie);

        } 

    }
    

    public static void session_destroy() {
        // Retrieve the current request
        HttpServletRequest request = RequestHolder.getRequest();
        
        // Retrieve the session ID from the current session
        String sessionId = (String) request.getSession().getAttribute("sessionIdDylan");
        
        if (sessionId != null) 
        {
            // Delete the session data from the database based on the session ID
            try 
            {
                new SessionModel(ConnectionHolder.getConnection()).deleteBySessionId(sessionId);
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    
        // Create a session cookie with the same name as the session ID cookie
        Cookie sessionCookie = new Cookie("sessionIdDylan", null);
        
        // Set the cookie to expire by setting its maxAge to 0 (or a negative value)
        sessionCookie.setMaxAge(0);
         
        // Add the cookie to the response to delete it
        HttpServletResponse response = RequestHolder.getResponse();

        response.addCookie(sessionCookie);
    
 
    }
    
    
    
    public static boolean storeData(String key, Object value) throws Exception
    {
        String sessionId = CookieRetriever.retrieveSessionIdFromCookie(RequestHolder.getRequest());
        if (sessionId == null) 
        {
            session_start();
            sessionId =CookieRetriever.retrieveSessionIdFromCookie(RequestHolder.getRequest());
        }
        try 
        {
            String valueToInsert = value.toString();
            // Check if the record exists
            SessionModel sessionModel = new SessionModel(ConnectionHolder.getConnection());
            sessionModel.setKey(key);
            sessionModel.setValue(valueToInsert);
            if  (!sessionModel.updateSessionData()) 
            {
                return sessionModel.create() ;
            } 
            return true;

        }
        catch (Exception e) 
        {
            throw new Exception("Store tsy mety");
        }

    }

    public String getData(String key) throws Exception 
    {

        SessionModel s= new SessionModel(ConnectionHolder.getConnection());
        s.setKey(key);
        return s.read(key).getValue();
    }
    
    
}
