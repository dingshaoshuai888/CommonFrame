package com.retrofit.demo.help.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class ImageHelp {

    public static void load(Context context, ImageView imageView, String url) {
        load(context, imageView, url, 0, 0);
    }

    public static void loadCircle(Context context, ImageView imageView, String url) {
        loadCircle(context, imageView, url, 0, 0);
    }

    public static void loadRound(Context context, ImageView imageView, String url, int radius) {
        loadRound(context, imageView, url, 0, 0, radius);
    }

    public static void load(Context context, ImageView imageView, int resId) {
        Glide.with(context).load(resId).into(imageView);
    }

    /**
     * 普通加载
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void load(Context context, ImageView imageView, String url, int place, int error) {
        RequestOptions options = new RequestOptions()
                .placeholder(place)
                .error(error)
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCircle(Context context, ImageView imageView, String url, int place, int error) {
        RequestOptions options = new RequestOptions()
                .circleCrop()
                .placeholder(place)
                .error(error)
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadRound(Context context, ImageView imageView, String url, int place, int error, int radius) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new RoundedCorners(radius))
                .placeholder(place)
                .error(error)
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }
}

