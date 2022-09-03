package DAO;

import Models.Conexion;
import Models.Medidas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MedidasDao {

    Conexion conexion = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean registrar(Medidas medidas) {

        String sql = "INSERT INTO medidas (medida,nombre_corto) VALUES(?,?)";

        try {
            con = conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, medidas.getNombre());
            ps.setString(2, medidas.getNombreConto());

            ps.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            e.printStackTrace(System.out);
            return false;
        }

    }

    public List listaMedidases(String valor) {
        List<Medidas> listMedidas = new ArrayList<>();
        String sql = "SELECT * FROM medidas ORDER BY estado ASC";
        String buscar = "SELECT * FROM medidas WHERE medida LIKE '%" + valor + "%' OR nombre_corto LIKE '%" + valor + "%'";

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
                Medidas medidas = new Medidas();
                medidas.setIdMedidas(rs.getInt("id_medidas"));
                medidas.setNombre(rs.getString("medida"));
                medidas.setNombreConto(rs.getString("nombre_corto"));
                medidas.setEstado(rs.getString("estado"));

                listMedidas.add(medidas);
            }

            return listMedidas;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            e.printStackTrace(System.out);
            return null;
        }
    }

    public boolean modificar(Medidas medidas) {

        String sql = "UPDATE medidas SET medida=?, nombre_corto=? WHERE id_medidas = ? ";

        try {
            con = conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, medidas.getNombre());
            ps.setString(2, medidas.getNombreConto());
            ps.setInt(3, medidas.getIdMedidas());

            ps.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            e.printStackTrace(System.out);
            return false;
        }

    }

    public boolean accion(String estado, int id) {
        String sql = "UPDATE medidas SET estado = ? WHERE id_medidas = ?";
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
