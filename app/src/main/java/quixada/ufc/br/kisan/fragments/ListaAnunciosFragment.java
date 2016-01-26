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
import android.widget.PopupMenu;
import android.widget.Toast;
import java.util.ArrayList;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.activity.VisualizarAnuncioActivity;
import quixada.ufc.br.kisan.adapter.ExpandableListAdapter;
import quixada.ufc.br.kisan.adapter.GridViewAdapter;
import quixada.ufc.br.kisan.model.Grupo;


public class ListaAnunciosFragment extends Fragment  implements PopupMenu.OnMenuItemClickListener  {

    /**
     * Called when the activity is first created.
     * */


    private GridView gridView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_anuncios, container, false);



        ExpandableListView listView = (ExpandableListView) view.findViewById(R.id.livros_list);


        ArrayList<Grupo> groups = preparaData();
        final ExpandableListAdapter adapter = new ExpandableListAdapter(getActivity(), groups);
        listView.setAdapter(adapter);


        return view;


    }



    private ArrayList<Grupo> preparaData() {
        Grupo group1 = new Grupo("John Doe");
        group1.filhos.add("john.doe@gmail.com");
        group1.filhos.add("doe.john@gmail.com");

        Grupo group2 = new Grupo("John Doe");
        group1.filhos.add("john.doe@gmail.com");
        group1.filhos.add("doe.john@gmail.com");



        ArrayList<Grupo> groups = new ArrayList<Grupo>();
        groups.add(group1);
        groups.add(group2);

        return groups;
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

    }
