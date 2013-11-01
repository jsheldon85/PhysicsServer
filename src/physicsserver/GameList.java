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
    
    public boolean ipIsHost(String hostIP){
        return hostIPMachineListMap.containsKey(hostIP);
    }
    
    public void joinGame(String hostIP, Machine node){
        hostIPMachineListMap.get(hostIP).addMachine(node);
    }
    
    public void leaveGame(String hostIP, Machine node){
        hostIPMachineListMap.get(hostIP).removeMachine(node);
    }
    
    public void changeDistance(Machine node){
        MachineList.ipDistanceMap.put(node.ip, node.distance);
        for(MachineList game : hostIPMachineListMap.values()){
            game.newDistance(node);
        }
    }
}