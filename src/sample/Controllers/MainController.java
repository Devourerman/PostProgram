package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.DB;
import sample.User;
import sample.article;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {

    @FXML
    private Button button_exit, button_addArticles, button_profile;

    @FXML
    private VBox paneVBox;

    private DB db = new DB();

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        ResultSet res = db.getArticles();

        while(res.next()) {
            Node node = null;
            try {
                node = FXMLLoader.load(getClass().getResource("/sample/fxml/article.fxml"));

                Label title = (Label) node.lookup("#title");
                title.setText(res.getString("title"));

                String Title = title.toString();

                Label intro = (Label) node.lookup("#intro");
                intro.setText(res.getString("intro"));

                String Intro = intro.toString();

                final Node nodeSet = node;

                node.setOnMouseEntered(mouseEvent -> {
                    nodeSet.setStyle("-fx-background-color: #707173");
                });

                node.setOnMouseExited(mouseEvent -> {
                    nodeSet.setStyle("-fx-background-color: #343434");
                });

                paneVBox.setOnMouseClicked(mouseEvent -> {

                    try {
                        FileOutputStream fos = new FileOutputStream("article");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);

                        oos.writeObject(new article(Title, Intro));
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/sample/fxml/articleView.fxml"));
                        Stage primaryStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                        primaryStage.setTitle("News");
                        primaryStage.setScene(new Scene(root, 600, 400));
                        primaryStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            HBox hBox = new HBox();
            hBox.getChildren().add(node);
            hBox.setAlignment(Pos.BASELINE_CENTER);
            paneVBox.getChildren().add(hBox);
            paneVBox.setSpacing(10);
        }

        button_exit.setOnAction(actionEvent -> {
            try {
                FileOutputStream fos = new FileOutputStream("saves");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(new User(""));

                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/fxml/sample.fxml"));
                Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                primaryStage.setTitle("News");
                primaryStage.setScene(new Scene(root, 600, 400));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        button_addArticles.setOnAction(actionEvent -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/fxml/addArticle.fxml"));
                Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                primaryStage.setTitle("News");
                primaryStage.setScene(new Scene(root, 600, 400));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        button_profile.setOnAction(actionEvent -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/fxml/changeEmail.fxml"));
                Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                primaryStage.setTitle("News");
                primaryStage.setScene(new Scene(root, 600, 400));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

}
