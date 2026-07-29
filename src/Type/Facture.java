package Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Facture {
    private int id;
    private LocalDate dateFacture;
    private Client client;
    private List<LigneFacture> lignes;


    public Facture(int id, LocalDate dateFacture, Client client) {
        this.id = id;
        this.dateFacture = dateFacture;
        this.client = client;
        this.lignes = new ArrayList<>();
    }

    public Facture(LocalDate dateFacture, Client client) {
        this.dateFacture = dateFacture;
        this.client = client;
        this.lignes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(LocalDate dateFacture) {
        this.dateFacture = dateFacture;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<LigneFacture> getLignes() {
        return lignes;
    }

    // Ajouter un produit a la facture
    public void ajouterLigne(Produit produit, int quantite) {
        lignes.add(new LigneFacture(produit, quantite));
    }

    // total de la facture
    public double getTotal() {
        double total = 0;
        for (LigneFacture ligne : lignes) {
            total += ligne.getSousTotal();  // ← getSousTotal(), pas getTotal()
        }
        return total;
    }

    // construire le fichier format CSV
    public String toFileFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(";");
        sb.append(dateFacture.format(formatter)).append(";");
        sb.append(client.getId()).append(";");

        //  la liste des produits et les quantités
        for (int i = 0; i < lignes.size(); i++) {
            LigneFacture ligne = lignes.get(i);
            sb.append(ligne.getProduit().getId())
                    .append("|")
                    .append(ligne.getQuantite());
            if (i < lignes.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return "Facture n°" + id + " du " + dateFacture +
                " - Client: " + client.getNom() + " " + client.getPrenom() +
                " - Total: " + String.format("%.2f", getTotal()) + " €";
    }

    // Affichage les détaillé de la facture

    public String toDetailFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== FACTURE N°").append(id).append(" ==========\n");
        sb.append("Date: ").append(dateFacture).append("\n\n");
        sb.append("CLIENT:\n");
        sb.append("  ").append(client.getPrenom()).append(" ").append(client.getNom()).append("\n");
        sb.append("  ").append(client.getAdresse()).append("\n");
        sb.append("  ").append(client.getCodePostal()).append(" ").append(client.getVille()).append("\n");
        sb.append("  Email: ").append(client.getEmail()).append("\n\n");
        sb.append("DÉTAIL:\n");
        sb.append("-".repeat(80)).append("\n");

        for (LigneFacture ligne : lignes) {
            sb.append(ligne.toDetailFormat()).append("\n");
        }

        sb.append("-".repeat(80)).append("\n");
        sb.append(String.format("TOTAL: %.2f €\n", getTotal()));
        sb.append("=".repeat(40)).append("\n");

        return sb.toString();
    }
}