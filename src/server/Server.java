package server;


import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import java.util.Vector;

public class Server {

    List<ClientHandler> clients;
    private static int PORT = 8981;
    ServerSocket server = null;
    Socket socket = null;
    public Server(){
        clients = new Vector<>();
        String inf = "Info : ";
        try {
            server = new ServerSocket(PORT);
            System.out.println(inf + "Сервер запущен");
            while (true){
                socket = server.accept();
                System.out.println(inf + "Клиент подключился");

                clients.add(new ClientHandler(this, socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void broadcastMsg(String msg){
        for (ClientHandler client : clients){
            client.sendMsg(msg);
        }
    }
}
