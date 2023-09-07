package edu.auburn.cpsc4970;

import edu.auburn.cpsc4970.auth.AULoginException;
import edu.auburn.cpsc4970.auth.UsernamePasswordAuthenticator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit test for UsernamePasswordAuthenticatorTest
 */
public class UsernamePasswordAuthenticatorTest {

    private UsernamePasswordAuthenticator usernamePasswordAuthenticator;

    HashMap<String, String> validUsers = new HashMap<>();
    HashMap<String, String> invalidUsers = new HashMap<>();
    HashMap<String, String> invalidPasswords = new HashMap<>();
    String validUser;

    @Before
    public void setUp() throws Exception {
        usernamePasswordAuthenticator = new UsernamePasswordAuthenticator();

        validUser = "Jordan";

        // Setup valid user tests
        validUsers.put("Jordan", "1957");
        validUsers.put("Hare", "2010");
        validUsers.put("Petrie", "1857");

        // Setup invalid user tests
        invalidUsers.put("Voldemort","kalkde");
        invalidUsers.put("Goblin", "djqp3");

        // Setup invalid password tests
        invalidPasswords.put("Jordan", "jdkse");
        invalidPasswords.put("Hare", "Wrong");
        invalidPasswords.put("Petrie", "pppddd");
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test Null Value Username
     */
    @Test
    public void testUsernameNullValue() throws AULoginException, IOException {
        exception.expect(AULoginException.class);
        exception.expectMessage("Username can not be null");

        usernamePasswordAuthenticator.login(null, "Password");
    }

    /**
     * Test Null Value Password
     */
    @Test
    public void testPasswordNullValue() throws AULoginException, IOException {
        exception.expect(AULoginException.class);
        exception.expectMessage("Password can not be null");

        usernamePasswordAuthenticator.login(validUser, null);
    }

    /**
     * Test Invalid Password
     */
    @Test
    public void testInvalidPasswords() throws AULoginException, IOException {

        for (Map.Entry<String, String> entry : invalidPasswords.entrySet()) {
            String errorMessage = "Password \""+entry.getValue()+"\" for \"" + entry.getKey() + "\" is invalid";
            try {
                usernamePasswordAuthenticator.login(entry.getKey(), entry.getValue());
            } catch (AULoginException e) {
                assertEquals(errorMessage,e.getMessage() );
            }
        }
    }

    /**
     * Test Invalid Users
     */
    @Test
    public void testInvalidUsers() throws AULoginException, IOException {

        for (Map.Entry<String, String> entry : invalidUsers.entrySet()) {
            String errorMessage = "User \"" + entry.getKey() + "\" does not exist.";
            try {
                usernamePasswordAuthenticator.login(entry.getKey(), entry.getValue());
            } catch (AULoginException e) {
                assertEquals(errorMessage, e.getMessage());
            }
        }
    }

    /**
     * Test Valid Users
     */
    @Test
    public void testValidUsers() throws AULoginException, IOException {
        for (Map.Entry<String, String> entry : validUsers.entrySet()) {
            usernamePasswordAuthenticator.login(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Password complexity tests
     */
    @Test
    public void testPasswordComplexity() throws AULoginException, IOException {

        assertTrue(usernamePasswordAuthenticator.checkPasswordComplexity("123"));
        assertTrue(usernamePasswordAuthenticator.checkPasswordComplexity("1234567"));
        assertFalse(usernamePasswordAuthenticator.checkPasswordComplexity("1"));
        assertFalse(usernamePasswordAuthenticator.checkPasswordComplexity("12"));

    }
}


