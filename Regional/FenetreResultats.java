package interface_ui;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import niveau.*;
import vivant.*;

public class FenetreResultats extends JFrame {
         List<Region> regions;
         JTextArea resultArea;

    public FenetreResultats(List<Region> regions) {
        this.regions = new ArrayList<>(regions);
        initialiserUI();
    }

    void initialiserUI() {
        setTitle("Résultats des élections");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        
        JPanel filterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Création des filtres
        FiltreRegion filtreRegion = new FiltreRegion(regions);
        FiltreDistrict filtreDistrict = new FiltreDistrict();
        FiltreCommune filtreCommune = new FiltreCommune();
        FiltreBureau filtreBureau = new FiltreBureau();

        // Configuration des contraintes
        gbc.gridy = 0;
        ajouterComposant(filterPanel, new JLabel("Région:"), filtreRegion, gbc);
        gbc.gridy++;
        ajouterComposant(filterPanel, new JLabel("District:"), filtreDistrict, gbc);
        gbc.gridy++;
        ajouterComposant(filterPanel, new JLabel("Commune:"), filtreCommune, gbc);
        gbc.gridy++;
        ajouterComposant(filterPanel, new JLabel("Bureau:"), filtreBureau, gbc);

        
        configurerListeners(filtreRegion, filtreDistrict, filtreCommune, filtreBureau);

        // Zone de résultats (bas)
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Bouton pour afficher les résultats
        JButton showResultsButton = new JButton("Afficher les résultats");
        showResultsButton.addActionListener(e -> afficherResultats(
            filtreRegion.getRegionSelectionnee(),
            filtreDistrict.getSelectedIndex() >= 0 ? filtreDistrict.getSelectedIndex() : null,
            filtreCommune.getSelectedIndex() >= 0 ? filtreCommune.getSelectedIndex() : null,
            filtreBureau.getSelectedIndex() >= 0 ? filtreBureau.getSelectedIndex() : null
        ));

        // Panel pour le bouton
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(showResultsButton);

        // Ajout des composants au panel principal
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);
    }

 void ajouterComposant(JPanel panel, JLabel label, JComponent component, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(component, gbc);
    }

 void configurerListeners(FiltreRegion filtreRegion, FiltreDistrict filtreDistrict,
                                   FiltreCommune filtreCommune, FiltreBureau filtreBureau) {
        filtreRegion.addActionListener(e -> {
            Region region = filtreRegion.getRegionSelectionnee();
            filtreDistrict.setRegion(region);
            filtreCommune.setDistrict(null);
            filtreBureau.setCommune(null);
        });

        filtreDistrict.addActionListener(e -> {
            if (filtreDistrict.getSelectedIndex() >= 0 && filtreRegion.getRegionSelectionnee() != null) {
                District district = filtreRegion.getRegionSelectionnee()
                    .getDistricts().get(filtreDistrict.getSelectedIndex());
                filtreCommune.setDistrict(district);
            }
            filtreBureau.setCommune(null);
        });

        filtreCommune.addActionListener(e -> {
            if (filtreCommune.getSelectedIndex() >= 0 && filtreDistrict.getSelectedIndex() >= 0 
                && filtreRegion.getRegionSelectionnee() != null) {
                Commune commune = filtreRegion.getRegionSelectionnee()
                    .getDistricts().get(filtreDistrict.getSelectedIndex())
                    .getCommunes().get(filtreCommune.getSelectedIndex());
                filtreBureau.setCommune(commune);
            }
        });
    }

 void afficherResultats(Region region, Integer districtIndex, Integer communeIndex, Integer bureauIndex) {
        resultArea.setText(""); // Effacer les résultats précédents
        
        if (region == null) {
            resultArea.append("Veuillez sélectionner une région\n");
            return;
        }

        if (districtIndex == null) {
            for (District district : region.getDistricts()) {
                afficherResultatsDistrict(district);
            }
            return;
        }

        District district = region.getDistricts().get(districtIndex);
        
        if (communeIndex == null) {
            // Afficher toutes les communes du district
            for (Commune commune : district.getCommunes()) {
                afficherResultatsCommune(commune);
            }
            return;
        }

        Commune commune = district.getCommunes().get(communeIndex);
        
        if (bureauIndex == null) {
            // Afficher tous les bureaux de la commune
            for (BureauVote bureau : commune.getBureaux()) {
                afficherResultatsBureau(bureau);
            }
            return;
        }

        // Afficher un bureau spécifique
        BureauVote bureau = commune.getBureaux().get(bureauIndex);
        afficherResultatsBureau(bureau);
    }

 void afficherResultatsDistrict(District district) {
        resultArea.append("=== DISTRICT: " + district.getNom().toUpperCase() + " ===\n");
        
        List<Depute> deputesDistrict = new ArrayList<>();
        for (Commune commune : district.getCommunes()) {
            for (BureauVote bureau : commune.getBureaux()) {
                deputesDistrict.addAll(bureau.getDeputes());
            }
        }
        
        trierDeputes(deputesDistrict);
        
        if (!deputesDistrict.isEmpty()) {
            Depute premier = deputesDistrict.get(0);
            resultArea.append(String.format("  1. %-20s %-10s %5d votes (Titulaire)%n", 
                    premier.getNom(), "(" + premier.getGroupe() + ")", premier.getNbVotes()));
            
            if (district.afficheDeuxDeputes() && deputesDistrict.size() > 1) {
                Depute deuxieme = deputesDistrict.get(1);
                if (premier.estEluAvecSuppleant(deuxieme)) {
                    resultArea.append(String.format("  2. %-20s %-10s %5d votes (Titulaire)%n", 
                            deuxieme.getNom(), "(" + deuxieme.getGroupe() + ")", deuxieme.getNbVotes()));
                } else {
                    resultArea.append(String.format("  2. %-20s %-10s %5d votes (Suppléant: %s)%n", 
                            premier.getSuppleant().getNom(), "(" + premier.getGroupe() + ")", 
                            premier.getNbVotes()/2, premier.getSuppleant().getNom()));
                }
            }
        }
        resultArea.append("\n");
    }
 void afficherResultatsCommune(Commune commune) {
        resultArea.append("  === COMMUNE: " + commune.getNom().toUpperCase() + " ===\n");
        
        for (BureauVote bureau : commune.getBureaux()) {
            afficherResultatsBureau(bureau);
        }
    }

  void afficherResultatsBureau(BureauVote bureau) {
        resultArea.append("    === BUREAU: " + bureau.getNom().toUpperCase() + " ===\n");
        
        List<Depute> deputesBureau = new ArrayList<>(bureau.getDeputes());
        trierDeputes(deputesBureau);
        
        int top = bureau.afficheDeuxDeputes() ? Math.min(2, deputesBureau.size()) : 1;
        for (int i = 0; i < top; i++) {
            Depute dep = deputesBureau.get(i);
            resultArea.append(String.format("      %d. %-20s %-10s %5d votes%n", 
                    i+1, dep.getNom(), "(" + dep.getGroupe() + ")", dep.getNbVotes()));
        }
        resultArea.append("\n");
    }

 void trierDeputes(List<Depute> deputes) {
        deputes.sort((d1, d2) -> Integer.compare(d2.getNbVotes(), d1.getNbVotes()));
    }
}