package quixada.ufc.br.kisan.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.activity.MapsActivity;
import quixada.ufc.br.kisan.adapter.EpandableListWishAdapter;
import quixada.ufc.br.kisan.adapter.OnCustomClickListener;
import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.model.Livro;
import quixada.ufc.br.kisan.model.Usuario;
import quixada.ufc.br.kisan.services.ListarLivrosWhishListService;
import quixada.ufc.br.kisan.services.WebHelper;
import quixada.ufc.br.kisan.services.WebResult;


public class WishListFragment extends Fragment implements OnCustomClickListener {


    private static final String TAG = "WishListFragment";

    EpandableListWishAdapter listAdapter;
    ExpandableListView expListView;
    CustomApplication application = new CustomApplication();
    private static final String BUSCAR_PROPRIETARIO_LIVRO = "buscar-proproetario-livro";


    String url = "http://"+application.getIp()+"/KisanSERVER/livros/livrosUsuarioWishList/";


    private ProgressBar progressBar;
    private ArrayList<Livro> tdLivros = new ArrayList<Livro>();
    private BroadcastReceiver broadcastReceiver;
    Usuario usuario = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);


        EventBus.getDefault().register(this);


        progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
        expListView = (ExpandableListView) view.findViewById(R.id.expandableListView);

        if (tdLivros.size() <= 0 ){
            progressBar.setVisibility(view.GONE);
        }else{
            progressBar.setVisibility(View.VISIBLE);
        }


        listAdapter = new EpandableListWishAdapter(getActivity(), tdLivros, this);
        Intent intent = new Intent(getActivity(), ListarLivrosWhishListService.class);
        getActivity().startService(intent);


        application = (CustomApplication) getActivity().getApplicationContext();
        usuario = application.getUsuario();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                final int serviceResult = intent.getIntExtra("result", -1);


                if (serviceResult == getActivity().RESULT_OK){


                            ArrayList<Livro> livros = (ArrayList<Livro>) intent.getSerializableExtra("data");
                            Log.i(TAG, livros.toString());

                            for (Livro livro : livros) {
                                tdLivros.add(livro);
                                expListView.setAdapter(listAdapter);
                            }

                           // listAdapter.setListData(tdLivros);
                            //listAdapter.notifyDataSetChanged();


                            if (livros == null) {
                                Toast.makeText(getActivity(), "Você não possui livros em sua lista de desejos !", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(), "Seus livros desejados", Toast.LENGTH_SHORT).show();
                            }

                        }
                        }



           //     if (serviceResult == getActivity().RESULT_OK) {
            //        ArrayList<Livro> livros = (ArrayList<Livro>) intent.getSerializableExtra("data");
            //         Log.i(TAG, livros.toString());
            //
            //        for (Livro livro : livros) {
            //            tdLivros.add(livro);
            //            expListView.setAdapter(listAdapter);
            //        }

            //        listAdapter.setListData(tdLivros);
            //       listAdapter.notifyDataSetChanged();


            //       if (livros == null) {
            //            Toast.makeText(getActivity(), "Você não possui livros em sua lista de desejos !", Toast.LENGTH_SHORT).show();

            //       } else {
            //          Toast.makeText(getActivity(), "Seus livros desejados", Toast.LENGTH_SHORT).show();
            //      }

            //  }
            //  }




        };

        return view;

    }

    @Subscribe
    public void OnEvent( Livro livro){
        listAdapter.addNovoLivro(livro);
    }


    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }


    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("ListarLivrosWhishList"));

    }

    @Override
    public void OnCustomClick(View view, Long position) {
        Log.i(TAG, "id:" + position);
        new addBuscarProprietarioLivro().execute(position);
    }



    private class addBuscarProprietarioLivro extends AsyncTask<Long,Void, String> {

        final WebHelper http = new WebHelper();
        Livro livro = null;
        final Gson parser = new Gson();
        String url = "http://"+application.getIp()+"/KisanSERVER/livros/";


        protected String doInBackground(Long... urls) {

            Long id = urls[0];
            String url_m = url + id;

            try {

                final WebResult webResult = http.executeHTTP(url_m, "GET", null);
                if(webResult.getHttpCode() == 200) {

                   livro  = parser.fromJson(webResult.getHttpBody(), Livro.class);
                }

            } catch (IOException e) {
                Log.d(TAG, "Exception calling add service", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(getActivity(), MapsActivity.class);
        //    intent.putExtra("usuario", livro.getUsuario());
            getActivity().startActivity(intent);


        }

    }

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
