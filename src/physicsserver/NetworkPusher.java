package physicsserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

class NetworkPusher extends Thread{
    LinkedBlockingQueue<ArrayList<String>> toUpdate;
    
    NetworkPusher(LinkedBlockingQueue<ArrayList<String>> toUpdate) {
        this.toUpdate = toUpdate;
   }

    @Override
    public void run() {
        while (true){
            ArrayList<String> message = null;
            try {
                message = toUpdate.take();
     //           System.out.println("OUT FULL: "+message);
                Socket s = new Socket(message.get(0), 2002);  //Clients on 2002 Servers on 2004
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                
                System.out.println("OUT: "+message.get(1));
                out.println(message.get(1));

                out.close();
                s.close();
            }
            catch (InterruptedException ex) {
                Logger.getLogger(NetworkPusher.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (UnknownHostException ex) {
                Logger.getLogger(NetworkPusher.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException ex) {
                Logger.getLogger(NetworkPusher.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
}