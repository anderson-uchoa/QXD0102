package quixada.ufc.br.kisan.model;

import java.io.Serializable;
import java.util.List;


public class Autor implements Serializable {

	private static final long serialVersionUID = 1L;


	private Long id;

	private String nome;

	private List<Livro> livros;

	public Autor() {
		// TODO Auto-generated constructor stub
	}

}
