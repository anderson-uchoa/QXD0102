package quixada.ufc.br.kisan.dataBase;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;
import quixada.ufc.br.kisan.chat.ChatMessage;

/**
 * Created by andersonuchoa on 15/02/16.
 */
public class MensagemRealm {

    protected Realm realm;

    public MensagemRealm(Context context) {
        RealmConfiguration realmConfiguration =
                new RealmConfiguration.Builder(context)
                        .deleteRealmIfMigrationNeeded()
                        .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        this.realm = Realm.getDefaultInstance();
    }


    public boolean adicionar(Conversa conversa) {
        try {
            this.realm.beginTransaction();
            conversa.setId(getNextKey());
            realm.copyToRealmOrUpdate(conversa);
            this.realm.commitTransaction();
        } catch (RealmException ex) {
            this.realm.cancelTransaction();
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public List<Conversa> buscar(Long id) {
        RealmResults<Conversa> conversas = this.realm.where(Conversa.class).equalTo("idUsuario2",id).or().equalTo("idUsuario1", id).findAll();
        if (conversas != null) {
            return conversas;
        }
        return null;
    }

    public int getNextKey() {
        return realm.where(Conversa.class).max("id").intValue() + 1;
    }

    public void close() {
        this.realm.close();
    }


}
