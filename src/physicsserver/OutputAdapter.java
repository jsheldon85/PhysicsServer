package physicsserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OutputAdapter {
    
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
    
    private static void sendUpdate(String ip, String[] args){
        String message = "";
        for(String param : args){
            message += param + " | ";
        }
        message = message.substring(0, message.length()-3);
        send(ip, message);
    }
    
    private static void send(String ip, String message) {
        try {
            Socket s = new Socket(ip, 2002);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);

            System.out.println("OUT:  "+message);
            out.println(message);

            out.close();
            s.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(OutputAdapter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OutputAdapter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
