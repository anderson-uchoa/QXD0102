package quixada.ufc.br.kisan.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import quixada.ufc.br.kisan.R;
import quixada.ufc.br.kisan.activity.MapsActivity;
import quixada.ufc.br.kisan.adapter.ExpandableListWishAdapter;
import quixada.ufc.br.kisan.adapter.OnCustomClickListener;
import quixada.ufc.br.kisan.application.CustomApplication;
import quixada.ufc.br.kisan.model.Livro;
import quixada.ufc.br.kisan.model.Usuario;
import quixada.ufc.br.kisan.services.ListarLivrosWhishListService;



public class WishListFragment extends Fragment implements OnCustomClickListener {


    private static final String TAG = "WishListFragment";

    ExpandableListWishAdapter listAdapter;
    ExpandableListView expListView;
    CustomApplication application = new CustomApplication();


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


        listAdapter = new ExpandableListWishAdapter(getActivity(), tdLivros, this);
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


                            if (livros == null) {
                                Toast.makeText(getActivity(), "Você não possui livros em sua lista de desejos !", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(), "Seus livros desejados", Toast.LENGTH_SHORT).show();
                            }

                        }
                        }

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

    }

    @Override
    public void OnCustomClick(View view, Usuario usuario) {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        intent.putExtra("usuario", usuario);
        getActivity().startActivity(intent);

    }

    @Override
    public void OnCustomClick(View view, Livro livro) {

    }

}

