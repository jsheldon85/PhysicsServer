package physicsserver;

import java.util.HashMap;

public class GameList {
    HashMap<String, MachineList> hostIPMachineListMap;
    static HashMap<String, Double> ipDistanceMap = new HashMap();
    
    public void GameList(){
        
    }
    public void hostGame(Machine node){
        MachineList game = new MachineList(node.ip);
        hostIPMachineListMap.put(node.ip, game);
    }
    
    public boolean joinGame(String hostIP, Machine node){
        updateDistanceMap(node);
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
        updateDistanceMap(node);
        for(MachineList game : hostIPMachineListMap.values()){
            game.newDistance(node);
        }
    }
    
    private void updateDistanceMap(Machine node){
        ipDistanceMap.put(node.ip, node.distance);
    }
    
    private boolean ipIsHost(String hostIP){
        return hostIPMachineListMap.containsKey(hostIP);
    }
}