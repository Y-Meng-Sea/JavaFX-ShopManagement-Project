package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class App extends Application {

        @Override
        public void start(Stage window) throws IOException, SQLException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("App.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Image icon = new Image("App/apple.png");
            window.getIcons().add(icon);
            window.setTitle("ShopManagement");
            window.setScene(scene);
            window.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
