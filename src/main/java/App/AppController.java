package App;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.sql.*;
import java.util.Iterator;

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
    private ImageView previousImage;
    @FXML
    private TextField previousId;
    @FXML
    private TextField previousName;
    @FXML
    private TextField previousPrice;
    @FXML
    private TextField previousQuantity;
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

    ///
    private File UpdateImageFile;

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
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Wrong input");
               alert.setContentText("You search is not following our instruction");
               alert.show();
//
            }
        });

        updateImageButton.setOnAction(e-> {
            try {

                FileChooseer1();
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
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        cancelUpdateButton.setOnAction(e->{
            updateProductImage.setImage(null);
            updateProductName.clear();
            updateProductId.clear();
            updateProductPrice.clear();
            updateProductQuantity.clear();
        });

        //================= Delete product feature ====================//
        searchForDelete.setOnAction(e->{
            try {
                searchProductForDelete();
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
        cancelDeleteButton.setOnAction(e->{
            deleteProductName.clear();
            deleteProductID.clear();
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

    // ========================== update product feature=============================//
    private  int PreviousID;
    private byte[] previousImageByte;
    public void searchProductForUpdate() throws SQLException {
        String searchId = selectProductId.getText();
        String searchName = selectProductName.getText();
        Updatedata updatedata = new Updatedata();
        updatedata.searchProduct(searchId, searchName);
        previousId.setText(String.valueOf(updatedata.getProductID()));
        previousName.setText(updatedata.getProductName());
        previousQuantity.setText(String.valueOf(updatedata.getProductQuantity()));
        previousPrice.setText(String.valueOf(updatedata.getProductPrice()));
        previousImage.setImage(updatedata.getProductImage());
        previousImageByte = Updatedata.getPreviousImage(searchId,searchName);
        PreviousID =Integer.parseInt(previousId.getText());
        previousId.setEditable(false);
        previousName.setEditable(false);
        previousQuantity.setEditable(false);
        previousPrice.setEditable(false);
    }
    private int newId = 0;
    private String newName = "";
    private int newQuantity = 0;
    private double newPrice = 0;
    private byte [] newImage = null;
    private void updateTheSelectProduct() throws SQLException, IOException {
        if(!updateProductId.getText().isEmpty()){
            newId =Integer.parseInt(updateProductId.getText());
        }else {
            newId =Integer.parseInt(previousId.getText());
        }

        if(!updateProductName.getText().isEmpty()){
            newName = updateProductName.getText();
        }else {
            newName = previousName.getText();
        }

        if(!updateProductQuantity.getText().isEmpty()){
            newQuantity =Integer.parseInt(updateProductQuantity.getText());
        }else{
            newQuantity = Integer.parseInt(previousQuantity.getText());
        }


        if(!updateProductPrice.getText().isEmpty()){
        newPrice = Double.parseDouble(updateProductPrice.getText());
        }else{
            newPrice = Double.parseDouble(previousPrice.getText());
        }

        if(updateProductImage.getImage() != null){
            newImage =readImage(UpdateImageFile);
        }

        Updatedata updatedata = new Updatedata();
        if (newId == 0 && newName.equals("") && newQuantity == 0 && newPrice == 0 && newImage== null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No change");
            alert.setContentText("There are no specific change in you product !!");
            alert.show();
        }else if (newImage == null){
            updatedata.updateProduct(previousImageByte,newId,newName,newQuantity,newPrice,PreviousID);

        }else {
            updatedata.updateProduct(newImage,newId,newName,newQuantity,newPrice,PreviousID);

        }
        // Update the observable list
        for (StoreData product : productList) {
            if (product.getProductId() == PreviousID) {
                if (newImage != null) {
                    Image image = new Image(new ByteArrayInputStream(newImage));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    product.setProductImage(imageView);
                }else {
                    Image image = new Image(new ByteArrayInputStream(previousImageByte));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    product.setProductImage(imageView);
                }
                if (newId != 0) {
                    product.setProductId(newId);
                }else{
                    product.setProductID(PreviousID);
                }
                if (!newName.isEmpty()) {
                    product.setProductName(newName);
                }else {
                    product.setProductName(previousName.getText());
                }
                if (newQuantity != 0) {
                    product.setProductQuantity(newQuantity);
                }else {
                    product.setProductQuantity(Integer.parseInt(previousQuantity.getText()));
                }
                if (newPrice != 0.0) {
                    product.setPriceAsCurrency(newPrice + "$");
                }else {
                    product.setPriceAsCurrency(previousPrice.getText() + "$");
                }
                break;
            }
        }
        tableview.refresh();
        updateProductId.clear();
        updateProductName.clear();
        updateProductQuantity.clear();
        updateProductPrice.clear();
        updateProductImage.setImage(null);
        previousId.clear();
        previousName.clear();
        previousQuantity.clear();
        previousPrice.clear();
        previousImage.setImage(null);
        selectProductId.clear();
        selectProductName.clear();
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
        String deletetId = deleteProductID.getText();
        String deleteName = deleteProductName.getText();
        // Iterate over the productList to find and delete the product
        Iterator<StoreData> iterator = productList.iterator();
        while (iterator.hasNext()) {
            StoreData product = iterator.next();
            if (String.valueOf(product.getProductId()).equals(deletetId) || String.valueOf(product.getProductName()).equals(deleteName)) {
                // Match found, delete the product
                iterator.remove();

                // Optionally, delete from database
                DeleteData deleteData = new DeleteData();
                deleteData.delete(deletetId, product.getProductName());

                // Clear fields and reset image
                deleteProductID.clear();
                deleteProductName.clear();
                deleteProductImage.setImage(null);

                // Update TableView immediately
                Platform.runLater(() -> {
                    tableview.setItems(null); // Clear TableView
                    tableview.setItems(productList); // Re-set TableView with updated productList
                });

                // Exit loop since we found and deleted the product
                break;
            }
        }
    }


    //================= Connect to the database section and add product to observableList ====================//
    public void connectDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost/storedata";
        String user = "root";
        String password = "Pa$$w0rd";
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from stock");

        while (resultSet.next()) {
            int productIdFromSQL = resultSet.getInt("productID");
            String productNameFromSQL = resultSet.getString("productName");
            int productQuantityFromSQL = resultSet.getInt("productQuantity");
            double priceFromSQL = resultSet.getDouble("Price");
            String convertPrice = Double.toString(priceFromSQL);
            String priceAsCurrency = convertPrice + "$";
            byte[] byteImageFromSQL = resultSet.getBytes("productImage");

            // Convert byte[] to image
            Image productImage = new Image(new ByteArrayInputStream(byteImageFromSQL));
            ImageView productImageView = new ImageView(productImage);
            productImageView.setFitHeight(50);
            productImageView.setFitWidth(50);

            // Add product to observable list
            productList.add(new StoreData(productImageView, productIdFromSQL, productNameFromSQL, productQuantityFromSQL, priceAsCurrency));
        }

        resultSet.close();
        statement.close();
        connection.close();
    }


    public static byte[] imageToByteArray(Image image) {
        byte[] byteArray = null;
        try {
            // Determine image dimensions
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            // Create byte array output stream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Create pixel reader for the image
            PixelReader pixelReader = image.getPixelReader();

            // Iterate through pixels to extract color information
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color color = pixelReader.getColor(x, y);
                    int red = (int) (((Color) color).getRed() * 255);
                    int green = (int) (color.getGreen() * 255);
                    int blue = (int) (color.getBlue() * 255);

                    // Write RGB values to output stream
                    outputStream.write(red);
                    outputStream.write(green);
                    outputStream.write(blue);
                }
            }

            // Convert output stream to byte array
            byteArray = outputStream.toByteArray();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }
    //================= the File chooser Function ====================//
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
    public void FileChooseer1()throws IOException,SQLException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image file","*.png","*.jpg","*,jpeg"));
        File file = fileChooser.showOpenDialog(new Stage());
        readImage(file);
        UpdateImageFile = file ;
        try{
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                updateProductImage.setImage(image);

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

}
