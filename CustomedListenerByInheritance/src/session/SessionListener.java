package session;

import databaseAccess.ConnectionHolder;
import databaseAccess.PosgtreConnection;
import databaseAccess.SessionModel;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;

public class SessionListener implements HttpSessionBindingListener{

    
    public SessionModel getModel(HttpSessionBindingEvent event)
    {
        PosgtreConnection pg = ConnectionHolder.getConnection();
        SessionModel sModel= new SessionModel(pg);
        sModel.setCryptedIDSession(SessionHolder.getSessionValue());
        sModel.setKey(event.getName());
        sModel.setValue(event.getValue().toString());
        return sModel;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        /*
         * Add session to database
         */

        


    
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        /*
         * Remove session from database
         */
    }
    
}
