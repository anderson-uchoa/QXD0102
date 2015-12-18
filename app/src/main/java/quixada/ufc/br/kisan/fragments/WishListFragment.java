package quixada.ufc.br.kisan.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.activity.ChatActivity;
import quixada.ufc.br.kisan.activity.VisualizarAnuncioActivity;
import quixada.ufc.br.kisan.activity.Visualizar_Localizacao_Activity;
import quixada.ufc.br.kisan.adapter.MeusAnunciosAdapter;
import quixada.ufc.br.kisan.adapter.WishListAdapter;


public class WishListFragment extends Fragment {




    private String livros[] = new String[]{"Caso dos Dez Negrinhos", "Harry Potter e a pedra filosofal"};

    private Integer[] imgid = {
          R.raw.casodosdeznegrinhos,
            R.raw.harryoottereapedrafilosofal
    };

    private ListView lista;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


View view =  inflater.inflate(R.layout.fragment_wish_list, container, false);


        WishListAdapter adapter = new WishListAdapter(getActivity(), livros, imgid);
        lista = (ListView) view.findViewById(R.id.listView);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Visualizar_Localizacao_Activity.class);
                startActivity(intent);
            }
        });

        return view;
    }





    @Override
    public void onResume() {
        super.onResume();
    }
}
