package com.srz.androidtools.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.srz.androidtools.R;

public class AlarmSender {

    private Activity activity;
    private static Toast toast;

    public AlarmSender(Activity activity) {
        this.activity = activity;
    }

    public static void sendInstantMessage(int msgId, Context context) {
        String msg = context.getString(msgId);
        sendInstantMessage(msg, context);
    }

    public static void sendInstantMessage(String msg, Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast,
                null);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        TextView title = (TextView) layout.findViewById(R.id.text);

        title.setText(msg);
        if (toast == null)
            toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 200);
        toast.setDuration(2000);
        toast.setView(layout);

        toast.show();
    }
}
