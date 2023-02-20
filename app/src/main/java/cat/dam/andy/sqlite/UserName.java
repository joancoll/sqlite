package cat.dam.andy.sqlite;

public class UserName {
    private int id;
    private String name;

    public UserName(int id, String nom) {
        this.id = id;
        this.name = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
