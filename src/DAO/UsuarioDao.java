package DAO;

// @author Edison Osorio
import Models.Conexion;
import Models.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioDao {

    Conexion conexion = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public Usuario login(String usuarioLogin, String clave) {
        String sql = "SELECT * FROM usuarios WHERE usuario= ? AND clave = ?";
        Usuario usuario = new Usuario();
        try {
            con = conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, usuarioLogin);
            ps.setString(2, clave);

            rs = ps.executeQuery();

            if (rs.next()) {
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCaja(rs.getString("caja"));
                usuario.setRol(rs.getString("rol"));
                usuario.setEstado(rs.getString("estado"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            e.printStackTrace(System.out);
        }
        return usuario;
    }

    public boolean registrar(Usuario users) {

        String sql = "INSERT INTO usuarios (usuario, nombre, clave, caja,rol) VALUES(?,?,?,?,?)";

        try {
            con = conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, users.getUsuario());
            ps.setString(2, users.getNombre());
            ps.setString(3, users.getClave());
            ps.setString(4, users.getCaja());
            ps.setString(5, users.getRol());

            ps.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            e.printStackTrace(System.out);
            return false;
        }

    }

    public List listaUsuarios(String valor) {
        List<Usuario> listUser = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY estado ASC";
        String buscar = "SELECT * FROM usuarios WHERE usuario LIKE '%" + valor + "%' OR nombre LIKE '%" + valor + "%'";

        try {
            con = conexion.getConexion();
            if (valor.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
            } else {
                ps = con.prepareStatement(buscar);
                rs = ps.executeQuery();
            }

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCaja(rs.getString("caja"));
                usuario.setRol(rs.getString("rol"));
                usuario.setEstado(rs.getString("estado"));

                listUser.add(usuario);
            }

            return listUser;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            e.printStackTrace(System.out);
            return null;
        }
    }

    public boolean modificar(Usuario users) {

        String sql = "UPDATE usuarios SET usuario=?, nombre=?,caja=?, rol=? WHERE id_usuario = ? ";

        try {
            con = conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, users.getUsuario());
            ps.setString(2, users.getNombre());
            ps.setString(3, users.getCaja());
            ps.setString(4, users.getRol());
            ps.setInt(5, users.getIdUsuario());

            ps.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            e.printStackTrace(System.out);
            return false;
        }

    }

    public boolean accion(String estado, int id) {
        String sql = "UPDATE usuarios SET estado = ? WHERE id_usuario = ?";
        try {
            con = conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, id);

            ps.execute();
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

}
