package UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import utils.Calcul;
import utils.Dataset;

public class Measures_Controller {

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

            int index = combox_attr.getSelectionModel().getSelectedIndex();

            String text = "Attribut : " + combox_attr.getSelectionModel().getSelectedItem()
                    + "\t Moyenne : " + Calcul.moy(dataset, index) + "\n"
                    + "\t Médiane : " + Calcul.mediane(dataset, index) + "\n"
                    + "\t Q1 : " + Calcul.Q1(dataset, index) + "\n"
                    + "\t Q3 : " + Calcul.Q3(dataset, index) + "\n"
                    + "\t IQR : " + Calcul.ecart_inter(dataset, index) + "\n"
                    + "\t Mode : " + Calcul.mode(dataset, index) + "\n"
                    + "\t MidRange : " + Calcul.midRange(dataset, index) + "\n"
                    + "\t Variance : " + Calcul.variance(dataset, index) + "\n"
                    + "\t Écart type : " + Calcul.ecart_type(dataset, index) + "\n"
                    + "\t" + Calcul.symetrique(dataset, index) + "\n";

            describe.setText(text);
            describe.setEditable(false);
        }
    }
}
