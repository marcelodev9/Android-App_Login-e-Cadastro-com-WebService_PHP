package com.example.marcelo.consumindowebservice.Atividades;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

public class RegistrarActivity extends AppCompatActivity {

    /* Declara as variaveis de referencias dos componentes */
    private EditText etNome;
    private EditText etCelular;
    private EditText etSenha;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        /* Busca as referencias dos componentes que estão na view e as atribui nas variaveis declaradas */
        etCelular = (EditText) findViewById(R.id.etCelular);
        etSenha = (EditText) findViewById(R.id.etSenha);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        etNome = (EditText) findViewById(R.id.etNome);

        /* Atribui informações a actionbar */
        super.getSupportActionBar().setTitle("Registrar");
        super.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etNome.getText().toString();
                String celular = etCelular.getText().toString();
                String senha = etSenha.getText().toString();
                if(!nome.equals("") && !celular.equals("") && !senha.equals("")) {
                    registrarUsuario(nome, celular, senha);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Não deixe nenhum campo em branco!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    /* Método fara a requisição ao webservice para registrtar o usuario */
    public void registrarUsuario(final String nome, final String celular, final String senha){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        RequestHelper req = new RequestHelper(this, Constantes.urlRegistrar, new RequestHelper.IRequests() {
            @Override
            public void Pre() {
             progressDialog.setMessage("Por favor, aguarde...");
             progressDialog.show();
            }
            @Override
            public void Pos(JSONObject jsonObject) throws JSONException {
                progressDialog.dismiss();
             if(jsonObject.getInt("status_requisicao") == 1){
                 limparCampos();
                 Toast.makeText(getApplicationContext(), "Registrado com sucesso!", Toast.LENGTH_LONG).show();
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
                hashMap.put("nome", nome);
                hashMap.put("celular", celular);
                hashMap.put("senha", senha);
                return hashMap;
            }
        });
        req.execRequest();
    }

    /* Método limpa os campos dos componentes edittext */
    public void limparCampos(){
        etNome.setText("");
        etCelular.setText("");
        etSenha.setText("");
    }
}
