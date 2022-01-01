package utils;


import org.jfree.chart.util.ArrayUtils;
import org.jfree.data.xy.XYSeriesCollection;

import javax.lang.model.type.ArrayType;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Main2 {

    public static void main(String[] args) throws FileNotFoundException {

        // Lecture et creation du dataset
        Dataset ds = new Dataset();
        XYSeriesCollection dataset = ds.ReadDataset("dataset/seeds_dataset.txt");


        ds.min_max_normalization();
        ds.discretisationEqual(4);
        ds.printDataset();

    }


}

