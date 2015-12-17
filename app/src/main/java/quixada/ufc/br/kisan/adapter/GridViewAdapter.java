package quixada.ufc.br.kisan.adapter;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import quixada.ufc.br.kisan.R;

/**
 * Created by andersonuchoa on 13/12/15.
 */
public class GridViewAdapter  extends BaseAdapter{

    private ArrayList<String> listLivros;
    private ArrayList<Integer> listCapas;
    private Activity fragment;



    public GridViewAdapter(Activity fragment,ArrayList<String> listLivros, ArrayList<Integer> listFlag) {
        super();
        this.listLivros = listLivros;
        this.listCapas = listFlag;
        this.fragment = fragment;
    }


    @Override
    public int getCount() {
        return listLivros.size();
    }

    @Override
    public Object getItem(int position) {
        return listLivros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder {
        public ImageView imgViewCapa;
        public TextView txtViewTitulo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder view;
        LayoutInflater inflator = fragment.getLayoutInflater();
       // LayoutInflater inflator = (LayoutInflater) convertView.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
       // LayoutInflater inflator = activity.getLayoutInflater();

        if(convertView==null) {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.gridview_row, null);

            view.txtViewTitulo = (TextView) convertView.findViewById(R.id.textView1);
            view.imgViewCapa = (ImageView) convertView.findViewById(R.id.imageView1);

            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }

        view.txtViewTitulo.setText(listLivros.get(position));
        view.imgViewCapa.setImageResource(listCapas.get(position));

        return convertView;
    }

}