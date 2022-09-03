package DAO;

import Models.Conexion;
import Models.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CategoriaDao {

    Conexion conexion = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean registrar(Categoria categoria) {

        String sql = "INSERT INTO categorias (categoria) VALUES(?)";

        try {
            con = conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, categoria.getNombre());

            ps.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            e.printStackTrace(System.out);
            return false;
        }

    }

    public List listaCategoriaes(String valor) {
        List<Categoria> listCategoria = new ArrayList<>();
        String sql = "SELECT * FROM categorias ORDER BY estado ASC";
        String buscar = "SELECT * FROM categoria WHERE categoria LIKE '%" + valor + "%'";

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
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNombre(rs.getString("categoria"));
                categoria.setEstado(rs.getString("estado"));

                listCategoria.add(categoria);
            }

            return listCategoria;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            e.printStackTrace(System.out);
            return null;
        }
    }

    public boolean modificar(Categoria categoria) {

        String sql = "UPDATE categorias SET categoria=? WHERE id_categoria = ? ";

        try {
            con = conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, categoria.getNombre());
            ps.setInt(2, categoria.getIdCategoria());

            ps.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            e.printStackTrace(System.out);
            return false;
        }

    }

    public boolean accion(String estado, int id) {
        String sql = "UPDATE categorias SET estado = ? WHERE id_categoria = ?";
        try {
            con = conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, id);

            ps.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }
}
