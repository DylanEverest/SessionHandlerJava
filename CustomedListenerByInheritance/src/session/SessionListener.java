package session;

import databaseAccess.ConnectionHolder;
import databaseAccess.PosgtreConnection;
import databaseAccess.SessionModel;

import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;

public class SessionListener implements HttpSessionBindingListener{



    @Override
    public void valueBound(HttpSessionBindingEvent event) 
    {
        /*
         * Add session to database
         */
        SessionModel sModel = getModel(event);
        try 
        {
            if  (!sModel.updateSessionData()) 
            {   
                sModel.create();
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        /*
         * Remove session from database
         */
        try 
        {
            getModel(event).deleteBySessionId() ;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

        
    public SessionModel getModel(HttpSessionBindingEvent event)
    {
        PosgtreConnection pg = ConnectionHolder.getConnection();
        SessionModel sModel= new SessionModel(pg);
        sModel.setCryptedIDSession(SessionHolder.getSessionValue());
        sModel.setKey(event.getName());
        sModel.setValue(event.getValue().toString());
        return sModel;
    }
    
}
