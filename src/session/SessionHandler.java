package session;

import java.util.HashMap;
import java.util.Map;

import cookie.CookieRetriever;
import databaseAccess.SessionModel;
import jakarta.servlet.http.Cookie;
import requestHandler.RequestHolder;

public class SessionHandler {
    private String sessionId;
    private Map<String, Object> data = new HashMap<>();
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
    

    public void session_destroy()
    {
        // maka anle sessionId anle cookie anle olona

        // 
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
    

    public Object getData(String key)
    {
        return data.get(key);
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
