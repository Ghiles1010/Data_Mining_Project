package UI;

import common.Dataset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import part2.extraction.FrequentItemSets;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Mine_Controller {




    @FXML
    private ComboBox<String> algo_combox;

    @FXML
    private TextField support;

    @FXML
    private TextField conf;

    @FXML
    private TextArea result_area;

    Dataset dataset;

    public  void init(String dataset_path, ArrayList<String[]> result_dataset){


        dataset = new Dataset(dataset_path);

        ObservableList<String> options = FXCollections.observableArrayList("Apriori", "Eclat");
        algo_combox.setItems(options);

    }

    @FXML
    void Mine(ActionEvent event) {

        String seleted = algo_combox.getSelectionModel().getSelectedItem();

        if(     seleted != null
                && !seleted.isEmpty()
                && !conf.getText().isEmpty()
                && !support.getText().isEmpty()){

            
        }

    }

    @FXML
    void back(ActionEvent event) {

    }

    @FXML
    void next(ActionEvent event) {

    }

}

