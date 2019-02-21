package com.mattrubacky.monet2.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mattrubacky.monet2.ui.activities.AddNotification;
import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.ui.adapter.ListView.StageNotificationAdapter;
import com.mattrubacky.monet2.data.deserialized.splatoon.StageNotification;
import com.mattrubacky.monet2.data.deserialized.splatoon.StageNotifications;

import java.util.ArrayList;

/**
 * Created by mattr on 10/21/2017.
 */

public class StageNotificationPickerDialog extends Dialog {
    int selected;
    ArrayList<StageNotification> notificationList;
    public StageNotificationPickerDialog(Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_manage_notifications);
        Typeface titleFont = Typeface.createFromAsset(getContext().getAssets(),"Paintball.otf");

        selected =-1;

        RelativeLayout card = findViewById(R.id.dialogCard);
        TextView title = findViewById(R.id.title);
        final ListView notificationListView = findViewById(R.id.ItemList);
        Button addNotification = findViewById(R.id.AddNotification);

        title.setText("Notifications");

        title.setTypeface(titleFont);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        final StageNotifications stageNotifications = gson.fromJson(settings.getString("stageNotifications","{\"notifications\":[]}"),StageNotifications.class);

        notificationList = stageNotifications.notifications;

        final StageNotificationAdapter stageAdapter = new StageNotificationAdapter(getContext(),notificationList,this);

        notificationListView.setAdapter(stageAdapter);

        card.setClipToOutline(true);

        addNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent = new Intent(getContext(), AddNotification.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("isGear",false);
                bundle.putBoolean("isEdit",false);
                intent.putExtras(bundle);
                dismiss();
                getContext().startActivity(intent);
            }
        });

    }
}
