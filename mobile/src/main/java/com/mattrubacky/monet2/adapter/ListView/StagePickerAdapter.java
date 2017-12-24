package com.mattrubacky.monet2.adapter.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mattrubacky.monet2.R;
import com.mattrubacky.monet2.deserialized.Stage;

import java.util.ArrayList;

/**
 * Created by mattr on 11/13/2017.
 */

public class StagePickerAdapter extends ArrayAdapter<Stage> {
    public StagePickerAdapter(Context context, ArrayList<Stage> input) {
        super(context, 0, input);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_stage_picker, parent, false);
        }
        Stage stage = getItem(position);

        TextView stageName = (TextView) convertView.findViewById(R.id.StageName);

        stageName.setText(stage.name);

        return convertView;
    }
}
