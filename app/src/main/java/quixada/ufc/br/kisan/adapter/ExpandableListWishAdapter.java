package quixada.ufc.br.kisan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.Util.CaminhosWebService;
import quixada.ufc.br.kisan.model.Livro;

/**
 * Created by andersonuchoa on 07/02/16.
 */
public class ExpandableListWishAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "ExpandableListWishAdapter";
    String url_imagem = "http://"+ CaminhosWebService.IP+"/KisanSERVER/file/";

    private Context context;
    private ArrayList<Livro> livros;
    private OnCustomClickListener callBack;


    public ExpandableListWishAdapter(Context context, ArrayList<Livro> livros, OnCustomClickListener callBack) {
        this.context = context;
        this.livros = livros;
        this.callBack = callBack;

    }


    public void addNovoLivro(Livro livro){
        livros.add(livro);
        notifyDataSetChanged();
    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.livros.get(groupPosition);
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
            Log.i(TAG, "entrou");
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_fragment_whislist, null);
        }


        TextView autor = (TextView)convertView.findViewById(R.id.autor_wishlist);
        TextView genero = (TextView)convertView.findViewById(R.id.genero_wishlist);
        TextView sinopse = (TextView)convertView.findViewById(R.id.sinopse_wishlist);
        TextView proprietario = (TextView)convertView.findViewById(R.id.proprietario_wishlist);
        TextView localizacao = (TextView)convertView.findViewById(R.id.localizacao_wishlist);


        autor.setText(child.getAutor());
        genero.setText(child.getGenero());
        sinopse.setText(child.getSinopse());
        proprietario.setText(child.getUsuario().getNome());
        localizacao.setText(child.getUsuario().getCidade());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return  1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.livros.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.livros.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final Livro livro = livros.get(groupPosition);

        String headerTitle = livro.getTitulo();


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_fragment_whishlist, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        ImageView imagem  = (ImageView) convertView
                .findViewById(R.id.capa);

        lblListHeader.setText(headerTitle);
        Picasso.with(context)
                .load(url_imagem+livro.getFoto())
                .placeholder(R.drawable.images)
                .error(R.drawable.images)
                .into(imagem);

        final ImageView visualizar_localicacao = (ImageView) convertView.findViewById(R.id.visualizarLocalicacao);

        visualizar_localicacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callBack.OnCustomClick(v, livro.getUsuario());
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


