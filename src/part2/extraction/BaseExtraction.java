package part2.extraction;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;

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

    public ArrayList<String> associationRules(double confidencePercentage){

        double min_confidence = confidencePercentage/100;

        ArrayList<String> associationRules = new ArrayList<>(  );
        HashMap<Set<String>, Integer> frequentItemsHash = createHashSupport();
        double confidence=0;

        for (ItemsetElement item : this.frequentItems){

            if (item.items.size() == 2){
                confidence = (double) item.support / frequentItemsHash.get(new HashSet<>(Collections.singleton(item.items.get(0))));
                if (confidence >= min_confidence){
                    associationRules.add(item.items.get(0) + " => " + item.items.get(1));
                }

                confidence = (double) item.support / frequentItemsHash.get(new HashSet<>(Collections.singleton(item.items.get(1))));
                if (confidence >= min_confidence){
                    associationRules.add(item.items.get(1) + " => " + item.items.get(0));
                }

            } else if (item.items.size() != 1){
                ArrayList<Set<String>> allItems = subArray(item.items);
                for (Set<String> leftSide : allItems){
                    // find right side
                    Set<String> rightSide = findRightSide(leftSide, item.items);
                    confidence = (double) item.support / frequentItemsHash.get(leftSide);
                    if (confidence >= min_confidence){
                        associationRules.add(leftSide + " => " + rightSide);
                    }
                }

            }
        }
        return associationRules;
    }

    private Set<String> findRightSide(Set<String> leftSide, ArrayList<String> item){
        Set<String> rightSide = new HashSet<>(  );

        for (String term : item){
            if (!leftSide.contains(term)){
                rightSide.add(term);
            }
        }
        return rightSide;
    }

    private HashMap<Set<String>, Integer> createHashSupport(){
        HashMap<Set<String>, Integer> frequentItemsHash = new HashMap<>(  );

        for (ItemsetElement item : this.frequentItems) {
            Set<String> terms = new HashSet<>(item.items);
            frequentItemsHash.put(terms, item.getSupport());
        }
        return frequentItemsHash;
    }

    private static ArrayList<Set<String>> subArray(ArrayList<String> array) {
        ArrayList<Set<String>> items = new ArrayList<>(  );
        int opsize = (int)Math.pow(2, array.size());

        for (int counter = 1; counter < opsize; counter++) {
            Set<String> terms = new HashSet<>(  );
            for (int j = 0; j < array.size(); j++) {

                if (BigInteger.valueOf(counter).testBit(j))
                    terms.add(array.get(j));
            }
            if (terms.size() < array.size( )){
                items.add(terms);
            }
        }
        return items;
    }

}
