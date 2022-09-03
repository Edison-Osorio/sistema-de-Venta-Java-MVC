package DAO;

import Models.Cliente;
import Models.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ClienteDao {

    Conexion conexion = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean registrar(Cliente cliente) {

        String sql = "INSERT INTO clientes (nombre, telefono, direccion) VALUES(?,?,?)";

        try {
            con = conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getTelefono());
            ps.setString(3, cliente.getDireccion());

            ps.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            e.printStackTrace(System.out);
            return false;
        }

    }

    public List listaClientes(String valor) {
        List<Cliente> listClient = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY estado ASC";
        String buscar = "SELECT * FROM clientes WHERE nombre LIKE '%" + valor + "%' OR telefono LIKE '%" + valor + "%'";

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
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setEstado(rs.getString("estado"));

                listClient.add(cliente);
            }

            return listClient;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            e.printStackTrace(System.out);
            return null;
        }
    }

    public boolean modificar(Cliente client) {

        String sql = "UPDATE clientes SET nombre=?, telefono=?,direccion=? WHERE id_cliente = ? ";

        try {
            con = conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, client.getNombre());
            ps.setString(2, client.getTelefono());
            ps.setString(3, client.getDireccion());
            ps.setInt(4, client.getIdCliente());

            ps.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            e.printStackTrace(System.out);
            return false;
        }

    }

    public boolean accion(String estado, int id) {
        String sql = "UPDATE clientes SET estado = ? WHERE id_cliente = ?";
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
