package g.y.v.vyg.Utils;

public class Model {
    String lat,lng,rName;

    public Model(String rName, String lng, String lat) {
        this.lng = lng;
        this.lat = lat;
        this.rName = rName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }
}