package App;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.sql.*;

public class Updatedata {
    private int productID;
    private String productName;
    private int productQuantity;
    private double productPrice;
    private Image productImage;
    private byte[] productImageByteArray;

    //===================== search method for search which product to update =================

        public void searchProduct(String searchID, String searchName) throws SQLException {
            String query = null;
            String url = "jdbc:mysql://localhost/storedata";
            String user = "root";
            String password = "Pa$$w0rd";
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;

            if (!searchID.isEmpty() && searchName.isEmpty()) {
                query = "SELECT * FROM stock WHERE productID= " + searchID;
            } else if (searchID.isEmpty() && !searchName.isEmpty()) {
                query = "SELECT * FROM stock WHERE productName=\"" + searchName + "\"";
            } else if (!searchID.isEmpty() && !searchName.isEmpty()) {
                query = "SELECT * FROM stock WHERE productID=" + searchID + " AND productName=\"" + searchName + "\"";
            }

            if (query != null) {
                resultSet = statement.executeQuery(query);
            }

            try {
                if (resultSet != null && resultSet.next()) {
                    this.productID = resultSet.getInt("productID");
                    this.productName = resultSet.getString("productName");
                    this.productQuantity = resultSet.getInt("productQuantity");
                    this.productPrice = resultSet.getDouble("Price");
                    this.productImageByteArray = resultSet.getBytes("productImage");
                    this.productImage = new Image(new ByteArrayInputStream(productImageByteArray));
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Product not found");
                    alert.setContentText("No product matches your search criteria.");
                    alert.show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (resultSet != null) resultSet.close();
                statement.close();
                connection.close();
            }
        }

    public void updateProduct(byte[] newImage, int newProductID, String newProductName, int newProductQuantity, double newProductPrice, int previousID) throws SQLException {
        String url = "jdbc:mysql://localhost/storedata?maxAllowedPacket=67108864";
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
        preparedStatement.setInt(6, previousID);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succeed ");
        alert.setContentText("This product have been update successfully");
        alert.show();
    }

    public static byte[] getPreviousImage(String searchID, String searchName) throws SQLException {
        byte[] previousImageByte = new byte[0];
        String url = "jdbc:mysql://localhost/storedata?maxAllowedPacket=67108864";
        String user = "root";
        String password = "Pa$$w0rd";
        Connection connection = DriverManager.getConnection(url,user,password);
        Statement statement = connection.createStatement();
        String query = null;
        if (!searchID.isEmpty() && searchName.isEmpty()) {
            query = "SELECT productImage FROM stock WHERE productID= " + searchID;
        } else if (searchID.isEmpty() && !searchName.isEmpty()) {
            query = "SELECT productImage FROM stock WHERE productName=\"" + searchName + "\"";
        } else if (!searchID.isEmpty() && !searchName.isEmpty()) {
            query = "SELECT productImage FROM stock WHERE productID=" + searchID + " AND productName=\"" + searchName + "\"";
        }
        ResultSet resultSet = null;
        if (query != null) {
            resultSet = statement.executeQuery(query);
        }
       if(resultSet != null && resultSet.next()){
           previousImageByte = resultSet.getBytes("productImage");

       }
        return previousImageByte;
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

    public byte[] getProductImageByteArray() {
        return new byte[0];
    }
}
