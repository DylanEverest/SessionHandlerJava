package session;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class SessionIdGenerator {
    private static SecureRandom random = new SecureRandom();

    public static String generateSessionId() {
        // Create a random string of bytes
        byte[] randomBytes = new byte[16];
        random.nextBytes(randomBytes);

        // Generate a unique identifier
        UUID uuid = UUID.nameUUIDFromBytes(randomBytes);

        // Convert the UUID to a byte array
        byte[] uuidBytes = new byte[16];
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();
        
        for (int i = 0; i < 8; i++) 
        {
            uuidBytes[i] = (byte) (mostSigBits >> (8 * (7 - i)));
            uuidBytes[8 + i] = (byte) (leastSigBits >> (8 * (7 - i)));
        }

        // Encode the byte array to a string
        String sessionId = Base64.getUrlEncoder().encodeToString(uuidBytes);

        return sessionId;
    }
}
