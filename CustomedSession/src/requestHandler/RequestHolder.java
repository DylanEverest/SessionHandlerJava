package requestHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestHolder {
    private static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<>();

    public static void setRequest(HttpServletRequest request) 
    {
        requestThreadLocal.set(request);
    }

    public static HttpServletRequest getRequest() 
    {
        return requestThreadLocal.get();
    }

    public static void removeRequest() 
    {
        requestThreadLocal.remove();
    }
    public static HttpServletResponse getResponse()
    {
        return responseThreadLocal.get();
    }
    public static void setResponse(HttpServletResponse response)
    {
        responseThreadLocal.set(response);
    }

}
