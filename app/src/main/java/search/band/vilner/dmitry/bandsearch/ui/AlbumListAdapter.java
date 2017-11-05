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
import search.band.vilner.dmitry.bandsearch.network.model.BandDetailsData;

public class AlbumListAdapter extends ArrayAdapter<BandDetailsData.Album> {

    private final int resource;

    public AlbumListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.albumName = convertView.findViewById(R.id.album_name);
            viewHolder.albumType = convertView.findViewById(R.id.type);
            viewHolder.albumYear = convertView.findViewById(R.id.year);
            convertView.setTag(viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        BandDetailsData.Album album = getItem(position);
        viewHolder.albumName.setText(album.title);
        viewHolder.albumType.setText(album.type);
        viewHolder.albumYear.setText(album.year);
        return convertView;
    }

    private class ViewHolder {
        TextView albumName;
        TextView albumType;
        TextView albumYear;
    }
}
