package cn.nju.se15.enginems.view.map.direction;

import android.content.Context;


/**
 * Created by 果宝 on 2018/ic_create/17.
 */

public class MyDirectionSensor implements OrientationSensorInterface {

    private float direction;

    private Orientation orientationSensor;

    public MyDirectionSensor(Context context) {
        orientationSensor = new Orientation(context, this);
        initSensor();
    }

    private void initSensor() {
        // set tolerance for any directions
        orientationSensor.init(1.0, 1.0, 1.0);
    }

    public void startSensor() {
        // set output speed and turn initialized sensor on
        // 0 Normal
        // ic_create UI
        // ic_seek GAME
        // ic_info FASTEST
        orientationSensor.on(0);
    }

    public void stopSensor() {
        orientationSensor.off();
    }

    /**
     * 根据实时接受的参数计算出用户当前的方位角
     * @param AZIMUTH
     * @param PITCH
     * @param ROLL
     */
    @Override
    public void orientation(Double AZIMUTH, Double PITCH, Double ROLL) {
        direction = AZIMUTH.floatValue();
    }

    public float getDirection() {
        return direction;
    }
}
