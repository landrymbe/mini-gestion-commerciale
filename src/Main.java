import Type.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Type.GestionFacturesPDF.*;

public class Main {
    private static List<Client> clients = new ArrayList<>();
    private static List<Produit> produits = new ArrayList<>();
    private static List<Facture> factures = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("*   MINI GESTION COMMERCIALE - VERSION  XL   *");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println();

        // Charger les données depuis les fichiers
        chargerDonnees();

        boolean quitter = false;
        while (!quitter) {
            afficherMenuPrincipal();
            int choix = lireEntier("Votre choix: ");

            switch (choix) {
                case 1:
                    menuClients();
                    break;
                case 2:
                    menuProduits();
                    break;
                case 3:
                    menuFactures();
                    break;
                case 0:
                    quitter = true;
                    sauvegarderDonnees();
                    System.out.println("\n✓ AU REVOIR !! ");
                    break;
                default:
                    System.out.println(" X Choix invalide !");
            }
        }

        scanner.close();
    }

    // MENU
    private static void afficherMenuPrincipal() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("               MENU PRINCIPAL");
        System.out.println("=".repeat(50));
        System.out.println("1. Gestion des clients");
        System.out.println("2. Gestion des produits");
        System.out.println("3. Gestion des factures");
        System.out.println("0. Quitter");
        System.out.println("=".repeat(50));
    }
// CLIENT
    private static void menuClients() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("           GESTION DES CLIENTS");
            System.out.println("-".repeat(50));
            System.out.println("1. Ajouter un client");
            System.out.println("2. Afficher tous les clients");
            System.out.println("3. Rechercher un client par ID");
            System.out.println("4. Supprimer un client");
            System.out.println("0. Retour au menu principal");
            System.out.println("-".repeat(50));

            int choix = lireEntier("Votre choix: ");

            switch (choix) {
                case 1:
                    ajouterClient();
                    break;
                case 2:
                    afficherClients();
                    break;
                case 3:
                    rechercherClient();
                    break;
                case 4:
                    supprimerClient();
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println("✗ Choix invalide !");
            }
        }
    }
    // FACTURE
    private static void menuProduits() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("           GESTION DES PRODUITS");
            System.out.println("-".repeat(50));
            System.out.println("1. Ajouter un produit");
            System.out.println("2. Afficher tous les produits");
            System.out.println("3. Rechercher un produit par ID");
            System.out.println("4. Supprimer un produit");
            System.out.println("0. Retour au menu principal");
            System.out.println("-".repeat(50));

            int choix = lireEntier("Votre choix: ");

            switch (choix) {
                case 1:
                    ajouterProduit();
                    break;
                case 2:
                    afficherProduits();
                    break;
                case 3:
                    rechercherProduit();
                    break;
                case 4:
                    supprimerProduit();
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println("✗ Choix invalide !");
            }
        }
    }
// FACTURES
    private static void menuFactures() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n" + "-".repeat(50));
            System.out.println("           GESTION DES FACTURES");
            System.out.println("-".repeat(50));
            System.out.println("1. Créer une nouvelle facture");
            System.out.println("2. Afficher toutes les factures");
            System.out.println("3. Afficher le détail d'une facture");
            System.out.println("4. Générer un PDF d'une facture");
            System.out.println("5. Supprimer une facture");
            System.out.println("0. Retour au menu principal");
            System.out.println("-".repeat(50));

            int choix = lireEntier("Votre choix: ");

            switch (choix) {
                case 1:
                    creerFacture();
                    break;
                case 2:
                    afficherFactures();
                    break;
                case 3:
                    afficherDetailFacture();
                    break;
                case 4:
                    genererPDFFacture();
                    break;
                case 5:
                    supprimerFacture();
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println("✗ Choix invalide !");
            }
        }
    }

    //  **GESTION CLIENTS

    private static void ajouterClient() {
        System.out.println("\n ***Ajout d'un nouveau client ***");
        scanner.nextLine();

        String nom = lireTexte("Nom: ");
        String prenom = lireTexte("Prénom: ");
        String email = lireTexte("Email: ");
        String adresse = lireTexte("Adresse: ");
        String codePostal = lireTexte("Code postal: ");
        String ville = lireTexte("Ville: ");

        int nouvelId = obtenirNouvelIdClient();
        Client client = new Client(nouvelId, nom, prenom, email, adresse, codePostal, ville);
        clients.add(client);

        System.out.println("✓ Client ajouté avec succès ! (ID: " + nouvelId + ")");
    }

    private static void afficherClients() {
        System.out.println("\n*****Liste des clients****");
        if (clients.isEmpty()) {
            System.out.println("Aucun client enregistré.");
        } else {
            for (Client client : clients) {
                System.out.println(client.toDisplayFormat());
            }
        }
    }

    private static void rechercherClient() {
        int id = lireEntier("ID du client à rechercher: ");
        Client client = trouverClientParId(id);

        if (client != null) {
            System.out.println("\n✓ Client trouvé:");
            System.out.println(client.toDisplayFormat());
        } else {
            System.out.println("✗ Client non trouvé.");
        }
    }

    private static void supprimerClient() {
        int id = lireEntier("ID du client à supprimer: ");
        Client client = trouverClientParId(id);

        if (client != null) {
            clients.remove(client);
            System.out.println("✓ Client supprimé avec succès !");
        } else {
            System.out.println("✗ Client non trouvé.");
        }
    }

    // GESTION PRODUITS

    private static void ajouterProduit() {
        System.out.println("\n*** Ajout d'un nouveau produit ***");
        scanner.nextLine();

        String description = lireTexte("Description: ");
        double prix = lireDouble("Prix: ");

        int nouvelId = obtenirNouvelIdProduit();
        Produit produit = new Produit(nouvelId, description, prix);
        produits.add(produit);

        System.out.println("✓ Produit ajouté avec succès ! (ID: " + nouvelId + ")");
    }

    private static void afficherProduits() {
        System.out.println("\n*** Liste des produits ***");
        if (produits.isEmpty()) {
            System.out.println("Aucun produit enregistré.");
        } else {
            for (Produit produit : produits) {
                System.out.println(produit.toDisplayFormat());
            }
        }
    }

    private static void rechercherProduit() {
        int id = lireEntier("ID du produit à rechercher: ");
        Produit produit = trouverProduitParId(id);

        if (produit != null) {
            System.out.println("\n✓ Produit trouvé:");
            System.out.println(produit.toDisplayFormat());
        } else {
            System.out.println("✗ Produit non trouvé.");
        }
    }

    private static void supprimerProduit() {
        int id = lireEntier("ID du produit à supprimer: ");
        Produit produit = trouverProduitParId(id);

        if (produit != null) {
            produits.remove(produit);
            System.out.println("✓ Produit supprimé avec succès !");
        } else {
            System.out.println("✗ Produit non trouvé.");
        }
    }

    //GESTION FACTURES

    private static void creerFacture() {
        System.out.println("\n--- Création d'une nouvelle facture ---");

        if (clients.isEmpty()) {
            System.out.println("✗ Aucun client disponible. Veuillez d'abord ajouter des clients.");
            return;
        }

        if (produits.isEmpty()) {
            System.out.println("✗ Aucun produit disponible. Veuillez d'abord ajouter des produits.");
            return;
        }

        // Sélection  un client
        afficherClients();
        int idClient = lireEntier("ID du client pour la facture: ");
        Client client = trouverClientParId(idClient);

        if (client == null) {
            System.out.println("✗ Client non trouvé.");
            return;
        }

        // Créer la facture
        int nouvelId = obtenirNouvelIdFacture();
        Facture facture = new Facture(nouvelId, LocalDate.now(), client);

        // Ajouter des produits
        boolean ajouterProduits = true;
        while (ajouterProduits) {
            afficherProduits();
            int idProduit = lireEntier("ID du produit à ajouter (0 pour terminer): ");

            if (idProduit == 0) {
                ajouterProduits = false;
            } else {
                Produit produit = trouverProduitParId(idProduit);
                if (produit != null) {
                    int quantite = lireEntier("Quantité: ");
                    facture.ajouterLigne(produit, quantite);
                    System.out.println("✓ Produit ajouté à la facture !");
                } else {
                    System.out.println("✗ Produit non trouvé.");
                }
            }
        }

        if (facture.getLignes().isEmpty()) {
            System.out.println("✗ Aucun produit ajouté. Facture annulée.");
        } else {
            factures.add(facture);
            System.out.println("\n✓ Facture créée avec succès ! (ID: " + nouvelId + ")");
            System.out.println(facture.toDetailFormat());
        }
    }

    private static void afficherFactures() {
        System.out.println("\n--- Liste des factures ---");
        if (factures.isEmpty()) {
            System.out.println("Aucune facture enregistrée.");
        } else {
            for (Facture facture : factures) {
                System.out.println(facture.toString());
            }
        }
    }

    private static void afficherDetailFacture() {
        int id = lireEntier("ID de la facture à afficher: ");
        Facture facture = trouverFactureParId(id);

        if (facture != null) {
            System.out.println(facture.toDetailFormat());
        } else {
            System.out.println("✗ Facture non trouvée.");
        }
    }

    private static void genererPDFFacture() {
        int id = lireEntier("ID de la facture à exporter en PDF: ");
        Facture facture = trouverFactureParId(id);

        if (facture != null) {
            String nomFichier = "facture_" + id + ".pdf";
            genererFacturePDF(facture, nomFichier);
        } else {
            System.out.println("✗ Facture non trouvée.");
        }
    }

    private static void supprimerFacture() {
        int id = lireEntier("ID de la facture à supprimer: ");
        Facture facture = trouverFactureParId(id);

        if (facture != null) {
            factures.remove(facture);
            System.out.println("✓ Facture supprimée avec succès !");
        } else {
            System.out.println("✗ Facture non trouvée.");
        }
    }

    // GESTION FICHIERS

    private static void chargerDonnees() {
        System.out.println("Chargement des données...");
        clients = GestionFichier.loadClients();
        produits = GestionFichier.loadProduits();
        factures = GestionFichier.loadFactures(clients, produits);
        System.out.println();
    }

    private static void sauvegarderDonnees() {
        System.out.println("\nSauvegarde des données...");
        GestionFichier.saveClients(clients);
        GestionFichier.saveProduits(produits);
        GestionFichier.saveFactures(factures);
    }

    // trouver un client par id

    private static Client trouverClientParId(int id) {
        for (Client client : clients) {
            if (client.getId() == id) {
                return client;
            }
        }
        return null;
    }

    private static Produit trouverProduitParId(int id) {
        for (Produit produit : produits) {
            if (produit.getId() == id) {
                return produit;
            }
        }
        return null;
    }

    private static Facture trouverFactureParId(int id) {
        for (Facture facture : factures) {
            if (facture.getId() == id) {
                return facture;
            }
        }
        return null;
    }

    private static int obtenirNouvelIdClient() {
        int maxId = 0;
        for (Client client : clients) {
            if (client.getId() > maxId) {
                maxId = client.getId();
            }
        }
        return maxId + 1;
    }

    private static int obtenirNouvelIdProduit() {
        int maxId = 0;
        for (Produit produit : produits) {
            if (produit.getId() > maxId) {

                maxId = produit.getId();
            }
        }
        return maxId + 1;
    }

    private static int obtenirNouvelIdFacture() {
        int maxId = 0;
        for (Facture facture : factures) {
            if (facture.getId() > maxId) {
                maxId = facture.getId();
            }
        }
        return maxId + 1;
    }

    // SAISIES

    private static int lireEntier(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.print("✗ Veuillez entrer un nombre valide: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
    private static double lireDouble(String message) {
        System.out.print(message);
        while (!scanner.hasNextDouble()) {
            System.out.print("✗ Veuillez entrer un nombre valide: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
    private static String lireTexte(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
}