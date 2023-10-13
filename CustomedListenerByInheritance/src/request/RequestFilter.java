package request;

import java.io.IOException;

import databaseAccess.ConnectionHolder;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        String confXMLPath =request.getServletContext().getRealPath("/WEB-INF/database.xml");
        try 
        {

            String database= XMLObject.createXMLObject(confXMLPath).getValueOfTheChildWithName("<database>") ;

            String user =XMLObject.createXMLObject(confXMLPath).getValueOfTheChildWithName("<user>") ;

            String password = XMLObject.createXMLObject(confXMLPath).getValueOfTheChildWithName("<password>") ;

            String ipaddr = XMLObject.createXMLObject(confXMLPath).getValueOfTheChildWithName("<ipaddr>");

            ConnectionHolder.setConnection(database, user, password, ipaddr);

        }
        catch (Exception e) 
        {
            throw new ServletException(e.getMessage());
        }
        /*
         * For the session
         */
        HttpServletRequest request1 = (HttpServletRequest) request;
        

        chain.doFilter(request, response);
    }

}