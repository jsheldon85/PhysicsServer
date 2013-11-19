/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package physicsserver;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vsc243
 */
public class MachineListTest {
    MachineList instance;
    Machine node0;
    Machine node1;
    Machine node2;
    
    public MachineListTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new MachineList("42.42.42.42");
        node0 = new Machine("0.0.0.0", 0);
        node1 = new Machine("0.0.0.1", 1);
        node2 = new Machine("0.0.0.2", -2);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAddMachine() {
        instance.addMachine(node0);
        assert(instance.list.get(0).equals(node0) && instance.list.size()==1);
        instance.addMachine(node1);
        assert(instance.list.get(0).equals(node0) && instance.list.get(1).equals(node1) && instance.list.size()==2);
        instance.addMachine(node2);
        assert(instance.list.get(0).equals(node2) && instance.list.get(1).equals(node0) && instance.list.get(2).equals(node1) && instance.list.size()==3);
    }

    @Test
    public void testRemoveMachine() {
        instance.addMachine(node0);
        instance.addMachine(node1);
        instance.addMachine(node2);
        instance.removeMachine(node2);
        assert(instance.list.get(0).equals(node0) && instance.list.get(1).equals(node1) && instance.list.size()==2);
        instance.removeMachine(node1);
        assert(instance.list.get(0).equals(node0) && instance.list.size()==1);
        instance.removeMachine(node0);
        assert(instance.list.size()==0);
    }
    
    @Test
    public void testRemoveMachineFromNothing(){
        instance.removeMachine(node0);
        assert(instance.list.size()==0);
    }

    /**
     * Test of newDistance method, of class MachineList.
     */
    @Test
    public void testNewDistance() {
        instance.addMachine(node0);
        instance.addMachine(node1);
        instance.addMachine(node2);
        Machine newNode0 = new Machine("0.0.0.0", -3);
        instance.newDistance(newNode0);
        assert(instance.list.get(0).equals(newNode0) && instance.list.size()==3);
    }
}