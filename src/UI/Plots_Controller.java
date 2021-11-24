package UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import utils.Calcul;
import utils.Charts;
import utils.Dataset;

public class Plots_Controller {



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

        dataset = new Dataset();
        dataset.ReadDataset(dataset_path);




    }




    @FXML
    public void show(){

        if(combox_attr.getSelectionModel().getSelectedItem() != null) {

            int index = combox_attr.getSelectionModel().getSelectedIndex();
            String s_chart = combox_chart.getSelectionModel().getSelectedItem();

            Charts charts = new Charts();

            switch (s_chart){
                case "Histogram":
                    charts.Create_Histogram(dataset, index, 10);
                    break;
                case "Box plot":
                    charts.Create_Boxplot(dataset, index);
                    break;
            }





        }
    }
}
