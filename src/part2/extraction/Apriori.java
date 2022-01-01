package part2.extraction;

import java.util.ArrayList;

public class Apriori extends BaseExtraction{

    public Apriori(ArrayList<String[]> discretizedData, int minsupPercentage) {
        super(discretizedData, minsupPercentage);
    }

    public ArrayList<ItemsetElement> Create_C1() {

        ArrayList<ItemsetElement> C1 = new ArrayList<>();

        for (int i = 0; i < this.discretizedData.size(); i++) {
            for (int j = 0; j < this.discretizedData.get(i).length; j++) {
                ArrayList<String> element_id = new ArrayList<>();//example:I21
                element_id.add(this.discretizedData.get(i)[j]);//like getting an element with ligne colonne from matrice
                int position=-1;//On considere initialement que element_id n'existe pas dans C1

                if(C1.isEmpty()) {
                    ItemsetElement element = new ItemsetElement(element_id, 1);
                    C1.add(element);
                }
                else{
                    for (int k = 0; k < C1.size(); k++) {
                        for (int l = 0; l < C1.get(k).items.size(); l++) {
                            if(C1.get(k).items.get(l).equals(element_id.get(0))){
                                position=k;
                                break;
                            }
                        }

                    }
                    if (position!=-1) { // il existe
                        C1.get(position).support++;
                    }
                    else {
                        ItemsetElement element = new ItemsetElement(element_id, 1);
                        C1.add(element);
                    }
                }
                //element_id.clear();
            }
        }
        return (C1);
    }

    private ArrayList<String> Common_items(ArrayList<String> items1, ArrayList<String> items2){
        ArrayList<String> common_items=new ArrayList<>();
        for (int i = 0; i < items1.size(); i++) {
            if(items2.contains(items1.get(i))){
                common_items.add(items1.get(i));
            }
        }
        return common_items;
    }

    private ArrayList<String> Combine_items(ArrayList<String> items1, ArrayList<String> items2, ArrayList<String> common_items){
        //ArrayList<String> combined_itemsets=new ArrayList();
        ArrayList<String> temp_items=new ArrayList<>();
        boolean exists;

        //remplir temp_items avec common_items
        for (int i = 0; i < common_items.size(); i++) {
            temp_items.add(common_items.get(i));
        }
        //remplir temp_items avec itesm1 qui ne sont pas dans common items
        for (int i = 0; i < items1.size(); i++) {
            exists=false;
            for (int j = 0; j < common_items.size(); j++) {
                if(items1.get(i).equals(common_items.get(j))){
                    exists=true;
                    break;
                }
            }
            if(!exists){
                temp_items.add(items1.get(i));
            }
        }
        //remplir temp_items avec itesm2 qui ne sont pas dans common items
        for (int i = 0; i < items1.size(); i++) {
            exists=false;
            for (int j = 0; j < common_items.size(); j++) {
                if(items2.get(i).equals(common_items.get(j))){
                    exists=true;
                    break;
                }
            }
            if(!exists){
                temp_items.add(items2.get(i));
            }
        }

        return temp_items;
    }

    private int calcul_supportk(ArrayList<String> combined_items) {
        int support=0;
        int nb_exist=0;
        //ArrayList<String> instance=new ArrayList<>();

        for (int i = 0; i < this.discretizedData.size(); i++) {
            ArrayList<String> instance=new ArrayList<>();
            //convert instance of this.discretizedData, from [] to arraylist
            for (String element:this.discretizedData.get(i)) {
                instance.add(element);
            }
            //compare if instance elements CONTAINS combined_items elements
            for (int j = 0; j < combined_items.size(); j++) {
                if (instance.contains(combined_items.get(j))) {
                    nb_exist++;
                }
            }
            if(nb_exist==combined_items.size()){support++;}
            nb_exist=0;
        }
        return support;
    }

    private boolean In_Ck(ArrayList<ItemsetElement> Ck, ArrayList<String> combined_items){
        boolean contained=false;
        for (int i = 0; i < Ck.size(); i++) {
            for (int j = 0; j < combined_items.size(); j++) {
                if(Ck.get(i).items.contains(combined_items.get(j))){
                    contained=true;
                }
                else{
                    contained=false;
                    break;
                }
            }
            if (contained){
                return true;
            }
        }
        return false;
    }

    private ArrayList<ItemsetElement> generate_Ck(ArrayList<ItemsetElement> Lk,int k) {
        ArrayList<ItemsetElement> Ck = new ArrayList<>();
        int support;

        if (k > 1) {
            for (int i = 0; i < (Lk.size()-1); i++) {
                ArrayList<String> items1;
                items1=Lk.get(i).items;
                for (int j = (i+1); j < Lk.size(); j++) {
                    ArrayList<String> items2;
                    items2=Lk.get(j).items;
                    if(k==2){
                        ArrayList<String> combined_items = new ArrayList<>();
                        combined_items.add(Lk.get(i).items.get(0));
                        combined_items.add(Lk.get(j).items.get(0));
                        support = calcul_supportk(combined_items);
                        ItemsetElement temp_Ck_element = new ItemsetElement(combined_items, support);
                        Ck.add(temp_Ck_element);
                    }
                    else {
                        ArrayList<String> common_items = new ArrayList<>();
                        common_items = Common_items(items1, items2);
                        if (common_items.size() == (k - 2)) {
                            ArrayList<String> combined_items = new ArrayList<>();
                            combined_items = Combine_items(items1, items2, common_items);
                            support = calcul_supportk(combined_items);
                            ItemsetElement temp_Ck_element = new ItemsetElement(combined_items, support);
                            if(!In_Ck(Ck,combined_items)){
                                Ck.add(temp_Ck_element);
                            }
                        }
                    }
                }
            }
        }
        else{
            ArrayList<ItemsetElement> C1=Create_C1();
            return C1;
        }
        return Ck;
    }

    private ArrayList<ItemsetElement> generate_Lk(ArrayList<ItemsetElement> Ck){
        ArrayList<ItemsetElement> Lk = new ArrayList();
        //Itemset_Element element_Lk=new ArrayList<>();

        for (int i = 0; i < Ck.size(); i++) {
            if(Ck.get(i).support >= this.minsup){
                ItemsetElement element_Lk = new ItemsetElement(Ck.get(i).items,Ck.get(i).support);
                Lk.add(element_Lk);
            }
        }
        return(Lk);
    }

    public ArrayList<ItemsetElement> run(){
        frequentItems = new ArrayList<>();

        ArrayList<ItemsetElement> Ck;
        ArrayList<ItemsetElement> Lk;
        
        int k=1;//to reference L1 L2 L3.. & C1 C2 C3....

        //Create C1 and L1
        Ck = Create_C1();
        Lk = generate_Lk(Ck);

        //add L1 to frequent items
        for (ItemsetElement element : Lk) {
            frequentItems.add(element);
        }
        k++;
        while(Lk.size()>2){
            Ck.clear();
            Ck=generate_Ck(Lk,k);
            Lk.clear();
            Lk=generate_Lk(Ck);
            //add Lk to frequent items
            for (ItemsetElement element:Lk) {
                frequentItems.add(element);
            }
            k++;
        }
        return frequentItems;
    }
}
