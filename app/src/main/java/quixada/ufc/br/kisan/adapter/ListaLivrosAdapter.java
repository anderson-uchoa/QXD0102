package quixada.ufc.br.kisan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.model.Livro;

/**
 * Created by andersonuchoa on 26/01/16.
 */
public class ListaLivrosAdapter extends BaseAdapter {


    private LayoutInflater inflater;
    private ArrayList<Livro> livros;


    public ListaLivrosAdapter (Context context, ArrayList<Livro> livros){
        this.livros = livros;
        inflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return livros.size();
    }

    @Override
    public Object getItem(int position) {
        return livros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Livro livro = livros.get(position);
        convertView = inflater.inflate(R.layout.intem_lista_tdlivros, null);

        ((TextView) convertView.findViewById(R.id.titulo_td_livro)).setText(livro.getTitulo());

        ((ImageView) convertView.findViewById(R.id.capa_td_livro)).setImageResource(R.drawable.images);
        return convertView;
    }
}



