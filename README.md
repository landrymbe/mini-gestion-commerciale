Mini Gestion Commerciale
Application de gestion commerciale en Java : administration d'un fichier clients et d'un catalogue produits, création de factures multi-lignes, et génération de documents PDF prêts à envoyer.
Le projet a été conçu comme un exercice complet de modélisation orientée objet : partir d'un besoin métier réel — facturer un client — et le traduire en un modèle de classes cohérent, avec persistance et export documentaire, sans dépendre d'un framework qui ferait le travail à ma place.
---
Aperçu
```
==================================================
               MENU PRINCIPAL
==================================================
1. Gestion des clients
2. Gestion des produits
3. Gestion des factures
0. Quitter
==================================================
Votre choix: 3

========================================
FACTURE N° 1
Date: 12/12/2025
Client: Yohann Timma
----------------------------------------
DÉTAIL:
--------------------------------------------------------------------------------
BIERE                          | Qté:  24 | Prix unitaire:     5.54 € | Sous-total:     132.96 €
VIN ROUGE                      | Qté:   6 | Prix unitaire:     8.23 € | Sous-total:      49.38 €
--------------------------------------------------------------------------------
TOTAL: 182.34 €
========================================

✓ Facture PDF générée avec succès : facture_1.pdf
```
---
Fonctionnalités
Gestion des clients
Ajout, consultation de la liste complète, recherche par identifiant, suppression. Chaque client porte son état civil et son adresse postale complète, réutilisée telle quelle dans l'en-tête des factures.
Catalogue produits
Mêmes opérations, sur un référentiel produit avec description et prix unitaire.
Facturation
Création d'une facture par sélection d'un client, puis ajout successif de lignes produit/quantité. Le total se calcule par agrégation des sous-totaux de chaque ligne, jamais stocké en dur — une modification de ligne se répercute immédiatement.
Export PDF
Génération d'une facture mise en forme via iText : titre, bloc d'informations client, tableau détaillé des lignes et total. Le document produit est directement présentable à un client.
Persistance
Chargement automatique au démarrage et sauvegarde à la fermeture, dans trois fichiers CSV lisibles et éditables à la main. Les relations entre entités sont reconstruites à la lecture : une facture ne stocke que les identifiants de son client et de ses produits, et retrouve les objets correspondants au chargement.
---
Installation
Prérequis
Un JDK 21 ou supérieur (le code utilise `String.repeat`, introduit en Java 11). Testé sur Temurin 21.
```bash
javac -version
```
Compilation
Linux / macOS
```bash
javac -encoding UTF-8 -cp "lib/itextpdf-5.5.12.jar" -d build $(find src -name "*.java")
java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -cp "build:lib/itextpdf-5.5.12.jar" Main
```
Windows (PowerShell)
```powershell
javac -encoding UTF-8 -cp "lib\itextpdf-5.5.12.jar" -d build (Get-ChildItem -Recurse src -Filter *.java).FullName
chcp 65001
java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -cp "build;lib\itextpdf-5.5.12.jar" Main
```
Dans un IDE
Ouvrir le dossier, puis déclarer `lib/itextpdf-5.5.12.jar` comme dépendance du projet (`File > Project Structure > Libraries` sous IntelliJ). Lancer `Main`.
L'exécution doit se faire depuis la racine du projet : les chemins des fichiers CSV sont relatifs au répertoire courant.
---
Format des données
Fichier	Structure
`clients.csv`	`id;nom;prenom;email;adresse;codePostal;ville`
`produits.csv`	`id;description;prix`
`factures.csv`	`id;date;idClient;idProduit|quantité,idProduit|quantité,…`
Exemple de facture : `2;13/12/2025;4;6\|2,5\|10,1\|4` — facture n°2 du 13/12/2025, pour le client 4, contenant 2 unités du produit 6, 10 du produit 5 et 4 du produit 1.
Les fichiers fournis contiennent un jeu de données de démonstration.
---
Compétences mises en œuvre
Modélisation objet et relations entre entités · Collections et généricité · API `java.time` · Lecture/écriture de fichiers et parsing · Intégration d'une bibliothèque tierce (iText) · Conception d'interface console · Gestion des encodages
---
Auteur
Landry Mbe — étudiant en informatique, ECE Bordeaux
github.com/landrymbe
---
Licence
Distribué sous licence MIT. Voir `LICENSE` pour le détail.
