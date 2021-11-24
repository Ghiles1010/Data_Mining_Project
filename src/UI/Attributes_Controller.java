package UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import utils.Dataset;

import java.awt.*;

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
    public void show(){

        if(combox_attr.getSelectionModel().getSelectedItem() != null) {

            describe.setText(dataset.desc_att(combox_attr.getSelectionModel().getSelectedIndex()));
            describe.setEditable(false);
        }
    }
}
