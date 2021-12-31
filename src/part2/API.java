package part2;

import common.Dataset;
import part2.pretreatment.Discretization;
import part2.pretreatment.Normalization;

import java.util.ArrayList;

public class API {

    public static Dataset normalization(Dataset ds, String normalizationAlgorithm){
        if (normalizationAlgorithm.equalsIgnoreCase("min-max")){
            return Normalization.minMax(ds);
        } else if (normalizationAlgorithm.equalsIgnoreCase("z-score")){
            return Normalization.zScore(ds);
        } else {
            return null;
        }
    }

    public static ArrayList<String[]> discretisation(Dataset ds, String normalizationAlgorithm, String discretizationAlgorithm, int Q){
        Dataset normalizedDataset = API.normalization(ds, normalizationAlgorithm);

        if (discretizationAlgorithm.equalsIgnoreCase("size")){
            return Discretization.sizeDiscretization(ds, Q);
        } else if (discretizationAlgorithm.equalsIgnoreCase("amplitude")){
            return Discretization.amplitudeDiscretization(ds, Q);
        } else {
            return null;
        }
    }
}
