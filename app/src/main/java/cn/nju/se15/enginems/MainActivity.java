package cn.nju.se15.enginems;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.lzp.floatingactionbuttonplus.FabTagLayout;
import com.lzp.floatingactionbuttonplus.FloatingActionButtonPlus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.nju.se15.enginems.util.AppData;
import cn.nju.se15.enginems.util.PermissionUtil;
import cn.nju.se15.enginems.view.analysis.DataAnalysisActivity;
import cn.nju.se15.enginems.view.bluetooth.BluetoothActivity;
import cn.nju.se15.enginems.view.map.MapFragment;
import cn.nju.se15.enginems.view.realdata.RealDataActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.multi_fab)
    FloatingActionButtonPlus mMultiFloatingActionButton;

    private MapFragment mMapFragment;

    private final int LOCATION_CODE = 0;
    private final int BLUE_TOOTH_CODE = 1;
    private final int DATA_ANALYSIS_CODE = 2;
    private final int REAL_DATA_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PermissionUtil.getInstance().getPermissions(this);

        // 初始化地图组件
        SDKInitializer.initialize(getApplicationContext());
        initCustomMap();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initWidget();
        AppData.setContext(getApplication());
    }

    private void initCustomMap() {
        try {
            String moduleName = getFilesDir().getAbsolutePath();
            File file = new File(moduleName + "/" + "map_style");

            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();

            InputStream input = getAssets().open("custom_config_dark");
            byte[] bytes = new byte[input.available()];
            input.read(bytes);

            FileOutputStream out = new FileOutputStream(file);
            out.write(bytes);

            MapView.setCustomMapStylePath(moduleName + "/map_style");

            input.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initWidget() {
        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fgm_map);
        mMultiFloatingActionButton.setOnItemClickListener(new FloatingActionButtonPlus.OnItemClickListener() {
            @Override
            public void onItemClick(FabTagLayout tagView, int position) {
                if(position == LOCATION_CODE) {
                    mMapFragment.setRequestLoc(true);
                    return;
                }

                // 测试是否连接上蓝牙
                boolean isLogin = AppData.getLoginState();
                if(!isLogin) {
                    BluetoothActivity.activityStart(MainActivity.this);
                    return;
                }

                switch (position) {
                    case BLUE_TOOTH_CODE:
                        BluetoothActivity.activityStart(MainActivity.this);
                        break;
                    case DATA_ANALYSIS_CODE :
                        DataAnalysisActivity.activityStart(MainActivity.this);
                        break;
                    case REAL_DATA_CODE:
                        mMapFragment.onPause();
                        RealDataActivity.activityStart(MainActivity.this);
                        mMapFragment.onResume();
                        break;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
