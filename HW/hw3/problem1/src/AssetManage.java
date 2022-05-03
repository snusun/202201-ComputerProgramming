import java.util.*;

public class AssetManage {

    private Map<Integer, Asset> idAsset = new HashMap<>();
    private Map<String, Lab> nameLab = new HashMap<>();

    public Map<Integer, Asset> getIdAsset() {return idAsset;}
    public Map<String, Lab> getNameLab() {return nameLab;}

    public boolean addAsset(int id, String item, int price, String location){
        // TODO sub-problem 1
        Asset existAsset = findAsset(id);
        if(existAsset!=null || id<0 || price<0 || price>100000) return false;
        Asset newAsset = new Asset(id, item, price, location);
        idAsset.put(id, newAsset);
        return true;
    }
    public boolean addLab(String labname){
        // TODO sub-problem 1
        Lab existLab = findLab(labname);
        if(existLab!=null || labname==null || labname.isEmpty()) return false;
        Lab newLab = new Lab(labname);
        nameLab.put(labname, newLab);
        return true;
    }
    public Asset findAsset(int id){
        // TODO sub-problem 1
        return idAsset.get(id);
    }
    public Lab findLab(String labname){
        // TODO sub-problem 1
        return nameLab.get(labname);
    }
    public List<Asset> findAssetsWithConditions(int minprice, int maxprice, String item, String location){
        // TODO sub-problem 2
        List<Asset> assetList = new LinkedList<>();

        for (Map.Entry<Integer, Asset> entry : idAsset.entrySet() ) {
            boolean priceCon =false, itemCon =false, locationCon = false;

            Asset asset = entry.getValue();
            if(minprice==-1 && maxprice==-1) {
                priceCon = true;
            } else if(asset.getPrice() >= minprice && asset.getPrice() <= maxprice){
                priceCon = true;
            }
            if(item.equals("All") || item.equals(asset.getItem())){
                itemCon = true;
            }
            if(location.equals("All") || location.equals(asset.getLocation())){
                locationCon = true;
            }
            if(priceCon && itemCon && locationCon){
                assetList.add(asset);
            }
        }

        Collections.sort(assetList);

        // If there is no asset matching all these conditions, return an empty list.

        return assetList;
    }
    public boolean buyNewAsset(Lab lab, int id) {
        // TODO sub-problem 3
        Asset asset = findAsset(id);
        if(findAsset(id)==null || lab==null) return false;
        if(asset.getOwners().size()!=0) return false;
        if(asset.getPrice() > lab.getBalance()) return false;

        lab.addAsset(asset);
        lab.setBalance(lab.getBalance()-asset.getPrice());
        asset.addOwner(lab);
        return true;
    }
    public boolean tradeBtwLabs(Lab buyer, Lab seller, int id){
        // TODO sub-problem 3
        Asset asset = findAsset(id);
        if(asset==null) return false;
        if(buyer==null || seller==null) return false;
        if(asset.getOwners().size()==0 || !asset.isOwned(seller) || asset.isOwned(buyer)) return false;
        if(asset.getPrice() > buyer.getBalance()) return false;

        int transPrice = asset.getPrice() / asset.getOwners().size();

        buyer.addAsset(asset);
        seller.removeAsset(asset);
        buyer.setBalance(buyer.getBalance()-transPrice);
        seller.setBalance(seller.getBalance()+transPrice);
        asset.addOwner(buyer);
        asset.removeOwner(seller);

        return true;
    }
   public boolean assetOnShare(Lab sharer, int id) {
       // TODO sub-problem 4
       Asset asset = findAsset(id);
       if(asset==null || sharer==null) return false;
       if(asset.getOwners().size()==0 || asset.isOwned(sharer)) return false;
       int transPrice = asset.getPrice() / (asset.getOwners().size()+1);
       if(transPrice > sharer.getBalance()) return false;

       int change = (asset.getPrice() / asset.getOwners().size()) - transPrice;

       sharer.addAsset(asset);
       sharer.setBalance(sharer.getBalance()-transPrice-change);
       asset.addOwner(sharer);
       List<Lab> onwers = asset.getOwners();
       for(Lab o: onwers){
           o.setBalance(o.getBalance()+change);
       }

       return true;
   }

}
