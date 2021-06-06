package App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;


public class StartController{

    private static Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public Button StartBtn;

    @FXML
    private RadioButton Orianted, Weighted;

    @FXML
    public void switchToPrimary(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        root = loader.load();
        MainController controller = loader.getController();
        controller.setOriantationAndWeight(Orianted.isSelected(), Weighted.isSelected());

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void close(){
        stage.close();
    }
}
