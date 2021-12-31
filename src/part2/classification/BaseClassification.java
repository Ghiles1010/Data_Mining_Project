package part2.classification;

import common.Dataset;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BaseClassification {

    protected Dataset dataset, train_data, test_data;
    protected HashMap<ArrayList<Double>, Integer> predictedData;
    protected ArrayList<ArrayList<Integer>> confusion_matrix;

    protected double executionTime;

    public BaseClassification(Dataset dataset, int testSize) {
        this.dataset = dataset;
        this.split_data(testSize);
    }

    public HashMap<ArrayList<Double>, Integer> getPredictedData() {
        return predictedData;
    }

    public ArrayList<ArrayList<Integer>> getConfusion_matrix() {
        return confusion_matrix;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public ArrayList<ArrayList<Integer>> confusionMatrix(){
        this.confusion_matrix = new ArrayList<>();
        int predictedClass, realClass;

        //Initialize confusion_matrix
        for (int i=0; i<3; i++) {
            this.confusion_matrix.add(new ArrayList<>());
            for (int j=0; j<3; j++) {
                this.confusion_matrix.get(i).add(0);
            }
        }

        for (ArrayList<Double> list : this.predictedData.keySet()) {

            predictedClass = this.predictedData.get(list);
            realClass = list.get(list.size() - 1).intValue();
            int value = this.confusion_matrix.get(realClass-1).get(predictedClass-1);
            value++;
            this.confusion_matrix.get(realClass-1).set(predictedClass-1, value);
        }

        return this.confusion_matrix;
    }

    public abstract void test();

    public void execute(){
        double y = System.nanoTime();
        test();
        double x = System.nanoTime();
        this.executionTime = x - y;

        this.confusionMatrix();
        System.out.println(  );
    }

    // this methos splits the data between train and test data
    protected void split_data(int testSize){
        ArrayList<ArrayList<Double>> train = new ArrayList<>();
        ArrayList<ArrayList<Double>> test = new ArrayList<>();
        double instanceClass;

        // nbTestx represent the number of
        int nbTest1 = testSize, nbTest2 = testSize, nbTest3 = testSize;
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
