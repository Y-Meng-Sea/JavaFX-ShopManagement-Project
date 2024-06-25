package App;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

import java.sql.*;

public class Updatedata {
    private String searchID ;
    private String searchName;
    private int productID;
    private String productName;
    private int productQuantity;
    private double productPrice;
    private Image productImage;
// Variable for store new product data
    private byte[] newImage;
    private int newProductID;
    private String newProductName;
    private int newProductQuantity;
    private double newProductPrice;
    private int previosID;


//===================== search method for search which product to update =================
    public void searchProduct(String searchID , String searchName) throws SQLException {
        String query= null;
        // connect to database
        String url = "jdbc:mysql://localhost/storedata";
        String user = "root";
        String password = "Pa$$w0rd";
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet =null;
        if(!searchID.equals("")&&searchName.equals("")){
            query = "SELECT * FROM stock WHERE productID= "+searchID;
            resultSet = statement.executeQuery(query);
        } else if (searchID.equals("")&&!searchName.equals("")) {
            query = "SELECT * FROM stock WHERE productName="+"\""+searchName+"\"";
            resultSet = statement.executeQuery(query);
        } else if (!searchID.equals("")&&!searchName.equals("")) {
            query = "SELECT * FROM stock WHERE productID="+"\""+searchID+"\""+"AND productName="+"\""+searchName+"\"";
            resultSet = statement.executeQuery(query);
        }

        try {
            if (resultSet!=null&&resultSet.next()){
                int productID = resultSet.getInt("productID");
                String productName = resultSet.getString("productName");
                int productQuantity = resultSet.getInt("productQuantity");
                double productPrice = resultSet.getDouble("Price");
                byte[] imageByte = resultSet.getBytes("productImage");

                Image productImage = new Image(new ByteArrayInputStream(imageByte));
                this.setProductID(productID);
                this.setProductName(productName);
                this.setProductQuantity(productQuantity);
                this.setProductPrice(productPrice);
                this.setProductImage(productImage);
            } else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("product not found");
                alert.setContentText("There are not product match to you keyword");
                alert.show();
            }
        } catch (NullPointerException nullPointerException){
            throw  nullPointerException;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateProduct(byte[] newImage, int newProductID, String newProductName, int newProductQuantity, double newProductPrice ,int previosID) throws SQLException {
        this.newImage = newImage;
        this.newProductID = newProductID;
        this.newProductName = newProductName;
        this.newProductQuantity = newProductQuantity;
        this.newProductPrice = newProductPrice;
        this.previosID = previosID;
        // connect to database
        String url = "jdbc:mysql://localhost/storedata";
        String user = "root";
        String password = "Pa$$w0rd";
        Connection connection = DriverManager.getConnection(url, user, password);
        String query = "UPDATE stock SET productImage = ?, productID = ?, productName = ?, productQuantity = ?, Price = ? WHERE productID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setBytes(1, newImage);
        preparedStatement.setInt(2, newProductID);
        preparedStatement.setString(3, newProductName);
        preparedStatement.setInt(4, newProductQuantity);
        preparedStatement.setDouble(5, newProductPrice);
        preparedStatement.setInt(6, previosID); // Use the actual product ID to update
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }


    public String getSearchID() {
        return searchID;
    }

    public void setSearchID(String searchID) {
        this.searchID = searchID;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public Image getProductImage() {
        return productImage;
    }

    public void setProductImage(Image productImage) {
        this.productImage = productImage;
    }

    public byte[] getNewImage() {
        return newImage;
    }

    public void setNewImage(byte[] newImage) {
        this.newImage = newImage;
    }

    public int getNewProductID() {
        return newProductID;
    }

    public void setNewProductID(int newProductID) {
        this.newProductID = newProductID;
    }

    public String getNewProductName() {
        return newProductName;
    }

    public void setNewProductName(String newProductName) {
        this.newProductName = newProductName;
    }

    public int getNewProductQuantity() {
        return newProductQuantity;
    }

    public void setNewProductQuantity(int newProductQuantity) {
        this.newProductQuantity = newProductQuantity;
    }

    public double getNewProductPrice() {
        return newProductPrice;
    }

    public void setNewProductPrice(double newProductPrice) {
        this.newProductPrice = newProductPrice;
    }
}
