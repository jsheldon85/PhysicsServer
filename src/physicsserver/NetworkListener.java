package physicsserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkListener {
    
    public static GameList gameList;
    
    public NetworkListener(){
        gameList = new GameList();
    }
    
    public void start(){
        try{
            ServerSocket sock = new ServerSocket(2004);
            while(true){
                Socket client = sock.accept();
    //             System.out.println("Hello, friend!");
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String message = in.readLine();
                System.out.println("IN: " + message);

                String ipAddress = client.getInetAddress().toString().split("/")[1];
    //              System.out.println("ipAddress: "+ipAddress);

                parse(message,ipAddress);//TODO Consider/Make this a seperate thread

                in.close();
                client.close();
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
        
    private void parse(String message,String ipAddress){//General Form: command | param1 | param2 | ... | paramn
        String[] params = message.split(" \\| ");
        switch(params[0]){
            case("hostGame")://command | distance
                System.out.println("SERVER: hostGame");
                gameList.hostGame(createMachine(ipAddress, params[1]));
                break;
            case("joinGame")://command | hostIp | distance
                System.out.println("SERVER: joinGame");
                gameList.joinGame(params[1], createMachine(ipAddress, params[2]));
                break;
            case("leaveGame")://command | hostIp
                System.out.println("SERVER: leaveGame");
                gameList.leaveGame(params[1], createMachine(ipAddress, "0"));
                break;
            case("changeDistance")://command | distance
                System.out.println("SERVER: changeDistance");
                gameList.changeDistance(createMachine(ipAddress, params[1]));
                break;
            case("getGames"):
                System.out.println("updateJoinableGames");
                gameList.getGames(ipAddress);
                break;
        }
    }
    
    private Machine createMachine(String ipAddress, String distance){
        return new Machine(ipAddress, Double.parseDouble(distance));
    }
}