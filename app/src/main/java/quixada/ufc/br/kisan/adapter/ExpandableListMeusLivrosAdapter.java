package quixada.ufc.br.kisan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.Util.CaminhosWebService;
import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.model.Livro;

/**
 * Created by andersonuchoa on 13/02/16.
 */
public class ExpandableListMeusLivrosAdapter extends BaseExpandableListAdapter {

    String url_imagem = "http://"+ CaminhosWebService.IP+"/KisanSERVER/file/";

    private Context context;
    private ArrayList<Livro> livros;
    private OnCustomClickListener callBack;


    public ExpandableListMeusLivrosAdapter(Context context, ArrayList<Livro> livros, OnCustomClickListener onCustomClickListeners) {
        this.context = context;
        this.livros = livros;
        this.callBack = onCustomClickListeners;

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
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.lista_item_meuslivros, null);
        }
        TextView titulo = (TextView)convertView.findViewById(R.id.input_titulo_livro);
        TextView autor = (TextView)convertView.findViewById(R.id.input_autor_livro);
        TextView genero = (TextView)convertView.findViewById(R.id.spinner_livro);
        TextView sinopse = (TextView)convertView.findViewById(R.id.input_descricao_livro);
        Button btn_Atualizar = (Button) convertView.findViewById(R.id.btn_Atualizar_livro);

        titulo.setText(child.getTitulo());
        autor.setText(child.getAutor());
        genero.setText(child.getGenero());
        sinopse.setText(child.getSinopse());


        btn_Atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
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
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.lista_grupo_meuslivros, null);
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


        final ImageView deletar = (ImageView) convertView.findViewById(R.id.deletarView);

        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                livros.remove(livros.get(groupPosition));
                notifyDataSetChanged();

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

    public void addNovoLivro(Livro livro) {
        livros.add(livro);
        notifyDataSetChanged();
    }
}
