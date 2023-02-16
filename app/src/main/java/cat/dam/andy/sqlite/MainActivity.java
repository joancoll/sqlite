package cat.dam.andy.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btn_desa, btn_mostra;
    private EditText et_nom;
    private DatabaseHelper databaseHelper;
    private TextView tv_noms;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        tv_noms = (TextView) findViewById(R.id.tv_noms);
        btn_desa = (Button) findViewById(R.id.btn_desa);
        btn_mostra = (Button) findViewById(R.id.btn_mostra);
        et_nom = (EditText) findViewById(R.id.et_nom);

        btn_desa.setOnClickListener(v -> {
            databaseHelper.afegeix_detallAlumne(et_nom.getText().toString());
            et_nom.setText("");
            Toast.makeText(MainActivity.this, "S'ha desat correctament!", Toast.LENGTH_SHORT).show();
        });
        btn_mostra.setOnClickListener(v -> {
            arrayList = databaseHelper.obte_llistaAlumnes();
            tv_noms.setText("");
            for (int i = 0; i < arrayList.size(); i++){
                tv_noms.setText(tv_noms.getText().toString()+" -"+arrayList.get(i));
            }
        });
    }
}