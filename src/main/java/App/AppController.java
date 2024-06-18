package App;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class AppController {
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
//    @FXML
//    private Button aboutButton;
    @FXML
    private FontAwesomeIcon aboutButton;
    @FXML
    private TableView<String> tableview ;
    @FXML
    private TableColumn<ImageView,?> productImage;
    @FXML
    private TableColumn<Integer,?> productID;
    @FXML
    private TableColumn<String,?> productName;
    @FXML
    private TableColumn<Integer,?> productQuantity;



}
