package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DB;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class ChangeController {

    @FXML
    private TextField log_ch;

    @FXML
    private TextField email_ch;

    @FXML
    private TextField pass_ch;

    @FXML
    private Button button_change, button_exit;

    private DB db = new DB();

    @FXML
    void initialize(){
        button_change.setOnAction(actionEvent -> {
            log_ch.setStyle("-fx-border-color: #fafafa");
            email_ch.setStyle("-fx-border-color: #fafafa");
            pass_ch.setStyle("-fx-border-color: #fafafa");
            button_change.setText("Изменить почту");

            if(log_ch.getCharacters().length() <= 3){
                log_ch.setStyle("-fx-border-color: red");
                return;
            } else if(email_ch.getCharacters().length() <= 5){
                email_ch.setStyle("-fx-border-color: red");
                return;
            } else if(pass_ch.getCharacters().length() <= 3){
                pass_ch.setStyle("-fx-border-color: red");
                return;
            }

            String pass = md5String(pass_ch.getCharacters().toString());

            try {
                boolean isChange = db.changeUserEmail(log_ch.getCharacters().toString(), email_ch.getCharacters().toString(), pass);
                if(isChange){
                    log_ch.setText("");
                    email_ch.setText("");
                    pass_ch.setText("");
                    button_change.setText("Готово");
                } else{
                    button_change.setText("Логин не найден");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        });

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
    }


    public static String md5String(String pass){
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(pass.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInteger = new BigInteger(1, digest);
        String md5Hex = bigInteger.toString(16);

        while(md5Hex.length() < 32)
            md5Hex = "0" + md5Hex;

        return md5Hex;
    }



}
