package App;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

import java.sql.*;

public class Updatedata {

    private int productID;
    private String productName;
    private int productQuantity;
    private double productPrice;
    private ImageView imageView;

     private String searchID ;
     private String searchName;

//     private int newProductID;
//     private String newProductName;
//     private int newProductQuantity;
//
//     private double newProductPrice;
//
//     private ImageView newImage;

    public Updatedata(){};
    public Updatedata(String searchID,String searchName){
        this.searchName = searchName;
        this.searchID = searchID;

    }

    public void updatedata() throws SQLException {
        String url = "jdbc:mysql://localhost/storedata";
        String user = "root";
        String password = "Pa$$w0rd";
        Connection connection = DriverManager.getConnection(url, user, password);
        String query = "";
        if (this.searchID != null && this.searchID.isEmpty() && !this.searchName.isEmpty()) {
            query = "SELECT * FROM stock WHERE productName = ?";
        } else if (this.searchID != null && !this.searchID.isEmpty() && this.searchName.isEmpty()) {
            query = "SELECT * FROM stock WHERE productID = ?";
        } else if (this.searchID != null && !this.searchID.isEmpty() && this.searchName != null && !this.searchName.isEmpty()) {
            query = "SELECT * FROM stock WHERE productID = ? AND productName = ?";
        } else {
            throw new IllegalArgumentException("Either searchID or searchName must be provided.");
        }

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        if (this.searchID != null && !this.searchID.isEmpty() && this.searchName.isEmpty()) {
            preparedStatement.setInt(1, Integer.parseInt(this.searchID));
        } else if (this.searchID == null && !this.searchName.isEmpty()) {
            preparedStatement.setString(1, this.searchName);
        } else if (this.searchID != null && !this.searchID.isEmpty() && this.searchName != null && !this.searchName.isEmpty()) {
            preparedStatement.setInt(1, Integer.parseInt(this.searchID));
            preparedStatement.setString(2, this.searchName);
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int productID = resultSet.getInt("productID");
            String productName = resultSet.getString("productName");
            int productQuantity = resultSet.getInt("productQuantity");
            byte[] bytesImage = resultSet.getBytes("productImage");
            double productPrice = resultSet.getDouble("Price");
            // convert image
            Image byteToImage = new Image(new ByteArrayInputStream(bytesImage));
            ImageView imageView = new ImageView(byteToImage);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            // assign to variable
            this.setProductPrice(productPrice);
            this.setProductID(productID);
            this.setProductName(productName);
            this.setImageView(imageView);
            this.setProductQuantity(productQuantity);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    public void NewProductUpdate(byte[] newImage, int newProductID, String newProductName, int newProductQuantity, double newProductPrice) throws SQLException {
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
        preparedStatement.setInt(6, 9); // Use the actual product ID to update
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }



    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
    public void setProductPrice(double productPrice){
        this.productPrice = productPrice;
    }

    public double getProductPrice(){return  this.productPrice;}
    public String getProductName(){
        return this.productName;
    }
    public int getProductID(){
        return this.productID;
    }
    public int getProductQuantity(){
        return this.productQuantity;
    }
    public ImageView getImageView(){
        return this.imageView;
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
}
