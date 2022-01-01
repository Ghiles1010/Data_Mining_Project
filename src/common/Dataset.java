package common;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

public class Dataset implements Iterable<ArrayList<Double>>{
    ArrayList<ArrayList<Double>> data;
    String[] Names = {"Area", "Perimeter", "Compactness", "Kernel_lenght", "Kernel_width",
            "Asymmetry_coefficient", "Kernel_groove_groove", "Classe"};

    public Dataset(String path) {
        this.ReadDataset(path);
    }

    public Dataset(ArrayList<ArrayList<Double>> data) {
        this.data = data;
    }

    public void ReadDataset(String path){
        this.data = new ArrayList<ArrayList<Double>>();
        try {
            BufferedReader BfReader = new BufferedReader(new FileReader(path));
            String line = BfReader.readLine();
            while (line != null){
                //Lire le fichier du dataset ligne par ligne et le mettre dans une 'ArrayList' de tableaux de 'doubles'
                String [] values = line.split("[ \t]+");
                //double [] values_double = new double[8];
                ArrayList instance = new ArrayList();
                for (int i = 0; i<values.length; i++){
                    if (values[i] != "")
                        //instance.set(i, (Double.parseDouble(values[i])));
                        instance.add((Double.parseDouble(values[i])));
                }
                this.data.add(instance);
                line = BfReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return this.data;
    }

    public int nbInstances() { return data.size(); }

    public int nbAttributes() { return data.get(0).size(); }

    public ArrayList<Double> getInstanceArrayList(int i) { return data.get(i); }

    public double[] getInstance(int index) {
        double[] instance = new double[nbAttributes()];
        for (int i = 0; i < instance.length; i++) {
            instance[i] = (double) (this.data.get(index)).get(i);
        }
        return instance;
    }

    public int getClass(int index) { return (int) ((double)data.get(index).get(this.nbAttributes()-1)); }

    public double[] getFeatures(int index) {
        return Arrays.copyOf(getInstance(index), this.nbAttributes()-1);
    }

    public double[] getColumn (int attribute_index){
        double[] column = new double[this.nbInstances()];

        for (int i = 0; i < this.nbInstances(); i++) {
            column[i] = data.get(i).get(attribute_index);
        }

        return column;
    }

    public int nbClasses(){
        double[] class_att = this.getColumn(nbAttributes()-1);
        return (int) java.util.Arrays.stream(class_att).distinct().count();
    }

    public boolean contains (ArrayList<Double> list){
        return this.data.contains(list);
    }

    public double [] getAllClasses (){
        double [] classes;
        classes = (java.util.Arrays.stream(this.getColumn(nbAttributes()-1)).distinct()).toArray();
        //for (double c : classes) { System.out.print("\n class : "+ (int)c); }
        return classes;
    }

    public void addInstance(double [] instance){
        ArrayList inst = (ArrayList) Arrays.stream(instance).boxed().collect(Collectors.toList());
        this.data.add(inst);
    }

    public void addInstance(int index, double [] instance){
        ArrayList inst = (ArrayList) Arrays.stream(instance).boxed().collect(Collectors.toList());
        this.data.add(index, inst);
    }

    public void editInstance(int instance_index, int attribute_index, double new_value){
        this.data.get(instance_index).set(attribute_index, new_value);
    }

    // edit an instance by giving an array of double
    public void editInstance(int instance_index, double [] instance){
        ArrayList inst = (ArrayList) Arrays.stream(instance).boxed().collect(Collectors.toList());
        this.data.set(instance_index, inst);
    }

    // edit an instance by giving an arraylist of doubles
    public void editInstance(int instance_index, ArrayList<Double> instance){
        this.data.set(instance_index, instance);
    }

    public void removeInstance (int index){
        data.remove(index);
    }

    public int nbInstancePerClass (int classe){       //Nombre d'instances de cette classe
        int nb_inst = 0;
        for (int i = 0; i < nbInstances(); i++) {
            if (this.getClass(i) == classe)  nb_inst++;
        }
        return nb_inst;
    }

    public void saveDataset() throws FileNotFoundException{
        PrintWriter PW = new PrintWriter(new FileOutputStream("dataset\\new_dataset.txt"));
        for (int i = 0; i < this.nbInstances(); i++) {
            double[] instance = this.getInstance(i);
            String inst = "";
            for (double value : instance) {
                inst = inst + value + "\t";
            }
            PW.println(inst);
        }
        PW.close();
    }

    public void printDataset () {
        for (int i = 0; i < this.nbInstances(); i++) {
            double[] instance = this.getInstance(i);
            System.out.print("\n");
            for (int j = 0; j < this.nbAttributes(); j++) {
                System.out.print(instance[j] + "\t");
            }
        }
    }

    public void printInstance (double[] instance) {
        for (int i = 0; i < instance.length; i++) {
            System.out.print(instance[i] + "\t");
        }
        System.out.println("\n");
    }

    // Description du DATASET
    public String desc(){
        return " Le dataset represente un échantillon de grains appartenant à trois variétés différentes de blé " +
                "Kama, Rosa and Canadian. Chaque classe a 70 elements";
    }

    public String class_desc() {
        double[] column = this.getColumn(7);
        int[] nombre = new int[this.nbClasses()];
        int[] pourcentage = new int[this.nbClasses()];

        for(int j=0; j<this.nbClasses(); j++) {
            nombre[j] = 0;
            for (int i = 0; i < column.length; i++){
                if(((int)column[i])== j+1){
                    nombre[j]+=1;
                }
            }
        }
        //pourcentage
        for (int i =0; i<nombre.length;i++) {
            pourcentage[i] = nombre[i] * 100 / column.length; }

        return "nombre d'instance de classe KAMA = " + nombre[0] + "\n le pourcentage = " + pourcentage[0]+"%"
                + "\nnombre d'instance de classe ROSA = " + nombre[1]+ "\n le pourcentage = " + pourcentage[1]+"%"
                + "\nnombre d'instance de classe CANADIAN = " + nombre[2] + "\n le pourcentage = "+pourcentage[2]+"%";
    }

    public String desc_att(int i){
        String[] name = {"area A", "perimeter P", "compactness C", "length of kernel", "width of kernel",
                "Asymmetry coefficient", "length of kernel groove","Classe"};
        String[] desc = {"la surface de la graine.", "le périmètre de la graine", "la compacité obtenue grace a la formule (4piA/P^2)",
                "La longeur de la graine", "La largeur de la graine", "Coefficient d'asymétrie", "La longeur du bout superieur de la graine", "La classe a la quelle appartient la graine"};

        StringBuilder sb = new StringBuilder();


        sb.append("Attribut \"").append(get_column_name(i)).append("\"");
        sb.append("\n\tNom : ").append(name[i]);
        sb.append("\n\tDescriptyion : ").append(desc[i]);
        if(i!=7){
            sb.append("\n\tLes valeurs sont des valeurs Réels");
            sb.append("\n\tL'attribut est : Numerique, Quantitatif, Continu");
        }else {
            sb.append("\n\tLes valeurs sont 1, 2, 3 ");
            sb.append("\n\tL'attribut est : Nominal, Qualitatif, Discret ");
        }


        return sb.toString();
    }

    //Valeurs manquantes
    public boolean val_manq(){
        boolean val = false;
        for(int att=0; att<this.nbAttributes();att++){
            double[] column = this.getColumn(att);
            for(int i=0; i<column.length; i++){
                if(String.valueOf(column[i])=="NAN" || String.valueOf(column[i])==" " ) val = true;
            }
        }
        return val;
    }

    public String get_column_name(int i){
        return Names[i];
    }

    public String[] getNames() {
        return Names;
    }

    @Override
    public Iterator<ArrayList<Double>> iterator() {
        return this.data.iterator();
    }
}