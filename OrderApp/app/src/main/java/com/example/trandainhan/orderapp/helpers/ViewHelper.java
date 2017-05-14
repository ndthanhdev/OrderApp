package com.example.trandainhan.orderapp.helpers;

import android.view.View;

/**
 * Created by duyth on 5/14/2017.
 */

public class ViewHelper {
    public static void moveToFront(View selectedView, View... prViews) {
        selectedView.setVisibility(View.VISIBLE);
        for (View view : prViews) {
            view.setVisibility(View.GONE);
        }
    }

    public static void moveToBack(View selectedView, View... prViews) {
        selectedView.setVisibility(View.GONE);
        for (View view : prViews) {
            view.setVisibility(View.VISIBLE);
        }
    }
}
