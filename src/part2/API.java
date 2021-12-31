package part2;

import common.Dataset;
import part2.classification.KNN;
import part2.classification.NaiveBayes;
import part2.pretreatment.Discretization;
import part2.pretreatment.Normalization;

import java.util.ArrayList;

public class API {

    public static Dataset normalization(String pathDataset, String normalizationAlgorithm){

        Dataset ds = new Dataset(pathDataset);

        if (normalizationAlgorithm.equalsIgnoreCase("minmax")){
            return Normalization.minMax(ds);
        } else if (normalizationAlgorithm.equalsIgnoreCase("zscore")){
            return Normalization.zScore(ds);
        } else {
            return null;
        }
    }

    private static ArrayList<String[]> discretisation(Dataset ds, String discretizationAlgorithm, int Q){

        if (discretizationAlgorithm.equalsIgnoreCase("size")){
            return Discretization.sizeDiscretization(ds, Q);
        } else if (discretizationAlgorithm.equalsIgnoreCase("amplitude")){
            return Discretization.amplitudeDiscretization(ds, Q);
        } else {
            return null;
        }
    }

    public static ArrayList<String[]> discretisation(String pathDataset, String normalizationAlgorithm, String discretizationAlgorithm, int Q){
        Dataset normalizedDataset = API.normalization(pathDataset, normalizationAlgorithm);

        if (discretizationAlgorithm.equalsIgnoreCase("size")){
            return Discretization.sizeDiscretization(normalizedDataset, Q);
        } else if (discretizationAlgorithm.equalsIgnoreCase("amplitude")){
            return Discretization.amplitudeDiscretization(normalizedDataset, Q);
        } else {
            return null;
        }
    }

    public static KNN knn(String pathDataset, int testSize, String normalizationAlgorithm, int k, String distanceType){
        Dataset normalizedDataset = API.normalization(pathDataset, normalizationAlgorithm);
        KNN knn = new KNN(normalizedDataset, k, distanceType, testSize);
        knn.execute();
        return knn;
    }

    public static NaiveBayes naiveBayes(String pathDataset, int testSize, String normalizationAlgorithm, String discretizationAlgorithm, int Q){
        Dataset normalizedDataset = API.normalization(pathDataset, normalizationAlgorithm);
        ArrayList<String[]> discretizationData  = API.discretisation(normalizedDataset, discretizationAlgorithm, Q);
        NaiveBayes naiveBayes = new NaiveBayes(normalizedDataset, testSize, discretizationData, Q);
        naiveBayes.execute();
        return naiveBayes;
    }
}
