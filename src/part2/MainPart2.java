package part2;



import common.Dataset;
import part2.classification.KNN;
import part2.classification.NaiveBayes;
import part2.extraction.Apriori;
import part2.extraction.ECLAT;
import part2.pretreatment.Discretization;
import part2.pretreatment.Normalization;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainPart2 {

    public static void main(String[] args) throws FileNotFoundException {

        Dataset ds = API.normalization("dataset/seeds_dataset.txt", "minmax");
        ArrayList<String[]> r =  API.discretisation(ds, "amplitude", 4);

        System.out.println(API.knn(ds, 20, 5, "euclidean").getPrintedConfusion_matrix());

    }
}
