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

import java.io.IOException;
import java.util.ArrayList;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.Util.CaminhosWebService;
import quixada.ufc.br.kisan.adapter.ExpandableListAnunciosAdapter;
import quixada.ufc.br.kisan.adapter.OnCustomClickListener;
import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.model.Livro;
import quixada.ufc.br.kisan.model.Usuario;
import quixada.ufc.br.kisan.services.ListarLivrosService;
import quixada.ufc.br.kisan.services.WebHelper;
import quixada.ufc.br.kisan.services.WebResult;

public class ListaAnunciosFragment extends Fragment  implements OnCustomClickListener {
    private static final String TAG = "ListaAnunciosFragment";


    String url = "http://"+ CaminhosWebService.IP+"/KisanSERVER/livros/insereLivroWishList/";

    ExpandableListAnunciosAdapter listAdapter;
    ExpandableListView expListView;
    private ProgressBar progressBar;
    private ArrayList<Livro> tdLivros = new ArrayList<Livro>();
    private BroadcastReceiver broadcastReceiver;

   private Usuario usuario = null;

    private Livro livro;
    private long id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_anuncios, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
        expListView = (ExpandableListView) view.findViewById(R.id.expandableListView_wishlist);

        CustomApplication  application = (CustomApplication) getActivity().getApplication();
        usuario = application.getUsuario();
        id = usuario.getId();

        Log.i( TAG, usuario.getId().toString());



        if (tdLivros.size() <= 0 ){
           progressBar.setVisibility(view.GONE);
       }else{
          progressBar.setVisibility(View.VISIBLE);
      }


        listAdapter = new ExpandableListAnunciosAdapter(getActivity(), tdLivros, this);
        Intent intent = new Intent(getActivity(), ListarLivrosService.class);
        getActivity().startService(intent);


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

        return view;

    }




    @Override
    public void OnCustomClick(View view, Long position) {
      livro = new Livro();
      livro.setId( position);

        Log.i(TAG, "usuario:" + id);
        new addLivroWhishlist().execute(id);

    }

    @Override
    public void OnCustomClick(View view, Usuario usuario) {

    }

    @Override
    public void OnCustomClick(View view, Livro livro) {

    }

    private class addLivroWhishlist extends AsyncTask<Long,Void, Livro>{

        final WebHelper http = new WebHelper();
        Livro novoLivroWishlist = null;
        final Gson parser = new Gson();

        protected Livro doInBackground(Long... urls) {

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

            return novoLivroWishlist;
        }

        @Override
        protected void onPostExecute(Livro s) {
            super.onPostExecute(s);
            if (s == null){

                Toast.makeText(getActivity(), "Livro já esta na sua WishList!", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getActivity(), "Livro adicionado na WishList!", Toast.LENGTH_SHORT).show();

                EventBus.getDefault().post(s);
            }






        }



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
}
