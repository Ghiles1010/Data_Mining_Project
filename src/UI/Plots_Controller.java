package UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Calcul;
import utils.Charts;
import utils.Dataset;

import java.io.IOException;

public class Plots_Controller {


    @FXML
    TextField nb_bins;

    @FXML
    ComboBox<String> combox_attr;

    @FXML
    ComboBox<String> combox_chart;


    Dataset dataset;

    public void init(String dataset_path){

        ObservableList<String> options = FXCollections.observableArrayList("Area", "Perimeter", "Compactness", "Kernel_lenght", "Kernel_width",
                "Asymmetry_coefficient", "Kernel_groove_groove", "Classe");
        combox_attr.setItems(options);

        ObservableList<String> charts = FXCollections.observableArrayList("Histogram", "Box plot");
        combox_chart.setItems(charts);

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
            String s_chart = combox_chart.getSelectionModel().getSelectedItem();

            Charts charts = new Charts();

            switch (s_chart){
                case "Histogram":

                    int number_bins = Integer.parseInt(nb_bins.getText());
                    charts.Create_Histogram(dataset, index, number_bins);
                    break;
                case "Box plot":

                    charts.Create_Boxplot(dataset, index);
                    break;
            }





        }
    }
}
