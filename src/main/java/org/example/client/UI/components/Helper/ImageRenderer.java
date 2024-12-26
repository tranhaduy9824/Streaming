
package org.example.client.UI.components.Helper;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ImageRenderer extends DefaultTableCellRenderer {

    private static final String PLACEHOLDER_TEXT = "";

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (cellComponent instanceof JLabel) {
            JLabel label = (JLabel) cellComponent;
            if (value instanceof ImageIcon) {
                ImageIcon imageIcon = (ImageIcon) value;
                label.setIcon(imageIcon);
                label.setText(PLACEHOLDER_TEXT);
            }
        }

        return cellComponent;
    }
}