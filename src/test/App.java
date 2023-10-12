package test;

import java.io.PrintWriter;

import databaseAccess.SessionModel;
import session.SessionHandler;
import utils.XMLParser.attribute.XMLObject;

public class App 
{
    public static void main(String[] args) throws Exception 
    {
        try
        {
           String x= XMLObject.createXMLObject("/opt/apache-tomcat-10.1.13/webapps/TestSession/WEB-INF/database.xml").getValueOfTheChildWithName("<password>");
           System.out.println("a:"+x);
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }
}
