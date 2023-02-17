package cat.dam.andy.sqlite;

import android.content.ContentValues; import android.content.Context;
import android.database.Cursor; import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper; import android.util.Log;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "db_names";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "NAMES";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "nom";

    // Utilitza un String parametritzat per a la instrucció que crea de la taula names
    private static final String SQL_TABLE_CREATION = "CREATE TABLE "
            + TABLE_NAME + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT );";
    //constructor
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d("Table:", SQL_TABLE_CREATION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //quan es crea l'objecte a partir de la classe crearà la taula si no està creada
        db.execSQL(SQL_TABLE_CREATION);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int db_version_old, int db_version_new) {
        //si hi ha canvis a la versió de la base de dades esborra la taula i la torna a crear
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");
        onCreate(db);
    }

    //MÈTODES PER A LA BASE DE DADES
    
    //inserta un nou registre a la taula
    public void insertUsername(String name) {
        // Obre la base de dades en mode escriptura i inserta nou registre
        SQLiteDatabase db = this.getWritableDatabase();
        // Crea valors de contingut
        ContentValues valors = new ContentValues();
        valors.put(KEY_NAME, name);
        // inserta una fila a la taula names
        db.insert(TABLE_NAME, null, valors);
        //no cal tancar la base de dades, ja que es fa automàticament
    }
    
    //retorna tots els noms de la taula
    public ArrayList<String> getAllUsernames() {
        ArrayList<String> arrayList = new ArrayList<>();
        String nom;
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // Recorre totes les files i les afegeix a la llista
        if (c.moveToFirst()) {
            do {
                nom = c.getString(c.getColumnIndexOrThrow(KEY_NAME));
                // afegeix a la llista de noms
                arrayList.add(nom);
            } while (c.moveToNext());
            Log.d("array", arrayList.toString());
        }
        c.close(); //tanca el cursor
        //no cal tancar la base de dades, ja que es fa automàticament
        return arrayList;
    }
}
