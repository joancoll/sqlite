package cat.dam.andy.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues; import android.content.Context;
import android.database.Cursor; import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper; import android.util.Log;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String NOM_BD = "bd_alumnes";
    private static final int VERSIO_BD = 1;
    private static final String TAULA_ALUMNES = "alumnes";
    private static final String CLAU_ID = "id";
    private static final String CLAU_NOM = "nom";
    // Utilitza un String parametritzat per a la instrucció que crea de la taula alumnes
    private static final String CREA_TAULA_ALUMNES = "CREATE TABLE "
            + TAULA_ALUMNES + "(" + CLAU_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + CLAU_NOM + " TEXT );";
    public DatabaseHelper(Context context) {
        super(context, NOM_BD, null, VERSIO_BD);
        Log.d("table", CREA_TAULA_ALUMNES);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //quan es crea l'objecte a partir de la classe crearà la taula si no està creada
        db.execSQL(CREA_TAULA_ALUMNES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int versio_antiga, int versio_nova) {
        db.execSQL("DROP TABLE IF EXISTS '" + TAULA_ALUMNES + "'");
        onCreate(db);
    }
    public void afegeix_detallAlumne(String alumne) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Crea valors de contingut
        ContentValues valors = new ContentValues();
        valors.put(CLAU_NOM, alumne);
        // inserta una fila a la taula alumnes
        long insert = db.insert(TAULA_ALUMNES, null, valors);
    }
    @SuppressLint("Range")
    public ArrayList<String> obte_llistaAlumnes() {
        ArrayList<String> arrayList_Alumnes = new ArrayList<>();
        String nom;
        String selectQuery = "SELECT  * FROM " + TAULA_ALUMNES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // Recorre totes les files i les afegeix a la llista
        if (c.moveToFirst()) {
            do {
                nom = c.getString(c.getColumnIndex(CLAU_NOM));
                // afegeix a la llista d'alumnes
                arrayList_Alumnes.add(nom);
            } while (c.moveToNext());
            Log.d("array", arrayList_Alumnes.toString());
        }
        return arrayList_Alumnes;
    }
}
