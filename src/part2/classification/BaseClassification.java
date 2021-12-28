package part2.classification;

import common.Dataset;

import java.util.ArrayList;

public class BaseClassification {

    protected Dataset dataset;
    protected ArrayList<Integer> train_data_indexes, test_data_indexes;

    public BaseClassification(Dataset dataset) {
        this.dataset = dataset;
    }

    public BaseClassification(String path) {
        this.dataset = new Dataset(path);
    }

    // this methos splits the data between train and test data
    protected void split_data(){

    }


}
