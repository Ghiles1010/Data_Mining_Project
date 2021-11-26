package UI;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.Dataset;
import utils.Row;

import java.io.FileNotFoundException;
import java.io.IOException;


public class Manipulate_Controller{

    private final int COL_WIDTH = 100;
    private  Dataset dataset;

    @FXML
    TableView<Row> table;


    public  void init(String dataset_path){


        dataset = new Dataset();
        dataset.ReadDataset(dataset_path);

        init_table();


    }

    @FXML
    public void back() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Main_Scene.fxml"));
        Parent eval_scene = loader.load();

        Stage window = (Stage) table.getScene().getWindow();
        window.setScene(new Scene(eval_scene));
    }


    private void init_table(){


        for (int i=0;i<dataset.Nb_attributes();i++){

            String column_name = dataset.get_column_name(i);

            TableColumn<Row, Double> column = new TableColumn(column_name);

            column.setCellValueFactory(new PropertyValueFactory<Row, Double>(column_name));

            column.setPrefWidth(COL_WIDTH);
            table.getColumns().add(column);
            table.setPrefWidth(table.getPrefWidth() + COL_WIDTH);
        }

        final ObservableList<Row> rows = FXCollections.observableArrayList();

        for(int i=0; i<dataset.Nb_Instances(); i++){
            Row row = new Row(dataset.getInstance(i));
            rows.add(row);
        }


        table.setItems(rows);
        table.setEditable(true);
    }

    @FXML
    public void save() throws FileNotFoundException {

        dataset.SaveDataset();

    }

    @FXML
    public void edit() throws IOException {

        Row selected_row = table.getSelectionModel().getSelectedItem();

        if(selected_row != null) {


            double[] instance = selected_row.getInstance();
            int index = table.getSelectionModel().getSelectedIndex();

            Edit_Table_Controller edit_table_controller = new Edit_Table_Controller();

            instance = edit_table_controller.display(dataset.getNames(), instance);

            Row row = new Row(instance);

            table.getItems().set(index, row);
            for (int i = 0; i < instance.length; i++) {
                dataset.editInstance(index, i, instance[i]);

            }
        }
    }

    @FXML
    public void add() throws IOException {

        double [] instance = new double[dataset.Nb_attributes()];

        Add_Table_Controller add_table_controller = new Add_Table_Controller();

        instance = add_table_controller.display(dataset.getNames());

        Row row = new Row(instance);

        table.getItems().add(row);

        dataset.addInstance(instance);

    }

    @FXML
    public void delete(){

        Row selected_row = table.getSelectionModel().getSelectedItem();

        if(selected_row != null) {

            int index = table.getSelectionModel().getSelectedIndex();

            table.getItems().remove(index);
            dataset.removeInstance(index);

        }




    }


}
