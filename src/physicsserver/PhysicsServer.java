package physicsserver;

public class PhysicsServer {    
    
    public static void main(String[] args) {
        //PhysicsServer server = new PhysicsServer();
        NetworkPusher pusherThread = new NetworkPusher(MachineList.toUpdate);
        NetworkListener listenerThread = new NetworkListener();
        pusherThread.start();
        listenerThread.start();
    }
}
