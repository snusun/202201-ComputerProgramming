package relay.map;

public class Map {
    private double waterStart;
    private double waterEnd;
    private double mapEnd;

    public Map(double waterStart, double waterEnd, double mapEnd) {
        this.waterStart = waterStart;
        this.waterEnd = waterEnd;
        this.mapEnd = mapEnd;
    }

    public double getWaterStart(){
        return waterStart;
    }

    public double getWaterEnd(){
        return waterEnd;
    }

    public double getMapEnd(){
        return mapEnd;
    }

    public boolean getOnWater(double position) {
        //TODO: Problem 2.1
        return position >= waterStart && position < waterEnd;
    }
}

