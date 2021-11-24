package UI;

import UI.Eval_controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Main_Controller implements Initializable {


    @FXML
    TextField dataset_field;
    @FXML
    ComboBox<String> action_combox;

    private File choosen_file;


    private void init_algo_combox(){

        action_combox.getItems().add("Manipulate dataset");
        action_combox.getItems().add("Describe dataset");
        action_combox.getItems().add("Describe attributes");
        action_combox.getItems().add("Calculate measures");
        action_combox.getItems().add("Draw plots");
        action_combox.getItems().add("Compare");
    }

    private String get_selected_scene(){

        String selected_action = action_combox.getSelectionModel().getSelectedItem();

        switch (selected_action){
            case "Manipulate dataset":
                return "Manipulate.fxml";

            case "Describe dataset":
                return "Dataset_Scene.fxml";

            case "Describe attributes":
                return "Attributes_Scene.fxml";

            case "Calculate measures":
                return  "Measures_Scene.fxml";

            case "Draw plots":
                return "Plots_Scene.fxml";

            case "Compare":
                return "Compare_Scene.fxml";

            default:
                return null;
        }
    }


    @FXML
    void launch(ActionEvent event) throws IOException {

        if(choosen_file != null && action_combox.getSelectionModel().getSelectedItem() != null) {

            System.out.println(get_selected_scene());

            String selected_scene = get_selected_scene();


            System.out.println(get_selected_scene());


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(get_selected_scene()));
            Parent eval_scene = loader.load();



            switch (selected_scene){
                case "Manipulate.fxml":
                    Manipulate_Controller controller = loader.getController();
                    controller.init(choosen_file.getAbsolutePath());
                    break;

                case "Dataset_Scene.fxml":
                    Dataset_Controller controller1 = loader.getController();
                    controller1.init(choosen_file.getAbsolutePath());
                    break;

                case "Attributes_Scene.fxml":
                    Attributes_Controller controller2 = loader.getController();
                    controller2.init(choosen_file.getAbsolutePath());
                    break;

                case "Measures_Scene.fxml":
                    Measures_Controller controller3 = loader.getController();
                    controller3.init(choosen_file.getAbsolutePath());
                    break;

                case "Plots_Scene.fxml":
                    Plots_Controller controller4 = loader.getController();
                    controller4.init(choosen_file.getAbsolutePath());
                    break;

                case "Compare_Scene.fxml":
                    Compare_Controller controller5 = loader.getController();
                    controller5.init(choosen_file.getAbsolutePath());
                    break;



            }


            Stage window = (Stage) action_combox.getScene().getWindow();
            window.setScene(new Scene(eval_scene));

        }

    }

    @FXML
    private void browse(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT/CSV files", "*.txt","*.csv")
        );
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        choosen_file = fileChooser.showOpenDialog(null);

        if (choosen_file != null){
            dataset_field.setText(choosen_file.getAbsolutePath());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dataset_field.editableProperty().setValue(false);
        init_algo_combox();

    }
}
