package Controllers;

import DAO.ProductoDao;
import Models.Combo;
import Models.Producto;
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

public class ProductosControllers implements ActionListener, MouseListener, KeyListener {

    private Producto producto;
    private ProductoDao productoDao;
    private PanelAdmin panelAdmin;
    DefaultTableModel modelo = new DefaultTableModel();

    public ProductosControllers(Producto producto, ProductoDao productoDao, PanelAdmin panelAdmin) {
        this.producto = producto;
        this.productoDao = productoDao;
        this.panelAdmin = panelAdmin;

        this.panelAdmin.btnRegistrarPro.addActionListener(this);
        this.panelAdmin.btnModificarPro.addActionListener(this);
        this.panelAdmin.btnNuevoPro.addActionListener(this);

        this.panelAdmin.jMenuEliminarPro.addActionListener(this);
        this.panelAdmin.jMenuReingresarPro.addActionListener(this);

        this.panelAdmin.TableProductos.addMouseListener(this);
        this.panelAdmin.JLabelProductos.addMouseListener(this);

        listarProducts();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelAdmin.btnRegistrarPro) {
            if (panelAdmin.txtCodigoPro.getText().equals("")
                    || panelAdmin.txtDescripcionPro.getText().equals("")
                    || panelAdmin.txtPrecioCompraPro.getText().equals("")
                    || panelAdmin.txtPrecioVentaPro.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                producto.setCodigo(panelAdmin.txtCodigoPro.getText());
                producto.setDescripcion(panelAdmin.txtDescripcionPro.getText());
                producto.setPrecioCompra(Double.parseDouble(panelAdmin.txtPrecioCompraPro.getText()));
                producto.setPrecioVenta(Double.parseDouble(panelAdmin.txtPrecioVentaPro.getText()));
                Combo itemP = (Combo) panelAdmin.cbxProveedorPro.getSelectedItem();
                Combo itemM = (Combo) panelAdmin.cbxMedidaPro.getSelectedItem();
                Combo itemC = (Combo) panelAdmin.cbxCategoriaPro.getSelectedItem();
                producto.setIdProveedor(itemP.getId());
                producto.setIdMedida(itemM.getId());
                producto.setIdCategoria(itemC.getId());
                if (productoDao.registrar(producto)) {
                    limpiarTable();
                    listarProducts();
                    // limpiar();
                    JOptionPane.showMessageDialog(null, "Producto registrado con Exito !");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar el producto");

                }
            }
        } else if (e.getSource() == panelAdmin.btnModificarPro) {
            if (panelAdmin.txtCodigoPro.getText().equals("")
                    || panelAdmin.txtDescripcionPro.getText().equals("")
                    || panelAdmin.txtPrecioCompraPro.getText().equals("")
                    || panelAdmin.txtPrecioVentaPro.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {

                producto.setCodigo(panelAdmin.txtCodigoPro.getText());
                producto.setDescripcion(panelAdmin.txtDescripcionPro.getText());
                producto.setPrecioCompra(Double.parseDouble(panelAdmin.txtPrecioCompraPro.getText()));
                producto.setPrecioVenta(Double.parseDouble(panelAdmin.txtPrecioVentaPro.getText()));
                Combo itemP = (Combo) panelAdmin.cbxProveedorPro.getSelectedItem();
                Combo itemM = (Combo) panelAdmin.cbxMedidaPro.getSelectedItem();
                Combo itemC = (Combo) panelAdmin.cbxCategoriaPro.getSelectedItem();
                producto.setIdProveedor(itemP.getId());
                producto.setIdMedida(itemM.getId());
                producto.setIdCategoria(itemC.getId());

                producto.setIdProducto(Integer.parseInt(panelAdmin.txtIdPro.getText()));

                if (productoDao.modificar(producto)) {
                    limpiarTable();
                    listarProducts();
//                    limpiar();
                    JOptionPane.showMessageDialog(null, "Producto modificado con Exito !");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al modificar el producto");

                }
            }
        } else if (e.getSource() == panelAdmin.jMenuEliminarPro) {
            if (panelAdmin.txtIdPro.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
            } else {
                int id = Integer.parseInt(panelAdmin.txtIdPro.getText());
                if (productoDao.accion("Inactivo", id)) {
                    limpiarTable();
                    listarProducts();
//                    limpiar();
                    JOptionPane.showMessageDialog(null, "Producto eliminado !");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el producto");
                }

            }
        } else if (e.getSource() == panelAdmin.jMenuReingresarPro) {
            if (panelAdmin.txtIdPro.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para reingresar");
            } else {
                int id = Integer.parseInt(panelAdmin.txtIdPro.getText());
                if (productoDao.accion("Activo", id)) {
                    limpiarTable();
                    listarProducts();
//                    limpiar();
                    JOptionPane.showMessageDialog(null, "Producto reingresado !");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al reigresar el producto");
                }

            }
        } else {
//            limpiar();
        }

    }

    public void listarProducts() {
        Tables color = new Tables();
        panelAdmin.TableProductos.setDefaultRenderer(panelAdmin.TableProductos.getColumnClass(0), color);
        List<Producto> list = productoDao.listaProductos(panelAdmin.txtBuscarProducto.getText());
        modelo = (DefaultTableModel) panelAdmin.TableProductos.getModel();
        Object[] object = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            object[0] = list.get(i).getIdProducto();
            object[1] = list.get(i).getCodigo();
            object[2] = list.get(i).getDescripcion();
            object[3] = list.get(i).getPrecioVenta();
            object[4] = list.get(i).getCantidad();
            object[5] = list.get(i).getEstado();
            modelo.addRow(object);
        }

        panelAdmin.TableUsers.setModel(modelo);
        JTableHeader header = panelAdmin.TableUsers.getTableHeader();
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

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == panelAdmin.TableProductos) {
            int fila = panelAdmin.TableProductos.rowAtPoint(e.getPoint());
            panelAdmin.txtIdPro.setText(panelAdmin.TableProductos.getValueAt(fila, 0).toString());

            producto = productoDao.buscarPro(Integer.parseInt(panelAdmin.txtIdPro.getText()));

            panelAdmin.txtCodigoPro.setText(producto.getCodigo());
            panelAdmin.txtDescripcionPro.setText(producto.getDescripcion());
            panelAdmin.txtPrecioCompraPro.setText("" + producto.getPrecioCompra());
            panelAdmin.txtPrecioVentaPro.setText("" + producto.getPrecioVenta());
            panelAdmin.cbxProveedorPro.setSelectedItem(new Combo(producto.getIdProveedor(), producto.getProveedor()));
            panelAdmin.cbxMedidaPro.setSelectedItem(new Combo(producto.getIdMedida(), producto.getMedida()));
            panelAdmin.cbxCategoriaPro.setSelectedItem(new Combo(producto.getIdCategoria(), producto.getCategoria()));
            

        } else if (e.getSource() == panelAdmin.JLabelProductos) {
             panelAdmin.jTabbedPane1.setSelectedIndex(0);
             limpiarTable();
             listarProducts();
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
    }

}
