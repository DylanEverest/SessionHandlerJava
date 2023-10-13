package request;

import java.io.IOException;

import databaseAccess.ConnectionHolder;
import databaseAccess.PosgtreConnection;
import databaseAccess.SessionModel;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import session.SessionHolder;
import session.SessionIdGenerator;
import utils.XMLParser.attribute.XMLObject;

@WebFilter("/*")
public class RequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException 
    {
       /*
         * For the database init
         */
        setDatabase(request);
        /*
         * hold the session
         */
        setSession(((HttpServletRequest) request) ,(HttpServletResponse) response);

        /*
            initialize the session
        */        
        initializeSessionContent((HttpServletRequest) request) ;

        chain.doFilter(request, response);
    }

    public boolean setDatabase(ServletRequest request)
    {
        String confXMLPath =request.getServletContext().getRealPath("/WEB-INF/database.xml");
        try 
        {

            String database= XMLObject.createXMLObject(confXMLPath).getValueOfTheChildWithName("<database>") ;

            String user =XMLObject.createXMLObject(confXMLPath).getValueOfTheChildWithName("<user>") ;

            String password = XMLObject.createXMLObject(confXMLPath).getValueOfTheChildWithName("<password>") ;

            String ipaddr = XMLObject.createXMLObject(confXMLPath).getValueOfTheChildWithName("<ipaddr>");

            ConnectionHolder.setConnection(database, user, password, ipaddr);

            return true;
        }
        catch (Exception e) 
        {
            return false;
        }
    }
    public void setSession(HttpServletRequest request ,HttpServletResponse response)
    {
        Cookie [] cookies = request.getCookies();
        String value="";
        try{
            for (Cookie cookie : cookies)
            {
                if (cookie.getName().equals("JSESSIONID")) 
                {
                    value= cookie.getValue();
                    break;
                }
            }
        }
        catch(Exception e)
        {

            value = SessionIdGenerator.generateSessionId();
            response.addCookie(new Cookie("JSESSIONID", value)) ;
        }

        SessionHolder.setSessionValue(value);

    }

    public void initializeSessionContent(HttpServletRequest httpServletRequest)
    {
        PosgtreConnection pg = ConnectionHolder.getConnection() ;

        HttpSession session = httpServletRequest.getSession() ;

        SessionModel model = new SessionModel(pg);
        
        model.setCryptedIDSession(SessionHolder.getSessionValue());

        try 
        {
            SessionModel[] allMatched= model.getAllBySessionID();
            
            for (SessionModel sessionModel : allMatched) 
            {
                session.setAttribute(sessionModel.getKey() ,sessionModel.getValue());
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }


        
    }

}