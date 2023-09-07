package edu.auburn.cpsc4970.auth;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AWSUtilsTest {

    @Test
    public void testRetrieveValue () {

        String jsonString = AWSUtils.getSecret("msb0094-secret");

        if (jsonString==null) {
            assertTrue("Null value returned from AWSUtils.getSecret", false);
        }

        JSONObject obj = new JSONObject(jsonString);
        String user = obj.getString("postgres-user");
        assertEquals("student_read", user);
    }
}
