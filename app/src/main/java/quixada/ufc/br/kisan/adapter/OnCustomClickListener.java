package quixada.ufc.br.kisan.adapter;


import android.view.View;

import quixada.ufc.br.kisan.model.Livro;
import quixada.ufc.br.kisan.model.Usuario;

public interface OnCustomClickListener {

    public void OnCustomClick(View view, Long position);
    public void OnCustomClick(View view, Usuario usuario);
    public void OnCustomClick(View view, Livro livro);

}
