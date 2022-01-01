package part2.extraction;

import common.Dataset;

import java.util.*;

public class FrequentItemSets {

    public static int calcul_support (String A, ArrayList<String[]> dataset_disc){
        int support = 0;
        for(int i=0;i<dataset_disc.size();i++){
            String[] ligne=dataset_disc.get(i);
            for(int j=0;j<ligne.length;j++){
                if(ligne[j].equals(A)){support++;}
            }
        }
        return support;

    }

    public static int item_exist (ArrayList<ElementC1> C1, String element_id){
        for (int i = 0; i< C1.size(); i++) {
            if (C1.get(i).itemset.equals(element_id)){
                return i;
            }
        }
        return -1;
    }

    public static ArrayList<ElementC1> Create_itemset1 (ArrayList<String[]> dataset_disc, int min_sup){

        ArrayList<ElementC1> C1 = new ArrayList<>();
        for (int i = 0; i< dataset_disc.size(); i++){

            for(int j=0; j<dataset_disc.get(i).length; j++ ){

                String element_id = dataset_disc.get(i)[j];

                int position = item_exist(C1,element_id);
                if (position != -1){      // il existe
                    C1.get(position).freq ++;
                }
                else {
                    ElementC1 element = new ElementC1(element_id,1);
                    C1.add(element);
                }

            }

        }
        return(C1);

    }

    public static ArrayList<String> Create_L1(Dataset ds, int min_sup, ArrayList<ElementC1> C1){
        int support_min = (ds.nbInstances()) * min_sup / 100;

        ArrayList<String> L1 = new ArrayList<>();
        for(int i=0; i<C1.size(); i++) {

            if(C1.get(i).freq >= support_min){
                L1.add(C1.get(i).itemset);
            }
        }
        return L1;
    }

    public static int calcul_support2 (String[] A,ArrayList<String[]> dataset_disc){
        int support = 0;
        for(int i=0; i<dataset_disc.size(); i++) {

            String[] ligne = dataset_disc.get(i);

            boolean found = false;
            int j = 0;
            while(j < (ligne.length)-1) {

                int k = 1;
                while(k < (ligne.length)) {
                    if (!ligne[j].equals(ligne[k])) {
                        if (A[0].equals(ligne[j]) || A[1].equals(ligne[j])) {
                            if (A[0].equals(ligne[k]) || A[1].equals(ligne[k])) {
                                support++;
                                found = true;
                            }
                        }
                    }
                    if(found){break;}
                    else{k++;}
                }
                if(found){break;}
                else{j++;}
            }
        }
        return support;
    }

    public static ArrayList<String[]> Create_itemset2(ArrayList<String[]> dataset_disc, ArrayList<String> L1){

        String I1,I2;
        ArrayList<String[]> C2 = new ArrayList<>();

        for(int i=0; i<(L1.size())-1; i++){

            I1 = L1.get(i);
            int j = i + 1;
            while(j < L1.size()){
                I2 = L1.get(j);
                String[] temp_itemset2 = new String[3];
                temp_itemset2[0] = I1;
                temp_itemset2[1] = I2;
                String[] A = new String[2];
                A[0] = I1;
                A[1] = I2;
                temp_itemset2[2] = String.valueOf(calcul_support2(A,dataset_disc));
                C2.add(temp_itemset2);
                j++;
            }
        }
        return (C2);
    }


    public static  ArrayList<String[]> sort_L2(ArrayList<String[]> L2){
        String item;
        ArrayList<String[]> new_L2 = new ArrayList<>();
        ArrayList<String> items1 = new ArrayList<>();
        for(int i=0; i<L2.size(); i++) {
            item = L2.get(i)[0];
            if (!items1.contains(item)) {
                items1.add(item);
            }
        }
        Collections.sort(items1); //L2 is a set of elements like {x,y} sort the x parts and put them in items1
        SortedSet<String> items2= new TreeSet<>(); //will be used temporarely to sort the y in {x,y}
        //now items1 is sorted

        for(int i=0;i<items1.size();i++) { //parcourir les x et les y
            for (int j = 0; j < L2.size(); j++) {
                if (L2.get(j)[0].equals(items1.get(i))) {
                    item = L2.get(j)[1];
                    items2.add(item);
                    //j++;
                }
                else {
                    //items2 is automatically sorted
                    Iterator<String> iter = items2.iterator();
                    while (iter.hasNext()) {
                        String[] xy = new String[3];
                        xy[0] = items1.get(i);
                        xy[1] = iter.next();
                        new_L2.add(xy);
                    }
                    items2.clear();
                }

            }
        }
        return(new_L2);
    }

    public static ArrayList<String[]> Create_L2(Dataset ds, int min_sup, ArrayList<String[]> C2){
        int support_min=(ds.nbInstances())*min_sup/100;
        ArrayList<String[]> L2=new ArrayList<>();
        for(int i=0;i<C2.size();i++){

            if(Integer.valueOf(C2.get(i)[2])>=support_min ){
                L2.add(C2.get(i));
            }
        }
        L2 = sort_L2(L2);
        return (L2);
    }

}
