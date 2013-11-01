package physicsserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkListener {
    
    public GameList gameList;
    
    public NetworkListener(GameList gameList){
        this.gameList = gameList;
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
        
    private void parse(String message,String ipAddress){//General Form: reqNum | command | param1 | param2 | ... | paramn
        String[] params = message.split(" \\| ");
    //    int i = 0;
    //    for (String param : params){
    //        System.out.println("param"+Integer.toString(i++)+"\t"+param);
    //    }
        try{

            ArrayList<String> temp = new ArrayList<>(2);
            temp.add(ipAddress);
   //         System.out.println("PARAM1: "+params[1]);
            switch(params[1]){
                case("joinGame")://reqNumber | command | hostIp | distance
                    System.out.println("SERVER: joinGame");
                    if(ipIsHost(params[2], temp, params[0])) gameList.joinGame(params[2], createMachine(ipAddress, params[3]));
                    break;
                case("hostGame")://reqNumber | command | distance
                    System.out.println("SERVER: login");
                    temp.add(params[0]);
                    gameList.hostGame(createMachine(ipAddress, params[2]));
                    break;
                case("leaveGame")://reqNumber | command | hostIp
                    System.out.println("SERVER: login");
                    if(ipIsHost(params[2], temp, params[0])) gameList.leaveGame(params[2], createMachine(ipAddress, params[3]));
                    break;
                case("changeDistance")://reqNumber | command | distance
                    System.out.println("SERVER: login");
                    temp.add(params[0]+" | "+users.login(params[2],params[3],ipAddress));
                    break;
            }
            if (temp.size()==2) MachineList.toUpdate.put(temp);
        } catch (InterruptedException ex) {
            Logger.getLogger(NetworkListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Error e){
            System.out.println(e);
        }
    }

    private boolean ipIsHost(String hostIP, ArrayList<String> temp, String reqNumber){
        if(gameList.ipIsHost(hostIP)){
            return true;
        }
        temp.add(reqNumber + " | false");
        return false;
    }
    
    private Machine createMachine(String ipAddress, String distance){
        return new Machine(ipAddress, Float.parseFloat(distance));
    }
}