package App;

import javafx.scene.image.ImageView;

public class StoreData {
    private ImageView productImage;
    private int productID ;
    private String productName;
    private int productQuantity;
    public StoreData(ImageView productImage,int productID,String productName,int productQuantity){
        this.productImage = productImage;
        this.productID = productID;
        this.productName = productName;
        this.productQuantity = productQuantity;
    }

    public ImageView getProductImage() {
        return productImage;
    }

    public void setProductImage(ImageView productImage) {
        this.productImage = productImage;
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
}
