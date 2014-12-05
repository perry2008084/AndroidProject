package com.bookcell;

/**
 * Created by perry on 2014/10/12.
 */
import android.view.View;
import android.widget.ImageView;

public class ViewCache {
    private View baseView;
    private ImageView imageView;

    public ViewCache(View baseView) {
        this.baseView = baseView;
    }

    public ImageView getImageView() {
        if (imageView == null) {
            imageView = (ImageView) baseView.findViewById(R.id.iv_icon);
        }
        return imageView;
    }
}
