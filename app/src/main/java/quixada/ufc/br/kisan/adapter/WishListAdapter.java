package quixada.ufc.br.kisan.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import quixada.ufc.br.kisan.R;

/**
 * Created by andersonuchoa on 18/12/15.
 */
public class WishListAdapter extends ArrayAdapter<String> {
    public WishListAdapter(Context context, int resource) {
        super(context, resource);
    }

    /*

    private final Activity context;
    private final String[] itemname;
    private final Integer[] integers;

    public WishListAdapter(Activity context, String[] itemname, Integer[] integers) {
        super(context, R.layout.item_list_meus_livros, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.integers=integers;
    }

    public View getView(int position,View view, ViewGroup parent){

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.item_list_meus_livros,null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.texto_principal);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        txtTitle.setText(itemname[position]);
        imageView.setImageResource(integers[position]);


        return rowView;
    }
*/
}
