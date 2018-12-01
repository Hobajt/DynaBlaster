package com.kopecrad.dynablaster.game.infrastructure.score;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kopecrad.dynablaster.R;

import java.util.List;

public class ScoreAdapter extends ArrayAdapter<Score> {

    private int layoutResourceId;
    private Context context;
    private List<Score> data;

    public ScoreAdapter(Context context, int layoutResourceId, List<Score> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ScoreHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ScoreHolder();
            holder.player= (TextView)row.findViewById(R.id.scoreEntry_player);
            holder.score= (TextView)row.findViewById(R.id.scoreEntry_score);
            holder.date= (TextView)row.findViewById(R.id.scoreEntry_date);

            row.setTag(holder);
        }
        else {
            holder = (ScoreHolder) row.getTag();
        }

        Score entry = data.get(position);
        holder.player.setText(entry.getPlayer());
        holder.score.setText(entry.getScore());
        holder.date.setText(entry.getDate());

        return row;
    }

    private class ScoreHolder {
        TextView player;
        TextView score;
        TextView date;
    }
}
