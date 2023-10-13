package session;

import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;

public class SessionListener implements HttpSessionBindingListener{

    

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
