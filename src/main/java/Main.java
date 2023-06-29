import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/server_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setScene(scene);
        stage.show();

        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/client_form.fxml"));
        Scene scene1 = new Scene(fxmlLoader1.load());

        Stage stage1 = new Stage();
        stage1.setScene(scene1);
        stage1.show();
    }
}
