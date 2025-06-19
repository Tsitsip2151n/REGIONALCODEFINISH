package interface_ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import niveau.*;


public class FenetreSaisie extends JFrame {
    List<Region> regions;

    public FenetreSaisie(List<Region> regions) {
        this.regions = regions;
        initialiserUI();
    }

    void initialiserUI() {
        setTitle("Saisie de votes");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Création des filtres
        FiltreRegion filtreRegion = new FiltreRegion(regions);
        FiltreDistrict filtreDistrict = new FiltreDistrict();
        FiltreCommune filtreCommune = new FiltreCommune();
        FiltreBureau filtreBureau = new FiltreBureau();
        FiltreDepute filtreDepute = new FiltreDepute();

        // Champ pour le nombre de votes
        JTextField nbVotesField = new JTextField();
        JLabel nbVotesLabel = new JLabel("Nombre de votes:");

        // Configuration des contraintes
        gbc.gridy = 0;
        ajouterComposant(mainPanel, new JLabel("Région:"), filtreRegion, gbc);
        gbc.gridy++;
        ajouterComposant(mainPanel, new JLabel("District:"), filtreDistrict, gbc);
        gbc.gridy++;
        ajouterComposant(mainPanel, new JLabel("Commune:"), filtreCommune, gbc);
        gbc.gridy++;
        ajouterComposant(mainPanel, new JLabel("Bureau:"), filtreBureau, gbc);
        gbc.gridy++;
        ajouterComposant(mainPanel, new JLabel("Député:"), filtreDepute, gbc);
        gbc.gridy++;
        ajouterComposant(mainPanel, nbVotesLabel, nbVotesField, gbc);
        gbc.gridy++;
        
        // Bouton d'insertion
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(new InsertButton(nbVotesField, filtreDepute, filtreBureau), gbc);

        // Gestion des événements
        configurerListeners(filtreRegion, filtreDistrict, filtreCommune, filtreBureau, filtreDepute);

        add(mainPanel);
    }

    private void ajouterComposant(JPanel panel, JLabel label, JComponent component, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(component, gbc);
    }

    private void configurerListeners(FiltreRegion filtreRegion, FiltreDistrict filtreDistrict,
                                   FiltreCommune filtreCommune, FiltreBureau filtreBureau,
                                   FiltreDepute filtreDepute) {
        filtreRegion.addActionListener(e -> {
            Region region = filtreRegion.getRegionSelectionnee();
            filtreDistrict.setRegion(region);
            filtreCommune.setDistrict(null);
            filtreBureau.setCommune(null);
            filtreDepute.setBureau(null);
        });

        filtreDistrict.addActionListener(e -> {
            if (filtreDistrict.getSelectedIndex() >= 0 && filtreRegion.getRegionSelectionnee() != null) {
                District district = filtreRegion.getRegionSelectionnee()
                    .getDistricts().get(filtreDistrict.getSelectedIndex());
                filtreCommune.setDistrict(district);
            }
            filtreBureau.setCommune(null);
            filtreDepute.setBureau(null);
        });

        filtreCommune.addActionListener(e -> {
            if (filtreCommune.getSelectedIndex() >= 0 && filtreDistrict.getSelectedIndex() >= 0 
                && filtreRegion.getRegionSelectionnee() != null) {
                Commune commune = filtreRegion.getRegionSelectionnee()
                    .getDistricts().get(filtreDistrict.getSelectedIndex())
                    .getCommunes().get(filtreCommune.getSelectedIndex());
                filtreBureau.setCommune(commune);
            }
            filtreDepute.setBureau(null);
        });

        filtreBureau.addActionListener(e -> {
            if (filtreBureau.getSelectedIndex() >= 0 && filtreCommune.getSelectedIndex() >= 0 
                && filtreDistrict.getSelectedIndex() >= 0 && filtreRegion.getRegionSelectionnee() != null) {
                BureauVote bureau = filtreRegion.getRegionSelectionnee()
                    .getDistricts().get(filtreDistrict.getSelectedIndex())
                    .getCommunes().get(filtreCommune.getSelectedIndex())
                    .getBureaux().get(filtreBureau.getSelectedIndex());
                filtreDepute.setBureau(bureau);
            }
        });
    }
}