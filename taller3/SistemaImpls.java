package taller3;

import java.awt.Component;
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
import javax.swing.SwingUtilities;

public   class SistemaImpls implements Sistema{
	private static SistemaImpls instance;
	
	private ArrayList<Texto> listaTextos=new ArrayList<>();
	private static ArrayList<Persona> listaPersonas=new ArrayList<>();
	private ArrayList<Reserva> listaReservas=new ArrayList<>();
	private ArrayList<Devolucion> listaDevoluciones=new ArrayList<>();

	private SistemaImpls() {}
	
	public static SistemaImpls getInstancia(){
		if (instance==null) {
			instance = new SistemaImpls();
		}
		return instance;
	}
	
	
	
	
	@Override
	public void leerArch() throws FileNotFoundException {
		
		Scanner scTextos = new Scanner(new File("textos.txt"));
		while (scTextos.hasNextLine()) {
			String lineaTexto = scTextos.nextLine();
			String[] partesTexto = lineaTexto.split(",");
			
			//aca podemos usar un metodo de los raros esos
			//para no tener q hacer 20 mil if

			//Pero para que exactamente se necesita hacer un metodo aca? No entiendo, me eche progra
			String tipoLibro = partesTexto[4];
			//System.out.println(tipoLibro);
			//aca se agregan dependiendo del tipo a las diferentes clases
			//aca se agregan a la listaTextos
			if(partesTexto[4].equals("Libro")) {
				listaTextos.add(new Libro(Integer.parseInt(partesTexto[0]), Integer.parseInt(partesTexto[1]), partesTexto[2], partesTexto[3], partesTexto[4], partesTexto[5], partesTexto[6], partesTexto[7]));
			}else if(partesTexto[4].equals("Comic")) {
				listaTextos.add(new Comic(Integer.parseInt(partesTexto[0]), Integer.parseInt(partesTexto[1]), partesTexto[2], partesTexto[3], partesTexto[4], partesTexto[5], partesTexto[6], partesTexto[7]));
			}else if(partesTexto[4].equals("Apunte")) {
				listaTextos.add(new Apunte(Integer.parseInt(partesTexto[0]), Integer.parseInt(partesTexto[1]), partesTexto[2], partesTexto[3], partesTexto[4], partesTexto[5], partesTexto[6]));
			}else if(partesTexto[4].equals("Paper")) {
				listaTextos.add(new Paper(Integer.parseInt(partesTexto[0]), Integer.parseInt(partesTexto[1]), partesTexto[2], partesTexto[3], partesTexto[4], partesTexto[5], partesTexto[6]));
			}
			
			
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
					Usuario u = new Usuario(partesPersona[0],partesPersona[1],partesPersona[2],tipoPersona);
					listaPersonas.add(u);
				
				
				case("Trabajador"):
					//System.out.println("aol");
					Trabajador t = new Trabajador(partesPersona[0],partesPersona[1],partesPersona[2],tipoPersona);
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
			listaReservas.add(new Reserva(Integer.parseInt(partesReserva[0]), partesReserva[1], Integer.parseInt(partesReserva[2]), partesReserva[3], partesReserva[4]));
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
			listaDevoluciones.add(new Devolucion(Integer.parseInt(partesDevolucion[0]), Integer.parseInt(partesDevolucion[1]), partesDevolucion[2], partesDevolucion[3], partesDevolucion[4]));
			//Devoluciones  listas ahora lo demas
			
		}
		scDevolucion.close();
		
	}

	

	@Override
	public void Interfaz() {
		JFrame frame = new JFrame("Ingresar Datos");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
        
       
	}
	private static void placeComponents(JPanel panel) {
    	panel.setLayout(null);

        JLabel rutLabel = new JLabel("RUT(12345678-9): ");
        rutLabel.setBounds(10, 20, 800, 25);
        panel.add(rutLabel);

        JTextField rutText = new JTextField(20);
        rutText.setBounds(200, 20, 165, 25);
        panel.add(rutText);
        
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(200, 50, 165, 25);
        panel.add(passwordText);
        
        JLabel fechaLabel = new JLabel("Ingrese la fecha(AAAA/MM/DD):");
        fechaLabel.setBounds(10, 80, 800, 25);
        panel.add(fechaLabel);

        JTextField fechaActual = new JTextField(20);
        fechaActual.setBounds(200, 80, 165, 25);
        panel.add(fechaActual);
        
        JButton button = new JButton("Submit");
        button.setBounds(200, 120, 80, 25);
        panel.add(button);
        
        
        
        
        // Acción al presionar el botón
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rut = rutText.getText();
                char[] passwordChars = passwordText.getPassword();
                String password = new String(passwordChars);
                String fecha = fechaActual.getText();

                
                
                // Aquí puedes usar los valores almacenados (rut y password) como desees
                boolean revisarDatosCorrectos =false;
                
                for (Persona p : listaPersonas) {
                	if (p.getRut().equals(rut) && p.getPass().equals(password)) {
                		revisarDatosCorrectos=true;
                	}
				}
                System.out.println(fecha);
                if(revisarDatosCorrectos && fecha!=null) {
                	
                	System.out.println("RUT ingresado: " + rut);
                	System.out.println("Contraseña ingresada: " + password);
                	String mensajeBienvenida = "¡Bienvenido, usuario con RUT: " + rut + "!";
                	JOptionPane.showMessageDialog(null, mensajeBienvenida);
                	//aca se prosigue con lo q se deba hacer
                	//comandos para los clientes y/o trabajadores
                	//menuCliente();
                	//menuTrabajador();
                	String a = getTipoPersona(rut);
                	System.out.println(a);
                	if (a.equals("Usuario")) {
                		System.out.println("usuraior");
                		
                		/*
                		 * 
                		 * 
                		 * 
                		 * 
                		 * nosesise hace asi
                		 */
                		Sistema s = SistemaImpls.getInstancia();
						s.menuCliente();
                		
                	}
                	else if(a.equals("Trabajador")){
                		System.out.println("oal");
                		
                		/*
                		 * 
                		 * advertencia
                		 * AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
                		 */
                		Sistema s = SistemaImpls.getInstancia();
                		s.menuTrabajador();
                		
                	}
                	else {
                		System.out.println("esto no deberia pasar");

                	}
                	
                }
                
                //solo se revisa la fecha ya que los demas datos se revisan en la lectura de la lista
                else if (revisarDatosCorrectos==false && fecha!= null) {
                	JFrame frameRegistro = new JFrame("Ingresar Datos");
                    frameRegistro.setSize(350, 200);
                    frameRegistro.setLocationRelativeTo(null);
                	JPanel panelRegistro = new JPanel();
                    frameRegistro.add(panelRegistro);
					SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
                	agregarBotonSiNo(panelRegistro);
                    frameRegistro.setVisible(true);

                	
                	
                }
                else {
            		String mensajeError = "Ingrese sus datos correctamente / Ingrese todos los datos";
            		JOptionPane.showMessageDialog(null, mensajeError);
            	}
                
                
                
                
                
                
                
            }

			private String getTipoPersona(String rut) {
				for (Persona p : listaPersonas) {
					if (p.getRut().equals(rut)) {
						return p.getTipoPersona();
					}
				}
				return null;
				
			}

			private void agregarBotonSiNo(JPanel panelRegistro) {
            	JLabel mensajeRegistro = new JLabel("¡Bienvenido, Usuario no Registrado. Desea Registrarse?");
            	mensajeRegistro.setBounds(10, 20, 800, 25);
                panelRegistro.add(mensajeRegistro);
                
		    	panelRegistro.setLayout(null);

				JButton buttonSi = new JButton("Si");
		        buttonSi.setBounds(80, 120, 80, 25);
		        panelRegistro.add(buttonSi);
		        
		        JButton buttonNo = new JButton("No");
		        buttonNo.setBounds(180, 120, 80, 25);
		        panelRegistro.add(buttonNo);
		        
		        
		        buttonSi.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						//este codigo cierra la ventana
						SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
						
						//esto no se si deberia ir asi
						//ayuda
						/*
						 * 
						 * 
						 * 
						 * aAHHHHHHHHHHHHHHHH
						 */
						Sistema s = SistemaImpls.getInstancia();
						s.registroCliente();
						
				        
					}
		        }); 
		        buttonNo.addActionListener(new ActionListener() {
		        	
					@Override
					public void actionPerformed(ActionEvent e) {
						String mensajeError = "Se terminara el programa";
	            		JOptionPane.showMessageDialog(null, mensajeError);
						SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();

					}
					
		       });
		        
			}
        });
    }



	@Override
	public void registroCliente() {
		System.out.println("wenasns");
		
		JFrame frameRegistroCliente = new JFrame("Registro Cliente... Ingresar Datos");
		frameRegistroCliente.setSize(500, 500);
		frameRegistroCliente.setLocationRelativeTo(null);
        JPanel panelRegistroCliente = new JPanel();
        frameRegistroCliente.add(panelRegistroCliente);
        placeComponentsRegistro(panelRegistroCliente);
        frameRegistroCliente.setVisible(true);
		
		
		
		
		
		
	}



	private void placeComponentsRegistro(JPanel panelRegistroCliente) {
		panelRegistroCliente.setLayout(null);

        JLabel rutLabel = new JLabel("RUT(12345678-9): ");
        rutLabel.setBounds(10, 20, 800, 25);
        panelRegistroCliente.add(rutLabel);

        JTextField rutText = new JTextField(20);
        rutText.setBounds(200, 20, 165, 25);
        panelRegistroCliente.add(rutText);
        
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panelRegistroCliente.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(200, 50, 165, 25);
        panelRegistroCliente.add(passwordText);
        
        JLabel fechaLabel = new JLabel("Ingrese la fecha(AAAA/MM/DD):");
        fechaLabel.setBounds(10, 80, 800, 25);
        panelRegistroCliente.add(fechaLabel);

        JTextField fechaActual = new JTextField(20);
        fechaActual.setBounds(200, 80, 165, 25);
        panelRegistroCliente.add(fechaActual);
        
        JButton button = new JButton("Submit");
        button.setBounds(200, 120, 80, 25);
        panelRegistroCliente.add(button);
        
        
        
        
        // Acción al presionar el botón
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rut = rutText.getText();
                char[] passwordChars = passwordText.getPassword();
                String password = new String(passwordChars);
                String fecha = fechaActual.getText();

                
                
                // Aquí puedes usar los valores almacenados (rut y password) como desees
                boolean revisarDatosCorrectos =false;
                
                for (Persona p : listaPersonas) {
                	if (p.getRut().equals(rut) && p.getPass().equals(password)) {
                		revisarDatosCorrectos=true;
                	}
				}
                System.out.println(fecha);
                if(revisarDatosCorrectos && fecha==null) {
                	// Ejemplo de mensaje de bienvenida utilizando el RUT del usuario
                	String mensajeError = "¡Error, usuario Ya existe: ";
                	JOptionPane.showMessageDialog(null, mensajeError);
                }
                else if (revisarDatosCorrectos==false && fecha!= null&& rut!= null&& password!= null) {
                	/*
                	 * Este es el importante
                	 * Se almacenan los datos aqui
                	 * en alguna lista o algo asi
                	 * 
                	 * 
                	 */
                }
                else {
            		String mensajeError = "Ingrese sus datos correctamente / Ingrese todos los datos";
            		JOptionPane.showMessageDialog(null, mensajeError);
            	}
                
                
                
                
                
                
                
            }
        });
	}



	@Override
	public void menuTrabajador() {
		System.out.println("yo trabajo");
	}



	@Override
	public void menuCliente() {
		System.out.println("yono trabajo");
		System.out.println("yo notrabajo");

	}
	
	
	
}
