package App;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;

public class AppController {
    @FXML
    private Button inputImage;
    @FXML
    private ImageView imagepreview;
    @FXML
    private TextField inputId;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputQuantity;
    @FXML
    private TextField inputPrice;
    public Label label;
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
    @FXML
    private TableColumn<StoreData,Double> Price;
    @FXML
    private Button addProductButton;
    @FXML
    private Button caccelButton;
    @FXML
    private AnchorPane statusPane;
    @FXML
    private AnchorPane addPane;
    private ObservableList<StoreData> productList = FXCollections.observableArrayList();
    public File getfile;

    @FXML
    public void initialize(){

        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        productImage.setCellValueFactory(new PropertyValueFactory<>("productImage"));
        Price.setCellValueFactory(new PropertyValueFactory<>("priceAsCurrency"));

        addProductButton.setOnAction(e->{
            try {
                addProductToDatabase(getfile);

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        caccelButton.setOnAction(e->{
            inputId.clear();
            inputName.clear();
            inputPrice.clear();
            inputQuantity.clear();
            imagepreview.setImage(null);

        });
        inputImage.setOnAction(e->{
            try {
                FileChooseer();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        try {
            connectDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableview.setItems(productList);

        statusButton.setOnAction(e->{
            statusPane.setVisible(true);
            addPane.setVisible(false);
        });
        addButton.setOnAction(e->{
            addPane.setVisible(true);
            statusPane.setVisible(false);
        });
    }

    public void addProductToDatabase(File file) throws SQLException, IOException {
        byte[] imageBytes = readImage(file);
        int productid = Integer.parseInt(inputId.getText());
        String productname = inputName.getText();
        int productquantity = Integer.parseInt(inputQuantity.getText());
        double productprice = Double.parseDouble(inputPrice.getText());

        String url = "jdbc:mysql://localhost/storedata";
        String user = "root";
        String password = "Pa$$w0rd";
        Connection connection = DriverManager.getConnection(url,user,password);
        String query = "INSERT INTO stock (productimage,productid, productname, productquantity,price) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setBytes(1,imageBytes);
        preparedStatement.setInt(2, productid);
        preparedStatement.setString(3, productname);
        preparedStatement.setInt(4, productquantity);
        preparedStatement.setDouble(5, productprice);
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

        inputId.clear();
        inputName.clear();
        inputPrice.clear();
        inputQuantity.clear();
        imagepreview.setImage(null);
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
            double priceFromSQL = resultSet.getDouble("Price");
            String ConvertPrice = Double.toString(priceFromSQL);
            String PriceAsCurrency = ConvertPrice +"$";
            byte[] ByteImageFromSQL = resultSet.getBytes("productImage");
            //convert to image
            Image productImage = new Image(new ByteArrayInputStream(ByteImageFromSQL));
            ImageView productImageView = new ImageView(productImage);
            productImageView.setFitHeight(50);
            productImageView.setFitWidth(50);
            productList.add(new StoreData(productImageView,productIdFromSQL,productNameFromSQL,productQuantityFromSQL,PriceAsCurrency));
        }
    }

    public void FileChooseer()throws IOException,SQLException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image file","*.png","*.jpg","*,jpeg"));
        File file = fileChooser.showOpenDialog(new Stage());
        readImage(file);
        getfile = file ;
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imagepreview.setImage(image);
        }
    }
    // read image to BLOB (binary large object)
    public byte[] readImage(File file)throws IOException{
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] byteArray = new byte[(int) file.length()];
        fileInputStream.read(byteArray);
        fileInputStream.close();
        return byteArray;
    }

}
