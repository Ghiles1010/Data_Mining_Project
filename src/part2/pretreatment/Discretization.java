package part2.pretreatment;

import common.Dataset;

import java.util.ArrayList;
import java.util.Arrays;

public class Discretization {


    public static ArrayList<String[]> sizeDiscretization(Dataset ds, int Q){

        ArrayList<String[]> result = new ArrayList<>();
        for (int i=0; i<ds.nbInstances(); i++){
            result.add(sizeDiscretization(ds, i, Q));
        }
        return result;
    }

    public static String[] sizeDiscretization(Dataset ds, int instance_index, int Q){
        //Discretisation en classes d'effectifs egaux (Equal-frequency)

        double [] instance = ds.getInstance(instance_index);
        String[] new_instance = new String[instance.length-1];

        for (int i = 0; i < instance.length - 1 ; i++) {
            double[] attribut_col = ds.getColumn(i);
            double[] bornes_sup = bornes(ds, attribut_col, Q); //Recuperer les bornes superieur des intervalles de cette attributs
            double borne_sup = bornes_sup[0];
            int num_intervalle = 1;
            //Pour la valeur : trouver le num de l'intervalle (en comparant la valeur avec la borne sup des intervalles)
            while (borne_sup <= bornes_sup[Q-1]){   // bornes_sup[Q-1] : borne max
                if (instance[i] < borne_sup) {
                    break;
                }
                else if(instance[i] >= borne_sup) {
                    System.out.print("I'm here");
                    borne_sup = bornes_sup[num_intervalle];
                    num_intervalle = num_intervalle + 1;
                }
            }
            new_instance[i] = "I" + (i+1) + num_intervalle;
        }
        return new_instance;
    }


    public static double[] bornes(Dataset ds, double[] attribut_col , int Q){
        //fonction qui permet de calculer les bornes des intervalles pour la discretisation en classes d'effectifs egaux
        int nb_inst = (int) Math.ceil(ds.nbInstances() / Q); //Nombre d'instances par intervalle.

        //double[] attribut_col = ds.getColumn(att_index);
        Arrays.sort(attribut_col);

        double[] bornes_sup = new double[Q]; //Tableau qui va contenir les bornes supérieurs des 'Q' intervalles

        for (int i = 1; i < Q; i++) {
            int indice_quantile = nb_inst * i - 1;
            bornes_sup[i-1] = attribut_col[indice_quantile];
        }
        bornes_sup[Q-1] = attribut_col[attribut_col.length-1]; //la borne sup du dernier intervalle

        return bornes_sup;
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
                if (instance[i] < borne_sup || instance[i] == max) {
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
