package part2.extraction;

import java.util.*;

public class ECLAT extends BaseExtraction{

    private HashMap<String, ArrayList<Integer>> vectorialRepresentation;

    public ECLAT(ArrayList<String[]> discretizedData, int minsupPercentage) {
        super(discretizedData, minsupPercentage);
    }

    private Set<String> generateTerms() {
        Set<String> terms = new HashSet<>();
        for (String[] instance : this.discretizedData){
            Collections.addAll(terms, instance);
        }
        return terms;
    }

    private void generateVerticalRepresentation(Set<String> terms){
        this.vectorialRepresentation = new HashMap<>(  );

        for (String  term : terms){
            ArrayList<Integer> line = new ArrayList<>(  );
            for (int i=0; i<this.discretizedData.size(); i++){
                if(Arrays.asList(this.discretizedData.get(i)).contains(term)){
                    line.add(1);
                } else {
                    line.add(0);
                }
            }
            this.vectorialRepresentation.put(term, line);
        }
    }

    private ArrayList<ItemsetElement> generateC1(Set<String> terms){
        ArrayList<ItemsetElement> C1 = new ArrayList<>(  );

        for (String term : terms){
            ArrayList<String> item = new ArrayList<>(  );
            item.add(term);
            C1.add(new ItemsetElement(item));
        }
        return C1;
    }

    private ArrayList<ItemsetElement> generateCk(ArrayList<ItemsetElement> Lk, int k){
        ArrayList<ItemsetElement> Ck = new ArrayList<>(  );

        // generating all the combinaisons
        for (int i=0; i<Lk.size(); i++){
            for (int j=i+1; j<Lk.size(); j++){
                if(compare(Lk.get(i), Lk.get(j), k)){
                    Set<String> newLine = new HashSet<>(  );
                    newLine.addAll(Lk.get(i).items);
                    newLine.addAll(Lk.get(j).items);
                    if (! alreadyExist(Ck, newLine)){
                        Ck.add(new ItemsetElement(new ArrayList<>( newLine )));
                    }
                }
            }
        }
        return Ck;
    }

    private boolean compare(ItemsetElement item1, ItemsetElement item2, int k){
        int value = k==2? 0 : Math.floorDiv(k, 2);


        for (String term : item1.items){
            if (item2.items.contains(term)){
                value--;
            }
        }
        return value == 0;
    }

    private boolean alreadyExist(ArrayList<ItemsetElement> Ck, Set<String> set){

        for (ItemsetElement item : Ck){
            boolean exist = true;
            for (String term : set){
                if (! item.items.contains(term)){
                    exist = false;
                }
            }
            if (exist){
                return true;
            }
        }
        return false;
    }

    private int supportItem(ItemsetElement item){
        // selecting the terms with their list
        ArrayList<ArrayList<Integer>> terms = new ArrayList<>(  );
        for (String term : item.items){
            terms.add(this.vectorialRepresentation.get(term));
        }

        // creating a intersection list between terms
        ArrayList<Integer> newLine = new ArrayList<>(  );
        for (int i=0; i<this.discretizedData.size(); i++){
            boolean exist = true;
            int j=0;
            while (j<terms.size() && exist){
                if(terms.get(j).get(i) == 0){
                    exist = false;
                }
                j++;
            }
            if (exist){
                newLine.add(1);
            } else {
                newLine.add(0);
            }
        }

        // calculatig the support
        int support = 0;
        for (Integer exist : newLine){
            support += exist;
        }
        return support;
    }

    public ArrayList<ItemsetElement> run(){

        ArrayList<ItemsetElement> Ck, Lk;

        // generate terms
        Set<String> terms = generateTerms();

        // tableVerticale ← Représentation verticale des données de transaction
        generateVerticalRepresentation(terms);

        // Construction des candidats C1 (1-itemsets)
        Ck = generateC1(terms);


        // adding support to C1 and constructing L1
        Lk = new ArrayList<>(  );
        for (ItemsetElement item : Ck){
            item.setSupport(supportItem(item));
            if (item.getSupport() >= this.minsup){
                Lk.add(item);
            }
        }
        frequentItems = new ArrayList<>( Lk );

        int k = 1;
        while (!Lk.isEmpty()){
            k++;
            Ck = generateCk(Lk, k);

            // adding support to Ck and constructing Lk
            Lk = new ArrayList<>(  );
            for (ItemsetElement item : Ck){
                item.setSupport(supportItem(item));
                if (item.getSupport() >= this.minsup){
                    Lk.add(item);
                }
            }
            frequentItems.addAll(Lk);
        }
        return frequentItems;
    }

}
