package g.y.v.vyg;


public class Constants {

    public static String  gmApi(String latitude,String longitude,String radius,String type){
        String url= "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius="+radius+"&type="+type+"&key=AIzaSyC3_ndLS93DsNFqSB-78VuA00A0hrI8B5A";
        return url;
    }

    public static String insertUrl="https://galqayeva.000webhostapp.com/insertUser.php";
    public static String loginUrl="https://galqayeva.000webhostapp.com/login.php";
}

//https://developers.google.com/places/web-service/search