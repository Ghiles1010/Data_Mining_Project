package part2.pretreatment;

import common.Dataset;

import java.util.ArrayList;

public class Discretization {


    public static ArrayList<String[]> sizeDiscretization(Dataset ds, int Q){

        ArrayList<String[]> result = new ArrayList<>();
        for (int i=0; i<ds.nbInstances(); i++){
            result.add(sizeDiscretization(ds, i, Q));
        }
        return result;
    }

    public static String[] sizeDiscretization(Dataset ds, int instance_index, int Q){
        // TO-DO
        return new String[ds.nbAttributes()-1];
    }

    public static ArrayList<String[]> amplitudeDiscretization(Dataset ds, int Q){

        ArrayList<String[]> result = new ArrayList<>();
        for (int i=0; i<ds.nbInstances(); i++){
            result.add(amplitudeDiscretization(ds, i, Q));
        }
        return result;
    }

    public static String[] amplitudeDiscretization(Dataset ds, int instance_index, int Q){
        double min = 0;
        double max = 1;
        double width = 1 / (double)Q;  // On considere l'intervalle [0,1]

        double [] instance = ds.getInstance(instance_index);
        String[] new_instance = new String[instance.length-1];

        for (int i = 0; i < instance.length - 1 ; i++) {
            double borne_sup = min + width;
            int num_intervalle = 1;
            while (borne_sup <= max) {
                if (instance[i] < borne_sup) {
                    break;
                }
                else if(instance[i] >= borne_sup) {
                    borne_sup = borne_sup + width;
                    num_intervalle = num_intervalle + 1;
                }
            }

            new_instance[i] = "I" + (i+1) + num_intervalle;
        }
        return new_instance;
    }
}
