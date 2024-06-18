package App;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;

public class App extends Application {
    public void connectDatabase() throws SQLException {
        ObservableList<StoreData> productList = FXCollections.observableArrayList();
        String url = "jdbc:mysql://localhost/storedata";
        String user = "root";
        String password = "Pa$$w0rd";
        Connection connection = DriverManager.getConnection(url,user,password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from stock");
        while (resultSet.next()){
            int productIdFromSQL = resultSet.getInt("productID");
            String productNameFromSQL = resultSet.getString("productName");
            int productQuantityFromSQL = resultSet.getInt("productQuantity");
            byte[] ByteImageFromSQL = resultSet.getBytes("productImage");
            //convert to image
            Image productImage = new Image(new ByteArrayInputStream(ByteImageFromSQL));
            ImageView productImageView = new ImageView(productImage);
            productList.add(new StoreData(productImageView,productIdFromSQL,productNameFromSQL,productQuantityFromSQL));
        }
    }
        @Override
        public void start(Stage window) throws IOException, SQLException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("App.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Image icon = new Image("App/apple.png");
            connectDatabase();
            window.getIcons().add(icon);
            window.setTitle("ShopManagement");
            window.setScene(scene);
            window.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
