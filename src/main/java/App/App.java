package App;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

    public class App extends Application {
        @Override
        public void start(Stage window){
            Image icon = new Image("App/apple.png");
            window.getIcons().add(icon);
            window.setTitle("ShopManagement");
            window.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
