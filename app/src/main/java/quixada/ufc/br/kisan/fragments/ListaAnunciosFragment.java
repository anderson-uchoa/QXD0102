package quixada.ufc.br.kisan.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;
import java.util.ArrayList;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.activity.VisualizarAnuncioActivity;
import quixada.ufc.br.kisan.adapter.ExpandableListAdapter;
import quixada.ufc.br.kisan.adapter.GridViewAdapter;
import quixada.ufc.br.kisan.adapter.MeusLivrosAdapter;
import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.model.Grupo;
import quixada.ufc.br.kisan.model.Livro;
import quixada.ufc.br.kisan.model.Usuario;


public class ListaAnunciosFragment extends Fragment  implements PopupMenu.OnMenuItemClickListener  {
    private static final String TAG = "VisualizarMeusAnunciosActivity";

    String url = "http://10.0.2.2:8080/KisanSERVER/usuario/livros";

    private ArrayList<Livro> todosLivros = new ArrayList<Livro>();
    private MeusLivrosAdapter meusLivrosAdapter;
    private ListView listView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_anuncios, container, false);

        return view;


    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_AddWishList:
                Toast.makeText(getActivity(), "Adicionado na WishList", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_Visualizar:
                Toast.makeText(getActivity(), "Visualizar Livro", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), VisualizarAnuncioActivity.class);
                startActivity(intent);

                return true;
        }

        return  true;
    }

    private class ListarTdLivros {
    }
}
