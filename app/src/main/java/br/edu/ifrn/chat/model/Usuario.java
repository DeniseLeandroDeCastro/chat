package br.edu.ifrn.chat.model;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

import br.edu.ifrn.chat.config.ConfiguracaoFirebase;
import br.edu.ifrn.chat.helper.UsuarioFirebase;

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private String foto;

    public Usuario() { }

    public void atualizar() {
        String identificadorUsuario = UsuarioFirebase.getIdentificadorUsuario();
        DatabaseReference database = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference usuariosRef = database.child("usuarios")
                .child(identificadorUsuario);

        Map<String, Object> valoresUsuario = converterParaMap();
        usuariosRef.updateChildren(valoresUsuario);
    }
    public Map<String, Object> converterParaMap() {
        HashMap<String, Object> usuarioMap = new HashMap<>();
        usuarioMap.put("Id", getId());
        return usuarioMap;
    }

    public void salvar() {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference usuario = firebaseRef
                .child("usuarios")
                .child(getId());
        usuario.setValue(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {

        this.foto = foto;
    }
}
