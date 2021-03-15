package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public TextArea textArea;
    @FXML
    public TextField textField;

    private final String IP_ADDRESS = "localhost";
    private final int PORT = 8981;
    private Socket socket;
    DataInputStream putinfo;
    DataOutputStream outinfo;
    String inf = "Info : ";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            socket = new Socket(IP_ADDRESS, PORT);
            putinfo = new DataInputStream(socket.getInputStream());
            outinfo = new DataOutputStream(socket.getOutputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    while (true) {


                        String someword = putinfo.readUTF();

                        if (someword.equals("/end")) {
                            break;
                        }

                        textArea.appendText(someword + "\n");
                    }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            System.out.println(inf + "Мы отключились от сервера.");
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMsg(ActionEvent actionEvent) {
        try {
            outinfo.writeUTF(textField.getText());
        textField.clear();
        textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
