package replay;

import com.company.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class WarningMessage extends Main {

    public void createWarningMessage() throws IOException {
        Parent settingsRoot = FXMLLoader.load(getClass().getResource("WarningMessage.fxml"));
        Scene settingsScene = new Scene(settingsRoot, 700, 400);
        settingsRoot.setId("saveRoot");
        Stage saveStage = new Stage();
        settingsScene.getStylesheets().add(this.getClass().getResource("/assets/style.css").toExternalForm());
        saveStage.getIcons().add(new Image("/assets/images/icon.png"));
        saveStage.setScene(settingsScene);
        saveStage.centerOnScreen();
        saveStage.show();
    }

    @FXML
    public Button confirmButton;

    @FXML
    protected void closeWarningMessage(ActionEvent actionEvent) throws IOException, ParseException, InterruptedException {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }


}
