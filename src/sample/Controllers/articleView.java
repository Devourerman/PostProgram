package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.DB;
import sample.article;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class articleView {

    @FXML
    private Button button_exit;

    @FXML
    private TextField title;

    @FXML
    private VBox intro;

    private DB db = new DB();

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        button_exit.setOnAction(actionEvent -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/fxml/main.fxml"));
                Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                primaryStage.setTitle("News");
                primaryStage.setScene(new Scene(root, 600, 400));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("saves");
            ObjectInputStream ois = new ObjectInputStream(fis);

            title.setText(ois.readObject().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
