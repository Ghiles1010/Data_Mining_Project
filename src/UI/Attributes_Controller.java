package UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import utils.Dataset;

import java.awt.*;
import java.io.IOException;

public class Attributes_Controller {

    @FXML
    TextArea describe;

    @FXML
    ComboBox<String> combox_attr;


    Dataset dataset;

    public void init(String dataset_path){

        ObservableList<String> options = FXCollections.observableArrayList("Area", "Perimeter", "Compactness", "Kernel_lenght", "Kernel_width",
                "Asymmetry_coefficient", "Kernel_groove_groove", "Classe");

        combox_attr.setItems(options);

        dataset = new Dataset();
        dataset.ReadDataset(dataset_path);




    }

    @FXML
    public void back() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Main_Scene.fxml"));
        Parent eval_scene = loader.load();

        Stage window = (Stage) combox_attr.getScene().getWindow();
        window.setScene(new Scene(eval_scene));
    }

    @FXML
    public void show(){

        if(combox_attr.getSelectionModel().getSelectedItem() != null) {

            describe.setText(dataset.desc_att(combox_attr.getSelectionModel().getSelectedIndex()));
            describe.setEditable(false);
        }
    }
}
