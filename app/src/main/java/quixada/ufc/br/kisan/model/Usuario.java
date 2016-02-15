package quixada.ufc.br.kisan.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


public class Usuario implements Serializable {


	public Usuario() {

	}

	public Usuario(Long id, Long id_facebook, String foto, String nome, String email, String cidade) {
		this.id = id;
		this.id_facebook = id_facebook;
		this.foto = foto;
		this.nome = nome;
		this.email = email;
		this.cidade = cidade;

	}

	private Long id;

	private Long id_facebook;

	private String foto;

	private String nome;

	private String email;


	private String cidade;



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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}


	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
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
				", foto='" + foto + '\'' +
				", nome='" + nome + '\'' +
				", email='" + email + '\'' +
				", cidade='" + cidade + '\'' +
				'}';
	}
}


