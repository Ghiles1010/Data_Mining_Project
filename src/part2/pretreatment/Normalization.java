package part2.pretreatment;

import common.Calcul;
import common.Dataset;

import java.util.ArrayList;

public class Normalization {


    public static Dataset minMax(Dataset ds){
        ArrayList<Double> new_instance;
        ArrayList<ArrayList<Double>> temp = new ArrayList<>(  );

        for (ArrayList<Double> instance : ds){
            new_instance = new ArrayList<>();

            for (int i = 0; i < instance.size() - 1; i++) {
                new_instance.add(minMaxNorm(instance.get(i), Calcul.min(ds, i), Calcul.max(ds, i)));
            }
            new_instance.add(instance.get(instance.size()-1));
            temp.add(new_instance);
        }

        ds = new Dataset(temp);
        return ds;
    }

    public static double[] minMax(Dataset ds, int instance_index){
        double[] instance = ds.getInstance(instance_index);
        double[] new_instance = new double[instance.length];

        for (int i = 0; i < new_instance.length; i++) {
            new_instance[i] = minMaxNorm(instance[i], Calcul.min(ds, i), Calcul.max(ds, i));
        }
        return new_instance;
    }

    private static double minMaxNorm (double val, double min, double max){
        return (val - min)/(max-min);
    }

    public static void zScore(){

    }
}
