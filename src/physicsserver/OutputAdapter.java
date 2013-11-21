package physicsserver;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OutputAdapter {
    private static LinkedBlockingQueue<ArrayList<String>> toUpdate = new LinkedBlockingQueue();
    
    public static void startNetworkPusher(){
        NetworkPusher pusher = new NetworkPusher(toUpdate);
        pusher.start();
    }
    
    public static void sendUpdateSide(String ip, String hostIP, double relativeDistance, String newIp){
        String[] args = {"updateSide", hostIP, Double.toString(relativeDistance), newIp};
        sendUpdate(ip, args);
    }
    
    public static void sendRemoveSet(String ip, String hostIP){
        String[] args = {"removeSet", hostIP};
        sendUpdate(ip, args);
    }
    
    public static void sendUpdateGames(String ip, String[] gameList){
        String message = "";
        for(String hostIP : gameList){
            message += hostIP+",";
        }
        message = message.substring(0, message.length()-1);
        String[] args = {"updateGames", message};
        sendUpdate(ip, args);
    }
    
    //String ip, boolean isRightSide, double relativeDistance, String newIp
    private static void sendUpdate(String ip, String[] args){
        ArrayList<String> temp = new ArrayList<>(2);
        temp.add(ip);
        String message = "";
        for(String param : args){
            message += param + " | ";
        }
        message = message.substring(0, message.length()-3);
        temp.add(message);
        try {
            toUpdate.put(temp);
        }
        catch (InterruptedException ex) {
            Logger.getLogger(MachineList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
