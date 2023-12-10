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
	private static ArrayList<Persona> listaPersonas=new ArrayList<>();
	private ArrayList<Reserva> listaReservas=new ArrayList<>();
	private ArrayList<Devolucion> listaDevoluciones=new ArrayList<>();

	
	
	
	@Override
	public void leerArch() throws FileNotFoundException {
		
		Scanner scTextos = new Scanner(new File("textos.txt"));
		while (scTextos.hasNextLine()) {
			String lineaTexto = scTextos.nextLine();
			String[] partesTexto = lineaTexto.split(",");
			
			//aca podemos usar un metodo de los raros esos
			//para no tener q hacer 20 mil if
			String tipoLibro = partesTexto[4];
			//System.out.println(tipoLibro);
			//aca se agregan dependiendo del tipo a las diferentes clases
			//aca se agregan a la listaTextos
			
			
			
			//ya estaria cargado todos los textos ahora faltan los demas
		}
		scTextos.close();
		
		Scanner scPersonas = new Scanner(new File("personas.txt"));
		while (scPersonas.hasNextLine()) {
			String lineaPersona = scPersonas.nextLine();
			String[] partesPersona = lineaPersona.split(",");
			
			
			//lo mismo q recien
			String tipoPersona = partesPersona[3];
			//System.out.println(tipoPersona);
			switch (tipoPersona) {
				case ("Usuario"):
					//System.out.println("oal");
					Usuario u = new Usuario(partesPersona[0],partesPersona[1],partesPersona[2]);
					listaPersonas.add(u);
				
				
				case("Trabajador"):
					//System.out.println("aol");
					Trabajador t = new Trabajador(partesPersona[0],partesPersona[1],partesPersona[2]);
					listaPersonas.add(t);
					
			}
			
			//se agrega
			//se agrega a la listaPersona
			//personas listas ahora lo demas
			
			
			
		}
		scPersonas.close();
		
		Scanner scReservas = new Scanner(new File("reservas.txt"));
		while (scReservas.hasNextLine()) {
			String lineaReserva = scReservas.nextLine();
			String[] partesReserva = lineaReserva.split(",");
			
			//lo mismo q recien
			//se agrega
			//se agrega a la listareservas
			//Resrvas listas ahora lo demas
			
		}
		scReservas.close();
		
		
		Scanner scDevolucion = new Scanner(new File("devoluciones.txt"));
		while (scDevolucion.hasNextLine()) {
			String lineaDevolucion = scDevolucion.nextLine();
			String[] partesDevolucion = lineaDevolucion.split(",");
			
			//lo mismo q recien
			//se agrega
			//se agrega a la listaDevoluciones
			//Devoluciones  listas ahora lo demas
			
		}
		scDevolucion.close();
		
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
                boolean revisarDatosCorrectos =false;
                
                for (Persona p : listaPersonas) {
                	if (p.getRut().equals(rut) && p.getPass().equals(password)) {
                		revisarDatosCorrectos=true;
                	}
				}
                
                if(revisarDatosCorrectos) {
                	System.out.println("RUT ingresado: " + rut);
                	System.out.println("Contraseña ingresada: " + password);
                	
                	// Ejemplo de mensaje de bienvenida utilizando el RUT del usuario
                	String mensajeBienvenida = "¡Bienvenido, usuario con RUT: " + rut + "!";
                	JOptionPane.showMessageDialog(null, mensajeBienvenida);
                	
                	// Por ejemplo, podrías llamar a una función para verificar la contraseña o guardar el RUT y la contraseña en algún lugar.
                	
                }
                else {
            		String mensajeError = "Ingrese sus datos correctamente";
            		JOptionPane.showMessageDialog(null, mensajeError);
            	}
                
                
                
                
                
                
                
            }
        });
    }
	
	
	
}
