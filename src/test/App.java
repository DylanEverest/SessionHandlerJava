package test;

import java.io.PrintWriter;

import databaseAccess.SessionModel;
import session.SessionHandler;

public class App 
{
    public static void main(String[] args) throws Exception 
    {
        try
        {
            SessionModel.deleteBySessionId("N6h91ZWkOi2AFmQCm-vPrw==");
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }
}
