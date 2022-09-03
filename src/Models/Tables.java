package Models;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Tables extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
         if (table.getValueAt(row, column).toString().equals("Inactivo")) {
             setBackground(Color.red);
             setForeground(Color.white);
        }else{
             setBackground(Color.white);
             setForeground(Color.black);
         }
         return this;
    }

}
