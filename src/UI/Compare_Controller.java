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
import utils.Charts;
import utils.Dataset;

import java.io.IOException;

public class Compare_Controller {




    @FXML
    ComboBox<String> combox_attr1;

    @FXML
    ComboBox<String> combox_attr2;

    @FXML
    TextArea results;


    Dataset dataset;

    public void init(String dataset_path){

        ObservableList<String> options = FXCollections.observableArrayList("Area", "Perimeter", "Compactness", "Kernel_lenght", "Kernel_width",
                "Asymmetry_coefficient", "Kernel_groove_groove", "Classe");

        combox_attr1.setItems(options);
        combox_attr2.setItems(options);


        dataset = new Dataset();
        dataset.ReadDataset(dataset_path);


    }

    @FXML
    public void back() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Main_Scene.fxml"));
        Parent eval_scene = loader.load();

        Stage window = (Stage) combox_attr2.getScene().getWindow();
        window.setScene(new Scene(eval_scene));
    }




    @FXML
    public void compare(){

        if(combox_attr1.getSelectionModel().getSelectedItem() != null && combox_attr2.getSelectionModel().getSelectedItem() != null) {

            int index1 = combox_attr1.getSelectionModel().getSelectedIndex();
            int index2 = combox_attr2.getSelectionModel().getSelectedIndex();



            Charts charts = new Charts();
            charts.Create_Scatter(dataset, index1, index2);

            double corr = Calcul.correlation(dataset, index1, index2);

            String text = "Correllation : " + corr
                    + "\nInterpretation " + Calcul.correlation_interpretation(corr);

            results.setText(text);
            results.setEditable(false);





        }
    }
}
