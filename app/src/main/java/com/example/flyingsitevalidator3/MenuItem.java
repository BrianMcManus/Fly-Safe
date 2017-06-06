package com.example.flyingsitevalidator3;

import android.graphics.drawable.Drawable;

/**
 * Created by Brian on 12/12/2016.
 */

public class MenuItem {
    public static String title = "title";
    public static Drawable icon;

    public MenuItem(String nTitle, Drawable nIcon)
    {
        title = nTitle;
        icon = nIcon;
    }


    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        MenuItem.title = title;
    }


    public static Drawable getIcon() {
        return icon;
    }

    public static void setIcon(Drawable icon) {
        MenuItem.icon = icon;
    }


}
