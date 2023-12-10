package taller3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SistemaImpls implements Sistema{
	private ArrayList<Texto> listaTextos=new ArrayList<>();
	
	
	
	
	@Override
	public void leerArch() throws FileNotFoundException {
		Scanner sc = new Scanner(new File("textos.txt"));
		while (sc.hasNextLine()) {
			String linea = sc.nextLine();
			System.out.println(linea);
			
			
			
		}
		
		
		
		
	}

	

	@Override
	public void Interfaz() {
		JFrame frame = new JFrame("Ingresar Datos");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
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
                char[] passwordChars = passwordText.getPassword();
                String password = new String(passwordChars);

                // Aquí puedes usar los valores almacenados (rut y password) como desees
                System.out.println("RUT ingresado: " + rut);
                System.out.println("Contraseña ingresada: " + password);

                // Ejemplo de mensaje de bienvenida utilizando el RUT del usuario
                String mensajeBienvenida = "¡Bienvenido, usuario con RUT: " + rut + "!";
                JOptionPane.showMessageDialog(null, mensajeBienvenida);

                // Por ejemplo, podrías llamar a una función para verificar la contraseña o guardar el RUT y la contraseña en algún lugar.
            }
        });
    }
	
	
	
}
