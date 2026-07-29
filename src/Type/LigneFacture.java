package Type;

public class LigneFacture {
    private Produit produit;
    private int quantite;

    public LigneFacture(Produit produit, int quantite) {
        this.produit = produit;
        this.quantite = quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    // calcul total de la ligne
    public double getSousTotal() {
        return produit.getPrix() * quantite;
    }

    @Override
    public String toString() {
        return String.format("%s x %d = %.2f €",
                produit.getDescription(), quantite, getSousTotal());
    }

    //Achiffer des detail pour la facture
    public String toDetailFormat() {
        return String.format("%-30s | Qté: %3d | Prix unitaire: %8.2f € | Sous-total: %10.2f €",
                produit.getDescription(), quantite, produit.getPrix(), getSousTotal());
    }

}


