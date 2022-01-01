package part2.extraction;

import java.util.ArrayList;

public class ItemsetElement {
    ArrayList<String> items;
    int support;

    public ItemsetElement(ArrayList<String> items, int support){
        this.items = items;
        this.support = support;
    }

    public ItemsetElement(ArrayList<String> items){
        this.items = items;
    }

    public void setSupport(int support) {
        this.support = support;
    }

    public int getSupport() {
        return support;
    }

    public boolean unique_items(){
        for (int i = 0; i < (this.items.size()-1); i++) {
            char ch = (items).get(i).charAt(1);
            for (int j = i+1; j < this.items.size(); j++) {
                if(this.items.get(j).charAt(1)==ch){return false;}
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "{" + items + '}';
    }
}
