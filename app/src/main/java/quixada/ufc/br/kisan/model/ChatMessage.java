package quixada.ufc.br.kisan.model;

/**
 * Created by allef on 13/12/15.
 */
public class ChatMessage {
    public boolean left;
    public String message;

    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }
}