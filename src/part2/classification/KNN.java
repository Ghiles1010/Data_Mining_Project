package part2.classification;

import common.Dataset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class KNN extends BaseClassification{

    private final int k;
    private final String distanceType;

    public KNN(Dataset dataset, int k, String distanceType, int testSize) {
        super(dataset, testSize);
        this.k = k;

        if (distanceType.equalsIgnoreCase("euclidean") || distanceType.equalsIgnoreCase("manhattan")){
            this.distanceType = distanceType;
        } else {
            this.distanceType = "euclidean";
        }
    }

    private double euclideanDistance(ArrayList<Double> l1, ArrayList<Double> l2){
        double dist = 0;

        for (int i=0; i<l1.size(); i++){
            dist += Math.pow(l1.get(i) - l2.get(i), 2);
        }
        return Math.sqrt(dist);
    }

    private double manhattanDistance(ArrayList<Double> l1, ArrayList<Double> l2){
        double dist = 0;

        for (int i=0; i<l1.size(); i++){
            dist += Math.abs(l1.get(i) - l2.get(i));
        }
        return dist;
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

        double dist = 0;

        // remove the class instance
        instance = new ArrayList<>( instance );
        instance.remove(this.test_data.nbAttributes()-1);

        HashMap<Integer, Double> nearests = new HashMap<>();

        for (int i=0; i<this.train_data.nbInstances(); i++){
            ArrayList<Double> train_instance = this.train_data.getInstanceArrayList(i);

            if (distanceType.equalsIgnoreCase("euclidean")){
                dist = this.euclideanDistance(instance, train_instance);
            } else if (distanceType.equalsIgnoreCase("manhattan")){
                dist = this.manhattanDistance(instance, train_instance);
            }

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

    @Override
    public void test(){

        this.predictedData = new HashMap<>(  );
        for (ArrayList<Double> list : this.test_data){
            int predictedClass = predict(list);
            this.predictedData.put(list, predictedClass);
        }
    }
}
