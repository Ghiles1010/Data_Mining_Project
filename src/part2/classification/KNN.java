package part2.classification;

import common.Dataset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class KNN extends BaseClassification{

    private int k;

    public KNN(Dataset dataset, int k) {
        super(dataset);
        this.k = k;
    }

    public KNN(String path, int k) {
        super(path);
        this.k = k;
    }

    private HashMap<Integer, Double> nearests(HashMap<Integer, Double> nearests, int index, double dist){

        if (nearests.size() < this.k){
            nearests.put(index, dist);
        } else {

            int max_key = -1;
            double max = 0;

            for (int i : nearests.keySet()){
                if (max_key == -1){
                    max_key = i;
                    max = nearests.get(i);
                } else if (nearests.get(i) > max) {
                    max = nearests.get(i);
                    max_key = i;
                }
            }

            if (dist < max){
                nearests.remove(max_key);
                nearests.put(index, dist);
            }
        }
        return nearests;
    }

    public int predict(ArrayList<Double> instance){

        // remove the class instance
        instance = new ArrayList<>( instance );
        instance.remove(this.test_data.nbAttributes()-1);

        HashMap<Integer, Double> nearests = new HashMap<>();

        for (int i=0; i<this.train_data.nbInstances(); i++){
            ArrayList<Double> train_instance = this.train_data.getInstanceArrayList(i);
            double dist = this.euclidianDistance(instance, train_instance);
            nearests = this.nearests(nearests, i, dist);
        }

        ArrayList<Integer> classes = new ArrayList<>();

        for (int i=0; i<this.test_data.nbClasses(); i++){
            classes.add(0);
        }

        for (int i : nearests.keySet()){
            classes.set(train_data.getClass(i)-1, classes.get(train_data.getClass(i)-1)+1);
        }

        int predictedClass = classes.indexOf(Collections.max(classes)) + 1;

        return predictedClass;
    }

    public void test(){
        for (ArrayList<Double> list : this.test_data){

            int reelClass = list.get(this.test_data.nbAttributes()-1).intValue();
            int predictedClass = predict(list);
            if (predictedClass != reelClass){
                System.out.println(reelClass  + " " + predictedClass );
            }
        }
    }
}
