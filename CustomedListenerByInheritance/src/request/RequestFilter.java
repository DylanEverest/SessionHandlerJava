package request;

import java.io.IOException;

import databaseAccess.ConnectionHolder;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import session.SessionHolder;
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
         * For the session
         */
        setSession(((HttpServletRequest) request).getCookies());


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
    public void setSession(Cookie[] cookies)
    {
        String value="";
        for (Cookie cookie : cookies)
        {
            if (cookie.getName().equals("JSESSIONID")) 
            {
                value= cookie.getValue();
                break;
            }
        }
        SessionHolder.setSessionValue(value);

    }

    // public void 

}