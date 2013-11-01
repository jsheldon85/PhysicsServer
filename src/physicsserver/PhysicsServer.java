package physicsserver;

public class PhysicsServer {    
    
    public static void main(String[] args) {
        PhysicsServer server = new PhysicsServer();
        NetworkPusher thread = new NetworkPusher(MachineList.toUpdate);
        thread.start();
        server.NetworkPusher.start();
    }
}
