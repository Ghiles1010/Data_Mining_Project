package utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBoxAndWhiskerRenderer;
import org.jfree.data.statistics.*;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Charts {
    //XYSeriesCollection dataset;
    //int nb_instances;
    String[] Names = {"Area", "Perimeter", "Compactness", "length of kernel", "width of kernel",
            "Asymmetry coefficient", "length of kernel groove","Class"}; // les noms des attributs

    /*
    public Charts(XYSeriesCollection dataset) {
        this.dataset = dataset;
        this.nb_instances = dataset.getItemCount(0);
    }
     */

    public JFrame Create_Scatter(Dataset ds, int X_index, int Y_index){
        //Recuperer les colonnes d'indices 'X_index' et 'Y_index' et les mettre dans une collection
        XYSeriesCollection dataFrame = new XYSeriesCollection();
        XYSeries XY = new XYSeries("");
        for (int i = 0; i< ds.nbInstances(); i++){
            XY.add(ds.getInstance(i)[X_index], ds.getInstance(i)[Y_index]);
        }
        dataFrame.addSeries(XY);

        //Scatter Plot
        JFreeChart scatter = ChartFactory.createScatterPlot(
                "Diagramme de dispesion", Names[X_index], Names[Y_index],
                dataFrame, PlotOrientation.VERTICAL, true, true, true
        );

        ChartPanel panel = new ChartPanel(scatter);
        JFrame frame = new JFrame();
        frame.setSize(750, 500);
        frame.add(panel);
        frame.setVisible(true); //
        return frame;
    }


    public JFrame Create_Histogram(Dataset ds, int X_index, int bins){
        double [] column = ds.getColumn(X_index) ;
        HistogramDataset histogramDataset = new HistogramDataset();
        histogramDataset.setType(HistogramType.FREQUENCY);
        if (X_index == ds.nbAttributes() - 1)    //Si l'attribut choisi est la classe
            histogramDataset.addSeries("class", column, ds.nbClasses());
        else
            histogramDataset.addSeries("", column, bins);

        JFreeChart histogram = ChartFactory.createHistogram(
                "Histogramme", Names[X_index], "Frequency", histogramDataset,
                PlotOrientation.VERTICAL, true, true, true
        );

        ChartPanel panel = new ChartPanel(histogram);
        JFrame frame = new JFrame();
        frame.setSize(750, 500);
        frame.add(panel);
        frame.setVisible(true); //
        return frame;
    }

    public void Create_Boxplot (Dataset ds, int X_index){
        DefaultBoxAndWhiskerXYDataset dataset = new DefaultBoxAndWhiskerXYDataset("");

        double[] col = ds.getColumn(X_index) ;
        Number [] column = new Number[col.length];
        for (int i = 0; i < col.length; i++) column[i] = (Number) col[i];
        List column_list = Arrays.asList(column);

        //List column = Arrays.asList(this.getColumn(X_index));
        BoxAndWhiskerItem data = BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(column_list); //Le problème est ici : les valeurs retournées sont des nans
        dataset.add(new Date(), data);

        JFreeChart boxplot = ChartFactory.createBoxAndWhiskerChart(
                "Boxplot", Names[X_index], "", dataset, false); //


        //Personnaliser
        XYPlot plt = (XYPlot) boxplot.getPlot();
        ValueAxis x_axis = plt.getDomainAxis();
        x_axis.setTickLabelsVisible(false);

        ValueAxis y_axis = plt.getRangeAxis();

        y_axis.setRange(Calcul.min(ds,X_index)-1, Calcul.max(ds,X_index)+1);

        XYBoxAndWhiskerRenderer renderer = new XYBoxAndWhiskerRenderer();
        plt.setRenderer(renderer);
        renderer.setBoxPaint(Color.pink);

        //renderer.setMedianVisible(true);
        //renderer.setMeanVisible(false);

        ChartPanel panel = new ChartPanel(boxplot);
        JFrame frame = new JFrame();
        frame.setSize(750, 500);
        frame.add(panel);
        frame.setVisible(true); //
        //return frame;

    }


}
