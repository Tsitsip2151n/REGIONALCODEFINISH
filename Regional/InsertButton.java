package interface_ui;

import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import niveau.BureauVote;
import vivant.Depute;


public class InsertButton extends JButton {
    JTextField nbVotesField;
    FiltreDepute filtreDepute;
    FiltreBureau bureau;

    public InsertButton(JTextField nbVotesField, FiltreDepute filtreDepute, FiltreBureau bureauDeroule) {
        super("Ajouter vote");
        this.nbVotesField = nbVotesField;
        this.filtreDepute = filtreDepute;
        this.bureau = bureauDeroule;
        configurerComportement();
    }

    void configurerComportement() {
        addActionListener(e -> {
            try {
                Depute dep = filtreDepute.getDeputeSelectionne();
                BureauVote bureauVote = bureau.getBureauSelectionne();
                
                if (dep == null) {
                    throw new IllegalArgumentException("Aucun député sélectionné");
                }
                if (bureauVote == null) {
                    throw new IllegalArgumentException("Aucun bureau de vote sélectionné");
                }

                int nb = Integer.parseInt(nbVotesField.getText().trim());
                if (nb <= 0) {
                    throw new IllegalArgumentException("Le nombre de votes doit être positif");
                }

                dep.ajouterVotes(nb);
                enregistrerVote(dep, bureauVote, nb);
                nbVotesField.setText("");
                
                JOptionPane.showMessageDialog(InsertButton.this, 
                    "Vote enregistré avec succès pour " + dep.getNom(), 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(InsertButton.this, 
                    "Veuillez entrer un nombre valide", 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(InsertButton.this, 
                    ex.getMessage(), 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(InsertButton.this, 
                    "Erreur lors de l'enregistrement du vote", 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    void enregistrerVote(Depute dep, BureauVote bureau, int nbVotes) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("votes.txt", true))) {
            bw.write(String.format("%s;%s;%s;%d%n", 
                dep.getNom(), 
                dep.getGroupe(), 
                bureau.getNom(), 
                nbVotes));
            bw.flush();
        }
    }
}