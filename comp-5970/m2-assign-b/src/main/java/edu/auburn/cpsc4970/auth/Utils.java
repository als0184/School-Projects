package edu.auburn.cpsc4970.auth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

public class Utils {

    private static HashMap<String, String> userList = null;

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

    public static boolean checkPassword(String password) {

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        System.out.println(toHexString(salt));

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<hashedPassword.length;i++){
                sb.append(Integer.toHexString(0xff & hashedPassword[i]));
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
}
