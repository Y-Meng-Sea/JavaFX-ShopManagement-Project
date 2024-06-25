package App;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.nio.ByteBuffer;
import java.sql.*;
import java.util.Arrays;

public class AppController {

//=================== Side bar  UI ====================//

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


//=================== Pane UI ====================//

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


//=================== Product statue UI ====================//

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
    private ObservableList<StoreData> productList = FXCollections.observableArrayList();
    public File getfile;

//=================== ADD products  UI ====================//

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
    @FXML
    private Button addProductButton;
    @FXML
    private Button caccelButton;

//=================== Update product UI ====================//
    @FXML
    private TextField selectProductId; // select product using ID
    @FXML
    private TextField selectProductName; // select product using name
    @FXML
    private Button searchButton;  // search for product to update button
    @FXML
    private Button UpdateToSQLButton;  // update to databases button
    @FXML
    private Button cancelUpdateButton; // cancel update to database button
    @FXML
    private TextField updateProductId;
    @FXML
    private TextField updateProductName;
    @FXML
    private TextField updateProductQuantity;
    @FXML
    private TextField updateProductPrice;
    @FXML
    private ImageView updateProductImage;
    @FXML
    private Button updateImageButton; // import new image button

//=================== delete product UI ====================//

    @FXML
    private TextField productIDForDelete;
    @FXML
    private TextField nameProductForDelete;
    @FXML
    private Button searchForDelete;
    @FXML
    private TextField deleteProductID;
    @FXML
    private TextField deleteProductName;
    @FXML
    private ImageView deleteProductImage;
    @FXML
    private Button deleteProductButton ;
    @FXML
    private Button cancelDeleteButton;


    @FXML
    public void initialize(){

    //================= push product to the tableView Section ====================//
        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<>("productQuantity"));
        productImage.setCellValueFactory(new PropertyValueFactory<>("productImage"));
        Price.setCellValueFactory(new PropertyValueFactory<>("priceAsCurrency"));
        try {
            connectDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableview.setItems(productList);


    //================= Add new product feature====================//
        addProductButton.setOnAction(e->{
            try {
                addProductToDatabase(getfile);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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
        caccelButton.setOnAction(e->{
            inputId.clear();
            inputName.clear();
            inputPrice.clear();
            inputQuantity.clear();
            imagepreview.setImage(null);

        });


    //================= Update product feature ====================//
        searchButton.setOnAction(e->{
            try {
                searchProductForUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        updateImageButton.setOnAction(e-> {
            try {
                FileChooseerForUpdate();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        UpdateToSQLButton.setOnAction(e->{
            try {
                updateTheSelectProduct();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        //================= Delete product feature ====================//
        searchForDelete.setOnAction(e->{
            try {
                searchProductForDelete();
                System.out.println("click");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        deleteProductButton.setOnAction(e->{
            try {
                DeleteProduct();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });


    //================= Interactive with side bar Section ====================//
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
        deleteProductID.setEditable(false);
        deleteProductName.setEditable(false);

    }


    //================= Add product Section ====================//
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

    int previousID;
    String previousName;
    int previousQuantity ;
    double previousPrice;
    byte[] previousImage;

    //================= Update product Section ====================//
    public void searchProductForUpdate() throws SQLException {
        String searchId = selectProductId.getText();
        String searchName = selectProductName.getText();
        Updatedata updatedata = new Updatedata();
        updatedata.searchProduct(searchId,searchName);
        updateProductId.setText(String.valueOf(updatedata.getProductID()));
        updateProductName.setText(updatedata.getProductName());
        updateProductQuantity.setText(String.valueOf(updatedata.getProductQuantity()));
        updateProductPrice.setText(String.valueOf(updatedata.getProductPrice()));
        updateProductImage.setImage(updatedata.getProductImage());
        updateProductImage.setFitWidth(150);
        updateProductImage.setFitHeight(150);
        // get the previous version
        previousID = updatedata.getProductID();
        previousName = updatedata.getProductName();
        previousQuantity = updatedata.getProductQuantity();
        previousPrice = updatedata.getProductPrice();
        Image image = updatedata.getProductImage();
        previousImage = getImageData(image);
    }
    public void updateTheSelectProduct() throws SQLException {


    }

    //================= Delete product Section ====================//
    public void searchProductForDelete() throws SQLException {
        String notField = "";
        String searchidfordelete = productIDForDelete.getText();
        String searchNamefordelete = nameProductForDelete.getText();
        if(!searchidfordelete.equals("") && searchNamefordelete.equals("")){
            DeleteData deleteData = new DeleteData(searchidfordelete,notField);
            deleteData.deleteproduct();
            deleteProductID.setText(String.valueOf(deleteData.getProductId()));
            deleteProductName.setText(deleteData.getProductName());
            deleteProductImage.setImage(deleteData.getProductImage());
            deleteProductID.setEditable(false);
            deleteProductName.setEditable(false);
            productIDForDelete.clear();
            nameProductForDelete.clear();
        }else if(searchidfordelete.equals("") && !searchNamefordelete.equals("")){
            DeleteData deleteData = new DeleteData(notField,searchNamefordelete);
            deleteData.deleteproduct();
            deleteProductID.setText(String.valueOf(deleteData.getProductId()));
            deleteProductName.setText(deleteData.getProductName());
            deleteProductImage.setImage(deleteData.getProductImage());
            deleteProductID.setEditable(false);
            deleteProductName.setEditable(false);
            productIDForDelete.clear();
            nameProductForDelete.clear();
        }else if(!searchidfordelete.equals("")&&!searchNamefordelete.equals("")){
            DeleteData deleteData = new DeleteData(searchidfordelete,searchNamefordelete);
            deleteData.deleteproduct();
            deleteProductID.setText(String.valueOf(deleteData.getProductId()));
            deleteProductName.setText(deleteData.getProductName());
            deleteProductImage.setImage(deleteData.getProductImage());
            deleteProductID.setEditable(false);
            deleteProductName.setEditable(false);
            productIDForDelete.clear();
            nameProductForDelete.clear();
        }
    }
    public void DeleteProduct() throws SQLException {
        System.out.println(deleteProductID.getText());
        DeleteData deleteData = new DeleteData();
        deleteData.delete(deleteProductID.getText(),deleteProductName.getText());
        deleteProductID.clear();
        deleteProductName.clear();
        deleteProductImage.setImage(null);
    }

    //================= Connect to the database section and add product to observableList ====================//
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


    //================= the File chooser Function ====================//

    public void FileChooseerForUpdate()throws IOException,SQLException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image file","*.png","*.jpg","*,jpeg"));
        File file = fileChooser.showOpenDialog(new Stage());
        readImage(file);
        getfile = file ;
        try{
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                updateProductImage.setImage(image);
            }
        }catch (Error error){
           updateProductImage.setImage(updateProductImage.getImage());
        }
    }
    public void FileChooseer()throws IOException,SQLException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image file","*.png","*.jpg","*,jpeg"));
        File file = fileChooser.showOpenDialog(new Stage());
        readImage(file);
        getfile = file ;
        try{
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                imagepreview.setImage(image);
            }
        }catch (NullPointerException nullPointerException){
            throw nullPointerException;
        }
    }


    //================= the Convert file image to Bytes Arrays Function ====================//
    // read image to BLOB (binary large object)

    public byte[] readImage(File file)throws IOException{
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] byteArray = new byte[(int) file.length()];
        fileInputStream.read(byteArray);
        fileInputStream.close();
        return byteArray;
    }

    public byte[] getImageData(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        // ARGB format (4 bytes per pixel)
        WritablePixelFormat<ByteBuffer> format = PixelFormat.getByteBgraInstance();

        // Create byte buffer for image data
        ByteBuffer byteBuffer = ByteBuffer.allocate(width * height * 4);
        PixelReader pixelReader = image.getPixelReader();

        // Read pixels into byte buffer
        pixelReader.getPixels(0, 0, width, height, format, byteBuffer, width * 4);

        // Convert ByteBuffer to byte array
        byte[] imageData = new byte[byteBuffer.capacity()];
        byteBuffer.get(imageData);

        return imageData;
    }

}
