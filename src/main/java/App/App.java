package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {
        @Override
        public void start(Stage window) throws IOException, SQLException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("App.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            Image icon = new Image("App/apple.png");
            window.getIcons().add(icon);
            window.setTitle("ShopManagement");
            window.setScene(scene);
            Updatedata updatedata = new Updatedata( "","Sushi");
            updatedata.updatedata();
            System.out.println(updatedata.getProductName());

            window.setResizable(false);
            window.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
