package UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Dataset;

import java.io.IOException;

public class Dataset_Controller{


    private  Dataset dataset;

    @FXML
    TextArea general;
    @FXML
    TextField nb_class;
    @FXML
    TextField nb_instance;
    @FXML
    TextField nb_attributs;
    @FXML
    TextField missing;
    @FXML
    TextArea repartitions;

    public void init(String dataset_path){

        dataset = new Dataset();
        dataset.ReadDataset(dataset_path);

        general.setText(dataset.desc());
        general.setEditable(false);

        nb_class.setText(Integer.toString(dataset.Nb_classe()));
        nb_class.setEditable(false);

        nb_instance.setText(Integer.toString(dataset.Nb_Instances()));
        nb_instance.setEditable(false);

        nb_attributs.setText(Integer.toString(dataset.Nb_attributes()));
        nb_attributs.setEditable(false);

        missing.setText(Boolean.toString(dataset.val_manq()));
        missing.setEditable(false);

        repartitions.setText(dataset.class_desc());
        repartitions.setEditable(false);



    }

    @FXML
    public void back() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Main_Scene.fxml"));
        Parent eval_scene = loader.load();

        Stage window = (Stage) missing.getScene().getWindow();
        window.setScene(new Scene(eval_scene));
    }
}
