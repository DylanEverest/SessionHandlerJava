package session;



import cookie.CookieRetriever;
import databaseAccess.SessionModel;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import requestHandler.RequestHolder;

public class SessionHandler {
    private String sessionId;
    private long lastAccessedTime;
    
    
    public void session_start() {
        // Check if the session ID exists in the current cookie of the user

        String sessionIdFromCookie = CookieRetriever.retrieveSessionIdFromCookie(RequestHolder.getRequest());

    
        if (sessionIdFromCookie == null) {
            // Session ID does not exist in the current cookie, generate a new one
            sessionId = SessionIdGenerator.generateSessionId();
            
            // add the cookie for the client
            Cookie sessionCookie = new Cookie("sessionIdDylan", sessionId);

            RequestHolder.getResponse().addCookie(sessionCookie);

        } 
        else 
        {
            // Session ID exists in the cookie, use it
            sessionId = sessionIdFromCookie;
        }
    }
    

    public void session_destroy() {
        // Retrieve the current request
        HttpServletRequest request = RequestHolder.getRequest();
        
        // Retrieve the session ID from the current session
        String sessionId = (String) request.getSession().getAttribute("sessionIdDylan");
        
        if (sessionId != null) 
        {
            // Delete the session data from the database based on the session ID
            try 
            {
                SessionModel.deleteBySessionId(sessionId);
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
    
    
    
    public boolean storeData(String key, Object value) throws Exception
    {
        try 
        {
            String valueToInsert = value.toString();
            // Check if the record exists
            
            if  (!SessionModel.updateSessionData( sessionId ,key, valueToInsert)) 
            {
                SessionModel x =SessionModel.create(sessionId ,key, valueToInsert) ;
                return (x==null);
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

        return SessionModel.read(key).getValue();
    }
    
    public String getSessionId() 
    {
        return sessionId;
    }

    public long getLastAccessedTime() 
    {
        return lastAccessedTime;
    }
    public void setLastAccessedTime(long lastAccessedTime) 
    {
        this.lastAccessedTime = lastAccessedTime;
    }
    
}
