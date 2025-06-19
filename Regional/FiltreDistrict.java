package interface_ui;

import niveau.Region;

public class FiltreDistrict extends FiltresDeroulants {
     Region region;

    public FiltreDistrict() {
        super(new String[0]);
    }

    public void setRegion(Region region) {
        this.region = region;
        mettreAJour();
    }

    @Override
    public void mettreAJour() {
        removeAllItems();
        if (region != null) {
            region.getDistricts().forEach(d -> addItem(d.getNom()));
        }
    }
}