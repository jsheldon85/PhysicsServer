package physicsserver;

public class PhysicsServer {    
    
    public static void main(String[] args){
        //PhysicsServer server = new PhysicsServer();
        
        NetworkListener listenerThread = new NetworkListener();
        listenerThread.start();
        OutputAdapter.startNetworkPusher();
    }
}
