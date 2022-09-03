package Controllers;

// @author Edison Osorio
import Models.Tables;
import Models.Usuario;
import DAO.UsuarioDao;
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

public class UsuariosControllers implements ActionListener, MouseListener, KeyListener {

    private Usuario usuario;
    private UsuarioDao usuarioDao;

    private PanelAdmin panelAdmin;

    DefaultTableModel modelo = new DefaultTableModel();

    public UsuariosControllers(Usuario usuario, UsuarioDao usuarioDao, PanelAdmin panelAdmin) {
        this.usuario = usuario;
        this.usuarioDao = usuarioDao;
        this.panelAdmin = panelAdmin;
        this.panelAdmin.btnRegistrarUser.addActionListener(this);
        this.panelAdmin.btnModificarUser.addActionListener(this);
        this.panelAdmin.btnNuevoUser.addActionListener(this);
        this.panelAdmin.JMenuEliminarUser.addActionListener(this);
        this.panelAdmin.JMenuReingresarUser.addActionListener(this);
        this.panelAdmin.txtBuscarUsers.addKeyListener(this);
        this.panelAdmin.TableUsers.addMouseListener(this);
        
        this.panelAdmin.JLabelUser.addMouseListener(this);
        listarUsuarios();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelAdmin.btnRegistrarUser) {
            if (panelAdmin.txtBuscarUsers.getText().equals("")
                    || panelAdmin.txtNombreUser.getText().equals("")
                    || String.valueOf(panelAdmin.txtClaveUser.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                usuario.setUsuario(panelAdmin.txtUsuarioUser.getText());
                usuario.setNombre(panelAdmin.txtNombreUser.getText());
                usuario.setClave(String.valueOf(panelAdmin.txtClaveUser.getPassword()));
                usuario.setCaja(panelAdmin.cbxCajaUser.getSelectedItem().toString());
                usuario.setRol(panelAdmin.cbxRolUser.getSelectedItem().toString());
                if (usuarioDao.registrar(usuario)) {
                     limpiarTable();
                    listarUsuarios();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Usuario registrado con Exito !");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar el usuario");

                }
            }
        } else if (e.getSource() == panelAdmin.btnModificarUser) {
            if (panelAdmin.txtBuscarUsers.getText().equals("")
                    || panelAdmin.txtNombreUser.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                usuario.setUsuario(panelAdmin.txtUsuarioUser.getText());
                usuario.setNombre(panelAdmin.txtNombreUser.getText());
                usuario.setCaja(panelAdmin.cbxCajaUser.getSelectedItem().toString());
                usuario.setRol(panelAdmin.cbxRolUser.getSelectedItem().toString());
                usuario.setIdUsuario(Integer.parseInt(panelAdmin.txtIdUser.getText()));
                if (usuarioDao.modificar(usuario)) {
                    limpiarTable();
                    listarUsuarios();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Usuario modificado con Exito !");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al modificar el usuario");

                }
            }
        } else if (e.getSource() == panelAdmin.JMenuEliminarUser) {
            if (panelAdmin.txtIdUser.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
            } else {
                int id = Integer.parseInt(panelAdmin.txtIdUser.getText());
                if (usuarioDao.accion("Inactivo", id)) {
                    limpiarTable();
                    listarUsuarios();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Usuario eliminado !");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el usuario");
                }

            }
        } else if (e.getSource() == panelAdmin.JMenuReingresarUser) {
            if (panelAdmin.txtIdUser.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para reingresar");
            } else {
                int id = Integer.parseInt(panelAdmin.txtIdUser.getText());
                if (usuarioDao.accion("Activo", id)) {
                    limpiarTable();
                    listarUsuarios();
                    limpiar();
                    JOptionPane.showMessageDialog(null, "Usuario reingresado !");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al reigresarel usuario");
                }

            }
        }else {
            limpiar();
        }

    }

    public void listarUsuarios() {
        Tables color = new Tables();
        panelAdmin.TableUsers.setDefaultRenderer(panelAdmin.TableUsers.getColumnClass(0), color);
        List<Usuario> list = usuarioDao.listaUsuarios(panelAdmin.txtBuscarUsers.getText());
        modelo = (DefaultTableModel) panelAdmin.TableUsers.getModel();
        Object[] object = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            object[0] = list.get(i).getIdUsuario();
            object[1] = list.get(i).getUsuario();
            object[2] = list.get(i).getNombre();
            object[3] = list.get(i).getRol();
            object[4] = list.get(i).getCaja();
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
        if (e.getSource() == panelAdmin.TableUsers) {
            int fila = panelAdmin.TableUsers.rowAtPoint(e.getPoint());
            panelAdmin.txtIdUser.setText(panelAdmin.TableUsers.getValueAt(fila, 0).toString());
            panelAdmin.txtUsuarioUser.setText(panelAdmin.TableUsers.getValueAt(fila, 1).toString());
            panelAdmin.txtNombreUser.setText(panelAdmin.TableUsers.getValueAt(fila, 2).toString());
            panelAdmin.cbxCajaUser.setSelectedItem(panelAdmin.TableUsers.getValueAt(fila, 3).toString());
            panelAdmin.cbxRolUser.setSelectedItem(panelAdmin.TableUsers.getValueAt(fila, 4).toString());
            panelAdmin.txtClaveUser.setEnabled(false);
            panelAdmin.btnRegistrarUser.setEnabled(false);
        }else if(e.getSource() == panelAdmin.JLabelUser){
            panelAdmin.jTabbedPane1.setSelectedIndex(3);
            limpiarTable();
            listarUsuarios();
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
        if (e.getSource() == panelAdmin.txtBuscarUsers) {
            limpiarTable();
            listarUsuarios();
        }
    }
    
    
    private void limpiar(){
        panelAdmin.txtIdUser.setText("");
        panelAdmin.txtUsuarioUser.setText("");
        panelAdmin.txtNombreUser.setText("");
        panelAdmin.txtClaveUser.setText("");
    }

}
