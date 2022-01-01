package part2.extraction;

import java.util.ArrayList;

public abstract class BaseExtraction {

    protected ArrayList<String[]> discretizedData;
    protected final int minsup;

    protected ArrayList<ItemsetElement> frequentItems;

    protected double executionTime;

    public BaseExtraction(ArrayList<String[]> discretizedData, int minsupPercentage) {
        this.discretizedData = discretizedData;
        this.minsup = Math.floorDiv(minsupPercentage * discretizedData.size(), 100);
    }

    public ArrayList<ItemsetElement> getFrequentItems() {
        return frequentItems;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public abstract ArrayList<ItemsetElement> run();

    public void execute(){
        double y = System.nanoTime();
        run();
        double x = System.nanoTime();
        this.executionTime = x - y;
    }
}
