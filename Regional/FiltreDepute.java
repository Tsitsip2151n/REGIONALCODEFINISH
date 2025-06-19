package interface_ui;

import niveau.BureauVote;
import vivant.Depute;

public class FiltreDepute extends FiltresDeroulants {
    BureauVote bureau;

    public FiltreDepute() {
        super(new String[0]);
    }

    public void setBureau(BureauVote bureau) {
        this.bureau = bureau;
        mettreAJour();
    }

    @Override
    public void mettreAJour() {
        removeAllItems();
        if (bureau != null) {
            bureau.getDeputes().forEach(d -> addItem(d.getNom()));
        }
    }

    public Depute getDeputeSelectionne() {
        int index = getSelectedIndex();
        return bureau != null && index >= 0 ? bureau.getDeputes().get(index) : null;
    }
}