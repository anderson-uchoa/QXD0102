package quixada.ufc.br.kisan.fragments;

import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.PopupMenu;


public class WishListFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    /*

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


        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);


//        WishListAdapter adapter = new WishListAdapter(getActivity(), livros, imgid);
        lista = (ListView) view.findViewById(R.id.listView);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                popupMenu.setOnMenuItemClickListener(WishListFragment.this);
                popupMenu.inflate(R.menu.popup_menu_lista_whislist);
                popupMenu.show();



            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_deletar_wishList:
                Toast.makeText(getActivity(), "Item removido", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_visualizar_localizacao:
                Toast.makeText(getActivity(), "Visualizar Localização", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), Visualizar_Localizacao_Activity.class);
                startActivity(intent);
                return true;

        }
        return false;
    }
    */
}