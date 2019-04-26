package cn.fizzo.hub.school.utils;

import android.widget.ImageView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import cn.fizzo.hub.school.R;

/**
 * Created by Raul.Fan on 2016/11/8.
 */

public class ImageU {

    /**
     * 缓冲用户头像图片
     *
     * @param avatar
     * @param imageView
     */
    public static void loadUserImage(final String avatar, final ImageView imageView) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setCrop(true)
                .setLoadingDrawableId(R.drawable.ic_default_user)
                .setFailureDrawableId(R.drawable.ic_default_user)
                .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
//                .setSize(width, height)
                .build();
        x.image().bind(imageView, avatar, imageOptions);
    }

    /**
     * 缓冲用户头像图片
     *
     * @param avatar
     * @param imageView
     */
    public static void loadUserImage(final String avatar, final ImageView imageView,final int width, final int height) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setCrop(true)
                .setLoadingDrawableId(R.drawable.ic_default_user)
                .setFailureDrawableId(R.drawable.ic_default_user)
                .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
                .setSize(width, height)
                .build();
        x.image().bind(imageView, avatar, imageOptions);
    }


    /**
     * 缓冲门店图片
     *
     * @param avatar
     * @param imageView
     */
    public static void loadStoreImage(final String avatar, final ImageView imageView) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setCrop(true)
                .setLoadingDrawableId(R.drawable.ic_default_store_logo)
                .setFailureDrawableId(R.drawable.ic_default_store_logo)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .build();
        x.image().bind(imageView, avatar, imageOptions);
    }

}
