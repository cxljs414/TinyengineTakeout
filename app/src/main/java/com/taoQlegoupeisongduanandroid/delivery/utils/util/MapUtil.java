package com.taoQlegoupeisongduanandroid.delivery.utils.util;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;

import java.util.List;

/**
 * Created by zhangxiaomei on 2017/9/3.
 */

public class MapUtil {
    public static void centerTrackPoints(Context context,AMap map, List<LatLng> points,int padding){
        if(points == null || points.size() < 2){
            return;
        }

        LatLng first = points.get(0);
        double minLat = first.latitude;
        double maxLat = first.latitude;
        double minLon = first.longitude;
        double maxLon = first.longitude;
        for(int i = 1, num = points.size() ; i < num ; i++){
            LatLng t = points.get(i);
            if(t.latitude > maxLat){
                maxLat = t.latitude;
            }
            if(t.latitude < minLat){
                minLat = t.latitude;
            }
            if(t.longitude > maxLon){
                maxLon = t.longitude;
            }
            if(t.longitude < minLon){
                minLon = t.longitude;
            }
        }
        LatLngBounds bounds = new LatLngBounds(new LatLng(minLat, minLon), new LatLng(maxLat, maxLon));
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,padding));
    }
}
