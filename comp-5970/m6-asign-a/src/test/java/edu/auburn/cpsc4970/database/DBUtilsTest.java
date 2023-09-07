package edu.auburn.cpsc4970.database;


import edu.auburn.cpsc4970.auth.AULoginException;
import edu.auburn.cpsc4970.database.DBUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBUtilsTest {

    @Test
    public void testRetrieveValue () {

        assertEquals("Aubie Tiger", DBUtils.getNameForUser("aubie"));
    }

    @Test
    public void testGetPermissionValue () {

        try {
            assertEquals(0, DBUtils.getPermissionForUser("aubie"));
            assertEquals(0, DBUtils.getPermissionForUser("Hare"));
        } catch (AULoginException e) {
            assertTrue(false);
        }
    }

}
