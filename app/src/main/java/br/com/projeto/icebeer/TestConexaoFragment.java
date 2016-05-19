package br.com.projeto.icebeer;

import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class TestConexaoFragment extends Fragment {

    private Button btConexao;

    private final String LOG_TAG = TestConexaoFragment.class.getSimpleName();

    public TestConexaoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_conexao, container, false);

        btConexao = (Button) view.findViewById(R.id.btTestar);
        btConexao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                testaConexao(v);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void testaConexao(View v){
        TextView tv = (TextView) getActivity().findViewById(R.id.txtLog);
        tv.setText("Conectando ... ");
        EditText text = (EditText) getActivity().findViewById(R.id.txtUrl);
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String msg = "erro ao tentar conectar";
        try{
            URL url = new URL(text.getText().toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            /* Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            */
            //msg = buffer.toString();
            msg = "Sucesso :-)";
        } catch (IOException e){
            Log.e(LOG_TAG, "erro ao tentar conectar");
        } catch (NetworkOnMainThreadException n){
            Log.e(LOG_TAG, "Erro ao tentar se conectar ",n);
            msg = "Erro ao tentar se conectar";
        }

        tv.setText(msg);
    }
}