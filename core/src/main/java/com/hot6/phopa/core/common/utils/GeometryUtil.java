package com.hot6.phopa.core.common.utils;

/**Ø
 * Haversine Formula
 * φ2 = asin( sin φ1 ⋅ cos δ + cos φ1 ⋅ sin δ ⋅ cos θ )
 * λ2 = λ1 + atan2( sin θ ⋅ sin δ ⋅ cos φ1, cos δ − sin φ1 ⋅ sin φ2 )
 */
public class GeometryUtil {

    public static Location calculate(Double baseLatitude, Double baseLongitude, Double distance,
                                     Double bearing) {
        Double radianLatitude = toRadian(baseLatitude);
        Double radianLongitude = toRadian(baseLongitude);
        Double radianAngle = toRadian(bearing);
        Double distanceRadius = distance / 6371.01;

        Double latitude = Math.asin(sin(radianLatitude) * cos(distanceRadius) +
                cos(radianLatitude) * sin(distanceRadius) * cos(radianAngle));
        Double longitude = radianLongitude + Math.atan2(sin(radianAngle) * sin(distanceRadius) *
                cos(radianLatitude), cos(distanceRadius) - sin(radianLatitude) * sin(latitude));

//        longitude = normalizeLongitude(longitude);
        return new Location(toDegree(latitude), toDegree(longitude));
    }

    private static Double toRadian(Double coordinate) {
        return coordinate * Math.PI / 180.0;
    }

    private static Double toDegree(Double coordinate) {
        return coordinate * 180.0 / Math.PI;
    }

    private static Double sin(Double coordinate) {
        return Math.sin(coordinate);
    }

    private static Double cos(Double coordinate) {
        return Math.cos(coordinate);
    }

//    private static Double normalizeLongitude(Double longitude) {
//        return (longitude + 540) % 360 - 180;
//    }
//    public static Point createPoint(double lat, double lon) {
//        GeometryFactory gf = new GeometryFactory();
//        return gf.createPoint(new Coordinate(lat, lon));
//    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(toDegree(lat1)) * Math.sin(toDegree(lat2)) + Math.cos(toDegree(lat1)) * Math.cos(toDegree(lat2)) * Math.cos(toDegree(theta));

        dist = Math.acos(dist);
        dist = toRadian(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        return (dist);
    }
    
}