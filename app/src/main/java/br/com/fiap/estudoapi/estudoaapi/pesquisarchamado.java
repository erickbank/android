package br.com.fiap.estudoapi.estudoaapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class pesquisarchamado extends AppCompatActivity {
    ListView listaChamado;
    EditText codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisarchamado);
        listaChamado  = (ListView) findViewById(R.id.lvchamados);
        codigo = (EditText) findViewById(R.id.txtcodigof);

    }

    public void pesquisarchamado(View v){
        taskPesquisa task = new taskPesquisa();
        task.execute(codigo.getText().toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.itemCadastrar){

            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }

    class taskPesquisa extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL("https://assistenciaapi.herokuapp.com/rest/chamado/funcionario/" + params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if(conn.getResponseCode() == 200){
                    BufferedReader stream = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String linha ="";
                    StringBuilder resposta = new StringBuilder();
                    while((linha = stream.readLine()) != null){

                        resposta.append(linha);

                        return resposta.toString();
                    }

                }



            }catch(Exception e){
                e.printStackTrace();

            }

            return null;


        }

        @Override
        protected void onPostExecute(String s) {

            if(s != null){
               try  {


                   JSONArray array = new JSONArray(s);

                   List<Item> itens = new ArrayList<Item>();

                   for(int i = 0; i < array.length(); i++){
                       JSONObject item  = (JSONObject) array.get(i);
                       int codigo = item.getInt("codigoFuncionario");
                       String descricao = item.getString("descricao");
                       String datajson = item.getString("data");
                       SimpleDateFormat format  = new SimpleDateFormat("dd/MM/yyyy");
                       Date data = format.parse(datajson);
                       Boolean finalizado = item.getBoolean("finalizado");
                       itens.add(new Item(codigo,descricao,data,finalizado));


                   }

                   ListAdapter adapter  = new ArrayAdapter<Item>(pesquisarchamado.this,android.R.layout.simple_list_item_1,itens);
                   listaChamado.setAdapter(adapter);


               }catch(Exception e){
                  e.printStackTrace();
               }

            }else{

                Toast.makeText(pesquisarchamado.this,"Erro",Toast.LENGTH_LONG).show();
            }

        }
    }
}
