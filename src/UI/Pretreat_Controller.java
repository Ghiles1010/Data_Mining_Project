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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import part1.Row_str;
import part2.pretreatment.Discretization;
import part2.pretreatment.Normalization;

import java.io.IOException;
import java.util.ArrayList;

public class Pretreat_Controller {

    private final int COL_WIDTH = 100;

    @FXML
    private ComboBox<String> normal_combox;

    @FXML
    private ComboBox<String> discret_combox;

    @FXML
    private TextField Q;

    @FXML
    TableView<Row_str> table;

    Dataset dataset;

    String next_scene;


    public  void init(String dataset_path, String next_scene){

        this.next_scene = next_scene;

        dataset = new Dataset(dataset_path);

        ObservableList<String> options = FXCollections.observableArrayList("Min Max", "z-score");
        normal_combox.setItems(options);

        options = FXCollections.observableArrayList("Quantiles", "Equal intervals");
        discret_combox.setItems(options);

        discret_combox.setDisable(true);

        init_table();
        set_table(dataset);


    }

    private void init_table(){

        for (int i=0;i<dataset.nbAttributes();i++){

            String column_name = dataset.get_column_name(i);

            TableColumn<Row_str, Double> column = new TableColumn(column_name);

            column.setCellValueFactory(new PropertyValueFactory<Row_str, Double>(column_name));

            column.setPrefWidth(COL_WIDTH);
            table.getColumns().add(column);
            table.setPrefWidth(table.getPrefWidth() + COL_WIDTH);
        }

    }


    private void set_table(Dataset dataset){

        final ObservableList<Row_str> rows = FXCollections.observableArrayList();

        for(int i=0; i<dataset.nbInstances(); i++){
            Row_str row = new Row_str(dataset.getInstance(i));
            rows.add(row);
        }


        table.setItems(rows);
        table.setEditable(true);
    }

    @FXML
    void back(ActionEvent event) {

    }

    @FXML
    void next(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(next_scene));
        Parent scene = loader.load();

        switch (next_scene) {
//            case "Mine_Scene.fxml":
//                Manipulate_Controller controller = loader.getController();
//                controller.init(choosen_file.getAbsolutePath());
//                break;

//            case "Predict_Scene.fxml":
//                Dataset_Controller controller1 = loader.getController();
//                controller1.init(choosen_file.getAbsolutePath());
//                break;
        }

        Stage window = (Stage) normal_combox.getScene().getWindow();
        window.setScene(new Scene(scene));
    }


    @FXML
    void norm(ActionEvent event) {

        String selected = normal_combox.getSelectionModel().getSelectedItem();

        if (selected!=null && !selected.isEmpty()) {

            Dataset ds;

            if (normal_combox.getSelectionModel().getSelectedItem().equals("Min Max"))
                ds = Normalization.minMax(dataset);
            else
                ds = Normalization.zScore(dataset);


            table.setItems(null);
            set_table(ds);

            discret_combox.setDisable(false);
        }
    }

    @FXML
    void disc(ActionEvent event){

        String selected_n = normal_combox.getSelectionModel().getSelectedItem();
        String selected_d = discret_combox.getSelectionModel().getSelectedItem();

        if(! Q.getText().isEmpty()
                && selected_d != null
                && selected_n != null
                && !selected_d.isEmpty()
                && !selected_n.isEmpty()){

            int value =  Integer.parseInt(Q.getText());
            Dataset ds;

            if(normal_combox.getSelectionModel().getSelectedItem().equals("Min Max"))
                ds = Normalization.minMax(dataset);
            else
                ds = Normalization.zScore(dataset);


            ArrayList<String[]> result;

            if(discret_combox.getSelectionModel().getSelectedItem().equals("Quantiles"))
                result =  Discretization.amplitudeDiscretization(ds, value);
            else
                result =  Discretization.sizeDiscretization(ds, value);



            table.setItems(null);


            final ObservableList<Row_str> rows = FXCollections.observableArrayList();

            String[] line = new String[dataset.nbAttributes()];

            for(int i=0; i<result.size(); i++){
                // add class row
                System.arraycopy(result.get(i), 0, line, 0, dataset.nbAttributes() - 1);
                line[dataset.nbAttributes()-1] = Integer.toString(dataset.getClass(i));

                Row_str row = new Row_str(line);
                rows.add(row);
            }


            table.setItems(rows);
            table.setEditable(true);
        }


    }

}


