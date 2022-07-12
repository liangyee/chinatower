package com.vastio.util;

import java.util.ArrayList;

/**
 * geo 相关 工具类
 * <p>
 * Created by xlch at 2018/4/18
 */
public final class GeoUtil {

    static final double R = 6371; // Radius of the earth

    static final double a = 6378245.0;
    static final double ee = 0.00669342162296594323;

    private GeoUtil() {	}

    /**
     * 计算两点距离,输入单位是 角度如：120.12222.31.11111
     * 返回单位是：米
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000; // convert to meters
    }

    /**
     * 计算矩形区域内是否包含某个点
     */
    public static boolean isContain(double lat, double lon, double radius, double ptLat, double ptLon) {
        double x1 = lon - Math.toDegrees(radius / R / Math.cos(Math.toRadians(lat)));
        double x2 = lon + Math.toDegrees(radius / R / Math.cos(Math.toRadians(lat)));
        double y1 = lat - Math.toDegrees(radius / R);
        double y2 = lat + Math.toDegrees(radius / R);

        return ptLon >= x1 && ptLon <= x2 && ptLat >= y1 && ptLat <= y2;
    }

    public static double[] transformWgs84ToGcj02(double wglat, double wglon) {
        double dlat = transformLat(wglon - 105.0, wglat - 35.0);
        double dlon = transformLon(wglon - 105.0, wglat - 35.0);
        double radLat = wglat / 180 * Math.PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
        dlon = (dlon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);
        double[] result = {wglat + dlat, wglon + dlon};
        return result;
    }

    public static double[] transformGcj02ToWgs84(double wglat, double wglon) {
        double dLat = transformLat(wglon - 105.0, wglat - 35.0);
        double dLon = transformLon(wglon - 105.0, wglat - 35.0);
        double radLat = wglat / 180.0 * Math.PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);
        double[] result = {wglat - dLat, wglon - dLon};
        return result;
    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y * Math.PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }


    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x / 30.0 * Math.PI)) * 2.0 / 3.0;
        return ret;
    }

    public static boolean isPointInPolygon(double x, double y, String polygon) {
        Boolean result = false;

        ArrayList<Double> polygonXA = new ArrayList<Double>();
        ArrayList<Double> polygonYA = new ArrayList<Double>();
        String[] points = polygon.split(",");
        for (String point: points) {
            String[] ll = point.split(" ");
            polygonYA.add(Double.parseDouble(ll[1]));
            polygonXA.add(Double.parseDouble(ll[0]));
        }
        int j = polygonXA.size() - 1;

        for (int i = 0; i < polygonXA.size() - 1; i++) {
            if  (((polygonYA.get(i) > y) != (polygonYA.get(j) > y))
                    && (x < (polygonXA.get(j) - polygonXA.get(i)) * (y - polygonYA.get(i)) / (polygonYA.get(j) - polygonYA.get(i)) + polygonXA.get(i))) {
                result = !result;
            }
            j = i;
        }
        return result;
    }


}
