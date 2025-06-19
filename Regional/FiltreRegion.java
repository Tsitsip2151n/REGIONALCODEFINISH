package interface_ui;

import java.util.List;
import niveau.Region;

public class FiltreRegion extends FiltresDeroulants {
    List<Region> regions;

    public FiltreRegion(List<Region> regions) {
        super(regions.stream().map(Region::getNom).toArray(String[]::new));
        this.regions = List.copyOf(regions);
    }

    @Override
    public void mettreAJour() {}

    public Region getRegionSelectionnee() {
        int index = getSelectedIndex();
        return index >= 0 ? regions.get(index) : null;
    }
}