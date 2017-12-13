package com.example.marcelo.consumindowebservice.Atividades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.marcelo.consumindowebservice.RequestHelper;
import com.example.marcelo.consumindowebservice.Constantes;
import com.example.marcelo.consumindowebservice.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    /* Declara as variaveis de referencias dos componentes */
    private EditText etUsuario;
    private EditText etSenha;
    private Button btnRegistrar;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);

        /* Busca as referencias dos componentes que estão na view e as atribui nas variaveis declaradas */
        etUsuario = (EditText) findViewById(R.id.etCelular);
        etSenha = (EditText) findViewById(R.id.etSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);

        super.getSupportActionBar().setTitle("Login");

        /* Atrela um método no evento de click do botão, implementando a interface OnClickListener e a passando a como parametro */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String celular = etUsuario.getText().toString();
                String senha = etSenha.getText().toString();
                if(!celular.equals("") && !senha.equals("")) {
                    loginUsuario(etUsuario.getText().toString(), etSenha.getText().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Não deixe nenhum campo em branco!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrarActivity.class));
            }
        });

    }


    /* Método fara a requisição ao webservice para confirmar o login  */
    public void loginUsuario(final String celular, final String senha) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        RequestHelper req = new RequestHelper(this, Constantes.urlLogin, new RequestHelper.IRequests() {
            @Override
            public void Pre() {
                progressDialog.setMessage("Por favor, aguarde...");
                progressDialog.show();
            }
            @Override
            public void Pos(JSONObject jsonObject) throws JSONException {
                progressDialog.dismiss();
                    if(jsonObject.getInt("status_requisicao") == 1){
                        Toast.makeText(getApplicationContext(), "Login realizado com sucesso!", Toast.LENGTH_LONG).show();
                        //Recupera o nome do usuario que efetuou o login (retorno do webservice)
                        String nome = new JSONObject(jsonObject.getString("retorno")).getString("_Nome");
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("nome", nome);
                        startActivity(intent);
                        finish();
                    }
                    else if(jsonObject.getInt("status_requisicao") == 2){
                        Toast.makeText(getApplicationContext(), "Celular e/ou senha invalido(s)", Toast.LENGTH_LONG).show();
                    }
                  else{
                        Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                    }
                }
            @Override
            public void Error() {
               progressDialog.dismiss();
               Toast.makeText(getApplicationContext(), "Não foi possivel realizar o procedimento, verifique sua conexão!", Toast.LENGTH_LONG).show();
            }
            @Override
            public HashMap<String, String> params() {
                HashMap hashMap = new HashMap();
                hashMap.put("celular", celular);
                hashMap.put("senha", senha);
                return hashMap;
            }
        });
        req.execRequest();
    }

}
