package quixada.ufc.br.kisan.fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import java.util.ArrayList;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.adapter.GridViewAdapter;


public class ListaAnunciosFragment extends Fragment {

    /** Called when the activity is first created. */

    private GridViewAdapter mAdapter;
    private ArrayList<String> listLivros;
    private ArrayList<Integer> listCapas;

    private GridView gridView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_lista_anuncios, container, false);
        preparaList();
        // prepared arraylist and passed it to the Adapter class
        mAdapter = new GridViewAdapter(getActivity(),listLivros, listCapas);

        // Set custom adapter to gridview
        gridView = (GridView) view.findViewById(R.id.gridView1);
        gridView.setAdapter(mAdapter);

// Implement On Item click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {

                Toast.makeText(getContext(), (CharSequence) mAdapter.getItem(position), Toast.LENGTH_SHORT).show();


            }
        });
        return view;


    }

    private void preparaList() {

        listLivros = new ArrayList<String>();

        listLivros.add("Dom Quixote");
        listLivros.add("O Conde de Monte Cristo");
        listLivros.add(" Um Conto de Duas Cidades");
        listLivros.add("O Pequeno Príncipe");
        listLivros.add("O Senhor dos Anéis");
        listLivros.add("Harry Potter e a Pedra Filosofal");
        listLivros.add("O Caso dos Dez Negrinhos");
        listLivros.add("O Sonho da Câmara Vermelha");
        listLivros.add("O Leão, a Feiticeira e o Guarda-Roupa");
        listLivros.add("Ela, a Feiticeira");


        listCapas = new ArrayList<Integer>();

        listCapas.add(R.raw.domquixote);
        listCapas.add(R.raw.ocondedemontecristo);
        listCapas.add(R.raw.umcontodeduascidades);
        listCapas.add(R.raw.opequenoprincipe);
        listCapas.add(R.raw.osenhordosaneis);
        listCapas.add(R.raw.harryoottereapedrafilosofal);
        listCapas.add(R.raw.casodosdeznegrinhos);
        listCapas.add(R.raw.osonhodacamaravermelha);
        listCapas.add(R.raw.narnia);
        listCapas.add(R.raw.elaafeiticeira);




    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
