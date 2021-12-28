package utils;

import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SaveValues {

    private static final String resultsFolder = "results";

    public static void main(String[] args) throws FileNotFoundException {

        File directory = new File(resultsFolder);
        if (! directory.exists()){
            directory.mkdir();
        }

        // Lecture et creation du dataset
        Dataset ds = new Dataset("dataset/seeds_dataset.txt");

        try{
            FileWriter csvWriter = new FileWriter( resultsFolder + File.separator + "tendance.csv");
            csvWriter.append("attribut,moyenne,moyenne tronquée,médiane,mode,midRange\n");
            ArrayList<String> attr = new ArrayList<>( Arrays.asList("A", "P", "C","length of kernel","width of kernel","asymmetry coefficient", "length of kernel groove"));

            for(int att=0; att<7; att++){
                String str = attr.get(att) + "," +  Calcul.moy(ds,att) + "," + Calcul.moy_tranq(ds,att,10) + "," + Calcul.mediane(ds,att) + "," + Calcul.mode(ds,att) + "," + Calcul.mode(ds,att) + "\n";
                csvWriter.append(str);
                csvWriter.flush( );
                System.out.println( str );
            }
            csvWriter.close( );
        } catch (IOException E){
            System.out.println( "Error in CVS file" );
        }

        try{
            FileWriter csvWriter = new FileWriter( resultsFolder + File.separator + "dispersion.csv");
            csvWriter.append("attribut,étendue,min,Q1,mediane,Q3,max,écart interquartile,variance,l'écart type\n");
            ArrayList<String> attr = new ArrayList<>( Arrays.asList("A", "P", "C","length of kernel","width of kernel","asymmetry coefficient", "length of kernel groove"));

            for(int att=0; att<7; att++){
                String str = attr.get(att) + "," + Calcul.range(ds,att) + "," + Calcul.min(ds,att) + "," + Calcul.Q1(ds,att) + "," + Calcul.mediane(ds,att) + "," + Calcul.Q3(ds,att) + "," + Calcul.max(ds,att) + "," + (Calcul.Q3(ds,att) - Calcul.Q1(ds,att)) + "," + Calcul.variance(ds,att) + "," + Calcul.ecart_type(ds,att) + "\n";
                csvWriter.append(str);
                csvWriter.flush( );
                System.out.println( str );
            }
            csvWriter.close( );
        } catch (IOException E){
            System.out.println( "Error in CVS file" );
        }
    }
}
