package Controllers;

import Models.Cliente;
import DAO.ClienteDao;
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

public class ClientesControllers implements ActionListener, MouseListener, KeyListener {

    private Cliente cliente;
    private ClienteDao clienteDao;
    private PanelAdmin panelAdmin;
    DefaultTableModel modelo = new DefaultTableModel();

    public ClientesControllers(Cliente cliente, ClienteDao clienteDao, PanelAdmin panelAdmin) {
        this.cliente = cliente;
        this.clienteDao = clienteDao;
        this.panelAdmin = panelAdmin;

        this.panelAdmin.btnRegistrarCli.addActionListener(this);
        this.panelAdmin.btnModificarCli.addActionListener(this);
        this.panelAdmin.btnNuevoCli.addActionListener(this);
        
        this.panelAdmin.JMenuElimiarCli.addActionListener(this);
        this.panelAdmin.JMenuReingresarCli.addActionListener(this);
        
        this.panelAdmin.TableClientes.addMouseListener(this);
        
        this.panelAdmin.txtBuscarCli.addKeyListener(this);
        
        this.panelAdmin.JLabelClientes.addMouseListener(this);

        listarClientes();

    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelAdmin.btnRegistrarCli) {
            if (panelAdmin.txtNombreCli.getText().equals("")
                    || panelAdmin.txtTelefonoCli.getText().equals("")
                    || panelAdmin.txtDireccionCli.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                cliente.setNombre(panelAdmin.txtNombreCli.getText());
                cliente.setTelefono(panelAdmin.txtTelefonoCli.getText());
                cliente.setDireccion(panelAdmin.txtDireccionCli.getText());
                if (clienteDao.registrar(cliente)) {
                    limpiarTable();
                    listarClientes();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Cliente Registrado !");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar cliente");
                }
            }
        } else if (e.getSource() == panelAdmin.btnModificarCli) {
            if (panelAdmin.txtIdCli.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila");
            } else {
                if (panelAdmin.txtNombreCli.getText().equals("")
                        || panelAdmin.txtTelefonoCli.getText().equals("")
                        || panelAdmin.txtDireccionCli.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    cliente.setNombre(panelAdmin.txtNombreCli.getText());
                    cliente.setTelefono(panelAdmin.txtTelefonoCli.getText());
                    cliente.setDireccion(panelAdmin.txtDireccionCli.getText());
                    cliente.setIdCliente(Integer.parseInt(panelAdmin.txtIdCli.getText()));
                    if (clienteDao.modificar(cliente)) {
                        limpiarTable();
                        listarClientes();
                        limpiar();
                        JOptionPane.showMessageDialog(null, "Cliente modificado !");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al modificar el cliente");
                    }
                }
            }
        } else if(e.getSource() == panelAdmin.JMenuElimiarCli){
            if (panelAdmin.txtIdCli.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila");
            }else{
                  int id = Integer.parseInt(panelAdmin.txtIdCli.getText());
                if (clienteDao.accion("Inactivo", id)) {
                    limpiarTable();
                    listarClientes();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Usuario eliminado !");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el usuario");
                }
            }
        }else if (e.getSource() == panelAdmin.JMenuReingresarCli) {
            if (panelAdmin.txtIdCli.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para reingresar");
            } else {
                int id = Integer.parseInt(panelAdmin.txtIdCli.getText());
                if (clienteDao.accion("Activo", id)) {
                    limpiarTable();
                    listarClientes();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Cliente reingresado !");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al reigresar el cliente");
                }

            }
        }else{
            limpiar();
        }

    }

    public void listarClientes() {
        Tables color = new Tables();
        panelAdmin.TableClientes.setDefaultRenderer(panelAdmin.TableClientes.getColumnClass(0), color);
        List<Cliente> list = clienteDao.listaClientes(panelAdmin.txtBuscarCli.getText());
        modelo = (DefaultTableModel) panelAdmin.TableClientes.getModel();
        Object[] object = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            object[0] = list.get(i).getIdCliente();
            object[1] = list.get(i).getNombre();
            object[2] = list.get(i).getTelefono();
            object[3] = list.get(i).getDireccion();
            object[4] = list.get(i).getEstado();
            modelo.addRow(object);
        }

        panelAdmin.TableClientes.setModel(modelo);
        JTableHeader header = panelAdmin.TableClientes.getTableHeader();
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
    
      private void limpiar(){
        panelAdmin.txtNombreCli.setText("");
        panelAdmin.txtTelefonoCli.setText("");
        panelAdmin.txtDireccionCli.setText("");
        panelAdmin.txtIdCli.setText("");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == panelAdmin.TableClientes) {
            int fila = panelAdmin.TableClientes.rowAtPoint(e.getPoint());
            panelAdmin.txtIdCli.setText(panelAdmin.TableClientes.getValueAt(fila, 0).toString());
            panelAdmin.txtNombreCli.setText(panelAdmin.TableClientes.getValueAt(fila, 1).toString());
            panelAdmin.txtTelefonoCli.setText(panelAdmin.TableClientes.getValueAt(fila, 2).toString());
            panelAdmin.txtDireccionCli.setText(panelAdmin.TableClientes.getValueAt(fila, 3).toString());
            panelAdmin.btnRegistrarCli.setEnabled(false);
        }else if(e.getSource() == panelAdmin.JLabelClientes){
            panelAdmin.jTabbedPane1.setSelectedIndex(1);
            limpiarTable();
            listarClientes();
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
        if (e.getSource() == panelAdmin.txtBuscarCli) {
            limpiar();
            limpiarTable();
            listarClientes();
        }
    }
    
    
}
