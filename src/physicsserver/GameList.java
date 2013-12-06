package physicsserver;

import java.util.HashMap;

public class GameList {
    private HashMap<String, MachineList> hostIPMachineListMap;
    
    public GameList(){
        hostIPMachineListMap = new HashMap<>();
    }
    public void hostGame(Machine node){
        if(!hostIPMachineListMap.containsKey(node.ip)){
            MachineList game = new MachineList(node.ip);
            hostIPMachineListMap.put(node.ip, game);
            joinGame(node.ip, node);
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
        if(hostIPMachineListMap.size()==0){
            OutputAdapter.sendUpdateGames(ip, new String[]{""});
        }
        String[] games = new String[hostIPMachineListMap.size()];
        int i=0;
        for(String game : hostIPMachineListMap.keySet()){
            System.out.println("Game: "+game);
            games[i++] = game;
        }
        OutputAdapter.sendUpdateGames(ip, games);
    }
    
    private void endGame(String hostIP){
        hostIPMachineListMap.get(hostIP).removeAll();
        hostIPMachineListMap.remove(hostIP);
    }
    
    private boolean isIPHost(String hostIP){
        return hostIPMachineListMap.containsKey(hostIP);
    }
}