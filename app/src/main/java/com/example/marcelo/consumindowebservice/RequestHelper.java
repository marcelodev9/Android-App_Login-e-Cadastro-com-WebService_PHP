package com.example.marcelo.consumindowebservice;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

/**
 * Created by MARCELO on 12/04/2017.
 */

/* Classe com modificador final (não permite heranças) */
public final class RequestHelper {

    private Context cxt;
    private String url;
    private IRequests callback;

    /* Método construtor publico (permiten novas instancias) */
    public RequestHelper(Context myCxt, String url, IRequests iRequests){
        this.cxt = myCxt;
        this.url = url;
        this.callback = iRequests;
    }


    public void execRequest(){
        /* Executa o método pre (antes da requisicao) do callback implementado durante o instanciamento da classe */
        callback.Pre();
        RequestQueue req = Volley.newRequestQueue(cxt);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, this.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    /* Executa o método pos (resposta da requisicao) do callback implementando durante o instanciamento da classe */
                    callback.Pos(jsonObject);
                } catch (JSONException e) {
                    Log.i("JSONException", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.Error();
                Log.i("onErrorResponse", error.toString());
            }
        }) {
            public HashMap getParams() {
                /* Executa o método do callback que retorna uma objeto hashmap */
                return callback.params();
            }
        };
        req.add(stringRequest);
    }

    /* Interface que funcionara como callback */
    public interface IRequests{

        public void Pre();
        public void Pos(JSONObject jsonObject) throws JSONException;
        public void Error();
        public HashMap<String, String> params();
    }
}
