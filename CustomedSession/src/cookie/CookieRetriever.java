package cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieRetriever {
    
    public static String retrieveSessionIdFromCookie(HttpServletRequest request) {
        // Get the user's cookies from the request
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            // Iterate through the cookies to find the session ID
            for (Cookie cookie : cookies) {
                if ("sessionIdDylan".equals(cookie.getName())) {
                    // "sessionId" should match the name of your session cookie
                    return cookie.getValue();
                }
            }
        }

        // If the session ID cookie doesn't exist, return null
        return null;
    }



}
