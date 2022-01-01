package UI;

import common.Dataset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import part2.API;
import part2.extraction.BaseExtraction;
import part2.extraction.ECLAT;
import part2.extraction.FrequentItemSets;
import part2.extraction.ItemsetElement;

import javax.swing.table.TableModel;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Mine_Controller {




    @FXML
    private ComboBox<String> algo_combox;

    @FXML
    private TextField support;

    @FXML
    private TextField conf;

    @FXML
    private TextArea freq_area;
    @FXML
    private TextArea rule_area;
    @FXML
    private TextArea p_rule_area;
    @FXML
    private TextArea n_rule_area;

    @FXML
    private TextField time_field;


    ArrayList<String[]> result_dataset;

    public  void init(ArrayList<String[]> result_dataset){

        this.result_dataset = result_dataset;

        ObservableList<String> options = FXCollections.observableArrayList("Apriori", "Eclat");
        algo_combox.setItems(options);
        time_field.setEditable(false);

    }

    @FXML
    void Mine(ActionEvent event) {

        String seleted = algo_combox.getSelectionModel().getSelectedItem();

        if(     seleted != null
                && !seleted.isEmpty()
                && !conf.getText().isEmpty()
                && !support.getText().isEmpty()){


            int s = Integer.parseInt(support.getText());
            double c = Double.parseDouble(conf.getText());

            BaseExtraction algorithm;

            if(seleted.equals("Apriori"))
                algorithm = API.eclat(result_dataset, s);
            else
                algorithm = API.apriori(result_dataset, s);



            ArrayList<ItemsetElement> frequent = algorithm.getFrequentItems();


            String rules = algorithm.getAssociationRules(c);
            String negatif_rules = algorithm.getNeagitfCorrelationRules();
            String positif_rules = algorithm.getPositifCorrelationRules();


            String exec_time = String.format("%.2f seconds", algorithm.getExecutionTime() / Math.pow(10, 9));
            write_table(frequent, rules, negatif_rules, positif_rules, exec_time);

        }

    }

    void write_table(ArrayList<ItemsetElement> frequent, String rules, String negatif_rules,
                     String positif_rules, String execution_time){

        StringBuilder sb = new StringBuilder();


        for(ItemsetElement item: frequent){
            sb.append(item.toString()).append("\n");
        }

        freq_area.setText(sb.toString());

        rule_area.setText(rules);
        n_rule_area.setText(negatif_rules);
        p_rule_area.setText(positif_rules);

        time_field.setText(execution_time);
    }

    @FXML
    void back(ActionEvent event) {

    }

    @FXML
    void next(ActionEvent event) {

    }

}

