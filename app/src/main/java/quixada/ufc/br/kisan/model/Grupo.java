package quixada.ufc.br.kisan.model;

import java.util.ArrayList;

/**
 * Created by andersonuchoa on 16/01/16.
 */
public class Grupo {

    public  String Mnome;
    public ArrayList<String> filhos = new ArrayList<String>();


    public Grupo(String nome){
        Mnome = nome;
    }

}
