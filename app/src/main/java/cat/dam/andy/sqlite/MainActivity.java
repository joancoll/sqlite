package cat.dam.andy.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btn_save, btn_delete;
    private EditText et_name;
    private DatabaseHelper databaseHelper;
    private Spinner sp_names;
    private ArrayList<UserName> arrayListUsers;
    private int selectedUserId=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();
        initDatabase();
        initSpinner();
        refreshSpinner();
    }

    void initViews() {
        btn_save = findViewById(R.id.btn_save);
        btn_delete = findViewById(R.id.btn_show);
        et_name = findViewById(R.id.et_name);
        sp_names = findViewById(R.id.sp_names);
    }

    void initListeners() {
        btn_save.setOnClickListener(v -> {
            String name= et_name.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(this, R.string.empty_name, Toast.LENGTH_SHORT).show();
            }
            else {
                databaseHelper.insertUsername(et_name.getText().toString());
                et_name.setText("");
                refreshSpinner();
                Toast.makeText(this, R.string.saved_OK, Toast.LENGTH_SHORT).show();
            }
        });
        btn_delete.setOnClickListener(v -> {
            if (sp_names.getSelectedItem() != null && selectedUserId!=-1) {
                databaseHelper.deleteUsername(selectedUserId);
                refreshSpinner();
                Toast.makeText(this, R.string.deleted_OK + "("+selectedUserId+")", Toast.LENGTH_SHORT).show();
                selectedUserId=-1;
            }
            else {
                Toast.makeText(this, R.string.nothing_to_deleted, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDatabase() {
        databaseHelper = new DatabaseHelper(this);
    }

    private void initSpinner() {
        sp_names.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                selectedUserId = arrayListUsers.get(pos).getId();
                Toast.makeText(MainActivity.this, "Selected: " + parent.getItemAtPosition(pos).toString()+ " ("+selectedUserId+")", Toast.LENGTH_SHORT).show();
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedUserId=-1;
            }

        });
    }

    private void refreshSpinner() {
        arrayListUsers = databaseHelper.getAllUsers();
        String[] arrayListNames = new String[arrayListUsers.size()];
        for (int i = 0; i < arrayListUsers.size(); i++) {
            arrayListNames[i] = arrayListUsers.get(i).getName();
        }
        //layout predefinit de spinner
//        sp_names.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrayListNames));
        //layout personalitzat de spinner
        sp_names.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_item, arrayListNames));
    }
}