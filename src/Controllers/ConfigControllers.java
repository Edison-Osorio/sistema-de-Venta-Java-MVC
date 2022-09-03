package Controllers;

// @author Edison Osorio
import Views.PanelAdmin;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ConfigControllers implements MouseListener {

    private PanelAdmin panelAdmin;

    public ConfigControllers() {
    }

    public ConfigControllers(PanelAdmin panelAdmin) {
        this.panelAdmin = panelAdmin;
        this.panelAdmin.JLabelCategorias.addMouseListener(this);
        this.panelAdmin.JLabelClientes.addMouseListener(this);
        this.panelAdmin.JLabelConfig.addMouseListener(this);
        this.panelAdmin.JLabelMedidas.addMouseListener(this);
        this.panelAdmin.JLabelNuevaCompra.addMouseListener(this);
        this.panelAdmin.JLabelNuevaVenta.addMouseListener(this);
        this.panelAdmin.JLabelProveedor.addMouseListener(this);
        this.panelAdmin.JLabelUser.addMouseListener(this);
        this.panelAdmin.JLabelProductos.addMouseListener(this);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == panelAdmin.JLabelConfig) {
            panelAdmin.jTabbedPane1.setSelectedIndex(9);
            
        }
        

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == panelAdmin.JLabelCategorias) {
            panelAdmin.JPanelCategorias.setBackground(new Color(255, 51, 51));
        } else if (e.getSource() == panelAdmin.JLabelClientes) {
            panelAdmin.JPanelClientes.setBackground(new Color(255, 51, 51));
        } else if (e.getSource() == panelAdmin.JLabelConfig) {
            panelAdmin.JPanelConfig.setBackground(new Color(255, 51, 51));
        } else if (e.getSource() == panelAdmin.JLabelMedidas) {
            panelAdmin.JPanelMedidas.setBackground(new Color(255, 51, 51));
        } else if (e.getSource() == panelAdmin.JLabelNuevaCompra) {
            panelAdmin.JPanelNuevaCompra.setBackground(new Color(255, 51, 51));
        } else if (e.getSource() == panelAdmin.JLabelNuevaVenta) {
            panelAdmin.JPanelNuevaVenta.setBackground(new Color(255, 51, 51));
        } else if (e.getSource() == panelAdmin.JLabelProveedor) {
            panelAdmin.JPanelProveedor.setBackground(new Color(255, 51, 51));
        } else if (e.getSource() == panelAdmin.JLabelUser) {
            panelAdmin.JPanelUsers.setBackground(new Color(255, 51, 51));
        } else {
            panelAdmin.JPanelProductos.setBackground(new Color(255, 51, 51));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == panelAdmin.JLabelCategorias) {
            panelAdmin.JPanelCategorias.setBackground(new Color(51, 51, 51));
        } else if (e.getSource() == panelAdmin.JLabelClientes) {
            panelAdmin.JPanelClientes.setBackground(new Color(51, 51, 51));
        } else if (e.getSource() == panelAdmin.JLabelConfig) {
            panelAdmin.JPanelConfig.setBackground(new Color(51, 51, 51));
        } else if (e.getSource() == panelAdmin.JLabelMedidas) {
            panelAdmin.JPanelMedidas.setBackground(new Color(51, 51, 51));
        } else if (e.getSource() == panelAdmin.JLabelNuevaCompra) {
            panelAdmin.JPanelNuevaCompra.setBackground(new Color(51, 51, 51));
        } else if (e.getSource() == panelAdmin.JLabelNuevaVenta) {
            panelAdmin.JPanelNuevaVenta.setBackground(new Color(51, 51, 51));
        } else if (e.getSource() == panelAdmin.JLabelProveedor) {
            panelAdmin.JPanelProveedor.setBackground(new Color(51, 51, 51));
        } else if (e.getSource() == panelAdmin.JLabelUser) {
            panelAdmin.JPanelUsers.setBackground(new Color(51, 51, 51));
        } else {
            panelAdmin.JPanelProductos.setBackground(new Color(51, 51, 51));
        }
    }

}
