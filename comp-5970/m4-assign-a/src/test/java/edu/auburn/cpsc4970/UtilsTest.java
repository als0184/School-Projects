package edu.auburn.cpsc4970;

import edu.auburn.cpsc4970.auth.AULoginException;
import edu.auburn.cpsc4970.auth.Utils;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {

    /**
     * Test retrieving properties value
     */
    @Test
    public void testRetrieveValue () {

        assertEquals("1234", Utils.getProperty("test.value"));
    }


}
