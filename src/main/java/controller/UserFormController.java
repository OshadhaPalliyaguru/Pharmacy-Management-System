package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.Users;
import services.UserService;
import services.impl.UserServiceImpl;

import java.net.URL;
import java.util.ResourceBundle;


public class UserFormController implements Initializable {

    private final UserService userService = new UserServiceImpl();


    @FXML
    private TableColumn<?, ?> colcreated_at;

    @FXML
    private TableColumn<?, ?> colemail;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colpassword;

    @FXML
    private TableColumn<?, ?> colrole;

    @FXML
    private TableColumn<?, ?> colusername;

    @FXML
    private TableView<Users> tblusers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colusername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colpassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colrole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colcreated_at.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        loadUsers();
    }

    private void loadUsers() {
        ObservableList<Users> usersList = FXCollections.observableArrayList();
        usersList.addAll(userService.getAllUsers());
        tblusers.setItems(usersList);
    }

}

