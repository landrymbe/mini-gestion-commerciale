package Type;

public class Produit {
    private int id;
    private String description;
    private double prix;
    public  Produit(int id, String description, double prix){
        this.id = id;
        this.description = description;
        this.prix = prix;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public double getPrix() {return prix;}

    public void setPrix(double prix) {this.prix = prix;}

    // le format CSV
    public String toFileFormat() {
        return id + ";" + description + ";" + prix;
    }
    // POUR Créer les ligne CSV du  produit
    public  static Produit fromFileFormat(String line) {
        String[] parts = line.split(";");
        return  new Produit(Integer.parseInt(parts[0]),
            parts[1],
                Double.parseDouble(parts[2]));
    }
    @Override
    public String toString() {
        return  "Produit{" + "id=" + id +
                ",description'" + description + '\'' +
                ",Prix=" + prix + '}' ;
    }
    // Afficher le format
    public String toDisplayFormat() {
        return  String.format("ID: %d | %s | prix: %.2f £", id,description,prix);
    }
}
