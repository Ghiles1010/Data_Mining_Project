package UI;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utils.Dataset;

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
}
