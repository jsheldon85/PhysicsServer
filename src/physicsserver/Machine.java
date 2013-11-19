package physicsserver;

public class Machine {
    public double distance;
    public String ip;
    
    public Machine(String ip, double distance){
        this.ip = ip;
        this.distance = distance;
    }
    
    public boolean equals(Machine node){
        return (node.ip.equals(ip));
    }
}