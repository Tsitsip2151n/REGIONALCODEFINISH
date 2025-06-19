package interface_ui;

import javax.swing.*;
import java.io.*;
import java.util.*;
import niveau.*;
import vivant.*;

public class GestionApplication {
     List<Region> regions;
     FenetreSaisie fenetreSaisie;
     FenetreResultats fenetreResultats;

    public GestionApplication(List<Region> regions) {
        this.regions = new ArrayList<>(Objects.requireNonNull(regions));
        chargerVotes();
        lancerApplication();
    }

     void chargerVotes() {
        File fichierVotes = new File("votes.txt");
        if (!fichierVotes.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fichierVotes))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                traiterLigneVote(ligne.trim());
            }
        } catch (IOException e) {
            afficherErreur("Erreur lors de la lecture du fichier de votes");
        }
    }

     void traiterLigneVote(String ligne) {
        if (ligne.isEmpty()) return;
        
        String[] parts = ligne.split(";");
        if (parts.length != 4) {
            System.err.println("Format invalide pour la ligne: " + ligne);
            return;
        }

        try {
            String nomDepute = parts[0];
            String groupeDepute = parts[1];
            String nomBureau = parts[2];
            int nbVotes = Integer.parseInt(parts[3]);

            boolean voteTrouve = false;
            
            // Parcours hiérarchique des structures
            for (Region region : regions) {
                for (District district : region.getDistricts()) {
                    for (Commune commune : district.getCommunes()) {
                        for (BureauVote bureau : commune.getBureaux()) {
                            if (bureau.getNom().equals(nomBureau)) {
                                for (Depute depute : bureau.getDeputes()) {
                                    if (depute.getNom().equals(nomDepute) && 
                                        depute.getGroupe().equals(groupeDepute)) {
                                        depute.ajouterVotes(nbVotes);
                                        voteTrouve = true;
                                        break;
                                    }
                                }
                            }
                            if (voteTrouve) break;
                        }
                        if (voteTrouve) break;
                    }
                    if (voteTrouve) break;
                }
                if (voteTrouve) break;
            }

            if (!voteTrouve) {
                System.err.println("Vote non attribué: " + ligne);
            }
        } catch (NumberFormatException e) {
            System.err.println("Nombre de votes invalide dans la ligne: " + ligne);
        }
    }

    private void afficherErreur(String message) {
        JOptionPane.showMessageDialog(null, 
            message, 
            "Erreur", 
            JOptionPane.ERROR_MESSAGE);
    }

    private void lancerApplication() {
        SwingUtilities.invokeLater(() -> {
            fenetreSaisie = new FenetreSaisie(regions);
            fenetreResultats = new FenetreResultats(regions);
            
            fenetreSaisie.setVisible(true);
            fenetreResultats.setVisible(true);
        });
    }

    public void fermerApplication() {
        if (fenetreSaisie != null) fenetreSaisie.dispose();
        if (fenetreResultats != null) fenetreResultats.dispose();
    }
}