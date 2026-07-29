package Type;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GestionFichier {
    private static final String FICHIER_CLIENTS = "clients.csv";
    private static final String FICHIER_PRODUITS = "produits.csv";
    private static final String FICHIER_FACTURES = "factures.csv";

    // l'étape clients

    public static void saveClients(List<Client> clients) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_CLIENTS))) {
            for (Client client : clients) {
                writer.write(client.toFileFormat());
                writer.newLine();
            }
            System.out.println("✓ Clients sauvegardés avec succès !!");
        } catch (IOException e) {
            System.err.println("✗ Erreur lors de la sauvegarde des clients : " + e.getMessage());
        }
    }

    public static List<Client> loadClients() {
        List<Client> clients = new ArrayList<>();
        File file = new File(FICHIER_CLIENTS);

        if (!file.exists()) {
            System.out.println("⚠ Fichier clients.csv non trouvé. Création d'une liste vide.");
            return clients;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_CLIENTS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    clients.add(Client.fromFileFormat(line));
                } catch (Exception e) {
                    System.err.println("✗ Erreur lors de la lecture d'une ligne client : " + line);
                }
            }
            System.out.println("✓ " + clients.size() + " clients chargés avec succès !!");
        } catch (IOException e) {
            System.err.println("✗ Erreur lors du chargement des clients : " + e.getMessage());
        }
        return clients;
    }

    // l'etape de produits

    public static void saveProduits(List<Produit> Produits) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_PRODUITS))) {
            for (Produit produit : Produits) {
                writer.write(produit.toFileFormat());
                writer.newLine();
            }
            System.out.println("✓ Produits sauvegardés avec succès !");
        } catch (IOException e) {
            System.err.println("✗ Erreur lors de la sauvegarde des produits : " + e.getMessage());
        }
    }

    public static List<Produit> loadProduits() {
        List<Produit> produits = new ArrayList<>();
        File file = new File(FICHIER_PRODUITS);

        if (!file.exists()) {
            System.out.println("⚠ Fichier produits.csv non trouvé. Création d'une liste vide.");
            return produits;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_PRODUITS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    produits.add(Produit.fromFileFormat(line));
                } catch (Exception e) {
                    System.err.println("✗ Erreur lors de la lecture d'une ligne produit : " + line);
                }
            }
            System.out.println("✓ " + produits.size() + " produits chargés avec succès !!");
        } catch (IOException e) {
            System.err.println("✗ Erreur lors du chargement des produits : " + e.getMessage());
        }

        return produits;
    }
    // l'etape facture

    public static void saveFactures(List<Facture> factures) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_FACTURES))) {
            for (Facture facture : factures) {
                writer.write(facture.toFileFormat());
                writer.newLine();
            }
            System.out.println("✓ Factures sauvegardées avec succès !!");
        } catch (IOException e) {
            System.err.println("✗ Erreur lors de la sauvegarde des factures : " + e.getMessage());
        }
    }

    public static List<Facture> loadFactures(List<Client> clients, List<Produit> produits) {
        List<Facture> factures = new ArrayList<>();
        File file = new File(FICHIER_FACTURES);

        if (!file.exists()) {
            System.out.println("⚠ Fichier factures.csv non trouvé. Création d'une liste vide.");
            return factures;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_FACTURES))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    // Format: idfacture;datefacture;idclient;idsproduits|quantites
                    String[] parts = line.split(";");

                    int idFacture = Integer.parseInt(parts[0]);
                    LocalDate date = LocalDate.parse(parts[1], formatter);
                    int idClient = Integer.parseInt(parts[2]);

                    // Trouve le client

                    Client client = findClientById(clients, idClient);
                    if (client == null) {
                        System.err.println("✗ Client introuvable pour la facture " + idFacture);
                        continue;
                    }

                    Facture facture = new Facture(idFacture, date, client);

                    // Parser les produits et quantités

                    if (parts.length > 3 && !parts[3].isEmpty()) {
                        String[] produitsQuantites = parts[3].split(",");
                        for (String pq : produitsQuantites) {
                            String[] pqParts = pq.split("\\|");
                            int idProduit = Integer.parseInt(pqParts[0]);
                            int quantite = Integer.parseInt(pqParts[1]);

                            Produit produit = findProduitById(produits, idProduit);
                            if (produit != null) {
                                facture.ajouterLigne(produit, quantite);
                            } else {
                                System.err.println("✗ Produit " + idProduit + " introuvable");
                            }
                        }
                    }
                    factures.add(facture);
                } catch (Exception e) {
                    System.err.println("✗ Erreur lors de la lecture d'une ligne facture : " + line);
                    e.printStackTrace();
                }
            }
            System.out.println("✓ " + factures.size() + " factures chargées avec succès !");
        } catch (IOException e) {
            System.err.println("✗ Erreur lors du chargement des factures : " + e.getMessage());
        }

        return factures;
    }

    // methodes utilitaires
    private static Client findClientById(List<Client> clients, int id) {
        for (Client client : clients) {
            if (client.getId() == id) {
                return client;
            }
        }
        return null;
    }

    private static Produit findProduitById(List<Produit> produits, int id) {
        for (Produit produit : produits) {
            if (produit.getId() == id) {
                return produit;
            }
        }
        return null;
    }
}