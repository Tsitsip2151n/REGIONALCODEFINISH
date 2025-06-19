package interface_ui;

import niveau.Commune;
import niveau.BureauVote;

public class FiltreBureau extends FiltresDeroulants {
    Commune commune;

    public FiltreBureau() {
        super(new String[0]);
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
        mettreAJour();
    }

    @Override
    public void mettreAJour() {
        removeAllItems();
        if (commune != null) {
            commune.getBureaux().forEach(b -> addItem(b.getNom()));
        }
    }

    public BureauVote getBureauSelectionne() {
        int index = getSelectedIndex();
        return commune != null && index >= 0 ? commune.getBureaux().get(index) : null;
    }
}