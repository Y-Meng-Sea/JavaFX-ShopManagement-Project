package App;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.sql.*;

public class Updatedata {

    private int productID;
    private String productName;
    private int productQuantity;
    private ImageView imageView;

     private String searchID ;
     private String searchName;

    public Updatedata(String searchID,String searchName){
        this.searchName = searchName;
        this.searchID = searchID;

    }

    public void updatedata()throws SQLException {
        String url = "jdbc:mysql://localhost/storedata";
        String user = "root";
        String password = "Pa$$w0rd";
        Connection connection = DriverManager.getConnection(url,user,password);
        String query = "";
        if (this.searchID != null && this.searchName == ""){
             query = "SELECT *FROM stock WHERE productID = " + searchID;
        } else if (this.searchID == "" && this.searchName != null) {
             query = "SELECT *FROM stock WHERE productName = " + "\""+searchName+"\"";
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            int productID = resultSet.getInt("productID");
            String productName = resultSet.getString("productName");
            int productQuantity = resultSet.getInt("productQuantity");
            byte[] bytesImage = resultSet.getBytes("productImage");
            // convert image
            Image byteToImage = new Image(new ByteArrayInputStream(bytesImage));
            ImageView imageView = new ImageView(byteToImage);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            // assing to variable
            this.setProductID(productID);
            this.setProductName(productName);
            this.setImageView(imageView);
            this.setProductQuantity(productQuantity);
        }
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


}
