package interface_ui;

import javax.swing.JComboBox;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.awt.Component;

public class FiltresDeroulants extends JComboBox<String> {
    
    public FiltresDeroulants(String[] items) {
        super(items);
        setRenderer(new CustomRenderer());
    }

    public void mettreAJour() {
        
    };

    class CustomRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, 
                                                    int index, boolean isSelected, 
                                                    boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, 
                                                           isSelected, cellHasFocus);
            if (value == null) {
                setText("");
            }
            return c;
        }
    }
}