package part2.classification;

import common.Dataset;

import java.util.ArrayList;
import java.util.HashMap;

public class NaiveBayes extends BaseClassification{

    private HashMap<ArrayList<Double>, String[]> discretizedTrain, discretizedTest;
    private final int nbIntervals;

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

    private int Nb_instance_per_classe(Dataset ds ,int classe){
        int cpt=0;
        for(int i=0; i<ds.nbInstances(); i++){
            int val;
            val = ds.getClass(i);
            if(val == classe){cpt++;}
        }
        return cpt;
    }

    //proba P=|C(i,d)|/|D|   (d=D)
    private double[] proba_per_classes(Dataset ds){
        //|Ci,d|
        double[] nb_instance_class = new double[3];
        for(int i=0; i<3; i++){
            nb_instance_class[i] = Nb_instance_per_classe(ds,i+1);
        }
        //|D|
        int D = ds.nbInstances();
        //|(Ci,d)|/|D|
        double[] classes_probas = new double[3];
        for(int i=0; i<3; i++){
            classes_probas[i] = (nb_instance_class[i]/D);
        }
        return(classes_probas);

    }

    //calcul probas per attribute
    private ArrayList<double[] > probas_per_attribute(int attribute_index){
        ArrayList<double[]> probas = new ArrayList<>();//Q=4, probas de [(C1,C1,C1,C1),(C2,C2,C2,C2),(C3,C3,C3,C3)]

        for (int i = 0; i < 3; i++) {
            double[] proba_class = new double[this.nbIntervals];//(C1,C1,C1,C1)
            for (int j = 0; j < this.nbIntervals; j++) {
                proba_class[j]=0;
            }
            probas.add(proba_class);
        }

        for(int i=0; i<this.train_data.nbInstances(); i++){

            String value =  this.discretizedTrain.get(train_data.getInstanceArrayList(i))[attribute_index];
            int classe = this.train_data.getClass(i);

            char ch = value.charAt(2);
            int intervalle = Integer.parseInt(String.valueOf(ch));

            double[] proba_class2;
            proba_class2 = probas.get(classe-1);
            proba_class2[intervalle-1]++;
            probas.set(classe-1, proba_class2);
        }
        //to verify frequences of intervalls in each class for a given attribute (CHECKED)

        for (int i = 0; i < probas.size(); i++) {
            for (int j = 0; j < probas.get(i).length; j++) {
                probas.get(i)[j] = probas.get(i)[j]/(this.train_data.nbInstances()/3.0);
            }
        }
        return probas;
    }

    //conditional probas
    private ArrayList<ArrayList<double[]>> Cond_probas(){
        ArrayList<ArrayList<double[]>> cond_probas = new ArrayList<>();

        for(int i=0; i<this.train_data.nbAttributes()-1; i++){
            cond_probas.add(probas_per_attribute(i));
        }
        //to verify final probas for each attribute (CHECKED)
        return cond_probas;
    }

    //Naive bayesian obtained probas for each class
    private double[] Naive_Bayesian(String[] instance){
        double[] naive_bayesian = new double[3];
        double[] probas_classes = proba_per_classes(this.train_data); //verified! 70 instance for each class
        ArrayList<ArrayList<double[]>> cond_probas=Cond_probas();// 3 probabilities(3 classes) for each attribute(7 attributes)

        for (int i = 0; i < 3; i++) {
            naive_bayesian[i] = probas_classes[i];

            for (int j = 0; j < instance.length-1; j++) {

                int intervalle = Integer.parseInt(String.valueOf(instance[j].charAt(2)));
                naive_bayesian[i] = naive_bayesian[i] * ((cond_probas.get(j).get(i))[intervalle-1]);
            }

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
        this.predictedData = new HashMap<>(  );
        for (ArrayList<Double> list : this.test_data){
            int predictedClass = predict(this.discretizedTest.get(list));
            this.predictedData.put(list, predictedClass);
        }
    }
}
