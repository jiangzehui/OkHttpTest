package com.example.app.okhttptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    ImageView iv;
    String ip = "http://apicloud.mob.com/v1/weather/citys?key=1e501124818b4";
    String ip2 = "http://apicloud.mob.com/v1/weather/citys";
   // String img_ip = "http://img5.imgtn.bdimg.com/it/u=2617959259,1071860302&fm=27&gp=0.jpg";
    String img_ip = "http://avatar.csdn.net/8/F/4/3_worship_kill.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        iv= (ImageView) findViewById(R.id.lv);
        //get();
        //post();
        OkHttpUtil.getInstence().loadImg(img_ip,iv);
    }



    public void get() {
        OkHttpUtil.getInstence().get(ip, new OkHttpUtil.CallBackJSON() {
            @Override
            public void success(JSONObject body) {
                tv.setText(body.toString());
            }

            @Override
            public void fail() {

            }
        });
    }

    public void post() {
        RequestBody requestBody = new FormBody.Builder().add("key", "1e501124818b4").build();
        OkHttpUtil.getInstence().post(ip2, requestBody, new OkHttpUtil.CallBackJSON() {
            @Override
            public void success(JSONObject body) {
                tv.setText(body.toString());
            }

            @Override
            public void fail() {

            }
        });
    }


}
