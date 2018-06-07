package eptalab.net.jsondemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ResourceBundle;

import eptalab.net.jsondemo.R;
import eptalab.net.jsondemo.model.Attrazione;

public class AttrazioniAdapter extends ArrayAdapter<Attrazione> {

    private ArrayList<Attrazione> attrazioni;
    private LayoutInflater layoutInflater;
    private int resource;
    private Context context;

    public AttrazioniAdapter(Context context, int resource, ArrayList<Attrazione> object) {
        super (context, resource, object);

        this.context = context;
        this.resource = resource;
        this.attrazioni = object;
        layoutInflater = (LayoutInflater.from(context));
    }

    private class ViewHolder {
        TextView name;
        TextView type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(resource, null);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.tv_name);
            viewHolder.type = convertView.findViewById(R.id.tv_type);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.name.setText(attrazioni.get(position).getName());
        viewHolder.type.setText(attrazioni.get(position).getType());


        return convertView;
    }


}
