package physicsserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MachineList {
    LinkedList<Machine> list;
    static HashMap<String, Float> ipDistanceMap = new HashMap();
    static LinkedBlockingQueue<ArrayList<String>> toUpdate = new LinkedBlockingQueue();
    
    public MachineList(){
        list = new LinkedList();
    }
    /* Machines A, B, C, D, E
     * A Hosts
     * B Joins A -> A & B
     * C Joins A -> A & B & C
     * C Hosts
     * D Joins C -> A & B & C  |  C & D
     * E Hosts
     * B Joins E -> A & B & C  |  C & D  |  B & E
     */
    
    public void addMachine(Machine newNode){
        for (int i=0; i<list.size(); i++){
            if(list.get(i).distance>newNode.distance) list.add(i, newNode);
            updateMachines(i);
            return;
        }
        list.add(newNode);
        updateMachines(list.size()-1);
    }
    
    public void removeMachine(Machine node){
        int index = -1;
        for(int i=0; i<list.size(); i++){
            if(list.get(i).equals(node)){
                index = i;
                break;
            }
        }
        if (index != -1){
            list.remove(index);
            updateMachines(index);
        }
    }
    
    public void newDistance(Machine node){
        for(int i=0; i<list.size(); i++){
            if(list.get(i).equals(node)){
                removeMachine(node);
                addMachine(node);
            }
        }        
    }
    
    private void updateMachines(int index){
        int left = index-1;
        int middle = index;
        int right = index+1;
        if(index == 0){
            removeSide(list.get(middle).ip, false);
            updateSide(list.get(middle).ip, true, getRelativeDistance(middle, right), list.get(right).ip);
            updateSide(list.get(right).ip, false, getRelativeDistance(right, middle), list.get(middle).ip);
        }
        else if(index == list.size()-1){
            updateSide(list.get(middle).ip, false, getRelativeDistance(middle, left), list.get(left).ip);
            updateSide(list.get(left).ip, true, getRelativeDistance(left, middle), list.get(middle).ip);
            removeSide(list.get(middle).ip, true);
        }
        else{
            updateSide(list.get(left).ip, true, getRelativeDistance(left, middle), list.get(middle).ip);
            updateSide(list.get(middle).ip, false, getRelativeDistance(middle, left), list.get(left).ip);
            updateSide(list.get(middle).ip, true, getRelativeDistance(middle, right), list.get(right).ip);
            updateSide(list.get(right).ip, false, getRelativeDistance(right, middle), list.get(middle).ip);
        }
    }
    
    private float getRelativeDistance(int focusIndex, int targetIndex){
        return ipDistanceMap.get(list.get(targetIndex).ip) - ipDistanceMap.get(list.get(focusIndex).ip);
    }
    
    private void updateSide(String ip, boolean isRightSide, float relativeDistance, String newIp){
        String[] args = {"updateSide", isRightSide?"right":"left", Float.toString(relativeDistance), newIp};
        sendUpdate(ip, args);
    }
        
    private void removeSide(String ip, boolean isRightSide){
        String[] args = {"removeSide", isRightSide?"right":"left"};
        sendUpdate(ip, args);
    }
    
    //String ip, boolean isRightSide, float relativeDistance, String newIp
    private void sendUpdate(String ip, String[] args){
        ArrayList<String> temp = new ArrayList<>(2);
        temp.add(ip);
        String monster = "";
        for(String param : args){
            monster += param + " | ";
        }
        monster = monster.substring(0, monster.length()-3);
        temp.add(monster);
        try {
            toUpdate.put(temp);
        }
        catch (InterruptedException ex) {
            Logger.getLogger(MachineList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}