package quixada.ufc.br.kisan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.fragments.ListaAnunciosFragment;
import quixada.ufc.br.kisan.fragments.WishListFragment;
import quixada.ufc.br.kisan.model.Livro;

/**
 * Created by andersonuchoa on 07/02/16.
 */
public class EpandableListWishAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private ArrayList<Livro> _livros;
    private boolean flagImage;
    private OnCustomClickListener callBack;


    public EpandableListWishAdapter(Context context,ArrayList<Livro> livros, OnCustomClickListener callBack) {
        this._context = context;
        this._livros = livros;
        this.flagImage = true;
        this.callBack = callBack;

    }


    public void addNovoLivro(Livro livro){
        _livros.add(livro);
        notifyDataSetChanged();
    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._livros.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Livro child = (Livro) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_fragment_whislist, null);
        }

        TextView autor = (TextView)convertView.findViewById(R.id.autor);
        TextView genero = (TextView)convertView.findViewById(R.id.genero);
        TextView sinopse = (TextView)convertView.findViewById(R.id.sinopse);
        TextView proprietario = (TextView)convertView.findViewById(R.id.proprietario);
        TextView localizacao = (TextView)convertView.findViewById(R.id.localizacao);

        autor.setText("");
        genero.setText("default");
        sinopse.setText(child.getSinopse());
        proprietario.setText(child.getUsuario().getNome());
        localizacao.setText("default");

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._livros.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._livros.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final Livro livro = _livros.get(groupPosition);

        String headerTitle = livro.getTitulo();
        //Integer capa = _livros.get(groupPosition).getImagem();

        Integer capa = R.drawable.images;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_fragment_whishlist, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        ImageView imagem  = (ImageView) convertView
                .findViewById(R.id.capa);

        lblListHeader.setText(headerTitle);
        imagem.setImageResource(capa);

        final ImageView visualizar_localicacao = (ImageView) convertView.findViewById(R.id.visualizarLocalicacao);

        visualizar_localicacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callBack.OnCustomClick(v, livro.getId());
            }
        });

        return convertView;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}


