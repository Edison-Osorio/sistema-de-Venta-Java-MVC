package Controllers;

import DAO.MedidasDao;
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

public class MedidasControllers implements ActionListener, MouseListener, KeyListener {

    private Medidas medidas;
    private MedidasDao medidasDao;
    private PanelAdmin panelAdmin;
    DefaultTableModel modelo = new DefaultTableModel();

    public MedidasControllers(Medidas medidas, MedidasDao medidasDao, PanelAdmin panelAdmin) {
        this.medidas = medidas;
        this.medidasDao = medidasDao;
        this.panelAdmin = panelAdmin;

        this.panelAdmin.btnRegistrarMedida.addActionListener(this);
        this.panelAdmin.btnModificarMedida.addActionListener(this);
        this.panelAdmin.btnNuevaMedida.addActionListener(this);

        this.panelAdmin.jMenuEliminarMedi.addActionListener(this);
        this.panelAdmin.jMenuReingresarMedi.addActionListener(this);
        this.panelAdmin.TableMedida.addMouseListener(this);
        this.panelAdmin.txtBuscarMedi.addKeyListener(this);
        this.panelAdmin.JLabelMedidas.addMouseListener(this);

        listarMedidas();
        llenarMedida();
        AutoCompleteDecorator.decorate(panelAdmin.cbxMedidaPro);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelAdmin.btnRegistrarMedida) {
            if (panelAdmin.txtNombreMedida.getText().equals("")
                    || panelAdmin.txtNombreCortoMedida.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son requeridos");
            } else {
                medidas.setNombre(panelAdmin.txtNombreMedida.getText());
                medidas.setNombreConto(panelAdmin.txtNombreCortoMedida.getText());
                if (medidasDao.registrar(medidas)) {
                    limpiarTable();
                    listarMedidas();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Medida registrado");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar una medida");
                }
            }
        } else if (e.getSource() == panelAdmin.btnModificarMedida) {
            if (panelAdmin.txtIdMedida.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila");
            } else {
                if (panelAdmin.txtNombreMedida.getText().equals("")
                        || panelAdmin.txtNombreCortoMedida.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son requeridos");
                } else {
                    medidas.setNombre(panelAdmin.txtNombreMedida.getText());
                    medidas.setNombreConto(panelAdmin.txtNombreCortoMedida.getText());
                    medidas.setIdMedidas(Integer.parseInt(panelAdmin.txtIdMedida.getText()));
                    if (medidasDao.modificar(medidas)) {
                        limpiarTable();
                        listarMedidas();
                        limpiar();
                        JOptionPane.showMessageDialog(null, "Medida actualizado");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al actualizar la medida");
                    }
                }

            }
        } else if (e.getSource() == panelAdmin.jMenuEliminarMedi) {
            if (panelAdmin.txtIdMedida.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila");
            } else {
                int id = Integer.parseInt(panelAdmin.txtIdMedida.getText());
                if (medidasDao.accion("Inactivo", id)) {
                    limpiarTable();
                    listarMedidas();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Medida eliminado !");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar la medida");
                }
            }
        } else if (e.getSource() == panelAdmin.jMenuReingresarMedi) {
            if (panelAdmin.txtIdMedida.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para reingresar");
            } else {
                int id = Integer.parseInt(panelAdmin.txtIdMedida.getText());
                if (medidasDao.accion("Activo", id)) {
                    limpiarTable();
                    listarMedidas();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Medida reingresado !");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al reigresar la medida");
                }

            }
        } else {
            limpiar();
        }
    }

    public void listarMedidas() {
        Tables color = new Tables();
        panelAdmin.TableMedida.setDefaultRenderer(panelAdmin.TableMedida.getColumnClass(0), color);
        List<Medidas> list = medidasDao.listaMedidases(panelAdmin.txtBuscarMedi.getText());
        modelo = (DefaultTableModel) panelAdmin.TableMedida.getModel();
        Object[] object = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            object[0] = list.get(i).getIdMedidas();
            object[1] = list.get(i).getNombre();
            object[2] = list.get(i).getNombreConto();
            object[3] = list.get(i).getEstado();
            modelo.addRow(object);
        }

        panelAdmin.TableMedida.setModel(modelo);
        JTableHeader header = panelAdmin.TableMedida.getTableHeader();
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

        panelAdmin.txtNombreMedida.setText("");
        panelAdmin.txtNombreCortoMedida.setText("");
        panelAdmin.txtIdMedida.setText("");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == panelAdmin.TableMedida) {
            int fila = panelAdmin.TableMedida.rowAtPoint(e.getPoint());
            panelAdmin.txtIdMedida.setText(panelAdmin.TableMedida.getValueAt(fila, 0).toString());
            panelAdmin.txtNombreMedida.setText(panelAdmin.TableMedida.getValueAt(fila, 1).toString());
            panelAdmin.txtNombreCortoMedida.setText(panelAdmin.TableMedida.getValueAt(fila, 2).toString());

            // panelAdmin.btnRegistrarCli.setEnabled(false);
        } else if (e.getSource() == panelAdmin.JLabelMedidas) {
            panelAdmin.jTabbedPane1.setSelectedIndex(5);
            limpiarTable();
            listarMedidas();
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
        if (e.getSource() == panelAdmin.txtBuscarMedi) {
            limpiar();
            limpiarTable();
            listarMedidas();
        }
    }
    
     //Metodo para el llenado del combo box
    private void llenarMedida() {
        List<Medidas> list = medidasDao.listaMedidases(panelAdmin.txtBuscarMedi.getText());
        
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getIdMedidas();
            String nombre = list.get(i).getNombre();
            panelAdmin.cbxMedidaPro.addItem(new Combo(id, nombre));
        }
    }

}
