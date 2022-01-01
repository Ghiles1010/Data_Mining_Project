package part2;

import common.Dataset;
import part2.classification.KNN;
import part2.classification.NaiveBayes;
import part2.extraction.ECLAT;
import part2.pretreatment.Discretization;
import part2.pretreatment.Normalization;

import java.util.ArrayList;

public class API {

    public static Dataset normalization(String pathDataset, String normalizationAlgorithm){
        Dataset ds = new Dataset(pathDataset);
        return normalization(ds, normalizationAlgorithm);
    }

    public static Dataset normalization(Dataset ds, String normalizationAlgorithm){
        if (normalizationAlgorithm.equalsIgnoreCase("minmax")){
            return Normalization.minMax(ds);
        } else if (normalizationAlgorithm.equalsIgnoreCase("zscore")){
            return Normalization.zScore(ds);
        } else {
            return null;
        }
    }

    public static ArrayList<String[]> discretisation(Dataset ds, String discretizationAlgorithm, int Q){

        if (discretizationAlgorithm.equalsIgnoreCase("size")){
            return Discretization.sizeDiscretization(ds, Q);
        } else if (discretizationAlgorithm.equalsIgnoreCase("amplitude")){
            return Discretization.amplitudeDiscretization(ds, Q);
        } else {
            return null;
        }
    }

    public static ECLAT eclat(ArrayList<String[]> discretizationData, int minSupportPercentage){
        ECLAT eclat = new ECLAT(discretizationData, minSupportPercentage);
        eclat.execute();
        return eclat;
    }

    public static KNN knn(Dataset ds, int testSize, int k, String distanceType){
        KNN knn = new KNN(ds, k, distanceType, testSize);
        knn.execute();
        return knn;
    }

    public static NaiveBayes naiveBayes(Dataset ds,  int testSize, ArrayList<String[]> discretizationData, int Q){
        NaiveBayes naiveBayes = new NaiveBayes(ds, testSize, discretizationData, Q);
        naiveBayes.execute();
        return naiveBayes;
    }
}
