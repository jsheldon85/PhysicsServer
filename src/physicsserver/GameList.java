package physicsserver;

import java.util.HashMap;

public class GameList {
    HashMap<String, MachineList> hostIPMachineListMap;
    
    public void GameList(){
        
    }
    public void hostGame(Machine node){
        MachineList game = new MachineList();
        hostIPMachineListMap.put(node.ip, game);
    }
    
    public boolean joinGame(String hostIP, Machine node){
        if(ipIsHost(hostIP)){
            hostIPMachineListMap.get(hostIP).addMachine(node);
            return true;
        }
        return false;
    }
    
    public boolean leaveGame(String hostIP, Machine node){
        if(ipIsHost(hostIP)){
            hostIPMachineListMap.get(hostIP).removeMachine(node);
            return true;
        }
        return false;
    }
    
    public void changeDistance(Machine node){
        MachineList.ipDistanceMap.put(node.ip, node.distance);
        for(MachineList game : hostIPMachineListMap.values()){
            game.newDistance(node);
        }
    }
    
    private boolean ipIsHost(String hostIP){
        return hostIPMachineListMap.containsKey(hostIP);
    }
}