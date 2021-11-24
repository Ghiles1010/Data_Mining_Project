package UI;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;



public class Eval_controller {


    @FXML
    private TextArea algo_name_Text_area;
    @FXML
    private TextArea dataset_name_Text_area;
    @FXML
    private TextArea trace_Text_area;
    @FXML
    private TextArea time_Text_area;
    @FXML
    private TextArea percent_Text_area;
    @FXML
    private ListView<String> solution_list;

    @FXML
    private Button back;

    private Boolean launched = false;

    private String algorithm_name;
    private String file_path;


    public void init(String algorithm_name, String file_path, String file_name){

        algo_name_Text_area.editableProperty().setValue(false);
        algo_name_Text_area.setText(algorithm_name);

        dataset_name_Text_area.editableProperty().setValue(false);
        dataset_name_Text_area.setText(file_name);

        trace_Text_area.editableProperty().setValue(false);
        time_Text_area.editableProperty().setValue(false);
        percent_Text_area.editableProperty().setValue(false);

        this.algorithm_name = algorithm_name;
        this.file_path = file_path;
    }

    @FXML
    private  void back() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Main_Scene.fxml"));
        Parent main_scene = loader.load();
        Stage window = (Stage) back.getScene().getWindow();
        window.setScene(new Scene(main_scene));
    }

    @FXML
    private void launch(){

        if(! launched) {

            launched = true;

            //SAT sat = SAT.createSAT(file_path);

            //Algorithm algorithm;

            switch (algorithm_name) {

                case "Depth First":
              //      algorithm = new depth_first_search(sat);
                    break;
                case "Breadth First":
                //    algorithm = new Breadth_first_search(sat);
                    break;
                case "A* - Heuristic 1":
                  //  algorithm = new AStarHeuristic1(sat);
                    break;
                case "A* - Heuristic 2":
                    //algorithm = new AStarHeuristic2(sat);
                    break;
                case "A* - Heuristic 3":
                    //algorithm = new AStarHeuristic3(sat);
                    break;
                case "A* - Heuristic 4":
                    //algorithm = new AStarHeuristic4(sat);
                    break;
                case "A* - Heuristic 5":
                    //algorithm = new AStarHeuristic5(sat);
                    break;
                case "Particle Swarm Optimization":
                    //algorithm = new PSO(sat, 10, 1,1,1,1, 100000 );
                    break;
                case "Genetic Algorithm":
                    //algorithm = new GeneticAlgorithm(sat, 100, 10000);
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + algorithm_name);
            }


            //Search task = new Search(this, algorithm);

           //new Thread(task).start();



        }

    }

    public void add_trace(String message){

        String current_message = trace_Text_area.getText();
        trace_Text_area.setText(current_message + message);

    }

    public void set_time(String time){
        time_Text_area.setText(time);
    }

    public void set_percent(String percent){percent_Text_area.setText(percent);}

    public void set_solution(ArrayList<Integer> solution){

        int cpt = 1;
        for(Integer literal : solution){

            String value = literal == 1 ? "true" : "false";

            String lit = "x" + cpt + " = " + value;

            solution_list.getItems().add(lit);

            cpt += 1;
        }
    }
}
