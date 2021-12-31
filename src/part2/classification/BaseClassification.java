package part2.classification;

import common.Dataset;

import java.util.ArrayList;

public class BaseClassification {

    protected Dataset dataset, train_data, test_data;

    public BaseClassification(Dataset dataset) {
        this.dataset = dataset;
        this.split_data();
    }

    public BaseClassification(String path) {
        this.dataset = new Dataset(path);
        this.split_data();
    }

    // this methos splits the data between train and test data
    protected void split_data(){
        ArrayList<ArrayList<Double>> train = new ArrayList<>();
        ArrayList<ArrayList<Double>> test = new ArrayList<>();
        double instanceClass;

        // nbTestx represent the number of
        int nbTest1 = 20, nbTest2 = 20, nbTest3 = 20;
        for (int i=0; i<this.dataset.nbInstances(); i++){
            instanceClass = this.dataset.getClass( i );
            if (instanceClass == 1){
                if (nbTest1 > 0){
                    test.add(this.dataset.getInstanceArrayList(i));
                    nbTest1--;
                } else {
                    train.add(this.dataset.getInstanceArrayList(i));
                }
            } else if (instanceClass == 2){
                if (nbTest2 > 0){
                    test.add(this.dataset.getInstanceArrayList(i));
                    nbTest2--;
                } else {
                    train.add(this.dataset.getInstanceArrayList(i));
                }
            } else if (instanceClass == 3){
                if (nbTest3 > 0){
                    test.add(this.dataset.getInstanceArrayList(i));
                    nbTest3--;
                } else {
                    train.add(this.dataset.getInstanceArrayList(i));
                }
            }
        }
        this.train_data = new Dataset(train);
        this.test_data = new Dataset(test);
    }

}
