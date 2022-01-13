package part2.classification;

import common.Dataset;

import java.util.ArrayList;
import java.util.HashMap;

public class NaiveBayes extends BaseClassification{

    private HashMap<ArrayList<Double>, String[]> discretizedTrain, discretizedTest;
    private final int nbIntervals;

    private ArrayList<HashMap<String, HashMap<Integer, Integer>>> frequencyTable;
    private ArrayList<HashMap<String, HashMap<Integer, Double>>> likelihoodTable;

    public NaiveBayes(Dataset dataset, int testSize, ArrayList<String[]> discretizedDataset ,int nbIntervals) {
        super(dataset, testSize);
        this.nbIntervals = nbIntervals;
        this.splitDiscretizedDataset(discretizedDataset);
    }

    private void splitDiscretizedDataset(ArrayList<String[]> discretizedDataset){

        this.discretizedTrain = new HashMap<>(  );
        this.discretizedTest = new HashMap<>(  );

        for(int i=0; i<discretizedDataset.size(); i++){
            ArrayList<Double> list = this.dataset.getInstanceArrayList(i);
            // can improve this function by adding an id to the dataset
            if(this.train_data.contains(list)){
                discretizedTrain.put(list, discretizedDataset.get(i));
            } else if(this.test_data.contains(list)){
                discretizedTest.put(list, discretizedDataset.get(i));
            } else {
                System.out.println( "Unexpected ERROR, data not in train nor in test dataset" );
            }

        }
    }

    private void createFrequencyTable(){
        this.frequencyTable = new ArrayList<>(  );

        for (int i=0; i<this.train_data.nbAttributes()-1; i++){
            frequencyTable.add(new HashMap<>(  ));
            for (int j=0; j<this.train_data.nbInstances()-1; j++){
                ArrayList<Double> list = this.train_data.getInstanceArrayList(j);
                String instance = this.discretizedTrain.get(list)[i];

                if (!frequencyTable.get(i).containsKey(instance)){
                    frequencyTable.get(i).put(instance, new HashMap<>( ));
                    frequencyTable.get(i).get(instance).put(1,0);
                    frequencyTable.get(i).get(instance).put(2,0);
                    frequencyTable.get(i).get(instance).put(3,0);
                }

                // get the class
                int realclass = train_data.getClass( j );
                int actualFrequency = frequencyTable.get(i).get(instance).get(realclass);
                frequencyTable.get(i).get(instance).replace(realclass, actualFrequency+1);
            }
        }

        // adding test labels
        for (int i=0; i<this.test_data.nbAttributes()-1; i++){
            for (int j=0; j<this.test_data.nbInstances()-1; j++){
                ArrayList<Double> list = this.test_data.getInstanceArrayList(j);
                String instance = this.discretizedTest.get(list)[i];

                if (!frequencyTable.get(i).containsKey(instance)){
                    frequencyTable.get(i).put(instance, new HashMap<>( ));
                    frequencyTable.get(i).get(instance).put(1,0);
                    frequencyTable.get(i).get(instance).put(2,0);
                    frequencyTable.get(i).get(instance).put(3,0);
                }
            }
        }
    }

    private void createLikelihoodTable(){
        this.likelihoodTable = new ArrayList<>(  );

        for (int i=0; i<this.frequencyTable.size(); i++){
            likelihoodTable.add(new HashMap<>(  ));
            for (String instance : this.frequencyTable.get(i).keySet()){
                likelihoodTable.get(i).put(instance, new HashMap<>(  ));
                int totalElements = frequencyTable.get(i).get(instance).get(1) + frequencyTable.get(i).get(instance).get(2) +
                        frequencyTable.get(i).get(instance).get(3);
                for (Integer c : frequencyTable.get(i).get(instance).keySet()){
                    double value = (double) frequencyTable.get(i).get(instance).get(c) / totalElements;
                    likelihoodTable.get(i).get(instance).put(c, value);
                }
            }
        }
    }

    private double calculateProbability(String[] instance, int c){

        double probability = 1.;

        for (int i = 0; i < instance.length-1; i++) {
            probability *= likelihoodTable.get(i).get(instance[i]).get(c);
        }
        return probability;
    }

    //Naive bayesian obtained probas for each class
    private double[] Naive_Bayesian(String[] instance){
        double[] naive_bayesian = new double[3];


        for (int i = 1; i <= 3; i++) {
            naive_bayesian[i-1] = calculateProbability(instance, i);
        }

        return naive_bayesian;
    }

    //Prediction de la classe avec naive bayesian
    public int predict(String[] instance){
        double[] naive_bayesian;
        naive_bayesian = Naive_Bayesian(instance);
        int classification=0;
        for (int i = 1; i < naive_bayesian.length; i++) {
            if(naive_bayesian[classification] < naive_bayesian[i]){
                classification=i;
            }
        }
        return (classification+1);
    }

    @Override
    public void test() {
        this.createFrequencyTable();
        this.createLikelihoodTable();

        this.predictedData = new HashMap<>(  );
        for (ArrayList<Double> list : this.test_data){
            int predictedClass = predict(this.discretizedTest.get(list));
            this.predictedData.put(list, predictedClass);
            System.out.println( list.get(list.size()-1) + " : " + predictedClass );
        }
        System.out.println( 11 );
    }
}
