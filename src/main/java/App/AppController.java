package App;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.sql.*;

public class AppController {
    @FXML
    private Button homeButton;
    @FXML
    private Button statusButton;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
//    @FXML
//    private Button aboutButton;
    @FXML
    private FontAwesomeIcon aboutButton;
    @FXML
    private TableView<StoreData> tableview ;
    @FXML
    private TableColumn<StoreData,ImageView> productImage;
    @FXML
    private TableColumn<StoreData,Integer> productID;
    @FXML
    private TableColumn<StoreData,String> productName;
    @FXML
    private TableColumn<StoreData,Integer> productQuantity;

    private ObservableList<StoreData> productList = FXCollections.observableArrayList();




    @FXML
    public void initialize(){
        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        productImage.setCellValueFactory(new PropertyValueFactory<>("productImage"));

        try {
            connectDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        tableview.setItems(productList);
    }

    public void connectDatabase() throws SQLException {
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
            productImageView.setFitHeight(50);
            productImageView.setFitWidth(50);
            productList.add(new StoreData(productImageView,productIdFromSQL,productNameFromSQL,productQuantityFromSQL));
        }
    }


}
