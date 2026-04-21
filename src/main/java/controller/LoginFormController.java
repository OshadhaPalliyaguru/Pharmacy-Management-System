package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dto.Users;
import services.UserService;
import services.impl.UserServiceImpl;

import java.io.IOException;

public class LoginFormController {

    // inside class LoginFormController
    private final UserService userService = new UserServiceImpl();

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnSigninOnActon(ActionEvent event) {

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Please fill all fields!");
            return;
        }

        Users loggedUser = userService.login(username, password);

        if (loggedUser != null) {
            showAlert("Login successful! Welcome " + loggedUser.getUsername());

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBoard.fxml"));
                Scene scene = new Scene(loader.load());

                Stage stage = (Stage) txtUsername.getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            showAlert("Invalid username or password!");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void hyperRegisterOnAction(ActionEvent event) {
        Stage stage1 = new Stage();
        try {
            stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Registerform.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage1.show();
    }
}
