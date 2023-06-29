package controller;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerFormController implements Initializable {

    @FXML
    private ImageView img;

    @FXML
    private Label lbl;

    private ServerSocket serverSocket;

    private Socket socket;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(1234);

                socket = serverSocket.accept();
                System.out.println("Client connected");
                new Thread(() -> {
                    try {
                        setImage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
//                new Thread(() -> {
//                    try {
//                        setMessage();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }).start();




            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void setMessage() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String message = br.readLine();
        System.out.println(message + " received!");
    }

    public void setImage() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        BufferedImage bufferedImage = ImageIO.read(bis);

        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        Platform.runLater(() -> {
            img.setImage(image);
            lbl.setText("Image received!");
        });
    }
}
