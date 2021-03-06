package br.edu.ifrn.chat.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import br.edu.ifrn.chat.R;
import br.edu.ifrn.chat.config.ConfiguracaoFirebase;
import br.edu.ifrn.chat.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText campoEmail, campoSenha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        campoEmail  = findViewById(R.id.editLoginEmail);
        campoSenha  = findViewById(R.id.editLoginSenha);
    }
    public void logarUsuario(Usuario usuario) {
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    abrirTelaPrincipal();
                } else {
                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch(FirebaseAuthInvalidUserException e) {
                        excecao = "Usu??rio(a) n??o est?? cadastrado(a)!";
                    } catch(FirebaseAuthInvalidCredentialsException e) {
                        excecao = "E-mail e senha n??o correspondem ao usu??rio!";
                    } catch(Exception e) {
                        excecao = "Erro ao cadastrar usu??rio: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * M??todo (onClick) aplicado
     * ao bot??o Logar
     */
    public void validarAutenticacaoUsuario(View view) {
        //Recuperar textos dos campos
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        //Validar se e-mail e senha foram digitados
            if(!textoEmail.isEmpty()) { //Verifica o e-mail
                if(!textoSenha.isEmpty()) { //Verifica a senha

                    Usuario usuario = new Usuario();
                    usuario.setEmail(textoEmail);
                    usuario.setSenha(textoSenha);

                    logarUsuario(usuario);

                } else {
                    Toast.makeText(LoginActivity.this,
                            "Preencha a senha!",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this,
                        "Preencha o e-mail!",
                        Toast.LENGTH_SHORT).show();
            }
        }

    /**
     * M??todo que vai direcionar o usu??rio
     * para a tela principal se ele j??
     * estiver logado.
     */
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();
        if(usuarioAtual != null) {
            abrirTelaPrincipal();
        }
    }

    /**
     * M??todo utilizado para abrir a tela de cadastro
     * quando o usu??rio clicar no texto 'N??o tem
     * conta? Cadastre-se'
     */
    public void abrirTelaCadastro(View view) {
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }

    /**
     * M??todo para direcionar o usu??rio para
     * a tela principal quando estiver logado
     */
    public void abrirTelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}