package Controllers;

import DAO.ProveedorDao;
import Models.*;
import Views.PanelAdmin;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class ProveedorControllers implements ActionListener, MouseListener, KeyListener {
    
    private Proveedor proveedor;
    private ProveedorDao proveedorDao;
    private PanelAdmin panelAdmin;
    DefaultTableModel modelo = new DefaultTableModel();
    
    public ProveedorControllers(Proveedor proveedor, ProveedorDao proveedorDao, PanelAdmin panelAdmin) {
        this.proveedor = proveedor;
        this.proveedorDao = proveedorDao;
        this.panelAdmin = panelAdmin;
        
        this.panelAdmin.btnRegistrarProv.addActionListener(this);
        this.panelAdmin.btnModificarProv.addActionListener(this);
        this.panelAdmin.btnNuevoProv.addActionListener(this);
        
        this.panelAdmin.jMenuEliminarProv.addActionListener(this);
        this.panelAdmin.jMenuReingresarProv.addActionListener(this);
        this.panelAdmin.TableProveedor.addMouseListener(this);
        this.panelAdmin.txtBuscarProv.addKeyListener(this);
        this.panelAdmin.JLabelProveedor.addMouseListener(this);
        
        
        listarProveedores();
        
        llenarProveedor();
        // Llamanos labreria de autocompletacion
        AutoCompleteDecorator.decorate(panelAdmin.cbxProveedorPro);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelAdmin.btnRegistrarProv) {
            if (panelAdmin.txtRucProveedor.getText().equals("")
                    || panelAdmin.txtNombreProv.getText().equals("")
                    || panelAdmin.txtTelefonoProv.getText().equals("")
                    || panelAdmin.txtDireccionProv.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son requeridos");
            } else {
                proveedor.setRuc(panelAdmin.txtRucProveedor.getText());
                proveedor.setNombre(panelAdmin.txtNombreProv.getText());
                proveedor.setTelefono(panelAdmin.txtTelefonoProv.getText());
                proveedor.setDireccion(panelAdmin.txtDireccionProv.getText());
                if (proveedorDao.registrar(proveedor)) {
                    limpiarTable();
                    listarProveedores();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Proveedor registrado");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar el proveedor");
                }
            }
        } else if (e.getSource() == panelAdmin.btnModificarProv) {
            if (panelAdmin.txtIdProv.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila");
            } else {
                if (panelAdmin.txtRucProveedor.getText().equals("")
                        || panelAdmin.txtNombreProv.getText().equals("")
                        || panelAdmin.txtTelefonoProv.getText().equals("")
                        || panelAdmin.txtDireccionProv.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son requeridos");
                } else {
                    proveedor.setRuc(panelAdmin.txtRucProveedor.getText());
                    proveedor.setNombre(panelAdmin.txtNombreProv.getText());
                    proveedor.setTelefono(panelAdmin.txtTelefonoProv.getText());
                    proveedor.setDireccion(panelAdmin.txtDireccionProv.getText());
                    proveedor.setIdProveedor(Integer.parseInt(panelAdmin.txtIdProv.getText()));
                    if (proveedorDao.modificar(proveedor)) {
                        limpiarTable();
                        listarProveedores();
                        limpiar();
                        JOptionPane.showMessageDialog(null, "Proveedor actualizado");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al actualizar el proveedor");
                    }
                }
                
            }
        } else if (e.getSource() == panelAdmin.jMenuEliminarProv) {
            if (panelAdmin.txtIdProv.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila");
            } else {
                int id = Integer.parseInt(panelAdmin.txtIdProv.getText());
                if (proveedorDao.accion("Inactivo", id)) {
                    limpiarTable();
                    listarProveedores();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Proveedor eliminado !");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el proveedor");
                }
            }
        } else if (e.getSource() == panelAdmin.jMenuReingresarProv) {
            if (panelAdmin.txtIdProv.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para reingresar");
            } else {
                int id = Integer.parseInt(panelAdmin.txtIdProv.getText());
                if (proveedorDao.accion("Activo", id)) {
                    limpiarTable();
                    listarProveedores();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Proveedor reingresado !");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al reigresar el proveedor");
                }
                
            }
        } else {
            limpiar();
        }
        
    }
    
    public void listarProveedores() {
        Tables color = new Tables();
        panelAdmin.TableProveedor.setDefaultRenderer(panelAdmin.TableProveedor.getColumnClass(0), color);
        List<Proveedor> list = proveedorDao.listaProveedores(panelAdmin.txtBuscarProv.getText());
        modelo = (DefaultTableModel) panelAdmin.TableProveedor.getModel();
        Object[] object = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            object[0] = list.get(i).getIdProveedor();
            object[1] = list.get(i).getRuc();
            object[2] = list.get(i).getNombre();
            object[3] = list.get(i).getTelefono();
            object[4] = list.get(i).getDireccion();
            object[5] = list.get(i).getEstado();
            modelo.addRow(object);
        }
        
        panelAdmin.TableProveedor.setModel(modelo);
        JTableHeader header = panelAdmin.TableProveedor.getTableHeader();
        header.setOpaque(false);
        header.setBackground(Color.blue);
        header.setForeground(Color.white);
    }
    
    public void limpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }
    
    private void limpiar() {
        panelAdmin.txtRucProveedor.setText("");
        panelAdmin.txtNombreProv.setText("");
        panelAdmin.txtTelefonoProv.setText("");
        panelAdmin.txtDireccionProv.setText("");
        panelAdmin.txtIdProv.setText("");
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == panelAdmin.TableProveedor) {
            int fila = panelAdmin.TableProveedor.rowAtPoint(e.getPoint());
            panelAdmin.txtIdProv.setText(panelAdmin.TableProveedor.getValueAt(fila, 0).toString());
            panelAdmin.txtRucProveedor.setText(panelAdmin.TableProveedor.getValueAt(fila, 1).toString());
            panelAdmin.txtNombreProv.setText(panelAdmin.TableProveedor.getValueAt(fila, 2).toString());
            panelAdmin.txtTelefonoProv.setText(panelAdmin.TableProveedor.getValueAt(fila, 3).toString());
            panelAdmin.txtDireccionProv.setText(panelAdmin.TableProveedor.getValueAt(fila, 4).toString());
            // panelAdmin.btnRegistrarCli.setEnabled(false);
        } else if (e.getSource() == panelAdmin.JLabelProveedor) {
            panelAdmin.jTabbedPane1.setSelectedIndex(2);
            limpiarTable();
            listarProveedores();
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
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == panelAdmin.txtBuscarProv) {
            limpiar();
            limpiarTable();
            listarProveedores();
        }
    }

    //Metodo para el llenado del combo box
    private void llenarProveedor() {
        List<Proveedor> list = proveedorDao.listaProveedores(panelAdmin.txtBuscarProv.getText());
        
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getIdProveedor();
            String nombre = list.get(i).getNombre();
            panelAdmin.cbxProveedorPro.addItem(new Combo(id, nombre));
        }
    }
    
}
