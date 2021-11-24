package UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.Row_Field;

import java.io.IOException;

public class Add_Table_Controller {



    final int COL_WIDTH = 100;

    double []  return_values;
    Stage secondStage;





    public double[] display(String [] names) throws IOException {

        secondStage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Add_Table_Scene.fxml"));
        Parent next_root = loader.load();

        Scene scene = new Scene(next_root, 600, 170);
        secondStage.setScene(scene);


        TableView<Row_Field> table_add = (TableView) scene.lookup("#table_add");
        Button ok_button = (Button) scene.lookup("#OK");

        ok_button.setOnAction(e -> {
                    return_values = table_add.getItems().get(0).get_doubles();
                    secondStage.close();
                }
        );


        for (String column_name : names) {

            TableColumn<Row_Field, TextField> column = new TableColumn<>(column_name);

            column.setCellValueFactory(new PropertyValueFactory<Row_Field, TextField>(column_name));

            column.setPrefWidth(this.COL_WIDTH);
            table_add.getColumns().add(column);
            table_add.setPrefWidth(table_add.getPrefWidth() + COL_WIDTH);
        }

        final ObservableList<Row_Field> rows = FXCollections.observableArrayList();
        Row_Field row = new Row_Field();
        rows.add(row);

        table_add.setItems(rows);
        table_add.setEditable(true);

        secondStage.showAndWait();


        return return_values;
    }

}
