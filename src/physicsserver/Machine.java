package physicsserver;

public class Machine {
    public float distance;
    public String ip;
    
    public Machine(String ip, float distance){
        this.ip = ip;
        this.distance = distance;
    }
    
    public boolean equals(Machine node){
        return (node.ip == ip);
    }
}