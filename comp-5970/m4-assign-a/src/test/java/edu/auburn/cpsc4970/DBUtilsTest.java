package edu.auburn.cpsc4970;

import edu.auburn.cpsc4970.auth.AULoginException;
import edu.auburn.cpsc4970.auth.Utils;
import edu.auburn.cpsc4970.database.DBUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBUtilsTest {

    @Test
    public void testRetrieveValue () {

        assertEquals("Aubie Tiger", DBUtils.getNameForUser("aubie").trim());
    }

}
