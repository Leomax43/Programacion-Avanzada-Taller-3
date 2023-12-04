package taller3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SistemaImpls implements Sistema{

	@Override
	public void leerArch() {
		JFrame frame = new JFrame("Ingresar Datos");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
	}

	@Override
	public void Interfaz() {
		JFrame frame = new JFrame("Ingresar Datos");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
	}
	private static void placeComponents(JPanel panel) {
    	panel.setLayout(null);

        JLabel rutLabel = new JLabel("RUT:");
        rutLabel.setBounds(10, 20, 80, 25);
        panel.add(rutLabel);

        JTextField rutText = new JTextField(20);
        rutText.setBounds(100, 20, 165, 25);
        panel.add(rutText);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(100, 80, 80, 25);
        panel.add(submitButton);

        // Acción al presionar el botón
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rut = rutText.getText();
                String password = new String(passwordText.getPassword());

                // Aquí puedes usar los valores almacenados (rut y password) como desees
                System.out.println("RUT ingresado: " + rut);
                System.out.println("Contraseña ingresada: " + password);

                // Por ejemplo, podrías llamar a una función para verificar la contraseña o guardar el RUT y la contraseña en algún lugar.
            }
        });
    
    }
	
	
	
}
