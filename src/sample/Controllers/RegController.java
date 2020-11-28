package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DB;
import javafx.scene.Node;
import sample.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class RegController {

        @FXML
        private CheckBox confidentional;

        @FXML
        private TextField login_reg;

        @FXML
        private TextField email;

        @FXML
        private PasswordField password_reg;

        @FXML
        private Button button_reg;

        @FXML
        private TextField login_aut;

        @FXML
        private PasswordField password_aut;

        @FXML
        private Button button_auth;

        private DB db = new DB();

        @FXML
        void initialize(){
            button_reg.setOnAction(actionEvent -> {
                login_reg.setStyle("-fx-border-color: #fafafa");
                email.setStyle("-fx-border-color: #fafafa");
                password_reg.setStyle("-fx-border-color: #fafafa");
                button_reg.setText("Зарегистрироваться");

                if(login_reg.getCharacters().length() <= 3){
                    login_reg.setStyle("-fx-border-color: red");
                    return;
                } else if(email.getCharacters().length() <= 5){
                    email.setStyle("-fx-border-color: red");
                    return;
                } else if(password_reg.getCharacters().length() <= 3){
                    password_reg.setStyle("-fx-border-color: red");
                    return;
                } else if(!confidentional.isSelected()){
                    button_reg.setText("Поставьте галочку!!!");
                    return;
                }

                String pass = md5String(password_reg.getCharacters().toString());

                try {
                    boolean isReg = db.regUser(login_reg.getCharacters().toString(), email.getCharacters().toString(), pass);
                    if(isReg){
                        login_reg.setText("");
                        email.setText("");
                        password_reg.setText("");
                        button_reg.setText("Готово :)");
                    } else{
                        button_reg.setText("Логин занят :(");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });

            button_auth.setOnAction(actionEvent -> {
                login_aut.setStyle("-fx-border-color: #fafafa");
                password_aut.setStyle("-fx-border-color: #fafafa");

                if(login_aut.getCharacters().length() <= 3){
                    login_aut.setStyle("-fx-border-color: red");
                    return;
                } else if(password_aut.getCharacters().length() <= 3){
                    password_aut.setStyle("-fx-border-color: red");
                    return;
                }

                String pass = md5String(password_aut.getCharacters().toString());

                try {
                    boolean isAuth = db.authUser(login_aut.getCharacters().toString(), pass);
                    if(isAuth){
                        FileOutputStream fos = new FileOutputStream("saves");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);

                        oos.writeObject(new User(login_aut.getCharacters().toString()));

                        oos.close();

                        login_aut.setText("");
                        password_aut.setText("");
                        button_auth.setText("Готово :)");

                        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/main.fxml"));
                        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        primaryStage.setTitle("News");
                        primaryStage.setScene(new Scene(root, 600, 400));
                        primaryStage.show();
                    } else{
                        button_auth.setText("Пользователь не найден");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
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
