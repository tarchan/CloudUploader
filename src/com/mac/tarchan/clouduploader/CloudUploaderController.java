/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.tarchan.clouduploader;

import com.cloudapp.api.CloudApp;
import com.cloudapp.api.model.CloudAppItem;
import com.cloudapp.impl.CloudAppImpl;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
    FileChooser fileChooser = new FileChooser();
    File file;
    GetCloudAppItemsService service = new GetCloudAppItemsService();
    @FXML
    private TextField emailField;
    @FXML
    private TextField passField;
    @FXML
    private Button loginButton;
    @FXML
    private Button uploadButton;
    @FXML
    private Label statusLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPEG ファイル (*.jpg;*.jpeg)", "*.jpg", "*.jpeg");
        FileChooser.ExtensionFilter gifFilter = new FileChooser.ExtensionFilter("GIF ファイル (*.gif)", "*.gif");
        FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter("すべてのファイル (*.*)", "*.*");
        fileChooser.getExtensionFilters().addAll(jpgFilter, gifFilter, allFilter);
    }

    @FXML
    private void handleLoginAction(ActionEvent t) {
        service.restart();
    }

    @FXML
    private void handleUploadAction(ActionEvent t) {
        file = fileChooser.showOpenDialog(null);
        System.out.println(file);
        if (file == null) {
            return;
        }

        PutCloudAppItemTask task = new PutCloudAppItemTask();
        statusLabel.textProperty().bind(task.messageProperty());
        new Thread(task).start();
    }

    class GetCloudAppItemsService extends Service<CloudApp> {

        @Override
        protected Task<CloudApp> createTask() {
            statusLabel.textProperty().bind(this.messageProperty());
            return new GetCloudAppItemsTask();
        }
    }

    class GetCloudAppItemsTask extends Task<CloudApp> {

        @Override
        protected CloudApp call() throws Exception {
            updateMessage("認証中");
            System.setProperty("java.net.useSystemProxies", "true");
            CloudApp api = new CloudAppImpl(emailField.getText(), passField.getText());
//            updateMessage("取得中: " + api.getAccountDetails());
//            ObservableList<CloudAppItem> items = FXCollections.observableArrayList(api.getItems(1, 10, CloudAppItem.Type.IMAGE, true, null));
//            for (CloudAppItem item : items) {
//                updateMessage(item.toString());
//            }
            updateMessage("完了");
            return api;
        }
    }

    class PutCloudAppItemTask extends Task<String> {

        @Override
        protected String call() throws Exception {
            updateMessage("認証中");
            System.setProperty("java.net.useSystemProxies", "true");
            CloudApp api = new CloudAppImpl(emailField.getText(), passField.getText());
            updateMessage("更新中");
            CloudAppItem item = api.upload(file);
            updateMessage("完了");
            return item.getUrl();
        }
    }
}
