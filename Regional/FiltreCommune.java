package interface_ui;

import niveau.District;

public class FiltreCommune extends FiltresDeroulants {
    District district;

    public FiltreCommune() {
        super(new String[0]);
    }

    public void setDistrict(District district) {
        this.district = district;
        mettreAJour();
    }

    @Override
    public void mettreAJour() {
        removeAllItems();
        if (district != null) {
            district.getCommunes().forEach(c -> addItem(c.getNom()));
        }
    }
}