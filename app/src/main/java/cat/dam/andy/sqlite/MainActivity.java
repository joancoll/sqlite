package cat.dam.andy.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
        initViews();
        initListeners();
        initDatabase();
    }

    void initViews() {
        btn_desa = findViewById(R.id.btn_desa);
        btn_mostra = findViewById(R.id.btn_mostra);
        et_nom = findViewById(R.id.et_nom);
        tv_noms = findViewById(R.id.tv_noms);
    }

    void initListeners() {
        btn_desa.setOnClickListener(v -> {
            databaseHelper.afegeix_detallAlumne(et_nom.getText().toString());
            et_nom.setText("");
            Toast.makeText(MainActivity.this, "S'ha desat correctament!", Toast.LENGTH_SHORT).show();
        });
        btn_mostra.setOnClickListener(v -> {
            arrayList = databaseHelper.obte_llistaAlumnes();
            tv_noms.setText("");
            for (int i = 0; i < arrayList.size(); i++) {
                tv_noms.setText(tv_noms.getText().toString() + " -" + arrayList.get(i));
            }
        });
    }

    private void initDatabase() {
        databaseHelper = new DatabaseHelper(this);
    }
}