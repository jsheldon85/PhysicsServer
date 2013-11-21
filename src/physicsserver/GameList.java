package physicsserver;

import java.util.HashMap;

public class GameList {
    private HashMap<String, MachineList> hostIPMachineListMap;
    
    public void GameList(){
        
    }
    public void hostGame(Machine node){
        if(!hostIPMachineListMap.containsKey(node.ip)){
            MachineList game = new MachineList(node.ip);
            hostIPMachineListMap.put(node.ip, game);
        }
    }
    
    public boolean joinGame(String hostIP, Machine node){
        if(isIPHost(hostIP)){
            hostIPMachineListMap.get(hostIP).addMachine(node);
            return true;
        }
        return false;
    }
    
    public boolean leaveGame(String hostIP, Machine node){
        if(isIPHost(hostIP)){
            if(hostIP.equals(node.ip)) endGame(hostIP);
            else hostIPMachineListMap.get(hostIP).removeMachine(node);
            return true;
        }
        return false;
    }
    
    public void changeDistance(Machine node){
        for(MachineList game : hostIPMachineListMap.values()){
            game.newDistance(node);
        }
    }
    
    public void getGames(String ip){
        OutputAdapter.sendUpdateGames(ip, (String[])hostIPMachineListMap.keySet().toArray());
    }
    
    private void endGame(String hostIP){
        hostIPMachineListMap.get(hostIP).removeAll();
        hostIPMachineListMap.remove(hostIP);
    }
    
    private boolean isIPHost(String hostIP){
        return hostIPMachineListMap.containsKey(hostIP);
    }
}