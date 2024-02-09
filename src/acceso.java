import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class acceso extends JFrame {

    private JPanel panel1;
    private JButton verificacionButton;
    private JTextField valusu;
    private JPasswordField valcontra;
    private JButton ingresarButton;
    private JButton registroButton;

    public acceso() {
        super("Conexion con SQL");
        setContentPane(panel1);
        verificacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conexion_base();
            }
        });
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        registroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/capacitacion";
                String usuarioDB = "root";
                String contraseñaDB = "123456";

                try (Connection conexion = DriverManager.getConnection(url, usuarioDB, contraseñaDB)) {
                    Registro registro = new Registro(conexion); // Pasa la conexión al constructor
                    registro.setSize(600,600);
                    registro.setVisible(true);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void conexion_base() {
        String url = "jdbc:mysql://localhost:3306/capacitacion";
        String usuarioDB = "root";
        String contraseñaDB = "123456";

        try (Connection conexion = DriverManager.getConnection(url, usuarioDB, contraseñaDB)) {
            JOptionPane.showMessageDialog(null, "Conexion no establecida, falla en la base de datos", "ERROR", JOptionPane.ERROR_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Conexion no establecida, falla en la base de datos", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void login() {
        String url = "jdbc:mysql://localhost:3306/CAPACITACION";
        String usuarioDB = "root";
        String contraseñaDB = "123456";

        String nombreUsuario = valusu.getText().trim();
        String cedula = new String(valcontra.getPassword()).trim();

        try (Connection conexion = DriverManager.getConnection(url, usuarioDB, contraseñaDB)) {
            String sql = "SELECT * FROM usuarios WHERE nombre=? AND cedula=?";
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, nombreUsuario);
            pstmt.setString(2, cedula);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la consulta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
