package quixada.ufc.br.kisan.chat;

import io.realm.RealmObject;

public class ChatMessage extends RealmObject{
	private boolean left;
	private String message;
	private String token;
	private String nomeUsuario;
	private String idFacebook;


	public ChatMessage(){}

	public ChatMessage(boolean left, String message) {
		super();
		this.left = left;
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getIdFacebook() {
		return idFacebook;
	}

	public void setIdFacebook(String idFacebook) {
		this.idFacebook = idFacebook;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}