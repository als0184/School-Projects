package edu.auburn.cpsc4970.auth;

import edu.auburn.cpsc4970.database.DBUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    private static HashMap<String, String> userList = null;
    private static ResourceBundle systemProperties = null;

    public static HashMap<String,String> parseUserJson(String userJsonList) {

        if (userList == null) {

            // Initialize return HashMap
            userList = new HashMap();

            // Parse Json String
            JSONObject obj = new JSONObject(userJsonList);
            JSONArray arr = obj.getJSONArray("users");
            for (int i = 0; i < arr.length(); i++) {
                String username = arr.getJSONObject(i).getString("user");
                String password = arr.getJSONObject(i).getString("password");
                userList.put(username, password);
            }
        }

        return userList;
    }

    // TODO - update with more recent hash algorithm
    public static boolean checkPassword(String password) {

        SecureRandom random = new SecureRandom();

        byte[] salt = new byte[16];
        random.nextBytes(salt);
        System.out.println(toHexString(salt));

        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuffer sb = new StringBuffer();
            for (byte b : hashedPassword) {
                sb.append(Integer.toHexString(0xff & b));
            }
            System.out.println(sb);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static String toHexString(byte[] hash)
    {
        /* Convert byte array of hash into digest */
        BigInteger number = new BigInteger(1, hash);

        /* Convert the digest into hex value */
        StringBuilder hexString = new StringBuilder(number.toString(16));

        /* Pad with leading zeros */
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    /**
     * Utility methods to retrieve a value from the resources/system.properties file.
     * @param propertyKey the key of the property to retrieve
     * @return the value associated with the key
     */
    public static String getProperty(String propertyKey) {

        if (systemProperties == null)
             systemProperties = ResourceBundle.getBundle("system");

        return systemProperties.getString(propertyKey);

    }

    public static Cipher getCipger () {

        Cipher cipher = null;
        try {
           cipher = Cipher.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        } catch (NoSuchPaddingException e) {
            logger.error(e.getMessage());
        }

        return cipher;
    }


}
