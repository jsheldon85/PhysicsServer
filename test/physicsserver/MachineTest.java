package physicsserver;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MachineTest {
    Machine node0;
    Machine node1;
    Machine node2;
    
    public MachineTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        node0 = new Machine("0.0.0.0", 0);
        node1 = new Machine("0.0.0.0", 1);
        node2 = new Machine("0.0.0.2", -2);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of equals method, of class Machine.
     */
    @Test
    public void testEquals() {
        assert(node0.equals(node0));
        assert(node0.equals(node1));
        assert(!node0.equals(node2));
    }
}