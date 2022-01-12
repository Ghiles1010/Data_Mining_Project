package UI;



import common.Dataset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import part2.API;
import part2.classification.BaseClassification;

import java.io.IOException;
import java.util.ArrayList;




public class Predict_Controller {




    @FXML
    private ComboBox<String> algo_combox;

    @FXML
    private TextField k_field;

    @FXML
    private TextField time_field;

    @FXML
    private TextField inst_field;

    @FXML
    private ComboBox<String> dist_combox;

    @FXML
    private TextArea matrix_area;

    @FXML
    private TextArea measure_area;




    Dataset dataset;
    ArrayList<String[]> disc_dataset;
    int Q;


    public  void init(Dataset dataset, ArrayList<String[]> disc_dataset, int Q){


        this.dataset = dataset;
        this.disc_dataset = disc_dataset;
        this.Q = Q;

        ObservableList<String> options = FXCollections.observableArrayList("Knn", "Bayes");
        algo_combox.setItems(options);

        options = FXCollections.observableArrayList("Euclidean", "Manhattan");
        dist_combox.setItems(options);


        time_field.setEditable(false);
    }

    @FXML
    void back(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Main_Scene.fxml"));
        Parent eval_scene = loader.load();

        Stage window = (Stage) measure_area.getScene().getWindow();
        window.setScene(new Scene(eval_scene));


    }

    @FXML
    void predict(ActionEvent event) {

        String selected_algo = algo_combox.getSelectionModel().getSelectedItem();
        String selected_dist = dist_combox.getSelectionModel().getSelectedItem();
        String k = k_field.getText();
        String nb_inst= inst_field.getText();


        if(  !nb_inst.isEmpty() && selected_algo != null ){

            BaseClassification algorithm = null;

            int t_size = Integer.parseInt(inst_field.getText());

            if(selected_algo.equals("Knn")) {
                if(!k.isEmpty() && selected_dist != null){
                    algorithm = API.knn(this.dataset, t_size, Integer.parseInt(k), selected_dist);
                }
            }
            else
                algorithm = API.naiveBayes(this.dataset, t_size, this.disc_dataset, Q);



            if(algorithm != null){

                String matrix = algorithm.getPrintedConfusion_matrix();
                matrix_area.setText(matrix);

                String exec_time = String.format("%.2f seconds", algorithm.getExecutionTime() / Math.pow(10, 9));
                time_field.setText(exec_time);

                StringBuilder sb = new StringBuilder();

                sb.append("Precision Class 1 : ").append(algorithm.getPrecision(1)).append("\n");
                sb.append("Precision Class 2 : ").append(algorithm.getPrecision(2)).append("\n");
                sb.append("Precision Class 3 : ").append(algorithm.getPrecision(3)).append("\n");

                sb.append("Mean Precision").append(algorithm.getMeanPrecision()).append("\n");



                sb.append("Accuracy Class 1 : ").append(algorithm.getAccuracy(1)).append("\n");
                sb.append("Accuracy Class 2 : ").append(algorithm.getAccuracy(2)).append("\n");
                sb.append("Accuracy Class 3 : ").append(algorithm.getAccuracy(3)).append("\n");

                sb.append("Mean Accuracy").append(algorithm.getMeanAccuracy()).append("\n");


                sb.append("Sensitivity Class 1 : ").append(algorithm.getSensitivity(1)).append("\n");
                sb.append("Sensitivity Class 2 : ").append(algorithm.getSensitivity(2)).append("\n");
                sb.append("Sensitivity Class 3 : ").append(algorithm.getSensitivity(3)).append("\n");

                sb.append("Mean Sensitivity").append(algorithm.getMeanSensitivity()).append("\n");




                sb.append("Recall Class 1 : ").append(algorithm.getRecall(1)).append("\n");
                sb.append("Recall Class 2 : ").append(algorithm.getRecall(2)).append("\n");
                sb.append("Recall Class 3 : ").append(algorithm.getRecall(3)).append("\n");

                sb.append("Mean Recall").append(algorithm.getMeanRecall()).append("\n");





                sb.append("F-Score Class 1 : ").append(algorithm.getFScore(1)).append("\n");
                sb.append("F-Score Class 2 : ").append(algorithm.getFScore(2)).append("\n");
                sb.append("F-Score Class 3 : ").append(algorithm.getFScore(3)).append("\n");

                sb.append("Mean F-Score : ").append(algorithm.getMeanFScore()).append("\n");




                sb.append("Specificity Class 1 : ").append(algorithm.getSpecificity(1)).append("\n");
                sb.append("Specificity Class 2 : ").append(algorithm.getSpecificity(2)).append("\n");
                sb.append("Specificity Class 3 : ").append(algorithm.getSpecificity(3)).append("\n");

                sb.append("Mean Specificity : ").append(algorithm.getMeanSpecificity()).append("\n");


                measure_area.setText(sb.toString());





            }




        }


    }

}
