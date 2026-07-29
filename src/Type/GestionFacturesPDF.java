package Type;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;

public class GestionFacturesPDF {
    public static void genererFacturePDF(Facture facture, String nomFichier) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(nomFichier));
            document.open();

            // FAIRE LA POLICES DU DOCUMENT
            Font titreFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, BaseColor.RED);
            Font sousTitreFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, BaseColor.BLUE);
            Font normalFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.GRAY);
            Font boldFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.GRAY);
            // LE TITRE
            Paragraph titre = new Paragraph("FACTURE N° " + facture.getId(), titreFont);
            titre.setAlignment(Element.ALIGN_CENTER);
            titre.setSpacingAfter(20);
            document.add(titre);

            // LA DATE DE LA FACTURE
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
            Paragraph date = new Paragraph("Date: " + facture.getDateFacture().format(formatter), normalFont);
            date.setSpacingAfter(20);
            document.add(date);

            //  detail du client
            Paragraph clientTitre = new Paragraph("INFORMATIONS DU CLIENT", sousTitreFont);
            clientTitre.setSpacingAfter(10);
            document.add(clientTitre);

            Client client = facture.getClient();
            Paragraph clientInfo = new Paragraph(
                    client.getPrenom() + " " + client.getNom() + "\n" +
                            client.getAdresse() + "\n" +
                            client.getCodePostal() + " " + client.getVille() + "\n" +
                            "Email: " + client.getEmail(),
                    normalFont);
            clientInfo.setSpacingAfter(20);
            document.add(clientInfo);
            // la separation
            document.add(new Paragraph("  "));
            // detail du Titre
            Paragraph detailTitre = new Paragraph("DETAIL DE LA FACTURE", sousTitreFont);
            detailTitre.setSpacingAfter(10);
            document.add(detailTitre);
            // table des produits
            PdfPTable table = new PdfPTable(4); // 4 colonnes
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setSpacingAfter(20);

            float[] columnWidths = {3f, 1f, 2f, 2f};
            table.setWidths(columnWidths);
            // en-tete du tableau
            PdfPCell cellHeader;
            cellHeader = new PdfPCell(new Phrase("Description", boldFont));
            cellHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellHeader.setPadding(5);
            table.addCell(cellHeader);

            cellHeader = new PdfPCell(new Phrase("QTE", boldFont));
            cellHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellHeader.setPadding(5);
            table.addCell(cellHeader);

            cellHeader = new PdfPCell(new Phrase("Prix Unit. (€)", boldFont));
            cellHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellHeader.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellHeader.setPadding(5);
            table.addCell(cellHeader);

            cellHeader = new PdfPCell(new Phrase("Sous-total (€)", boldFont));
            cellHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cellHeader.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellHeader.setPadding(5);
            table.addCell(cellHeader);

            // Lignes du tableau
            for (LigneFacture ligne : facture.getLignes()) {
                PdfPCell cell;
                // Description
                cell = new PdfPCell(new Phrase(ligne.getProduit().getDescription(), normalFont));
                cell.setPadding(5);
                table.addCell(cell);
                // Quantité
                cell = new PdfPCell(new Phrase(String.valueOf(ligne.getQuantite()), normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                table.addCell(cell);
                // Prix unitaire
                cell = new PdfPCell(new Phrase(String.format("%.2f", ligne.getProduit().getPrix()), normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                table.addCell(cell);
                // total
                cell = new PdfPCell(new Phrase(String.format("%.2f", ligne.getSousTotal()), normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                table.addCell(cell);
            }
            document.add(table);
            // Total
            Paragraph total = new Paragraph(
                    "TOTAL: " + String.format("%.2f", facture.getTotal()) + " €",
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK)
            );
            total.setAlignment(Element.ALIGN_RIGHT);
            total.setSpacingBefore(10);
            document.add(total);

            // Pied de page
            Paragraph footer = new Paragraph(
                    "\n\n TICKET CLIENT A CONSERVER " +
                            "Merci pour votre confiance !",
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.GRAY)
            );
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(30);
            document.add(footer);

            document.close();
            System.out.println("\n✓ Facture PDF générée avec succès : " + nomFichier);

        } catch (FileNotFoundException e) {
            System.err.println("Le document ne peut pas etre écrit");
        } catch (DocumentException e) {
            System.err.println(" Erreur dans l'ajout d'un élément dans le document");
            e.printStackTrace();
        }
    }
}




