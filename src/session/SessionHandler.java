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
        String sessionId = (String) request.getSession().getAttribute("sessionId");
        
        if (sessionId != null) {
            // Delete the session data from the database based on the session ID
            try {
                SessionModel.deleteBySessionId(sessionId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
        // Create a session cookie with the same name as the session ID cookie
        Cookie sessionCookie = new Cookie("sessionId", null);
        
        // Set the cookie to expire by setting its maxAge to 0 (or a negative value)
        sessionCookie.setMaxAge(0);
        
        // Set the path to match the path of the original session cookie
        sessionCookie.setPath("/");  // You may need to customize the path if it's different
    
        // Add the cookie to the response to delete it
        HttpServletResponse response = RequestHolder.getResponse();
        response.addCookie(sessionCookie);
    
        // Optionally, you can also invalidate the current session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
    
    
    
    public boolean storeData(String key, Object value) throws Exception
    {
        try 
        {
            String valueToInsert = value.toString();
            // Check if the record exists
            int pk = SessionModel.exists(sessionId, key) ;
            if  (pk!=-1) 
            {
                SessionModel.updateSessionData( pk ,key, valueToInsert);
            } 
            else 
            {
                SessionModel.create(sessionId, key+"="+valueToInsert) ;
            }
            return false;            
        } catch (Exception e) 
        {
            throw new Exception("Store tsy mety");
        }

    }

    public String getData(String sessionId, String key) throws Exception 
    {
        int pk = SessionModel.exists(sessionId, key);
        if (pk != -1) 
        {
            SessionModel session = SessionModel.read(pk);
         
            return extractValueFromSessions(session.getSessions(), key);
        }
        return null; // Session data not found
    }


    
    private String extractValueFromSessions(String sessions, String key) 
    {
        if (sessions != null) 
        {
            String[] keyValuePairs = sessions.split(",");

            for (String pair : keyValuePairs) 
            {
                String[] parts = pair.split("=");

                if (parts.length == 2 && parts[0].equals(key)) 
                {
                    return parts[1];
                }
            }
        }
        return null; 
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
