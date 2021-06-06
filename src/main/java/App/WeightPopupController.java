package App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WeightPopupController {

    private static Stage stage;
    private static int intWeight;
    @FXML
    private TextField weight;

    @FXML
    void setWeight(ActionEvent event) {
        check(weight.getText()) ;
        stage.close();
    }

    public static int getWeight() throws IOException {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(loadFXML("WeightPopup"),400, 100);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
        return intWeight;
    }

    public void check(String number) {
        try{
            Integer n = Integer.valueOf(number);
            this.intWeight = n != 0 ? n : 1;
        }
        catch (NumberFormatException ex){
            this.intWeight = 1;
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
