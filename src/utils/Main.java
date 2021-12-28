package utils;


import org.jfree.data.xy.XYSeriesCollection;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        // Lecture et creation du dataset
        Dataset ds = new Dataset("dataset/seeds_dataset.txt");
        ds.printDataset();

        System.out.println("\n Desc : ");
        ds.printDataset();
        int att=2;

        //Stats :
        System.out.println("\n Stats : \n");
        System.out.println("Moyenne = " + Calcul.moy(ds,att));
        System.out.println("Moyenne tronquée = " +  Calcul.moy_tranq(ds,att,10));
        System.out.println("Medianne = " +  Calcul.mediane(ds,att));
        System.out.println("Mode = " +  Calcul.mode(ds,att));
        System.out.println("MidRange = " +  Calcul.midRange(ds,att));
        System.out.println("Ecart interquartille = " +  Calcul.ecart_inter(ds,att));
        System.out.println("Etendu = " +  Calcul.range(ds,att));
        System.out.println("Quartiles : ["+Calcul.min(ds,att)+", "+Calcul.Q1(ds,att)+", "+Calcul.mediane(ds,att)+", "+Calcul.Q3(ds,att)+", "+Calcul.max(ds,att)+"]");
        System.out.println("Variance = " + Calcul.variance(ds,att));
        System.out.println("Ecart-type = " + Calcul.ecart_type(ds,att));

        // Symetrie
        Calcul.symetrique(ds,att);

        //les val aberrante
        System.out.println("Les valeurs aberrantes sont: "+Calcul.aberrant(ds,att));


        System.out.print("\n Nombre d'instances  : " + ds.nbInstances());
        System.out.print("\n Nombre d'attributs : " + ds.nbAttributes());
        System.out.println("\n Nombre de classes distinctes : " + ds.nbClasses());

        int X = 0; int Y = 1;
        double coef = Calcul.correlation(ds, X, Y);
        System.out.println("\n Correlation : " + coef);
        System.out.println("\n" + Calcul.correlation_interpretation(coef)); //Interpreter la valeur du coefficient de correlation


        System.out.print("\n\n New dataset : \n");
        double[] new_instance = {0.11, 0.22, 0.33, 0.44, 0.55, 0.66, 0.77, 4};
        //ds.addInstance(new_instance);   //Ajouter une nouvelle instance
        //ds.removeInstance(0);     // Supprimer l'instance ayant l'indice 0
        ds.printDataset();
        ds.saveDataset();       //Sauvegarder le dataset dans un fichier txt

        // Graphes
        Charts ch = new Charts();
        ch.Create_Scatter(ds,X,Y);
        ch.Create_Histogram(ds,X, 5);
        ch.Create_Boxplot(ds, Y);


    }
}

