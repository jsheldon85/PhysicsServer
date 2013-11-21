package physicsserver;

import java.util.LinkedList;

public class MachineList {
    LinkedList<Machine> list;
    String hostIP;
    
    public MachineList(String hostIP){
        list = new LinkedList();
        this.hostIP = hostIP;
    }
    
    public void addMachine(Machine newNode){
        int i=0;
        for (;i<list.size(); i++){
            if(list.get(i).distance>newNode.distance) break;
        }
        if(i==list.size()) list.add(newNode);
        else list.add(i,newNode);
        updateSet(i);
        updateAdjacentSides(i);
    }
    
    public void removeMachine(Machine node){
        int i = 0;
        for(;i<list.size(); i++){
            if(list.get(i).equals(node)) break;
        }
        if (i<list.size()){
            sendRemoveSet(i);
            list.remove(i);
            updateAdjacentSides(i);
        }
    }
    
    public void removeAll(){
        for(int i=0; i<list.size(); i++) sendRemoveSet(i);
        list.clear();
    }
    
    public void newDistance(Machine node){
        for(int i=0; i<list.size(); i++){
            if(list.get(i).equals(node)){
                removeMachine(node);
                addMachine(node);
            }
        }        
    }
    
    private void updateSet(int index){
        updateLeftSide(index);
        updateRightSide(index);
    }
    
    private void updateAdjacentSides(int index){
        updateLeftSide(index+1);
        updateRightSide(index-1);
    }
    
    private void updateLeftSide(int index){
        updateSide(index, false);
    }
    
    private void updateRightSide(int index){
        updateSide(index, true);
    }
    
    private void updateSide(int index, boolean isRightSide){
        if(isValidIndex(index)){
            Machine centerNode = list.get(index);
            String toIP = centerNode.ip, newIP = "";
            double distance = isRightSide? 0.000001:-0.000001;
            
            if(isRightSide? hasAdjacentRight(index) : hasAdjacentLeft(index)){
                Machine adjacentNode = list.get(isRightSide? index+1 : index-1);
                newIP = adjacentNode.ip;
                distance = getDifference(centerNode.distance, adjacentNode.distance);
            }
            OutputAdapter.sendUpdateSide(toIP, hostIP, distance, newIP);
        }
    }
    
    private boolean hasAdjacentLeft(int index){
        return index > 0;
    }
    
    private boolean hasAdjacentRight(int index){
        return index < list.size()-1;
    }
    
    private boolean isValidIndex(int index){
        return index >=0 && index < list.size();
    }
    
    private double getDifference(double focusDist, double targetDist){
        return targetDist - focusDist;
    }

    private void sendRemoveSet(int index){
        OutputAdapter.sendRemoveSet(list.get(index).ip, hostIP);
    }
}