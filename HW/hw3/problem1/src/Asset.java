import java.util.*;

public class Asset implements Comparable<Asset> {
    private int id;
    private String item;
    private int price;
    private String location;
    private List<Lab> owners;

    public Asset(int id, String item, int price, String location){
        this.id = id;
        this.item = item;
        this.price = price;
        this.location = location;
        this.owners = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getItem() {
        return item;
    }

    public int getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public List<Lab> getOwners(){return owners;}

    public void addOwner(Lab lab){
        owners.add(lab);
    }

    public void removeOwner(Lab lab){
        owners.remove(lab);
    }

    public boolean isOwned(Lab lab){
        for(Lab a: owners){
            if(a.equals(lab)) return true;
        }
        return false;
    }

    public String toString(){
        return id + "";
    }

    @Override
    public int compareTo(Asset o) {
        return this.id - o.id;
    }
    // TODO sub-problem 1-4


}
