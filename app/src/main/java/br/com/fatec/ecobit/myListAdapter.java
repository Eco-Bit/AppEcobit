package br.com.fatec.ecobit;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class myListAdapter extends ArrayAdapter <String> {

    private final Activity context;

    private final String[] mainTitle;
    private final String[] subTitle;
    private final int[] imagensArray;


    public myListAdapter(Activity context, String[] mainTitle, String[] subTitle, int[] imagensArray) {
        super(context, R.layout.lista_item, mainTitle);
        this.context = context;
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
        this.imagensArray = imagensArray;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.lista_item, null, true);

        TextView titleText = rowView.findViewById(R.id.itemTilte);
        TextView subTitleText = rowView.findViewById(R.id.itemSubtitle);
        ImageView imageView = rowView.findViewById(R.id.imageView);

        titleText.setText(mainTitle[position]);
        subTitleText.setText(subTitle[position]);
        imageView.setImageResource(imagensArray[position]);

        return rowView;
    }
}
