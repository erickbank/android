package br.com.fiap.estudoapi.estudoaapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONStringer;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
   private EditText codigo;
    private Spinner descricao;
    private CheckBox status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codigo = (EditText) findViewById(R.id.txtcodigo);
        descricao = (Spinner)findViewById(R.id.spntipo);
        status = (CheckBox) findViewById(R.id.ckbfinalizado);
    }

    public void Cadastrar(View v){
          taskchamado task = new taskchamado();
          task.execute(codigo.getText().toString(),descricao.getSelectedItem().toString(),String.valueOf(status.isChecked()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.itemChamados){

            Intent intent = new Intent(this,pesquisarchamado.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    class taskchamado extends AsyncTask<String,Void,Integer>{
        @Override
        protected Integer doInBackground(String... params) {

            try {
              URL url = new URL("https://assistenciaapi.herokuapp.com/rest/chamado/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                JSONStringer json = new JSONStringer();
                json.object();
                json.key("codigoFuncionario").value(params[0]);
                json.key("descricao").value(params[1]);
                json.key("finalizado").value(params[2]);
                json.endObject();

                OutputStreamWriter stream = new OutputStreamWriter(conn.getOutputStream());
                stream.write(json.toString());
                stream.close();

                return conn.getResponseCode();

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Integer s) {
             if(s == 201){
                 Toast.makeText(MainActivity.this,"Cadastro Sucesso", Toast.LENGTH_LONG).show();

             }else{
                 Toast.makeText(MainActivity.this,"Erro", Toast.LENGTH_LONG).show();
             }

            super.onPostExecute(s);
        }
    }


}
