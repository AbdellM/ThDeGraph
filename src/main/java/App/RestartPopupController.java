package App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RestartPopupController {

    private static Stage stage;
    private static Stage stage2;


    @FXML
    private Button retartBtn, closeBtn;

    @FXML
    void ClosePopup(ActionEvent event) {
       stage.close();
    }

    @FXML
    void Restart(ActionEvent event) throws IOException {
        stage.close();
        StartController.close();

        stage2 = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage2.setResizable(false);
        Scene scene = new Scene(loadFXML("Start"), 1200, 722);
        stage2.setScene(scene);
        stage2.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void display() throws IOException {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(loadFXML("RestartPopup"),600, 100);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
