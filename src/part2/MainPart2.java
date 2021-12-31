package part2;



import common.Dataset;
import part2.classification.KNN;
import part2.pretreatment.Discretization;
import part2.pretreatment.Normalization;

import java.io.FileNotFoundException;

public class MainPart2 {

    public static void main(String[] args) throws FileNotFoundException {

        // Lecture et creation du dataset
        Dataset ds = new Dataset("dataset/seeds_dataset.txt");

        ds = Normalization.zScore(ds);

        ds.printDataset();
        Discretization.amplitudeDiscretization(ds, 4);

        KNN knn = new KNN(ds, 5, "euclidean");
        knn.test();


    }
}
