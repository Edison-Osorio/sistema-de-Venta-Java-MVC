package Controllers;

import DAO.CategoriaDao;
import Models.Categoria;
import Models.Combo;
import Models.Medidas;
import Models.Tables;
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

public class CategoriaControllers implements ActionListener, MouseListener, KeyListener {

    private Categoria categoria;
    private CategoriaDao categoriaDao;
    private PanelAdmin panelAdmin;
    DefaultTableModel modelo = new DefaultTableModel();

    public CategoriaControllers(Categoria categoria, CategoriaDao categoriaDao, PanelAdmin panelAdmin) {
        this.categoria = categoria;
        this.categoriaDao = categoriaDao;
        this.panelAdmin = panelAdmin;

        this.panelAdmin.btnRegistrarCategoria.addActionListener(this);
        this.panelAdmin.btnModificarCategoria.addActionListener(this);
        this.panelAdmin.btnNuevaCategoria.addActionListener(this);

        this.panelAdmin.jMenuEliminarCat.addActionListener(this);
        this.panelAdmin.jMenuReingresarCat.addActionListener(this);
        this.panelAdmin.TableCategoria.addMouseListener(this);
        this.panelAdmin.txtBuscarCat.addKeyListener(this);
        this.panelAdmin.JLabelCategorias.addMouseListener(this);

        listarCategorias();
        
        llenarCategoria();
        AutoCompleteDecorator.decorate(panelAdmin.cbxCategoriaPro);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelAdmin.btnRegistrarCategoria) {
            if (panelAdmin.txtNombreCategoria.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son requeridos");
            } else {
                categoria.setNombre(panelAdmin.txtNombreCategoria.getText());
                if (categoriaDao.registrar(categoria)) {
                    limpiarTable();
                    listarCategorias();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Categorias registrado");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar una categoria");
                }
            }
        } else if (e.getSource() == panelAdmin.btnModificarCategoria) {
            if (panelAdmin.txtIdCategoria.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila");
            } else {
                if (panelAdmin.txtNombreCategoria.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son requeridos");
                } else {
                    categoria.setNombre(panelAdmin.txtNombreCategoria.getText());
                    categoria.setIdCategoria(Integer.parseInt(panelAdmin.txtIdCategoria.getText()));
                    if (categoriaDao.modificar(categoria)) {
                        limpiarTable();
                        listarCategorias();
                        limpiar();
                        JOptionPane.showMessageDialog(null, "Categorias actualizado");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al actualizar la categoria");
                    }
                }

            }
        } else if (e.getSource() == panelAdmin.jMenuEliminarCat) {
            if (panelAdmin.txtIdCategoria.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila");
            } else {
                int id = Integer.parseInt(panelAdmin.txtIdCategoria.getText());
                if (categoriaDao.accion("Inactivo", id)) {
                    limpiarTable();
                    listarCategorias();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Categorias eliminado!");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar la categorias");
                }
            }
        } else if (e.getSource() == panelAdmin.jMenuReingresarCat) {
            if (panelAdmin.txtIdCategoria.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para reingresar");
            } else {
                int id = Integer.parseInt(panelAdmin.txtIdCategoria.getText());
                if (categoriaDao.accion("Activo", id)) {
                    limpiarTable();
                    listarCategorias();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Catagoria reingresado !");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al reigresar la categoria");
                }

            }
        } else {
            limpiar();
        }
    }

    public void listarCategorias() {
        Tables color = new Tables();
        panelAdmin.TableCategoria.setDefaultRenderer(panelAdmin.TableCategoria.getColumnClass(0), color);
        List<Categoria> list = categoriaDao.listaCategoriaes(panelAdmin.txtBuscarCat.getText());
        modelo = (DefaultTableModel) panelAdmin.TableCategoria.getModel();
        Object[] object = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            object[0] = list.get(i).getIdCategoria();
            object[1] = list.get(i).getNombre();
            object[2] = list.get(i).getEstado();
            modelo.addRow(object);
        }

        panelAdmin.TableCategoria.setModel(modelo);
        JTableHeader header = panelAdmin.TableCategoria.getTableHeader();
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

        panelAdmin.txtNombreCategoria.setText("");
        panelAdmin.txtIdCategoria.setText("");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == panelAdmin.TableCategoria) {
            int fila = panelAdmin.TableCategoria.rowAtPoint(e.getPoint());
            panelAdmin.txtIdCategoria.setText(panelAdmin.TableCategoria.getValueAt(fila, 0).toString());
            panelAdmin.txtNombreCategoria.setText(panelAdmin.TableCategoria.getValueAt(fila, 1).toString());

            // panelAdmin.btnRegistrarCli.setEnabled(false);
        } else if (e.getSource() == panelAdmin.JLabelCategorias) {
            panelAdmin.jTabbedPane1.setSelectedIndex(4);
            limpiarTable();
            listarCategorias();
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
        if (e.getSource() == panelAdmin.txtBuscarCat) {
            limpiar();
            limpiarTable();
            listarCategorias();
        }
    }
    
     //Metodo para el llenado del combo box
    private void llenarCategoria() {
        List<Categoria> list = categoriaDao.listaCategoriaes(panelAdmin.txtBuscarCat.getText());
        
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getIdCategoria();
            String nombre = list.get(i).getNombre();
            panelAdmin.cbxCategoriaPro.addItem(new Combo(id, nombre));
        }
    }

}
