package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ClientFormController implements Initializable {

    private Socket socket;

    @FXML
    private ImageView img;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            socket = new Socket("localhost", 1234);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void btnSendOnAction(ActionEvent event) {
        try {

            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            Image fxImage = img.getImage();
            java.awt.Image awtImage = convertToAWTImage(fxImage);

            BufferedImage bufferedImage = new BufferedImage(awtImage.getWidth(null), awtImage.getHeight(null),BufferedImage.TYPE_INT_ARGB);
//            BufferedImage bufferedImage = new BufferedImage((int)fxImage.getWidth(), (int)fxImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics graphics = bufferedImage.createGraphics();
            graphics.drawImage(awtImage, 0, 0, null);
            graphics.dispose();

            ImageIO.write(bufferedImage,"png", bos);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private java.awt.Image convertToAWTImage(Image fxImage) {
        int width = (int) fxImage.getWidth();
        int height = (int) fxImage.getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        SwingFXUtils.fromFXImage(fxImage, bufferedImage);

        return bufferedImage;
    }

    @FXML
    void btnChooseOnAction(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("c:\\"));

        File file = fileChooser.showOpenDialog(new Stage());
        String filePath = file.getPath();

        img.setImage(new Image(filePath));

    }

    @FXML
    void btnSendMOnAction(ActionEvent event) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write("Hello hello hello");
        bw.newLine();
        bw.flush();
    }
}
