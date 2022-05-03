import java.util.*;

public class Lab {
    private String labname;
    private int balance;
    private Map<Integer, Asset> assetInventory;

    public Lab(String labname){
        // initial balance is 100,000 for each lab
        this.balance = 100000;
        this.labname = labname;
        assetInventory = new HashMap<>();
    }

    public String getLabname() {
        return labname;
    }

    public int getBalance(){return balance;}

    public void setBalance(int balance){
        this.balance = balance;
    }

    public void addAsset(Asset asset){
        assetInventory.put(asset.getId(), asset);
    }

    public void removeAsset(Asset asset){
        assetInventory.remove(asset.getId());
    }

    // TODO sub-problem 1-4


}
