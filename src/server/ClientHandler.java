package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
private Server server;
private Socket socket;
private DataInputStream putinfo;
private DataOutputStream outinfo;

    public ClientHandler(Server server, Socket socket) {

        String inf = "Info : ";
        try {
            this.server = server;
            this.socket = socket;
            putinfo = new DataInputStream(socket.getInputStream());
            outinfo = new DataOutputStream(socket.getOutputStream());

            new Thread(()-> {

                    try {
                    while (true) {


                        String someword = putinfo.readUTF();

                        if (someword.equals("/end")) {

                            break;
                        }
//                        System.out.println("Эхо-сервер : " + someword);
//                       outinfo.writeUTF("Клиент : " + someword);
                        server.broadcastMsg(someword);
                    }
                    } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                        System.out.println(inf + "Клиент отключился от сервера.");
                        server.unsubscribe(this);
                        try {
                            socket.close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }

            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void sendMsg(String msg){
        try {
            outinfo.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
