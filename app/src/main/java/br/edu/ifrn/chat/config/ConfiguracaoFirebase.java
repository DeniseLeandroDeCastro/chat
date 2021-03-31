package br.edu.ifrn.chat.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfiguracaoFirebase {

    private static DatabaseReference database;
    private static FirebaseAuth auth;
    private static StorageReference storage;

    //Método para retornar a instância do FirebaseDatabase
    public static DatabaseReference getFirebaseDatabase() {
        if(database == null) {
            database = FirebaseDatabase.getInstance().getReference();
            //O objeto database vai permitir gerenciar o banco de dados
        }
        return database;
    }

    //Método para retornar a instância do FirebaseAuth
    public static FirebaseAuth getFirebaseAutenticacao() {
        if(auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
    //Para salvar no storage
    public static StorageReference getFirebaseStorage() {
        if(storage == null) {
            storage = FirebaseStorage
                    .getInstance()
                    .getReference();
        }
        return storage;
    }
}
