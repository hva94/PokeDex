package com.hvasoft.pokedex.presentation.ui.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.hvasoft.pokedex.R;

public class UIExtensionFunctions {

    public static void showPopUpMessage(View view, String message, Boolean isError) {
        int duration = isError ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT;

        Snackbar snackbar = Snackbar.make(view, message, duration);
        ViewGroup.LayoutParams params = snackbar.getView().getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams marginParams) {
            int extraBottomMargin =
                    view.getContext().getResources().getDimensionPixelSize(R.dimen.common_padding_default);
            marginParams.setMargins(
                    marginParams.leftMargin,
                    marginParams.topMargin,
                    marginParams.rightMargin,
                    marginParams.bottomMargin + extraBottomMargin
            );
            snackbar.getView().setLayoutParams(marginParams);
        }

        snackbar.show();
    }

    public static void loadImageWithUrl(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerInside()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_caution)
                .into(imageView);
    }

}
