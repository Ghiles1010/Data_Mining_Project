package part2;



import common.Dataset;
import part2.classification.KNN;

import java.io.FileNotFoundException;

public class MainPart2 {

    public static void main(String[] args) throws FileNotFoundException {

        // Lecture et creation du dataset
        Dataset ds = new Dataset("dataset/seeds_dataset.txt");
        ds.printDataset();

        KNN k = new KNN(ds, 3);


    }
}
