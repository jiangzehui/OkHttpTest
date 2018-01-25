package com.example.app.okhttptest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

/**
 * Created by jiangzehui on 18/1/20.
 */

public class OkHttpUtil {

    private volatile static OkHttpUtil mInstance;
    private OkHttpClient okHttpClient;
    private static Handler mHandler = new Handler();

    public OkHttpUtil() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
    }


    public static OkHttpUtil getInstence() {
        if (mInstance == null) {
            synchronized (OkHttpUtil.class) {
                mInstance = new OkHttpUtil();
            }
        }

        return mInstance;
    }
/**----------------------------------get方式---------------------------------------------*/
    /**
     * get方式，返回值String
     *
     * @param url
     * @param callBackString
     */
    public void get(String url, final CallBackString callBackString) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBackString.fail();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    final String result = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBackString.success(result);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (response.body() != null) {
                        response.close();
                    }
                }
            }
        });
    }

    /**
     * get方式，返回值String
     *
     * @param url
     * @param callBackJSON
     */
    public void get(String url, final CallBackJSON callBackJSON) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBackJSON.fail();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    final JSONObject object = new JSONObject(response.body().string());
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBackJSON.success(object);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (response.body() != null) {
                        response.close();
                    }
                }
            }
        });
    }

    /**
     * ----------------------------------post方式---------------------------------------------
     */
    public void post(String url, RequestBody requestBody, final CallBackString callBackString) {

        Request request = new Request.Builder().url(url).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBackString.fail();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) {


                try {
                    final String result = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBackString.success(result);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (response.body() != null) {
                        response.close();
                    }
                }


            }
        });
    }

    public void post(String url, RequestBody requestBody, final CallBackJSON callBackJSON) {

        Request request = new Request.Builder().url(url).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBackJSON.fail();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {
                try {
                    final String result = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                callBackJSON.success(new JSONObject(result));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (response.body() != null) {
                        response.close();
                    }
                }
            }
        });


    }

    public void postJSON(String url, String json, final CallBackString callBackString) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBackString.fail();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    final String result = response.body().string();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBackString.success(result);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (response.body() != null) {
                        response.close();
                    }
                }

            }
        });
    }


    /**
     * ----------------------------------post方式---------------------------------------------
     */
    public void loadImg(String ip, final ImageView iv) {
        Request request = new Request.Builder().url(ip).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    InputStream inputStream = response.body().byteStream();
                    BufferedSource bufferedSource = Okio.buffer(Okio.source(inputStream));
                    final byte[] finalBytes = bufferedSource.readByteArray();

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inPreferredConfig = Bitmap.Config.RGB_565;//这个值是设置色彩模式，默认值是ARGB_8888，在这个模式下，一个像素点占用4bytes空间，一般对透明度不做要求的话，一般采用RGB_565模式，这个模式下一个像素点占用2bytes。
                            Bitmap bitmap = BitmapFactory.decodeByteArray(finalBytes, 0, finalBytes.length, options);
                            iv.setImageBitmap(bitmap);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }


    /**
     * ----------------------------------回调接口---------------------------------------------
     */
    public interface CallBackString {
        void success(String body);

        void fail();
    }

    public interface CallBackJSON {
        void success(JSONObject object);

        void fail();
    }


}
