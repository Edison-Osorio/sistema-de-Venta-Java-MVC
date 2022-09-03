package Controllers;

// @author Edison Osorio
import DAO.UsuarioDao;
import Models.*;
import Views.FrmLogin;
import Views.PanelAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginControllers implements ActionListener {

    private Usuario usuario;
    private UsuarioDao usuarioDao;

    private FrmLogin viewsLogin;

    public LoginControllers() {
    }

    public LoginControllers(Usuario usuario, UsuarioDao usuarioDao, FrmLogin viewsLogin) {
        this.usuario = usuario;
        this.usuarioDao = usuarioDao;
        this.viewsLogin = viewsLogin;

        this.viewsLogin.btnLogin.addActionListener(this);
        this.viewsLogin.btnCancelar.addActionListener(this);
        this.viewsLogin.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewsLogin.btnLogin) {
            if (viewsLogin.txtUsuario.getText().equals("") || String.valueOf(viewsLogin.txtClave.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            } else {
                String usuarioLogin = viewsLogin.txtUsuario.getText();
                String clave = String.valueOf(viewsLogin.txtClave.getPassword());
                usuario = usuarioDao.login(usuarioLogin, clave);
                if (usuario.getUsuario() != null) {
                    PanelAdmin panelAdmin = new PanelAdmin();
                    panelAdmin.setVisible(true);
                    this.viewsLogin.dispose();
                } else {

                    JOptionPane.showMessageDialog(null, "Usuario o Contraseña incorrecta");
                }
            }
        } else {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro que desea salir", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (pregunta == 0) {
                System.exit(0);
            }
        }
    }

}
