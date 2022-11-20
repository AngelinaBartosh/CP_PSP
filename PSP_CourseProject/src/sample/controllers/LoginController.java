package sample.controllers;

import sample.Main;
import sample.database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.models.User;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController extends Transition{
    public BorderPane borderPane;
    public TextField usernameField;
    public PasswordField passwordField;
    public static String Username;
    public void openRegister(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/Register.fxml"));
        borderPane.getChildren().removeAll();
        borderPane.getChildren().setAll(root);
    }

    public void loginUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        var username = usernameField.getText();
        var password = passwordField.getText();
        if(!username.equals("") && !password.equals("")) {
            if (!checkRole(username, password)) {
                User user = new User(username, password);
                Main.client.oos.writeInt(2);
                Main.client.oos.writeObject(user);

                if (user != null) {
                    Username = username;
                    System.out.println("Login - user: " + username + "\n");
                    Stage mainStage = getNewStage(actionEvent, "../views/Catalog.fxml", true);
                    if (mainStage != null) {
                        mainStage.show();
                    }
                } else {
                    System.out.println("user: " + username + " doesn't exist\n");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(username);
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong username/password combination");
                    alert.showAndWait();
                }
            } else {
                Stage newStage = getNewStage(actionEvent, "../views/Admin.fxml", true);
                if (newStage != null) {
                    newStage.show();
                }
                System.out.println("Admin logging...");
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(username);
            alert.setHeaderText(null);
            alert.setContentText("Некорректно введены данные. Повторите попытку");
            alert.showAndWait();
        }
    }

    public boolean checkRole(String username, String password) throws SQLException, ClassNotFoundException {
        if(!username.equals("") && !password.equals("")) {
            DatabaseConnection connectDB = new DatabaseConnection();
            Connection connection = connectDB.getConnection();

            String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";

            var role = "admin";

            CallableStatement callableStatement = connection.prepareCall(sql);
            callableStatement.setString(1, username);
            callableStatement.setString(2, password);
            callableStatement.setString(3, role);
            callableStatement.execute();
            ResultSet res = callableStatement.getResultSet();

            if (res.next()) {
                System.out.println("Login - admin");
                callableStatement.close();
                return true;
            } else {
                callableStatement.close();
                return false;
            }
        }
        return false;
    }

    public void closeApp(ActionEvent actionEvent) {
        System.exit(0);
    }
}
