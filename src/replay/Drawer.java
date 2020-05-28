package replay;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;
import java.io.IOException;

public class Drawer {

    static Stage primaryStage = new Stage();

    public void createGameWindow(String fileName) throws IOException, ParseException, InterruptedException {
        Parent root = FXMLLoader.load(getClass().getResource("Drawer.fxml"));
        Scene scene = new Scene(root);
        FieldDrawer replayField = new FieldDrawer();
        replayField.initialLayout(root,fileName);
        scene.getStylesheets().add(this.getClass().getResource("/assets/style.css").toExternalForm());
        primaryStage.getIcons().add(new Image("/assets/images/icon.png"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Match game!");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public void closeGameWindow() {
        primaryStage.close();
    }
}
