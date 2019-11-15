package com.bdb.mobilebanking;

import android.app.Application;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance;
    private RequestQueue mRequestQueue;
    //private ImageLoader mImageLoader;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

//    public ImageLoader getImageLoader() {
//        getRequestQueue();
//        if (mImageLoader == null) {
//            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
//        }
//        return this.mImageLoader;
//    }

//    public <T> void addToRequestQueue(Request<T> req, String tag) {
//        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
//        getRequestQueue().add(req);
//    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
        req.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public <T> void requestQueueWithoutRetryPolicy(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

//    public void cancelPendingRequests(Object tag) {
//        if (mRequestQueue != null) {
//            mRequestQueue.cancelAll(tag);
//        }
//    }
}
