package quixada.ufc.br.kisan.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.dataBase.Conversa;

/**
 * Created by andersonuchoa on 18/12/15.
 */
public class ConversasAdapter extends ArrayAdapter<String> {


    private final Activity context;
    private List<Conversa> conversas;
    private String[] nomes;

    public ConversasAdapter(Activity context, String[] nomes)  {
        super(context, R.layout.fila_lista_conversas, nomes);

        this.context=context;
        this.nomes = nomes;

    }

    public View getView(int position,View view, ViewGroup parent){

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.fila_lista_conversas,null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.texto_principal);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        txtTitle.setText(nomes[position]);


        return rowView;
    }

}
