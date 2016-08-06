package com.arek00.alarmclock.utils;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ViewUtils {

    public static String getTextFromViewElement(int elementId, Activity activity) {
        View view = activity.findViewById(elementId);

        if (view instanceof TextView) {
            return ((TextView) view).getText().toString();
        }

        return view.toString();
    }

    public static void setTextFromViewElement(int elementId, String text, Activity activity) {
        View view = activity.findViewById(elementId);

        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    public static void setTextFromViewElement(TextView element, String text) {
        element.setText(text);
    }
}
