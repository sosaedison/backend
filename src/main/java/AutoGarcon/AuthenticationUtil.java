package AutoGarcon;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

/**
 * Class for authenticating passwords for users
 */
public class AuthenticationUtil {

    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 512;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";

    public static Optional<String> hashPassword (String password, String salt) {

        /* Password and salt need to be char and byte arrays respectively because strings are immutable */
        char[] chars = password.toCharArray();
        byte[] bytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars, bytes, ITERATIONS, KEY_LENGTH); // creating secure keySpec

        Arrays.fill(chars, Character.MIN_VALUE); // No need to save sensitive data

        try {

            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] securePassword = factory.generateSecret(spec).getEncoded();
            return Optional.of(Base64.getEncoder().encodeToString(securePassword)); // return password hash

        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {

            System.err.println("Exception encountered in hashPassword()");
            return Optional.empty();

        } finally {

            spec.clearPassword();

        }
    }

    public static boolean verifyPassword (String password, String key, String salt) {
        Optional<String> optEncrypted = hashPassword(password, salt);
        return optEncrypted.map(hash -> hash.equals(key)).orElse(false); // return true if both hashes are equal, otherwise false.
    }
}
