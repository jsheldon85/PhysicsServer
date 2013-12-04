package physicsserver;

public class PhysicsServer {    
    
    public static void main(String[] args){  
        NetworkListener listenerThread = new NetworkListener();
        listenerThread.start();
    }
}
