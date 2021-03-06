package cn.nju.se15.enginems.view.realdata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import cn.nju.se15.enginems.R;

/**
 * Created by NJU.LG on 2019/4/20.
 */

public class RealDataActivity extends AppCompatActivity {
    public static void activityStart(Activity activity) {
        Intent intent = new Intent(activity, RealDataActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real);
        ButterKnife.bind(this);
    }
}
