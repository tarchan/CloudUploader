/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.tarchan.clouduploader;

import com.cloudapp.api.CloudApp;
import com.cloudapp.impl.CloudAppImpl;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 *
 * @author Takashi Ogura <tarchan at mac.com>
 */
public class CloudUploaderController implements Initializable {

    CloudApp api;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passField;
    @FXML
    private Button loginButton;
    @FXML
    private Button uploadButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleLoginAction(ActionEvent t) {
        api = new CloudAppImpl(emailField.textProperty().get(), passField.textProperty().get());
        System.out.println(api);
    }

    @FXML
    private void handleUploadAction(ActionEvent t) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        System.out.println(file);
    }
}
