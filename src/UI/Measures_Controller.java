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
import utils.Calcul;
import utils.Dataset;

import java.io.IOException;

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

        dataset = new Dataset(dataset_path);

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
