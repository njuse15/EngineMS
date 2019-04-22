package cn.nju.se15.enginems.view.map.location;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;

import cn.nju.se15.enginems.view.map.direction.MyDirectionSensor;


/**
 * Created by 果宝 on 2018/ic_create/16.
 */

public class MyLocationClient {

    private LocationClient mLocationClient;
    private MyDirectionSensor mDirectionSensor;

    // 更新定位的回调接口
    private OnUpdateMyLocationData mOnUpdateMyLocationData;
    public interface OnUpdateMyLocationData {
        void updateMyLocationData(MyLocationData locData);
    }

    public MyLocationClient(Context context, OnUpdateMyLocationData onUpdateMyLocationData) {
        this.mLocationClient = new LocationClient(context);
        this.mDirectionSensor = new MyDirectionSensor(context);
        this.mOnUpdateMyLocationData = onUpdateMyLocationData;
        initClient();
    }

    /**
     * 初始化定位客户端
     */
    private void initClient() {

        LocationClientOption option = new LocationClientOption();

        // 设置定位模式，LocationMode.Hight_Accuracy：高精度；
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        // 设置返回经纬度坐标类型，bd09ll：百度经纬度坐标；
        option.setCoorType("bd09ll");

        // 设置发起定位请求的间隔，int类型，单位ms
        // 如果设置为0，则代表单次定位，即仅定位一次，默认为0
        // 如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(1000);

        // 设置是否使用gps，使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setOpenGps(true);

        // 设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);

        // 定位SDK内部是一个service，并放到了独立进程。设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setIgnoreKillProcess(false);

        // 设置是否收集Crash信息，默认收集，即参数为false
        option.SetIgnoreCacheException(false);

        // 如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位
        option.setWifiCacheTimeOut(5 * 60 * 1000);

        // 设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        option.setEnableSimulateGps(false);

        // 设置mLocationClient并启动
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(new MyLocationListener());
    }

    public void startClient() {
        mLocationClient.start();
        mDirectionSensor.startSensor();
    }

    public void resumeClient() {
        mLocationClient.registerLocationListener(new MyLocationListener());
        mDirectionSensor.startSensor();
    }

    public void pauseClient() {
        mLocationClient.unRegisterLocationListener(new MyLocationListener());
        mDirectionSensor.stopSensor();
    }

    public void stopClient() {
        mLocationClient.stop();
        mDirectionSensor.stopSensor();
    }

    /**
     * 内部监听类
     * 用于实时处理定位信息
     */
    private class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            float direction = mDirectionSensor.getDirection();

            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    // 设置半径
                    .accuracy(bdLocation.getRadius())
                    // 设置方向信息
                    .direction(direction)
                    // 设置纬度
                    .latitude(bdLocation.getLatitude())
                    // 设置经度
                    .longitude(bdLocation.getLongitude())
                    .build();

            mOnUpdateMyLocationData.updateMyLocationData(locData);
        }
    }
}
