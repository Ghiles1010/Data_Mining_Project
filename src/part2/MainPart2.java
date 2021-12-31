package part2;



import common.Dataset;
import part2.classification.KNN;
import part2.classification.NaiveBayes;
import part2.pretreatment.Discretization;
import part2.pretreatment.Normalization;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainPart2 {

    public static void main(String[] args) throws FileNotFoundException {

        // Lecture et creation du dataset
        Dataset ds = new Dataset("dataset/seeds_dataset.txt");

        ds = Normalization.minMax(ds);

        ds.printDataset();
        ArrayList<String[]> discretization = Discretization.amplitudeDiscretization(ds, 4);

        NaiveBayes knn = new NaiveBayes(ds, 20, discretization, 20);
        knn.execute();


    }
}
