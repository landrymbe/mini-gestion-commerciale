package Type;
public class Client {
    private int id;
    private String nom;
    private String Prenom;
    private String email;
    private String adresse;
    private  String codePostal;
    private String ville;
public Client(int id,String nom, String prenom, String email, String adresse, String codePostal, String ville){
    this.id = id;
    this.nom = nom;
    this.Prenom = prenom;
    this .email = email;
    this.adresse = adresse;
    this.codePostal = codePostal;
    this.ville = ville;
}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getNom() {return nom;}

    public void setNom(String nom) {this.nom = nom;}

    public String getPrenom() {return Prenom;}

    public void setPrenom(String prenom) {Prenom = prenom;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getAdresse() {return adresse;}

    public String getCodePostal() {return codePostal;}

    public void setCodePostal(String codePostal) {this.codePostal = codePostal;}

    public String getVille() {return ville;}

    public void setVille(String ville) {this.ville = ville;}

    // POUR Créer le format CSV
public String toFileFormat() {
    return id + ";" + nom + ";" + Prenom + ";" + email + ";" + adresse + ";" + codePostal +";" + ville; }

    // créer un client depuis une ligne CSV
public static Client fromFileFormat(String  line) {
    String[] parts = line.split(";");
    return new Client(
            Integer.parseInt(parts[0]),
            parts[1],
            parts[2],
            parts[3],
            parts[4],
            parts[5],
            parts[6]
    );}
    @Override
    public String toString() {
        return "Client{" + "id" + id + ",nom='" + nom + '\'' +
                ",prenom='" + Prenom + '\'' +
                ", email='" + email + '\'' +
                ", adresse='" + adresse + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
    //AFFichage
    public String toDisplayFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id);
        sb.append(" | ").append(Prenom).append(" ").append(nom);
        sb.append(" | Email: ").append(email);
        sb.append(" | ").append(adresse);
        sb.append(", ").append(codePostal).append(" ").append(ville);
        return sb.toString();
    }
}

