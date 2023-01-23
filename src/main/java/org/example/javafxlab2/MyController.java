package org.example.javafxlab2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MyController implements Initializable {
    Stage stage;
    File dir;

    @FXML
    private Button chooseBtn;

    @FXML
    private TextField pathTextField;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private Pane pane;

    @FXML
    private ListView<String> listView;

    @FXML
    private void chooseFile(ActionEvent e) {

        ImageView rootIcon = new ImageView(new Image(getClass().getResourceAsStream("/Images/folder.png")));
        rootIcon.setFitHeight(10);
        rootIcon.setFitWidth(10);
        ImageView fileIcon = new ImageView(new Image(getClass().getResourceAsStream("/Images/file.png")));
        fileIcon.setFitHeight(10);
        fileIcon.setFitWidth(10);

        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        dir = dirChooser.showDialog(stage);

        if (dir == null) {
            return;
        }

        File[] allSubFiles = dir.listFiles();

        pathTextField.setText(dir.getAbsolutePath());

        TreeItem<String> roottreeItem = new TreeItem<>(dir.getAbsolutePath(), rootIcon);
        roottreeItem.setExpanded(true);

        for (File filename : allSubFiles) {
            createTree(filename, roottreeItem);
        }

        addItemsInListView();

        listView.setCellFactory(param -> new ListCell<String>() {
            @Override
            public void updateItem(String name, boolean empty) {

                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (new File(pathTextField.getText() + "\\", name).isDirectory()) {
                        // if (new File(pathTextField.getText() + "\\", name).isDirectory()) {
                        ImageView imageView = new ImageView();
                        imageView.setImage(new Image(getClass().getResourceAsStream("/Images/folder.png")));
                        imageView.setFitHeight(20);
                        imageView.setFitWidth(20);
                        setGraphic(imageView);

                    } else {
                        ImageView imageView = new ImageView();
                        imageView.setImage(new Image(getClass().getResourceAsStream("/Images/file.png")));
                        imageView.setFitHeight(20);
                        imageView.setFitWidth(20);
                        setGraphic(imageView);

                    }
                    setText(name);
                }
            }
        });

        treeView.setRoot(roottreeItem);

    }

    private void createTree(File file, TreeItem<String> root) {
        ImageView rootIcon = new ImageView(new Image(getClass().getResourceAsStream("/Images/folder.png")));
        rootIcon.setFitHeight(10);
        rootIcon.setFitWidth(10);
        ImageView fileIcon = new ImageView(new Image(getClass().getResourceAsStream("/Images/file1.png")));
        fileIcon.setFitHeight(10);
        fileIcon.setFitWidth(10);

        if (file.isFile()) {
            TreeItem<String> subItems = new TreeItem<>(file.getName(), fileIcon);
            root.getChildren().add(subItems);

        } else if (file.isDirectory()) {
            TreeItem<String> subItems = new TreeItem<>(file.getName(), fileIcon);
            root.getChildren().add(subItems);
            for (File f : file.listFiles()) {
                createTree(f, subItems);
            }

        }

    }

    private void addItemsInListView() {
        MultipleSelectionModel<TreeItem<String>> model = treeView.getSelectionModel();
        model.selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue,
                    TreeItem<String> newValue) {

                if (newValue != null) {
                    listView.getItems().clear();
                    pathTextField.setText(pathTextField.getText() + "\\" + newValue.getValue());

                    for (TreeItem<String> s : newValue.getChildren()) {

                        listView.getItems().add(s.getValue());
                    }

                }
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

}
