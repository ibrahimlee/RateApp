package com.chameleon.rateapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

public class RateApp implements View.OnTouchListener, View.OnClickListener {

    private static final String SHOW_COUNT = "show_count";
    private static RateApp rateApp;
    private final Context context;
    private final String appId;
    private final int color;
    private Button btnAskMeLater, btnCancel;
    private ImageView star_1, star_2, star_3, star_4, star_5;
    private MaterialDialog dialog;
    private SharedPreferences preferences;

    public static RateApp initialize(Context context, String appId, int color) {
        if (rateApp == null) {
            return new RateApp(context, appId, color);
        } else {
            return rateApp;
        }
    }

    private RateApp(final Context context, String appId, int color) {
        this.context = context;
        this.appId = appId;
        this.color = color;

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int showCount = preferences.getInt(SHOW_COUNT, 0);

        if (showCount != 5) {
            preferences.edit().putInt(SHOW_COUNT, showCount + 1).apply();
        } else {
            showRateDialog();
            preferences.edit().putInt(SHOW_COUNT, showCount + 1).apply();
        }
    }

    private void showRateDialog() {
        dialog = new MaterialDialog.Builder(context).cancelable(false).title(R.string.txt_rate_app).customView(R.layout.rate_layout, false).show();

        LinearLayout layout = (LinearLayout) dialog.getCustomView();

        if (layout != null) {
            star_1 = layout.findViewById(R.id.star_1);
            star_2 = layout.findViewById(R.id.star_2);
            star_3 = layout.findViewById(R.id.star_3);
            star_4 = layout.findViewById(R.id.star_4);
            star_5 = layout.findViewById(R.id.star_5);

            btnAskMeLater = layout.findViewById(R.id.btn_ask_me_later);
            btnCancel = layout.findViewById(R.id.btn_cancel);

            btnAskMeLater.setTextColor(color);
            btnCancel.setTextColor(color);

            setOnTouchListeners(star_1, star_2, star_3, star_4, star_5);

            setOnClickListeners(btnAskMeLater, btnCancel);
        }

    }

    private void setOnClickListeners(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    private void setOnTouchListeners(View... views) {
        for (View view : views) {
            view.setOnTouchListener(this);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int i = v.getId();
        if (i == R.id.star_1) {
            star_1.setImageResource(R.drawable.star_true);
            star_2.setImageResource(R.drawable.star_false);
            star_3.setImageResource(R.drawable.star_false);
            star_4.setImageResource(R.drawable.star_false);
            star_5.setImageResource(R.drawable.star_false);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, R.string.txt_successful, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }, 200);

        } else if (i == R.id.star_2) {
            star_1.setImageResource(R.drawable.star_true);
            star_2.setImageResource(R.drawable.star_true);
            star_3.setImageResource(R.drawable.star_false);
            star_4.setImageResource(R.drawable.star_false);
            star_5.setImageResource(R.drawable.star_false);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, R.string.txt_successful, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }, 200);

        } else if (i == R.id.star_3) {
            star_1.setImageResource(R.drawable.star_true);
            star_2.setImageResource(R.drawable.star_true);
            star_3.setImageResource(R.drawable.star_true);
            star_4.setImageResource(R.drawable.star_false);
            star_5.setImageResource(R.drawable.star_false);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, R.string.txt_successful, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }, 200);

        } else if (i == R.id.star_4) {
            star_1.setImageResource(R.drawable.star_true);
            star_2.setImageResource(R.drawable.star_true);
            star_3.setImageResource(R.drawable.star_true);
            star_4.setImageResource(R.drawable.star_true);
            star_5.setImageResource(R.drawable.star_false);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appId)));
                    dialog.dismiss();
                }
            }, 200);

        } else if (i == R.id.star_5) {
            star_1.setImageResource(R.drawable.star_true);
            star_2.setImageResource(R.drawable.star_true);
            star_3.setImageResource(R.drawable.star_true);
            star_4.setImageResource(R.drawable.star_true);
            star_5.setImageResource(R.drawable.star_true);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appId)));
                    dialog.dismiss();
                }
            }, 200);
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_ask_me_later) {
            preferences.edit().putInt(SHOW_COUNT, 3).apply();
            dialog.dismiss();

        } else if (i == R.id.btn_cancel) {
            preferences.edit().putInt(SHOW_COUNT, 5).apply();
            dialog.dismiss();
        }
    }
}
