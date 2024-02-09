import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Registro extends JFrame {
    private JTextField txtnombre;
    private JTextField txtcodigo;
    private JTextField txtcedula;
    private JTextField txtcontraseña;
    private JButton registrarseButton;
    private JPanel panelregistro;
    private JComboBox comboBox1;
    private Connection conexion;

    public Registro(Connection conexion) {
        super("Registro");
        setContentPane(panelregistro);
        this.conexion = conexion;
        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombrecito = txtnombre.getText();
                String codiguito = txtcodigo.getText();
                String cedulita = txtcedula.getText();
                String contraseñita = txtcontraseña.getText();

                insertarDatos(conexion, nombrecito, codiguito, cedulita, contraseñita);
            }
        });
    }
    public void insertarDatos(Connection conexion, String nombre, String codigo, String cedula, String contraseña) {
        String query = "INSERT INTO usuarios (nombre, codigo, cedula, contraseña) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, codigo);
            preparedStatement.setString(3, cedula);
            preparedStatement.setString(4, contraseña);

            int filasInsertadas = preparedStatement.executeUpdate();

            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(null, "Datos insertados correctamente", "EXITO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se han insertado datos", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL EJECUTAR LA CONSULTA: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

}
