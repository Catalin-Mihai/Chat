package com.example.catalin.chat;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

public class ContactsRecyclerSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int verticalSpaceHeight;
    Context context;

    public ContactsRecyclerSpaceItemDecoration(Context context, int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
        this.context = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = (int)convertDpToPixel(verticalSpaceHeight, context);
    }

    private static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
