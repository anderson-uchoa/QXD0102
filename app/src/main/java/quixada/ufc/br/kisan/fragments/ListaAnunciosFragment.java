package quixada.ufc.br.kisan.fragments;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.activity.VisualizarAnuncioActivity;
import quixada.ufc.br.kisan.activity.VisualizarMeusAnunciosActivity;
import quixada.ufc.br.kisan.adapter.ExpandableListAdapter;
import quixada.ufc.br.kisan.adapter.OnCustomClickListener;
import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.model.Livro;
import quixada.ufc.br.kisan.model.Usuario;
import quixada.ufc.br.kisan.services.ListarLivrosService;
import quixada.ufc.br.kisan.services.ListarLivrosWhishListService;
import quixada.ufc.br.kisan.services.WebHelper;
import quixada.ufc.br.kisan.services.WebResult;

public class ListaAnunciosFragment extends Fragment  implements PopupMenu.OnMenuItemClickListener,OnCustomClickListener {
    private static final String TAG = "VisualizarMeusAnunciosActivity";

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    CustomApplication application = new CustomApplication();
    String url = "http://"+application.getIp()+"/KisanSERVER/livros/insereLivroWishList/";


    private ProgressBar progressBar;
    private ArrayList<Livro> tdLivros = new ArrayList<Livro>();
    private BroadcastReceiver broadcastReceiver;
    Usuario usuario = null;

    private Livro livro;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_anuncios, container, false);



        progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
        expListView = (ExpandableListView) view.findViewById(R.id.expandableListView_wishlist);

      if (tdLivros.size() <= 0 ){
           progressBar.setVisibility(view.GONE);
       }else{
          progressBar.setVisibility(View.VISIBLE);
      }


        listAdapter = new ExpandableListAdapter(getActivity(), tdLivros);
        Intent intent = new Intent(getActivity(), ListarLivrosService.class);
        getActivity().startService(intent);


        application = (CustomApplication) getActivity().getApplicationContext();
        usuario = application.getUsuario();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                final int serviceResult = intent.getIntExtra("result", -1);

                if (serviceResult == getActivity().RESULT_OK) {
                    ArrayList<Livro> livros = (ArrayList<Livro>) intent.getSerializableExtra("data");
                  Log.i(TAG, livros.toString());

                    for (Livro livro : livros) {
                        tdLivros.add(livro);
                        expListView.setAdapter(listAdapter);
                    }

                    if (livros == null) {
                        Toast.makeText(getActivity(), "Você não possui livros adicionados!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "Sua lista de livros!", Toast.LENGTH_SHORT).show();
                    }

                }
            }




        };

        onStarClick();


        return view;


    }


    private void onStarClick(){


    }
    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }


    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("ListarLivros"));

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




    @Override
    public void OnCustomClick(View view, int position) {
      livro = new Livro();
      livro.setId((long) position);

        new addLivroWhishlist().execute(Long.valueOf(11));


    }

    private class addLivroWhishlist extends AsyncTask<Long,Void, String>{

        final WebHelper http = new WebHelper();
        Livro novoLivroWishlist = null;
        final Gson parser = new Gson();

        protected String doInBackground(Long... urls) {

            Long id = urls[0];

            String url_m = url + id;

            try {

                final String body = parser.toJson(livro, Livro.class);
                final WebResult webResult = http.executeHTTP(url_m, "POST", body);
                if(webResult.getHttpCode() == 200) {

                    novoLivroWishlist = parser.fromJson(webResult.getHttpBody(), Livro.class);
                }


            } catch (IOException e) {
                Log.d(TAG, "Exception calling add service", e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(getActivity(), ListarLivrosWhishListService.class);
            getActivity().startService(intent);



        }



    }
}
