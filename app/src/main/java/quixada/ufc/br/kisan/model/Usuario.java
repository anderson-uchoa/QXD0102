package quixada.ufc.br.kisan.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


public class Usuario implements Serializable {

	public Usuario() {
	}

	public Usuario(Long id, Long id_facebook, Byte[] foto, String nome, String email, String endereco, String cidade, List<Livro> livros, List<Livro> livrosDesejados) {
		this.id = id;
		this.id_facebook = id_facebook;
		this.foto = foto;
		this.nome = nome;
		this.email = email;
		this.endereco = endereco;
		this.cidade = cidade;
		this.livros = livros;
		this.livrosDesejados = livrosDesejados;
	}

	private Long id;

	private Long id_facebook;

	private Byte[] foto;

	private String nome;

	private String email;

	private String endereco;

	private String cidade;

	private List<Livro> livros;

	private List<Livro> livrosDesejados;


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Byte[] getFoto() {
		return foto;
	}

	public void setFoto(Byte[] foto) {
		this.foto = foto;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public List<Livro> getLivros() {
		return livros;
	}


	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public List<Livro> getLivrosDesejados() {
		return livrosDesejados;
	}

	public void setLivrosDesejados(List<Livro> livrosDesejados) {
		this.livrosDesejados = livrosDesejados;
	}

	public Long getId_facebook() {
		return id_facebook;
	}

	public void setId_facebook(Long id_facebook) {
		this.id_facebook = id_facebook;
	}


	@Override
	public String toString() {
		return "Usuario{" +
				"id=" + id +
				", id_facebook=" + id_facebook +
				", foto=" + Arrays.toString(foto) +
				", nome='" + nome + '\'' +
				", email='" + email + '\'' +
				", endereco='" + endereco + '\'' +
				", cidade='" + cidade + '\'' +
				", livros=" + livros +
				", livrosDesejados=" + livrosDesejados +
				'}';
	}
}


