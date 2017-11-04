package search.band.vilner.dmitry.bandsearch.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import search.band.vilner.dmitry.bandsearch.R;
import search.band.vilner.dmitry.bandsearch.network.model.BandShortInfo;

public class BandsListAdapter extends ArrayAdapter<BandShortInfo> {

    private final int resource;

    public BandsListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.bandName = convertView.findViewById(R.id.band_name);
            viewHolder.bandGenre = convertView.findViewById(R.id.genre);
            viewHolder.country = convertView.findViewById(R.id.country);
            convertView.setTag(viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        BandShortInfo bandInfo = getItem(position);
        viewHolder.bandName.setText(bandInfo.name);
        viewHolder.bandGenre.setText(bandInfo.genre);
        viewHolder.country.setText(bandInfo.country);
        return convertView;
    }

    private class ViewHolder {
        TextView bandName;
        TextView bandGenre;
        TextView country;
    }


}
