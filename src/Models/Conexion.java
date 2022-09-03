package Models;

// @author Edison Osorio
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

    Connection con;

    public Connection getConexion() {
        try {
            String database = "jdbc:mysql://localhost:3306/sistema_venta?useSSL=false&useTimezone=true&serverTimezone=UTC";
            con = DriverManager.getConnection(database, "root", "mysql");
            return con;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            e.printStackTrace(System.out);
        }
        return null;
    }

}
