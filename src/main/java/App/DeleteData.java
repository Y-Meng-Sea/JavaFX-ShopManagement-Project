package App;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.sql.*;

public class DeleteData {
    private int productId;
    private String productName;
    private Image productImage;

    private String searchIDForDelete;
    private String searchNameForDelete;

    public DeleteData(String searchIDForDelete, String searchNameForDelete) {
        this.searchIDForDelete = searchIDForDelete;
        this.searchNameForDelete = searchNameForDelete;
    }

    public DeleteData(){
    }

    public void deleteproduct() throws SQLException {
        String url = "jdbc:mysql://localhost/storedata";
        String user = "root";
        String password = "Pa$$w0rd";
        Connection connection = DriverManager.getConnection(url,user,password);
        String query = "";
        if(this.searchIDForDelete != "" && this.searchNameForDelete == ""){
            query = "SELECT *FROM stock WHERE productID = "+ searchIDForDelete;
        } else if (this.searchIDForDelete == "" && this.searchNameForDelete != "") {
            query = "SELECT *FROM stock WHERE productName = "+ "\""+searchNameForDelete+"\"";
        }else if (this.searchIDForDelete != "" && this.searchNameForDelete != ""){
            query = "SELECT * FROM stock WHERE productID =" +"\"" + searchIDForDelete + "\""+ "AND productName=" +"\"" + searchNameForDelete + "\"" ;
        }

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            int productID = resultSet.getInt("productID");
            String productName = resultSet.getString("productName");
            byte[] imageByte = resultSet.getBytes("productImage");
            //convert image
            Image image = new Image(new ByteArrayInputStream(imageByte));
            this.setProductId(productID);
            this.setProductName(productName);
            this.setProductImage(image);
        }
    }

    public void delete(String ID , String Name) throws SQLException {

        String url = "jdbc:mysql://localhost/storedata";
        String user = "root";
        String password = "Pa$$w0rd";
        Connection connection = DriverManager.getConnection(url,user,password);
        String query = "DELETE FROM stock WHERE productID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, Integer.parseInt(ID));
        preparedStatement.executeUpdate();
    }

    public String getSearchIDForDelete() {
        return searchIDForDelete;
    }

    public void setSearchIDForDelete(String searchIDForDelete) {
        this.searchIDForDelete = searchIDForDelete;
    }

    public String getSearchNameForDelete() {
        return searchNameForDelete;
    }

    public void setSearchNameForDelete(String searchNameForDelete) {
        this.searchNameForDelete = searchNameForDelete;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Image getProductImage() {
        return productImage;
    }

    public void setProductImage(Image productImage) {
        this.productImage = productImage;
    }
}
