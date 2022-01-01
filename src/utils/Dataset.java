package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



public class Dataset {

    final String NEW_PATH = "dataset\\new_dataset.txt";

    XYSeriesCollection dataset = new XYSeriesCollection();
    ArrayList data = new ArrayList();
    String[] Names = {"Area", "Perimeter", "Compactness", "Kernel_lenght", "Kernel_width",
            "Asymmetry_coefficient", "Kernel_groove_groove", "Classe"};

    public XYSeriesCollection ReadDataset(String path){
        try {
            BufferedReader BfReader = new BufferedReader(new FileReader(path));
            String line = BfReader.readLine();
            while (line != null){
                //Lire le fichier du dataset ligne par ligne et le mettre dans une 'ArrayList' de tableaux de 'doubles'
                String [] values = line.split("[ \t]+");
                double [] values_double = new double[8];
                for (int i = 0; i<values_double.length; i++){
                    if (values[i] != "")
                        values_double[i] = (Double.parseDouble(values[i]));
                }
                data.add(values_double);
                line = BfReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.CreateDataset();
        return this.dataset;
    }

    public void CreateDataset(){
        // Creation d'une collection de données (de type XYSeriesCollection)
        // Chaque colonne de la collection est de type 'XYSeries' où X vas contenir la valeur de l'attribut
        int nb_attributes = ((double [])this.data.get(0)).length;
        int nb_instances = this.data.size();
        for (int i = 0; i < nb_attributes; i++){
            XYSeries X = new XYSeries(""+i, false);
            for (int j=0; j<nb_instances; j++){
                X.add(((double[])data.get(j))[i], null); // Recuperer l'élément j de la ligne i.
            }
            this.dataset.addSeries(X);
        }
    }

    // Ajouter une instance dans le dataset
    public void addInstance(double [] instance){
        XYSeriesCollection new_dataset = new XYSeriesCollection();
        XYSeries column ;
        for(int i=0; i<this.Nb_attributes(); i++){
            column = this.dataset.getSeries(i);
            column.add(instance[i], null);
            new_dataset.addSeries(column);
        }
        this.dataset = new_dataset;
    }

    //Recuperer l'instance ayant l'indice "index"
    public double[] getInstance (int index){
        double[] instance = new double[this.Nb_attributes()];
        for(int i=0; i<this.Nb_attributes(); i++){
            instance[i] = (double)this.dataset.getX(i, index);
        }
        return instance;
    }




    public void removeInstance (int index){
        XYSeriesCollection new_dataset = new XYSeriesCollection();
        XYSeries column ;
        for(int i=0; i<this.Nb_attributes(); i++){
            column = this.dataset.getSeries(i);
            column.remove(index);
            new_dataset.addSeries(column);
        }
        this.dataset = new_dataset;
    }

    public double[] getColumn (int attribute_index){
        int nb_instances = this.dataset.getItemCount(0);
        double [] column = new double[nb_instances];
        for (int i = 0; i< nb_instances; i++){
            column[i] = (double) this.dataset.getX(attribute_index, i);
        }
        return column;
    }

    public void printDataset () {
        for (int i = 0; i < this.Nb_Instances(); i++) {
            double[] instance = this.getInstance(i);
            System.out.print("\n");
            for (double value : instance) {
                System.out.print(value + "\t");
            }
        }
    }

    //Sauvegarder le dataset dans un fichier txt
    public void SaveDataset() throws FileNotFoundException{
        PrintWriter PW = new PrintWriter(new FileOutputStream(NEW_PATH));
        for (int i = 0; i < this.Nb_Instances(); i++) {
            double[] instance = this.getInstance(i);
            String inst = "";
            for (double value : instance) {
                inst = inst + value + "\t";
            }
            PW.println(inst);
        }
        PW.close();
    }

    // Description du DATASET
    public String desc(){
        return " Le dataset represente un échantillon de grains appartenant à trois variétés différentes de blé " +
                "Kama, Rosa and Canadian. Chaque classe a 70 elements";
    }

    //Nombre d'instances
    public int Nb_Instances() { return this.dataset.getItemCount(0);  }

    //Nombre d'attributs
    public int Nb_attributes() { return this.dataset.getSeriesCount() ; }

    //Nombre de classes du dataset
    public  int Nb_classe() {
        double[] class_att = this.getColumn(7);
        return (int) java.util.Arrays.stream(class_att).distinct().count();
    }

    //Récuperer les classes
    public double [] getClasses () {
        double [] classes;
        classes = (java.util.Arrays.stream(this.getColumn(7)).distinct()).toArray();
        //for (double c : classes) { System.out.print("\n class : "+ (int)c); }
        return classes;
    }

    //Distribution des classes (nombre et pourcentage d'instances par classe)!!!!!!!!!!!!!!!!!!!!!!!!!!
    public String class_desc() {
        double[] column = this.getColumn(7);
        System.out.println(this.Nb_classe());
        int[] nombre = new int[this.Nb_classe()];
        int[] pourcentage = new int[this.Nb_classe()];

        for(int j=0; j<this.Nb_classe(); j++) {
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

    //Valeurs manquantes
    public boolean val_manq(){
        boolean val = false;
        for(int att=0; att<this.Nb_attributes();att++){
            double[] column = this.getColumn(att);
            for(int i=0; i<column.length; i++){
                if(String.valueOf(column[i])=="NAN" || String.valueOf(column[i])==" " ) val = true;
            }
        }
        return val;
    }

    /*Description des attributs
    Numéro, nom, description, type et valeurs possibles de chaque attribut
    Types possibles (Nominal, Binaire symétrique, Binaire asymétrique, Numérique) – (Qualitatif, Quantitatif) – (Discret, continu)*/

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

    public void editInstance(int instance_index, int attribut_index, double new_value){
        XYSeriesCollection new_dataset = new XYSeriesCollection();
        double[] new_col = this.getColumn(attribut_index);
        new_col[instance_index] = new_value;
        for (int i = 0; i < attribut_index; i++) {
            new_dataset.addSeries(this.dataset.getSeries(i));
        }
        new_dataset.addSeries(this.addColumn(new_col, attribut_index));
        for (int i = attribut_index+1; i < this.Nb_attributes(); i++) {
            new_dataset.addSeries(this.dataset.getSeries(i));
        }
        this.dataset = new_dataset;
    }

    public XYSeries addColumn (double[] column, int key){
        XYSeries new_column = new XYSeries(""+key, false);
        for (int i = 0; i < column.length; i++) {
            new_column.add(column[i], null);
        }
        return  new_column;
    }

    public String get_column_name(int i){
        return Names[i];
    }


    public String[] getNames() {
        return Names;
    }

    public void min_max_normalization(){

        for(int i=0; i<Nb_attributes(); i++){
            double min = Calcul.min(this, i);
            double max = Calcul.max(this, i);

            double [] column = getColumn(i);
            for(int j=0; j<Nb_Instances(); j++){
                double tempValue = (column[j] - min)/(max - min);
                editInstance(j, i, tempValue);
            }
        }
    }

    public void discretisationEqual(int q) {



        for(int i=0; i<Nb_attributes(); i++){
            double min = Calcul.min(this, i);
            double max = Calcul.max(this, i);

            double length = (max - min)/ q;

            double [] column = getColumn(i);

            for(int j=0; j<Nb_Instances(); j++){

                double tempValue = Math.floor(column[i] / length);
                editInstance(j, i, 10*i+tempValue);
            }
        }


    }

    public void frequent_item(){

        HashMap<Double, Double> capitalCities = new HashMap<Double, Double>();

        for(int i=0; i<Nb_attributes(); i++){

            for(int j=0; j<Nb_Instances(); j++){



            }
        }

    }




}










