package DAO;

import Models.Conexion;
import Models.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProductoDao {

    Conexion conexion = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean registrar(Producto producto) {

        String sql = "INSERT INTO productos (codigo, descripcion, precio_compra, precio_venta, id_proveedor, id_medida, id_categoria) VALUES(?,?,?,?,?,?,?)";

        try {
            con = conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecioCompra());
            ps.setDouble(4, producto.getPrecioVenta());
            ps.setInt(5, producto.getIdProveedor());
            ps.setInt(6, producto.getIdMedida());
            ps.setInt(7, producto.getIdCategoria());

            ps.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            e.printStackTrace(System.out);
            return false;
        }

    }

    public List listaProductos(String valor) {
        List<Producto> listProduct = new ArrayList<>();
        String sql = "SELECT * FROM productos ORDER BY estado ASC";
        String buscar = "SELECT * FROM productos WHERE codigo LIKE '%" + valor + "%' OR descripcio LIKE '%" + valor + "%'";

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
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setCodigo(rs.getString("codigo"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecioVenta(rs.getDouble("precio_venta"));
                producto.setCantidad(rs.getInt("cantidad"));
                producto.setEstado(rs.getString("estado"));

                listProduct.add(producto);
            }

            return listProduct;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            e.printStackTrace(System.out);
            return null;
        }
    }

    public boolean modificar(Producto producto) {

        String sql = "UPDATE productos SET codigo=?, descripcion=?,precio_compra=?, precio_venta=?,id_proveedor=?, id_medida=?, id_categoria=? WHERE id_producto = ? ";

        try {
            con = conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecioCompra());
            ps.setDouble(4, producto.getPrecioVenta());
            ps.setInt(5, producto.getIdProveedor());
            ps.setInt(6, producto.getIdMedida());
            ps.setInt(7, producto.getIdCategoria());
            ps.setInt(8, producto.getIdProducto());

            ps.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            e.printStackTrace(System.out);
            return false;
        }

    }

    public boolean accion(String estado, int id) {
        String sql = "UPDATE productos SET estado = ? WHERE id_producto = ?";
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

    public Producto buscarPro(int id) {
        String sql = "SELECT p.*, pr.id_proveedor, pr.proveedor, m.id_medidas, m.medida, c.id_categoria,c.categoria FROM productos p INNER JOIN proveedor pr ON p.id_proveedor = pr.id_proveedor INNER JOIN medidas m ON  p.id_medida = m.id_medidas INNER JOIN categorias c ON p.id_categoria = c.id_categoria WHERE p.id_producto = ?";
        Producto producto = new Producto();
        try {
            con = conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto.setCodigo(rs.getString("codigo"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecioCompra(rs.getDouble("precio_compra"));
                producto.setPrecioVenta(rs.getDouble("precio_venta"));
                producto.setIdProveedor(rs.getInt("id_proveedor"));
                producto.setIdMedida(rs.getInt("id_medida"));
                producto.setIdCategoria(rs.getInt("id_categoria"));
                producto.setProveedor(rs.getString("proveedor"));
                producto.setMedida(rs.getString("medida"));
                producto.setCategoria(rs.getString("categoria"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return producto;
    }
}
