package App;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
    private Button welcomeButton;
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
    private Button GotoConsoleButton;
    @FXML
    private Button goToShopButton;
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
    private AnchorPane homePane;
    @FXML
    private AnchorPane consolePane;
    @FXML
    private AnchorPane navbarPane;
    @FXML
    private AnchorPane statusPane;
    @FXML
    private AnchorPane addPane;
    @FXML
    private AnchorPane updatePane;
    @FXML
    private AnchorPane deletePane;
    @FXML
    private AnchorPane shopPane;
    @FXML
    private TextField selectProductId;
    @FXML
    private TextField selectProductName;
    @FXML
    private Button searchButton;
    @FXML
    private Button UpdateToSQLButton;
    @FXML
    private Button cancelUpdateButton;
    private ObservableList<StoreData> productList = FXCollections.observableArrayList();
    public File getfile;

    // update section
    @FXML
    private TextField updateProductId;
    @FXML
    private TextField updateProductName;
    @FXML
    private TextField updateProductQuantity;
    @FXML
    private TextField updateProductPrice;
    @FXML
    private ImageView updateImageView;
    @FXML
    private Button updateImageButton;

    @FXML
    public void initialize(){

        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        productImage.setCellValueFactory(new PropertyValueFactory<>("productImage"));
        Price.setCellValueFactory(new PropertyValueFactory<>("priceAsCurrency"));

        searchButton.setOnAction(e->{
            try {
                updateExistingProduct();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        UpdateToSQLButton.setOnAction(e->{
            try {
                addNewUpdate(getfile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        cancelUpdateButton.setOnAction(e->{
            updateImageView.setImage(null);

            updateProductId.clear();
            updateProductName.clear();
            updateProductPrice.clear();
            updateProductQuantity.clear();
        });
        addProductButton.setOnAction(e->{
            try {
                addProductToDatabase(getfile);

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // the
        caccelButton.setOnAction(e->{
            inputId.clear();
            inputName.clear();
            inputPrice.clear();
            inputQuantity.clear();
            imagepreview.setImage(null);

        });
// the choose image in the add product section
        inputImage.setOnAction(e->{
            try {
                FileChooseer();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // update the image in the update section
        updateImageButton.setOnAction(e->{
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image file","*.png","*.jpg","*,jpeg"));
                File file = fileChooser.showOpenDialog(new Stage());
                readImage(file);
                getfile = file ;
                if (file != null) {
                    Image image = new Image(file.toURI().toString());
                    updateImageView.setImage(image);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });



// connect to database and show the product to the tableview

        try {
            connectDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableview.setItems(productList);


        // nav bar button section
        homeButton.setOnAction(e->{
            homePane.setVisible(true);
            navbarPane.setVisible(true);
            shopPane.setVisible(false);
            consolePane.setVisible(false);
            statusPane.setVisible(false);
            addPane.setVisible(false);
            updatePane.setVisible(false);
            deletePane.setVisible(false);
        });

        welcomeButton.setOnAction(e->{
            homePane.setVisible(true);
            shopPane.setVisible(false);
        });
        GotoConsoleButton.setOnAction(e->{
            statusPane.setVisible(true);
            shopPane.setVisible(false);
            consolePane.setVisible(true);
            navbarPane.setVisible(false);
            homePane.setVisible(false);
        });
        goToShopButton.setOnAction(e->{
            shopPane.setVisible(true);
            homePane.setVisible(false);
        });
        statusButton.setOnAction(e->{
            statusPane.setVisible(true);
            homePane.setVisible(false);
            shopPane.setVisible(false);
            addPane.setVisible(false);
            updatePane.setVisible(false);
            deletePane.setVisible(false);
        });
        addButton.setOnAction(e->{
            addPane.setVisible(true);
            shopPane.setVisible(false);
            homePane.setVisible(false);
            statusPane.setVisible(false);
            updatePane.setVisible(false);
            deletePane.setVisible(false);
        });
        updateButton.setOnAction(e->{
            updatePane.setVisible(true);
            shopPane.setVisible(false);
            homePane.setVisible(false);
            addPane.setVisible(false);
            statusPane.setVisible(false);
            deletePane.setVisible(false);
        });
        deleteButton.setOnAction(e->{
            deletePane.setVisible(true);
            shopPane.setVisible(false);
            homePane.setVisible(false);
            addPane.setVisible(false);
            statusPane.setVisible(false);
            updatePane.setVisible(false);
        });

    }

    public void updateExistingProduct() throws SQLException, IOException {
        String notField = "";
        String searchId = selectProductId.getText();
        String searchName = selectProductName.getText();

        Updatedata updatedata = new Updatedata();

        if(searchId.equals("") && !searchName.equals("")){
            updatedata = new Updatedata(notField,searchName);
            updatedata.updatedata();
            updateProductId.setText(String.valueOf(updatedata.getProductID()));
            updateProductName.setText(updatedata.getProductName());
            updateProductQuantity.setText(String.valueOf(updatedata.getProductQuantity()));
            updateProductPrice.setText(String.valueOf(updatedata.getProductPrice()));
            updateImageView.setImage(updatedata.getImageView().getImage());
            selectProductName.clear();
            selectProductId.clear();
        }else if(!searchId.equals("")&& searchName.equals("")){
            updatedata = new Updatedata(searchId,notField);
            updatedata.updatedata();
            updateProductId.setText(String.valueOf(updatedata.getProductID()));
            updateProductName.setText(updatedata.getProductName());
            updateProductQuantity.setText(String.valueOf(updatedata.getProductQuantity()));
            updateProductPrice.setText(String.valueOf(updatedata.getProductPrice()));
            updateImageView.setImage(updatedata.getImageView().getImage());
            selectProductName.clear();
            selectProductId.clear();
        }else if(!searchId.equals("")&&!searchName.equals("")){
            try{
                updatedata = new Updatedata(searchId,searchName);
                updatedata.updatedata();
                updateProductId.setText(String.valueOf(updatedata.getProductID()));
                updateProductName.setText(updatedata.getProductName());
                updateProductQuantity.setText(String.valueOf(updatedata.getProductQuantity()));
                updateProductPrice.setText(String.valueOf(updatedata.getProductPrice()));
                updateImageView.setImage(updatedata.getImageView().getImage());
                selectProductName.clear();
                selectProductId.clear();
            }catch (NullPointerException nullPointerException){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("product not found ");
                alert.setContentText("There are no specific product match to that product ID or product Name we recommend just search using only ID or Name");
                alert.show();
            }
        }

    }

    public void addNewUpdate(File file) throws IOException, SQLException {
        Updatedata updatedata = new Updatedata();
        // get new data from text field
        int newProductId = Integer.parseInt(updateProductId.getText());
        String newProductName = updateProductName.getText();
        int newProductQuantity = Integer.parseInt(updateProductQuantity.getText());
        double newProductPrice = Double.parseDouble(updateProductPrice.getText());
        byte[] newImageBytes = readImage(file);
        updatedata.NewProductUpdate(newImageBytes,newProductId,newProductName,newProductQuantity,newProductPrice);
        connectDatabase();

        updateProductId.clear();
        updateProductName.clear();
        updateProductQuantity.clear();
        updateProductPrice.clear();
        updateImageView.setImage(null);

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

        // Add the new product to the ObservableList
        Image productImage = new Image(new ByteArrayInputStream(imageBytes));
        ImageView productImageView = new ImageView(productImage);
        productImageView.setFitHeight(50);
        productImageView.setFitWidth(50);
        String ConvertPrice = Double.toString(productprice);
        String PriceAsCurrency = ConvertPrice +"$";
        StoreData newProduct = new StoreData(productImageView, productid, productname, productquantity, PriceAsCurrency);
        productList.add(newProduct);
        preparedStatement.close();
        connection.close();

        inputId.clear();
        inputName.clear();
        inputPrice.clear();
        inputQuantity.clear();
        imagepreview.setImage(null);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add new product succeed");
        alert.setContentText("your product has been successfully added.");
        alert.show();
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
