package cn.nju.se15.enginems.view.map.draw;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;

import cn.nju.se15.enginems.R;

/**
 * Created by 果宝 on 2018/3/22.
 */

public class MyDrawTool {

    public final static int NORMAL_TRACK_MAKERT = R.drawable.ic_3d_rotation;

    private BaiduMap mBaiduMap;

    public MyDrawTool(BaiduMap map) {
        this.mBaiduMap = map;
    }

    public void drawTrackMarket(double latitude, double longitude,int marketType) {
        LatLng point = new LatLng(latitude, longitude);

        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(marketType);

//        //构建MarkerOption，用于在地图上添加Marker
//        OverlayOptions option = new MarkerOptions()
//                .position(point)
//                .icon(bitmap);

        ArrayList<BitmapDescriptor> bitmapGif = new ArrayList<>();
        bitmapGif.add(bitmap);

        MarkerOptions ooD = new MarkerOptions().position(point).icons(bitmapGif)
                .zIndex(0).period(10);

        // 生长动画
        ooD.animateType(MarkerOptions.MarkerAnimateType.grow);

        Marker mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));
    }

}
