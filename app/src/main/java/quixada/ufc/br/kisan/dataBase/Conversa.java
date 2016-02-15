package quixada.ufc.br.kisan.dataBase;

import io.realm.RealmList;
import io.realm.RealmObject;
import quixada.ufc.br.kisan.chat.ChatMessage;

/**
 * Created by andersonuchoa on 15/02/16.
 */
public class Conversa extends RealmObject {

    private int id;
    private int idUsuario1;
    private int idUsuario2;
    private String nome1;
    private String nome2;

    private RealmList<ChatMessage> chatMessages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario1() {
        return idUsuario1;
    }

    public void setIdUsuario1(int idUsuario1) {
        this.idUsuario1 = idUsuario1;
    }

    public int getIdUsuario2() {
        return idUsuario2;
    }

    public void setIdUsuario2(int idUsuario2) {
        this.idUsuario2 = idUsuario2;
    }

    public RealmList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(RealmList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public String getNome1() {
        return nome1;
    }

    public void setNome1(String nome1) {
        this.nome1 = nome1;
    }

    public String getNome2() {
        return nome2;
    }

    public void setNome2(String nome2) {
        this.nome2 = nome2;
    }
}
