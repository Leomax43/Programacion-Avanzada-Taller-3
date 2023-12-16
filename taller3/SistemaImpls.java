package taller3;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

public   class SistemaImpls implements Sistema{
	private static SistemaImpls instance;
	
	private ArrayList<Texto> listaTextos=new ArrayList<>();
	private static ArrayList<Persona> listaPersonas=new ArrayList<>();
	private ArrayList<Reserva> listaReservas=new ArrayList<>();
	private ArrayList<Devolucion> listaDevoluciones=new ArrayList<>();
	private ArrayList<Devolucion> pendientesPorPersona = new ArrayList<>();
	//esta es para cerrar las listas de libros que se abren en ciertas ventanas
	private JFrame frameListaTextos;
	private JFrame frameListaMorosos;
	private JFrame frameListaReservasNoDevueltas;
	private JFrame frameListaReservasDevueltas;
	private JFrame frameTodosLosLibrosBiblioteca;


	//esto es para el pago
	private boolean pago = false;

	
	
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
			//XDDDDDDDDDD nose 
			//me referia a algo q los separara en libro/comic/apunte/paper pero asi como lo hiciste queo re piola
			//yo lo iba a hacer como en 30 lineas
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
		
		
		Scanner scReservas = new Scanner(new File("reservas.txt"));
		while (scReservas.hasNextLine()) {
			String lineaReserva = scReservas.nextLine();
			String[] partesReserva = lineaReserva.split(",");
			
			//lo mismo q recien
			//se agrega
			//se agrega a la listareservas
			listaReservas.add(new Reserva(Integer.parseInt(partesReserva[0]), partesReserva[1], Integer.parseInt(partesReserva[2]), partesReserva[3], partesReserva[4]));
			//Resrvas listas ahora lo demas
			
			//ademas hay q agregarlo a la lista de reservas de cada persona
			for (Persona p : listaPersonas) {
			    if (p instanceof Usuario && p.getRut().equals(partesReserva[1]) && p.getTipoPersona().equals("Usuario") ) {
					//podriamos agregar solo el codigo del libro?
					//o el libro entero nose
					for (Texto t: listaTextos) {
						//se revisa si el codigo de la reserva es igual al codigo del texto
						//se revisa si se devolvio o no
						if (Integer.parseInt(partesReserva[2])==t.getCodigo()  && noDevuelto(Integer.parseInt(partesReserva[0]))) {
							((Usuario) p).agregarLibro(t);
						}
					}
				}
			}
		}
		scReservas.close();
		
		
		
		
	}

		
	private boolean noDevuelto(int codigoReserva) {
		for (Devolucion devolucion : listaDevoluciones) {
			if(devolucion.getCodigoReserva()==codigoReserva) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void ingresarRegistrarse() {
		JFrame frame = new JFrame("Menu Principal");
		
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        JPanel panelMenu = new JPanel();
        JLabel menuLabel = new JLabel("Menu Principal");
        
        menuLabel.setBounds(100, 20, 800, 25);
        panelMenu.add(menuLabel);
        frame.add(menuLabel);
        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 100)); // Crear un panel con FlowLayout centrado
        frame.add(panel);

        JButton buttonIngresar = new JButton("Ingresar");
        panel.add(buttonIngresar);

        JButton buttonRegistrarse = new JButton("Registrarse");
        panel.add(buttonRegistrarse);
        
        
        
        buttonIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();

        		Sistema s = SistemaImpls.getInstancia();
        		s.Interfaz();
            }
        });
        
        buttonRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();

        		Sistema s = SistemaImpls.getInstancia();
        		s.registroCliente();
            }
        });
        
        frame.setVisible(true);
        
	}
	
	
	
	@Override
	public void Interfaz() {
		JFrame frame = new JFrame("Menu Ingreso");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
        
        
        
       
	}
	private static void placeComponents(JPanel panel) {
    	panel.setLayout(null);
    	
    	JLabel mensajeLabel = new JLabel("Ingresar Datos");
        mensajeLabel.setBounds(150, 20, 800, 25);
        panel.add(mensajeLabel);
    	
    	
        JLabel rutLabel = new JLabel("RUT(12345678-9): ");
        rutLabel.setBounds(10, 50, 800, 25);
        panel.add(rutLabel);

        JTextField rutText = new JTextField(20);
        rutText.setBounds(200, 50, 165, 25);
        panel.add(rutText);
        
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(10, 80, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(200, 80, 165, 25);
        panel.add(passwordText);
        
        JLabel fechaLabel = new JLabel("Ingrese la fecha(AAAA/MM/DD):");
        fechaLabel.setBounds(10, 110, 800, 25);
        panel.add(fechaLabel);

        JTextField fechaActual = new JTextField(20);
        fechaActual.setBounds(200, 110, 165, 25);
        panel.add(fechaActual);
        
        JButton button = new JButton("Submit");
        button.setBounds(150, 140, 80, 25);
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
                if(revisarDatosCorrectos && !fecha.isBlank()) {
					SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
                	//aca se prosigue con lo q se deba hacer
                	//comandos para los clientes y/o trabajadores
                	//menuCliente();
                	//menuTrabajador();
                	String a = getTipoPersona(rut);
                	if (a.equals("Usuario")) {
                		String nombre = buscarNombreConRut(rut);
                		String mensajeBienvenida = "¡Bienvenido Usuario: "+nombre+ " con RUT: " + rut + "!";
                		JOptionPane.showMessageDialog(null, mensajeBienvenida);
                		
                		/*
                		 * 
                		 * 
                		 * 
                		 * 
                		 * nosesise hace asi
                		 */
                		Sistema s = SistemaImpls.getInstancia();
						s.menuCliente(rut,fecha);
                		
                	}
                	else if(a.equals("Trabajador")){
                		String nombre = buscarNombreConRut(rut);
                		String mensajeBienvenida = "¡Bienvenido Trabajador: "+nombre+ " con RUT: " + rut + "!";
                		JOptionPane.showMessageDialog(null, mensajeBienvenida);
                		/*
                		 * 
                		 * advertencia
                		 * AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
                		 */
                		Sistema s = SistemaImpls.getInstancia();
                		s.menuTrabajador(rut,fecha);
                		
                	}
                	else {
                		System.out.println("esto no deberia pasar");

                	}
                	
                }
                
                //solo se revisa la fecha ya que el nombre y rut se revisan en la lectura de la lista
                else if (revisarDatosCorrectos==false && !fecha.isBlank()) {
                	
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

			private String buscarNombreConRut(String rut) {
				for (Persona p : listaPersonas) {
                	if (p.getRut().equals(rut)) {
                		return p.getNombre();
                	}
				}				
				return null;
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
		JFrame frameRegistroCliente = new JFrame("Menu Registro Cliente");
		frameRegistroCliente.setSize(400, 300);
		frameRegistroCliente.setLocationRelativeTo(null);
        JPanel panelRegistroCliente = new JPanel();
        frameRegistroCliente.add(panelRegistroCliente);
        placeComponentsRegistro(panelRegistroCliente);
        frameRegistroCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameRegistroCliente.setVisible(true);
		
		
		
		
		
		
	}



	private void placeComponentsRegistro(JPanel panelRegistroCliente) {
		panelRegistroCliente.setLayout(null);
		
		JLabel mensajeLabel = new JLabel("Ingresar Datos Usuario");
        mensajeLabel.setBounds(150, 20, 800, 25);
        panelRegistroCliente.add(mensajeLabel);
        
		JLabel nombreLabel = new JLabel("nombre: ");
        nombreLabel.setBounds(10, 50, 800, 25);
        panelRegistroCliente.add(nombreLabel);
		
        JTextField nombreText = new JTextField(20);
        nombreText.setBounds(200, 50, 165, 25);
        panelRegistroCliente.add(nombreText);
        
        JLabel rutLabel = new JLabel("RUT(12345678-9): ");
        rutLabel.setBounds(10, 80, 800, 25);
        panelRegistroCliente.add(rutLabel);
        
        JTextField rutText = new JTextField(20);
        rutText.setBounds(200, 80, 165, 25);
        panelRegistroCliente.add(rutText);
        
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(10, 110, 80, 25);
        panelRegistroCliente.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(200, 110, 165, 25);
        panelRegistroCliente.add(passwordText);
        
        JButton button = new JButton("Submit");
        button.setBounds(150, 140, 80, 25);
        panelRegistroCliente.add(button);
        
        
        
        
        // Acción al presionar el botón
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rut = rutText.getText();
                char[] passwordChars = passwordText.getPassword();
                String password = new String(passwordChars);
                String nombre = nombreText.getText();
                
                // Aquí puedes usar los valores almacenados (rut y password) como desees
                boolean revisarDatosCorrectos =false;
                
                for (Persona p : listaPersonas) {
                	if (p.getRut().equals(rut) && p.getPass().equals(password)) {
                		revisarDatosCorrectos=true;
                	}
				}
                if(revisarDatosCorrectos && !nombre.isBlank()) {
                	// Ejemplo de mensaje de bienvenida utilizando el RUT del usuario
                	String mensajeError = "¡Error, usuario Ya existe: ";
                	JOptionPane.showMessageDialog(null, mensajeError);
                }
                else if (revisarDatosCorrectos==false && !nombre.isBlank()&& !rut.isBlank()&& !password.isBlank()) {
                	/*
                	 * Este es el importante
                	 * Se almacenan los datos aqui
                	 * en alguna lista o algo asi
                	 * 
                	 * 
                	 */
                	Persona p = new Usuario(nombre,rut,password,"Usuario");
                	listaPersonas.add(p);
					SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
					String mensaje = "Usuario Correctamente agregado";
            		JOptionPane.showMessageDialog(null, mensaje);
                	
                	Sistema s = SistemaImpls.getInstancia();
            		s.Interfaz();
            		
                }
                else {
            		String mensajeError = "Ingrese sus datos correctamente / Ingrese todos los datos";
            		JOptionPane.showMessageDialog(null, mensajeError);
            	}
                
                
                
                
                
                
                
            }
        });
	}



	

	@Override
	public void menuTrabajador(String rut, String fecha) {
		// TODO Auto-generated method stub
		System.out.println("yo trabajo");
		JFrame frame = new JFrame("Menu Ingreso");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 50));
        frame.add(panel);
        placeComponentsTrabajador(panel,rut,fecha);
        frame.setVisible(true);
	}

	

	private void placeComponentsTrabajador(JPanel panel, String rut, String fecha) {
		JButton buttonIngresarTexto = new JButton("Ingresar Nuevo Texto");
		buttonIngresarTexto.setBounds(10, 10, 80, 25);
        panel.add(buttonIngresarTexto);
        
        JButton buttonIngresarReserva = new JButton("Ingresar Reseva");
        buttonIngresarReserva.setBounds(100, 10, 80, 25);
        panel.add(buttonIngresarReserva);
        
        JButton buttonDevolver = new JButton("Devolver Texto");
        buttonDevolver.setBounds(10, 40, 80, 25);
        panel.add(buttonDevolver);
        
        JButton buttonVerRegistros = new JButton("Ver Registros de Reservas");
        buttonVerRegistros.setBounds(100, 40, 80, 25);
        panel.add(buttonVerRegistros);
        
        
        JButton buttonRevisarTexto = new JButton("Revisar Estado Texto");
        buttonRevisarTexto.setBounds(100, 40, 80, 25);
        panel.add(buttonRevisarTexto);
        
        JButton buttonEstadisticas = new JButton("Estadisticas");
        buttonEstadisticas.setBounds(100, 40, 80, 25);
        panel.add(buttonEstadisticas);
        
        buttonIngresarTexto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFrame frame = new JFrame("Menu Ingreso");
            	frame.setSize(500, 300);
            	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	frame.setLocationRelativeTo(null);
            	JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 50));
            	frame.add(panel);
            	placeComponentsIngresarTexto(panel,fecha,rut);
            	frame.setVisible(true);
				
				
			}

			private void placeComponentsIngresarTexto(JPanel panel, String fecha, String rut) {
				panel.setLayout(null);
		    	
		    	JLabel mensajeLabel = new JLabel("Ingresar Datos Del Texto");
		        mensajeLabel.setBounds(100, 20, 800, 25);
		        panel.add(mensajeLabel);
		    	
		    	
		        JLabel anoLabel = new JLabel("Año: ");
		        anoLabel.setBounds(100, 50, 80, 25);
		        panel.add(anoLabel);

		        JTextField anoText = new JTextField(20);
		        anoText.setBounds(250, 50, 165, 25);
		        panel.add(anoText);
		        
		        JLabel autorLabel = new JLabel("Nombre Autor: ");
		        autorLabel.setBounds(80, 80, 808, 25);
		        panel.add(autorLabel);

		        JTextField autorText = new JTextField(20);
		        autorText.setBounds(250, 80, 165, 25);
		        panel.add(autorText);
		        
		        JLabel nombreTextoLabel = new JLabel("Nombre Texto: ");
		        nombreTextoLabel.setBounds(80, 110, 808, 25);
		        panel.add(nombreTextoLabel);
		        
		        JTextField nombreTextoText = new JTextField(20);
		        nombreTextoText.setBounds(250, 110, 165, 25);
		        panel.add(nombreTextoText);
		        
		        JLabel tipoTextoLabel = new JLabel("Tipo Texto(Libro,Comic,Apunte,Paper): ");
		        tipoTextoLabel.setBounds(10, 140, 808, 25);
		        panel.add(tipoTextoLabel);
		        
		        JTextField tipoTextoText = new JTextField(20);
		        tipoTextoText.setBounds(250, 140, 165, 25);
		        panel.add(tipoTextoText);
		        
		        JButton button = new JButton("Submit");
		        button.setBounds(250, 170, 80, 25);
		        panel.add(button);
		        
		        button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
						Random r = new Random();
						String anoTexto = anoText.getText();
						int codigo = r.nextInt(999999);
						String autorTexto = autorText.getText();
						String nombreTexto = nombreTextoText.getText();
						String tipoTexto = tipoTextoText.getText();
		                
						
						switch (tipoTexto.toLowerCase()) {
							case ("libro"):
								JFrame frameLibro = new JFrame("Ingreso Libro");
								frameLibro.setSize(500, 300);
								frameLibro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								frameLibro.setLocationRelativeTo(null);
			            		JPanel panelLibro = new JPanel();
			            		frameLibro.add(panelLibro);
			            		placeComponentsIngresarLibro(panelLibro,Integer.parseInt(anoTexto),codigo,autorTexto,nombreTexto,tipoTexto);
			            		frameLibro.setVisible(true);
			            		break;
								
							case ("comic"):
								JFrame frameComic = new JFrame("Ingreso Comic");
								frameComic.setSize(500, 300);
								frameComic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								frameComic.setLocationRelativeTo(null);
		            			JPanel panelComic = new JPanel();
		            			frameComic.add(panelComic);
		            			placeComponentsIngresarComic(panelComic,Integer.parseInt(anoTexto),codigo,autorTexto,nombreTexto,tipoTexto);
		            			frameComic.setVisible(true);
			            		break;

		            		
							case ("apunte"):
								JFrame frameApunte = new JFrame("Ingreso Apunte");
								frameApunte.setSize(500, 300);
								frameApunte.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								frameApunte.setLocationRelativeTo(null);
								JPanel panelApunte = new JPanel();
								frameApunte.add(panelApunte);
								placeComponentsIngresarApunte(panelApunte,Integer.parseInt(anoTexto),codigo,autorTexto,nombreTexto,tipoTexto);
								frameApunte.setVisible(true);
								break;

								
							case ("paper"):
								JFrame framePaper = new JFrame("Ingreso Paper");
								framePaper.setSize(500, 300);
								framePaper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								framePaper.setLocationRelativeTo(null);
								JPanel panelPaper = new JPanel();
								framePaper.add(panelPaper);
								placeComponentsIngresarPaper(panelPaper,Integer.parseInt(anoTexto),codigo,autorTexto,nombreTexto,tipoTexto);
								framePaper.setVisible(true);
								break;

							default:
						        // Código que se ejecuta si opcion no coincide con ningún case
								String mensaje= "Ingrese Correctamente los datos";
		                		JOptionPane.showMessageDialog(null, mensaje);
		                		break;
						}
					}

					private void placeComponentsIngresarPaper(JPanel panelPaper, int anoTexto, int codigo,
							String autorTexto, String nombreTexto, String tipoTexto) {
						panelPaper.setLayout(null);
				    	
				    	JLabel mensajeLabel = new JLabel("Ingresar Datos Del Paper");
				        mensajeLabel.setBounds(100, 20, 800, 25);
				        panelPaper.add(mensajeLabel);
				    	
				    	
				        JLabel asignaturaLabel = new JLabel("Area Investigacion: ");
				        asignaturaLabel.setBounds(80, 50, 800, 25);
				        panelPaper.add(asignaturaLabel);

				        JTextField asignaturaText = new JTextField(20);
				        asignaturaText.setBounds(250, 50, 165, 25);
				        panelPaper.add(asignaturaText);
				        
				        JLabel carreraLabel = new JLabel("DOI123456789(solo numeros): ");
				        carreraLabel.setBounds(30, 80, 808, 25);
				        panelPaper.add(carreraLabel);

				        JTextField carreraText = new JTextField(20);
				        carreraText.setBounds(250, 80, 165, 25);
				        panelPaper.add(carreraText);
				        
				        
				        JButton button = new JButton("Submit");
				        button.setBounds(200, 110, 80, 25);
				        panelPaper.add(button);
				        
				        button.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								String areaInvestigacion = asignaturaText.getText();
								String d ="DOI";
								String doi= carreraText.getText();
								d+=doi;
								Paper p = new Paper(anoTexto,codigo,autorTexto,nombreTexto,"Paper",areaInvestigacion,doi);
								listaTextos.add(p);
								
								String mensaje= "Paper Agregado Correctamente";
			                	JOptionPane.showMessageDialog(null, mensaje);
			                	
								SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
								//se modifica el txt
								//se repite lo mismo con todo lo demas
								
								for (Texto t: listaTextos) {
									System.out.println(t);
								}
								
							}
				        	
				        });
					}

					private void placeComponentsIngresarApunte(JPanel panelApunte, int anoTexto, int codigo,
							String autorTexto, String nombreTexto, String tipoTexto) {
						panelApunte.setLayout(null);
				    	
				    	JLabel mensajeLabel = new JLabel("Ingresar Datos Del Apunte");
				        mensajeLabel.setBounds(100, 20, 800, 25);
				        panelApunte.add(mensajeLabel);
				    	
				    	
				        JLabel asignaturaLabel = new JLabel("Asignatura Asociada: ");
				        asignaturaLabel.setBounds(80, 50, 800, 25);
				        panelApunte.add(asignaturaLabel);

				        JTextField asignaturaText = new JTextField(20);
				        asignaturaText.setBounds(250, 50, 165, 25);
				        panelApunte.add(asignaturaText);
				        
				        JLabel carreraLabel = new JLabel("Carrera: ");
				        carreraLabel.setBounds(80, 80, 808, 25);
				        panelApunte.add(carreraLabel);

				        JTextField carreraText = new JTextField(20);
				        carreraText.setBounds(250, 80, 165, 25);
				        panelApunte.add(carreraText);
				        
				        
				        JButton button = new JButton("Submit");
				        button.setBounds(200, 110, 80, 25);
				        panelApunte.add(button);
				        
				        button.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								String asignatura = asignaturaText.getText();
								String carrera= carreraText.getText();
								
								Apunte a = new Apunte(anoTexto,codigo,autorTexto,nombreTexto,"Apunte",asignatura,carrera);
								listaTextos.add(a);
								
								String mensaje= "Apunte Agregado Correctamente";
			                	JOptionPane.showMessageDialog(null, mensaje);
			                	
								SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
								//se modifica el txt
								//se repite lo mismo con todo lo demas
								
								for (Texto t: listaTextos) {
									System.out.println(t);
								}
							}
				        	
				        });
					}

					private void placeComponentsIngresarComic(JPanel panelComic, int anoTexto, int codigo,
							String autorTexto, String nombreTexto, String tipoTexto) {
						
						panelComic.setLayout(null);
				    	
				    	JLabel mensajeLabel = new JLabel("Ingresar Datos Del Comic");
				        mensajeLabel.setBounds(100, 20, 800, 25);
				        panelComic.add(mensajeLabel);
				    	
				    	
				        JLabel companiaLabel = new JLabel("Compañia Editorial: ");
				        companiaLabel.setBounds(80, 50, 800, 25);
				        panelComic.add(companiaLabel);

				        JTextField companiaText = new JTextField(20);
				        companiaText.setBounds(250, 50, 165, 25);
				        panelComic.add(companiaText);
				        
				        JLabel generoLabel = new JLabel("Genero: ");
				        generoLabel.setBounds(80, 80, 808, 25);
				        panelComic.add(generoLabel);

				        JTextField generoText = new JTextField(20);
				        generoText.setBounds(250, 80, 165, 25);
				        panelComic.add(generoText);
				        
				        JLabel numeroSerieLabel = new JLabel("Numero De Serie: ");
				        numeroSerieLabel.setBounds(80, 110, 808, 25);
				        panelComic.add(numeroSerieLabel);

				        JTextField numeroSerieText = new JTextField(20);
				        numeroSerieText.setBounds(250, 110, 165, 25);
				        panelComic.add(numeroSerieText);
				        
				        JButton button = new JButton("Submit");
				        button.setBounds(250, 140, 80, 25);
				        panelComic.add(button);
				        
				        button.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								String compania= companiaText.getText();
								String genero = generoText.getText();
								String numeroSerie= numeroSerieText.getText();
								
								Comic c = new Comic(anoTexto,codigo,autorTexto,nombreTexto,"Comic",compania,genero,numeroSerie);
								listaTextos.add(c);
								//se modifica el txt
								
								String mensaje= "Comic Agregado Correctamente";
			                	JOptionPane.showMessageDialog(null, mensaje);
			                	
								SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
								//se modifica el txt
								//se repite lo mismo con todo lo demas
								
								for (Texto t: listaTextos) {
									System.out.println(t);
								}
							}
				        	
				        });
				        
					}

					private void placeComponentsIngresarLibro(JPanel panel, int anoTexto, int codigo, String autorTexto,
							String nombreTexto, String tipoTexto) {
						
							panel.setLayout(null);

							JLabel mensajeLabel = new JLabel("Ingresar Datos Del Libro");
					        mensajeLabel.setBounds(180, 20, 450, 25);
					        panel.add(mensajeLabel);
					    	
					    	
					        JLabel editorialLabel = new JLabel("Editorial: ");
					        editorialLabel.setBounds(70, 20, 80, 80);
					        panel.add(editorialLabel);

					        JTextField editorialText = new JTextField(20);
					        editorialText.setBounds(250, 50, 165, 25);
					        panel.add(editorialText);
					        
					        JLabel generoLiterarioLabel = new JLabel("Genero Literario: ");
					        generoLiterarioLabel.setBounds(70, 90, 165, 25);
					        panel.add(generoLiterarioLabel);

					        JTextField generoLiterarioText = new JTextField(20);
					        generoLiterarioText.setBounds(250, 90, 165, 25);
					        panel.add(generoLiterarioText);
					        
					        JLabel edicionLabel = new JLabel("Edicion: ");
					        edicionLabel.setBounds(70, 130, 808, 25);
					        panel.add(edicionLabel);
					        
					        JTextField edicionText = new JTextField(20);
					        edicionText.setBounds(250, 130, 165, 25);
					        panel.add(edicionText);
					        
					        JButton button = new JButton("Submit");
					        button.setBounds(250, 170, 80, 25);
					        panel.add(button);
					        
					        button.addActionListener(new ActionListener() {
					        	
								@Override
								public void actionPerformed(ActionEvent e) {
									String editorial= editorialText.getText();
									String generoLiterario = generoLiterarioText.getText();
									String edicion= edicionText.getText();
									
									Libro l = new Libro(anoTexto,codigo,autorTexto,nombreTexto,"Libro",editorial,generoLiterario,edicion);
									listaTextos.add(l);
									
									String mensaje= "Libro Agregado Correctamente";
				                	JOptionPane.showMessageDialog(null, mensaje);
				                	
									SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
									//se modifica el txt
									//se repite lo mismo con todo lo demas
									
									for (Texto t: listaTextos) {
										System.out.println(t);
									}
									
								}
					        	
					        });
												
					}

					
		        	
		        });
		        
			}
        	
        });
        buttonIngresarReserva.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("Menu Ingreso");
            	frame.setSize(500, 300);
            	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	frame.setLocationRelativeTo(null);
            	mostrarLibrosDisponibles();
            	JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 50));
            	frame.add(panel);
            	placeComponentsReservar(panel,fecha,rut);
            	frame.setVisible(true);
			}

			private ArrayList<Texto> mostrarLibrosDisponibles() {
				ArrayList<Texto> listaLibrosDisponibles = new ArrayList<>();
				
				//cont para revisar si alguna vez fue pedido el libro
				int contPeticion=0;
				
				
				for (Texto texto : listaTextos) {
					for (Reserva r: listaReservas) {
						
						//se revisa si el libro fue alguna vez reservado
						if (texto.getCodigo()==r.getCodigoObjeto()) {
							//si entra aca es pq fue reservado
							//ahora hay q revisar si lo devolvieron
							contPeticion++;
							for (Devolucion d: listaDevoluciones) {
								if (d.getCodigoReserva()==r.getCodigoReserva()) {
									//se pidio el libro y se devolvio
									listaLibrosDisponibles.add(texto);
								}
								
							}
							
						}
					}
					if(contPeticion==0) {
						//si entra aca es pq nunca lo reservaron antes
						listaLibrosDisponibles.add(texto);
					}
					contPeticion=0;
				}
				
				
				// Crear una nueva ventana (JFrame)

		        frameListaTextos = new JFrame("Lista De textos Disponibles");
		        frameListaTextos.setSize(800, 300);
		        frameListaTextos.setLocationRelativeTo(null);

		        // Crear un JTextArea para mostrar la lista
		        JTextArea textArea = new JTextArea();
		        textArea.setEditable(false); // Hacer el área de texto no editable

		        // Agregar los elementos del ArrayList al JTextArea
		        
		        for (Texto elemento : listaLibrosDisponibles) {
		            textArea.append(elemento + "\n"); // Agregar cada elemento seguido de un salto de línea
		        }
		        
		        // Agregar el JTextArea a un JScrollPane para permitir el desplazamiento si hay muchos elementos
		        JScrollPane scrollPane = new JScrollPane(textArea);

		        // Agregar el JScrollPane al JFrame
		        frameListaTextos.add(scrollPane, BorderLayout.CENTER);

		        // Mostrar la ventana
		        frameListaTextos.setVisible(true);
		        return listaLibrosDisponibles;				
			}

			private void placeComponentsReservar(JPanel panel, String fecha, String rut) {
				panel.setLayout(null);
		    	
		    	JLabel mensajeLabel = new JLabel("Ingresar Datos De La Reserva");
		        mensajeLabel.setBounds(100, 20, 800, 25);
		        panel.add(mensajeLabel);
		    	
		    	
		        JLabel rutLabel = new JLabel("Rut Que Desea Reservar: ");
		        rutLabel.setBounds(30, 50, 800, 25);
		        panel.add(rutLabel);

		        JTextField rutText = new JTextField(20);
		        rutText.setBounds(250, 50, 165, 25);
		        panel.add(rutText);
		        
		        JLabel codigoLabel = new JLabel("Codigo Del Texto: ");
		        codigoLabel.setBounds(80, 80, 808, 25);
		        panel.add(codigoLabel);

		        JTextField codigoText = new JTextField(20);
		        codigoText.setBounds(250, 80, 165, 25);
		        panel.add(codigoText);
		        
		        JLabel fechaRetiroLabel = new JLabel("Fecha Retiro(DD-MM-AAAA): ");
		        fechaRetiroLabel.setBounds(80, 110, 808, 25);
		        panel.add(fechaRetiroLabel);
		        
		        JTextField fechaRetiroText = new JTextField(20);
		        fechaRetiroText.setBounds(250, 110, 165, 25);
		        panel.add(fechaRetiroText);
		        
		        JLabel fechaEntregaLabel = new JLabel("Fecha Entrega(DD-MM-AAAA): ");
		        fechaEntregaLabel.setBounds(80, 140, 808, 25);
		        panel.add(fechaEntregaLabel);
		        
		        JTextField fechaEntregaText = new JTextField(20);
		        fechaEntregaText.setBounds(250, 140, 165, 25);
		        panel.add(fechaEntregaText);
		        
		        JButton button = new JButton("Submit");
		        button.setBounds(250, 170, 80, 25);
		        panel.add(button);
		        
		        button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						int codigo = contadorReservas();
						String rutTexto = rutText.getText();
						String codigoTexto = codigoText.getText();
						String fechaRetiro = fechaRetiroText.getText();
						String fechaEntrega = fechaEntregaText.getText();
						
						if(buscarCodigoTexto(codigoTexto) && buscarRutExiste(rutTexto)) {
							Reserva r = new Reserva(codigo,rutTexto,Integer.parseInt(codigoTexto),fechaRetiro,fechaEntrega);
							listaReservas.add(r);
							//se agrega al txt
							String mensaje= "Reserva Agregada Correctamente";
		                	JOptionPane.showMessageDialog(null, mensaje);
		                	
		                	//ademas hay q modificar la lista de libros disponibles
		                	
							SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
					        frameListaTextos.setVisible(false);
					        
							
						}
						else {
							String mensaje= "Datos Incorrectos";
		                	JOptionPane.showMessageDialog(null, mensaje);
							SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
					        frameListaTextos.setVisible(false);

						}
						
					}

					private boolean buscarRutExiste(String rutTexto) {
						for (Persona p: listaPersonas) {
							if (p instanceof Usuario && p.getRut().equals(rutTexto) && p.getTipoPersona().equals("Usuario")) {
								//se revisa que no tenga mas de 2 libros para poder reservar el 3ro
								int cont = ((Usuario) p).getListaLibrosReservados().size();
								if (cont<=2) return true;
							}
						}
						return false;
					}

					private boolean buscarCodigoTexto(String codigoTexto) {
						for (Texto t: listaTextos) {
							if (t.getCodigo()==Integer.parseInt(codigoTexto)) {
								return true;
							}
						}
						return false;
					}

					private int contadorReservas() {
						int cont=0;
						for (Reserva r : listaReservas) {
							cont++;
						}
						cont+=100000;
						return cont;
					}
		        	
		        });
			}
        	
        });
        buttonDevolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frameDevolver = new JFrame("Menu Devolver Texto");
				frameDevolver.setSize(400, 300);
				frameDevolver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frameDevolver.setLocationRelativeTo(null);
		        JPanel panelDevolver = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 50));
		        frameDevolver.add(panelDevolver);
		        placeComponentsDevolverTexto(panelDevolver,rut,fecha);
		        frameDevolver.setVisible(true);
			}

			private void placeComponentsDevolverTexto(JPanel panelDevolver, String rut, String fecha) {
				panelDevolver.setLayout(null);
		    	
		    	JLabel mensajeLabel = new JLabel("Ingresar Rut Cliente");
		        mensajeLabel.setBounds(100, 20, 800, 25);
		        panelDevolver.add(mensajeLabel);
		    	
		    	
		        JLabel rutLabel = new JLabel("Rut De La Persona Que Desea Devolver: ");
		        rutLabel.setBounds(50, 50, 800, 25);
		        panelDevolver.add(rutLabel);

		        JTextField rutText = new JTextField(20);
		        rutText.setBounds(70, 80, 165, 25);
		        panelDevolver.add(rutText);
		        
		        JButton button = new JButton("Submit");
		        button.setBounds(150, 110, 80, 25);
		        panelDevolver.add(button);
		        
		        button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String rutTexto = rutText.getText();
						if (personaExiste(rutTexto)) {
							Usuario u = buscarPersonaRut(rutTexto);
							ArrayList<Texto> listaReservados=u.getListaLibrosReservados();
							boolean VoF = mostrarLibrosReservados(listaReservados);
							if (VoF) {
								SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
								JFrame frameDevolucion = new JFrame("Menu Devolucion");
								frameDevolucion.setSize(400, 300);
								frameDevolucion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								frameDevolucion.setLocationRelativeTo(null);
			            		JPanel panelDevolucion = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 50));
			            		frameDevolucion.add(panelDevolucion);
			            		placeComponentsDevolucionTrabajador(panelDevolucion,fecha,listaReservados,rutTexto);
			            		frameDevolucion.setVisible(true);
							}
							else {
								//no tiene libros
								
							}
							
							
						}
						else {
							//ingreso mal el rut
						}
					}

					private void placeComponentsDevolucionTrabajador(JPanel panel, String fecha,
							ArrayList<Texto> listaReservados, String rutTexto) {
						panel.setLayout(null);
				    	
				    	JLabel mensajeLabel = new JLabel("Ingresar Codigo del Texto que quiera Devolver");
				        mensajeLabel.setBounds(70, 20, 800, 25);
				        panel.add(mensajeLabel);
				    	
				    	
				        JLabel CodigoLabel = new JLabel("Codigo: ");
				        CodigoLabel.setBounds(100, 50, 80, 25);
				        panel.add(CodigoLabel);

				        JTextField CodigoText = new JTextField(20);
				        CodigoText.setBounds(200, 50, 165, 25);
				        panel.add(CodigoText);
				        
				        JButton button = new JButton("Submit");
				        button.setBounds(200, 140, 80, 25);
				        panel.add(button);
				        
				        
				        // Acción al presionar el botón
				        button.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								String codigo = CodigoText.getText();
								boolean revisarCodigo = revisarCodigo(codigo,rutTexto);
								System.out.println("aaaaah");
								if (revisarCodigo) {
									//llegamos hasta el texto en cuestion dentro de la lista
									//ahora hay q revisar si esta dentro del plazo
									String fechaDevolucion="";
									int codigoReserva=0;
									String devolucionOriginal="";
									for (Reserva r: listaReservas) {
										if (r.getCodigoObjeto()==Integer.parseInt(codigo)) {
											fechaDevolucion = r.getFechaDevolucion();
											codigoReserva=r.getCodigoReserva();
											devolucionOriginal=r.getFechaDevolucion();
										}
									}
									boolean revisarFechaCorrecta = revisarFecha(fecha,fechaDevolucion);
									if (revisarFechaCorrecta) {
										//el libro se entrego a tiempo
										//por ende se elimina de manera normal
										//se agrega al txt de devoluciones con un "pagado"
										int contDevoluciones = contDevoluciones();
										Devolucion d = new Devolucion(contDevoluciones,codigoReserva,devolucionOriginal,fecha,"pagado");
										eliminarLibroListaPersonal(rutTexto,codigo);
										frameListaTextos.dispose();
										SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
										String mensaje= "Libro Devuelto Correctamente";
						        		JOptionPane.showMessageDialog(null, mensaje);
						        		
									}
									else {
										//el libro se retraso
										//por ende el usuario no puede entregarlo directamente
										//debe entregarlo un Trabajador
										//se agrega al txt de devoluciones con un pendiente
										String mensaje= "Advertencia Libro Devuelto Con Deuda";
						        		JOptionPane.showMessageDialog(null, mensaje);
						        		
										int contDevoluciones = contDevoluciones();
										Devolucion d = new Devolucion(contDevoluciones,codigoReserva,devolucionOriginal,fecha,"pendiente");
										eliminarLibroListaPersonal(rutTexto,codigo);
										frameListaTextos.dispose();
										SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
						        		
						        		/* de todos modos se usaria algo como esto
										int contDevoluciones = contDevoluciones();
										Devolucion d = new Devolucion(contDevoluciones,codigoReserva,devolucionOriginal,fecha,"pendiente");
										*/

									}
								}
							}

							private void eliminarLibroListaPersonal(String rutTexto, String codigo) {
								for (Persona p: listaPersonas) {
									if (p instanceof Usuario && p.getRut().equals(rutTexto) && p.getTipoPersona().equals("Usuario")) {
										Texto t =null;
										for (Texto texto: listaTextos) {
											if(texto.getCodigo()==Integer.parseInt(codigo)) {
												t = texto;
											}
										}
										
										((Usuario) p).eliminarLibro(t);
									}
								}
							}

							private int contDevoluciones() {
								int cont=0;
								for (Devolucion d: listaDevoluciones) {
									cont++;
								}
								cont+=1000000;
								return cont;
							}

							private boolean revisarFecha(String fecha, String fechaDevolucion) {
								String[] partesFechaActual = fecha.split("/");
								String[] partesFechaReserva =fechaDevolucion.split("-");
								
								int anioPedido = Integer.parseInt(partesFechaActual[0]);
					            int mesPedido = Integer.parseInt(partesFechaActual[1]);
					            int diaPedido = Integer.parseInt(partesFechaActual[2]);

					            int anioEntrega = Integer.parseInt(partesFechaReserva[2]);
					            int mesEntrega = Integer.parseInt(partesFechaReserva[1]);
					            int diaEntrega = Integer.parseInt(partesFechaReserva[0]);

					            // Comparación de fechas
					            if (anioPedido < anioEntrega || 
					                (anioPedido == anioEntrega && mesPedido < mesEntrega) || 
					                (anioPedido == anioEntrega && mesPedido == mesEntrega && diaPedido <= diaEntrega)) {
					                return true; // O realiza alguna acción si se cumple la condición
					            }
								
								return false;
							}

							private boolean revisarCodigo(String codigo, String rutTexto) {
								for (Persona p : listaPersonas) {
									if (p instanceof Usuario && p.getRut().equals(rutTexto) && p.getTipoPersona().equals("Usuario")) {
										ArrayList<Texto> minilista =((Usuario) p).getListaLibrosReservados();
										for (Texto texto : minilista) {
											if (Integer.parseInt(codigo)==texto.getCodigo()) {
												return true;
											}
											
											
										}
									}
								}
								return false;
							}
				        });
					}

					private boolean mostrarLibrosReservados(ArrayList<Texto> listaReservados) {
						frameListaTextos = new JFrame("Lista De textos Disponibles");
						frameListaTextos.setSize(800, 300);
						frameListaTextos.setLocationRelativeTo(null);
				        //ola
				        
				        
				        //alo
				        
				        if (listaReservados.size()==0) {
				        	String mensaje= "Usted no Posee Libros en Reserva";
			        		JOptionPane.showMessageDialog(null, mensaje);
			        		return false;
				        }
				        else {
				        	// Crear un JTextArea para mostrar la lista
				        	JTextArea textArea = new JTextArea();
				        	textArea.setEditable(false); // Hacer el área de texto no editable

				        	// Agregar los elementos del ArrayList al JTextArea
				        
				        	for (Texto elemento : listaReservados) {
				        		textArea.append(elemento + "\n"); // Agregar cada elemento seguido de un salto de línea
				        	}
				        
				        	// Agregar el JTextArea a un JScrollPane para permitir el desplazamiento si hay muchos elementos
				        	JScrollPane scrollPane = new JScrollPane(textArea);

				        	// Agregar el JScrollPane al JFrame
				        	frameListaTextos.add(scrollPane, BorderLayout.CENTER);

				        	// Mostrar la ventana
				        	frameListaTextos.setVisible(true);
				        	return true;
				        }
					}

					private Usuario buscarPersonaRut(String rutTexto) {
						for (Persona p: listaPersonas) {
							if (p instanceof Usuario && p.getRut().equals(rutTexto) && p.getTipoPersona().equals("Usuario")) {
								return ((Usuario) p);
							}
						}
						return null;
					}
					private boolean personaExiste(String rutTexto) {
						for (Persona p: listaPersonas) {
							if (p instanceof Usuario && p.getRut().equals(rutTexto) && p.getTipoPersona().equals("Usuario")) {
								return true;
							}

						}						
						return false;
					}
		        	
		        	
		        });
			}
        	
        });
        buttonVerRegistros.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> lista = new ArrayList<>();

				imprimirReservasDevueltas(lista);
				imprimirReservasNoDevueltas(lista);
				
			}

			private void imprimirReservasNoDevueltas(ArrayList<String> lista) {
				ArrayList<Reserva> reservas = listaReservas;
				ArrayList<Reserva> reservasRepetidas = new ArrayList<>();
				
				for (Reserva reserva : listaReservas) {
					for (Devolucion devolucion : listaDevoluciones) {
						if (reserva.getCodigoReserva()==devolucion.getCodigoReserva()) {
							reservasRepetidas.add(reserva);
						}

					}

				}
			    reservas.removeAll(reservasRepetidas);

				//hay q probar si funca
			    //si funca
			    //estas son las q no se han devuelto
			    //por ende se imprimen en pantalla
			    /*
				for (Reserva reserva : reservas) {
					System.out.println(reserva);
				}*/
			    frameListaReservasNoDevueltas = new JFrame("Lista de Reservas NO Devueltas");
			    frameListaReservasNoDevueltas.setSize(800, 300);
			    frameListaReservasNoDevueltas.setLocationRelativeTo(null);
			    frameListaReservasNoDevueltas.setLocation(0, 0);
		        // Crear un JTextArea para mostrar la lista
		        JTextArea textArea = new JTextArea();
		        textArea.setEditable(false); // Hacer el área de texto no editable

		        // Agregar los elementos del ArrayList al JTextArea
		        textArea.append("Lista de Reservas NO Devueltas" + "\n");

		        for (Reserva elemento : reservas) {
		            textArea.append(elemento.toString() + "\n"); // Agregar cada elemento seguido de un salto de línea
		        }
		        
		        // Agregar el JTextArea a un JScrollPane para permitir el desplazamiento si hay muchos elementos
		        JScrollPane scrollPane = new JScrollPane(textArea);

		        // Agregar el JScrollPane al JFrame
		        frameListaReservasNoDevueltas.add(scrollPane, BorderLayout.CENTER);

		        // Mostrar la ventana
		        frameListaReservasNoDevueltas.setVisible(true);
			    
				
			}

			private void imprimirReservasDevueltas(ArrayList<String> lista) {
				// TODO Auto-generated method stub
				
				for (Reserva r: listaReservas) {
					for (Devolucion devolucion : listaDevoluciones) {
						if (r.getCodigoReserva()==devolucion.getCodigoReserva()) {
							lista.add(r.toString());
						}
					}
				}
				//ya tenemos la lista hay q mostrarla
				//estas son las que ya se devolvieron
				frameListaReservasDevueltas = new JFrame("Lista de Reservas Ya Devueltas");
				frameListaReservasDevueltas.setSize(700, 300);
				frameListaReservasDevueltas.setLocationRelativeTo(null);
				frameListaReservasDevueltas.setLocation(800, 0); // A la derecha

		        // Crear un JTextArea para mostrar la lista
		        JTextArea textArea = new JTextArea();
		        textArea.setEditable(false); // Hacer el área de texto no editable

		        // Agregar los elementos del ArrayList al JTextArea
		        textArea.append("Lista de Reservas Ya Devueltas" + "\n");
		        for (String elemento : lista) {
		            textArea.append(elemento.toString() + "\n"); // Agregar cada elemento seguido de un salto de línea
		        }
		        
		        // Agregar el JTextArea a un JScrollPane para permitir el desplazamiento si hay muchos elementos
		        JScrollPane scrollPane = new JScrollPane(textArea);

		        // Agregar el JScrollPane al JFrame
		        frameListaReservasDevueltas.add(scrollPane, BorderLayout.CENTER);

		        // Mostrar la ventana
		        frameListaReservasDevueltas.setVisible(true);
				
			}
        	
        });
        buttonRevisarTexto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("Revisar Estado Texto");
		        frame.setSize(400, 300);
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frame.setLocationRelativeTo(null);
		        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 50));
		        frame.add(panel);
		        imprimirTodosLosTextosBiblioteca();
		        placeComponentsEstadoTexto(panel);
		        frame.setVisible(true);
			}

			private void imprimirTodosLosTextosBiblioteca() {
				frameTodosLosLibrosBiblioteca = new JFrame("Lista de Reservas NO Devueltas");
				frameTodosLosLibrosBiblioteca.setSize(800, 300);
				frameTodosLosLibrosBiblioteca.setLocationRelativeTo(null);
				frameTodosLosLibrosBiblioteca.setLocation(0, 0);
		        // Crear un JTextArea para mostrar la lista
		        JTextArea textArea = new JTextArea();
		        textArea.setEditable(false); // Hacer el área de texto no editable

		        // Agregar los elementos del ArrayList al JTextArea
		        textArea.append("Lista de Reservas NO Devueltas" + "\n");

		        for (Texto t: listaTextos) {
		            textArea.append(t.toString() + "\n"); // Agregar cada elemento seguido de un salto de línea
		        }
		        
		        // Agregar el JTextArea a un JScrollPane para permitir el desplazamiento si hay muchos elementos
		        JScrollPane scrollPane = new JScrollPane(textArea);

		        // Agregar el JScrollPane al JFrame
		        frameTodosLosLibrosBiblioteca.add(scrollPane, BorderLayout.CENTER);

		        // Mostrar la ventana
		        frameTodosLosLibrosBiblioteca.setVisible(true);
			}

			private void placeComponentsEstadoTexto(JPanel panel) {
				panel.setLayout(null);
		    	
		    	JLabel mensajeLabel = new JLabel("Ingresar Codigo y Fecha A Revisar");
		        mensajeLabel.setBounds(70, 20, 800, 25);
		        panel.add(mensajeLabel);
		    	
		    	
		        JLabel CodigoLabel = new JLabel("Codigo Del Texto: ");
		        CodigoLabel.setBounds(50, 50, 800, 25);
		        panel.add(CodigoLabel);

		        JTextField CodigoText = new JTextField(20);
		        CodigoText.setBounds(200, 50, 165, 25);
		        panel.add(CodigoText);
		        
		        
		        JLabel fechaLabel = new JLabel("Fecha A Revisar(AAAA/MM/DD): ");
		        fechaLabel.setBounds(30, 80, 800, 25);
		        panel.add(fechaLabel);

		        JTextField fechaText = new JTextField(20);
		        fechaText.setBounds(200, 80, 165, 25);
		        panel.add(fechaText);
		        
		        
		        JButton button = new JButton("Submit");
		        button.setBounds(150, 140, 80, 25);
		        panel.add(button);
		        
		        button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String codigo= CodigoText.getText();
		                String fecha = fechaText.getText();
		                
		                if(revisarCodigoExiste(codigo)) {
		    		        frameTodosLosLibrosBiblioteca.setVisible(false);
		    		        SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
		    		        //ahora hay que revisar si el texto se encuentra disponible 
		    		        //en la fecha que ingresamos
		    		        //para ello hay que revisar cada reserva y ver si es del texto
		    		        //si es del texto revisar las fechas entre las cuales esta reservado
		    		        //si esta reservado devolver reservado
		    		        //de lo contrario devolver disponible
		    		        revisarTextoDisponible(codigo,fecha);
		    		        
		    		        
		    		        
		                }
		                
		                
					}

					private void revisarTextoDisponible(String codigo, String fecha) {
						for (Reserva r: listaReservas) {
							if(r.getCodigoObjeto()==Integer.parseInt(codigo)) {
								if(revisarFecha(fecha,r.getFechaPedido(),r.getFechaDevolucion())) {
									//se encuentra entre las fechas
									//por ende no disponible
									
									String mensaje= "Libro No Disponible para Reserva en la fecha Solicitada";
				                	JOptionPane.showMessageDialog(null, mensaje);

									
								}
								else {
									//no esta entre las fechas
									//por ende disponible
									String mensaje= "Libro Disponible para Reserva en la fecha Solicitada";
				                	JOptionPane.showMessageDialog(null, mensaje);

								}
							}
						}
					}

					private boolean revisarFecha(String fechaARevisar, String fechaPedido, String fechaDevolucion) {
						String[] partesFechaARevisar = fechaARevisar.split("/");
					    String[] partesFechaPedido = fechaPedido.split("-");
					    String[] partesFechaDevolucion = fechaDevolucion.split("-");

					    int añoARevisar = Integer.parseInt(partesFechaARevisar[0]);
					    int mesARevisar = Integer.parseInt(partesFechaARevisar[1]);
					    int diaARevisar = Integer.parseInt(partesFechaARevisar[2]);

					    int añoPedido = Integer.parseInt(partesFechaPedido[2]);
					    int mesPedido = Integer.parseInt(partesFechaPedido[1]);
					    int diaPedido = Integer.parseInt(partesFechaPedido[0]);

					    int añoDevolucion = Integer.parseInt(partesFechaDevolucion[2]);
					    int mesDevolucion = Integer.parseInt(partesFechaDevolucion[1]);
					    int diaDevolucion = Integer.parseInt(partesFechaDevolucion[0]);

					    // Comparación
					    if ((añoARevisar > añoPedido || (añoARevisar == añoPedido && mesARevisar > mesPedido) ||
					            (añoARevisar == añoPedido && mesARevisar == mesPedido && diaARevisar >= diaPedido)) &&
					            (añoARevisar < añoDevolucion || (añoARevisar == añoDevolucion && mesARevisar < mesDevolucion) ||
					            (añoARevisar == añoDevolucion && mesARevisar == mesDevolucion && diaARevisar <= diaDevolucion))) {
					        return true; // La fechaARevisar está entre fechaPedido y fechaDevolucion o es igual a alguna de ellas
					    }

					    return false; // No está entre las fechas
					}

					private boolean revisarCodigoExiste(String codigo) {
						for (Texto t: listaTextos) {
							if(t.getCodigo()==Integer.parseInt(codigo)) {
								return true;
							}
						}						
						return false;
					}
		        	
		        });
		        
			}
        	
        });
        buttonEstadisticas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        
	}
	

	@Override
	public void menuCliente(String rut, String fecha) {
		JFrame frame = new JFrame("Menu Ingreso");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 50));
        frame.add(panel);
        placeComponentsCliente(panel,rut,fecha);
        frame.setVisible(true);
	}

	private void placeComponentsCliente(JPanel panel, String rut, String fecha) {
		JButton buttonReservar = new JButton("Reservar Texto");
		buttonReservar.setBounds(10, 10, 80, 25);
        panel.add(buttonReservar);
        
        JButton buttonDevolver = new JButton("Devolver Texto");
        buttonDevolver.setBounds(100, 10, 80, 25);
        panel.add(buttonDevolver);
        
        JButton buttonVer = new JButton("Ver Reservas");
        buttonVer.setBounds(10, 40, 80, 25);
        panel.add(buttonVer);
        
        JButton buttonPagar = new JButton("Pagar Multas");
        buttonPagar.setBounds(100, 40, 80, 25);
        panel.add(buttonPagar);
        
        for (Devolucion devolucion : listaDevoluciones) {
			if (devolucion.getEstadoDeuda().equals("pendiente")) {
				int codigoReserva = devolucion.getCodigoReserva();
				for (Reserva r: listaReservas) {
					if (r.getCodigoReserva()==codigoReserva) {
						String rutMoroso = r.getRut();
						if (rutMoroso.equals(rut)) {
							pendientesPorPersona.add(devolucion);
						}
					}
				}
			}
		}
        buttonReservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	boolean revisar = revisarSiUsuarioPuedeReservar(rut);
            	
            	if (revisar) {
            	ArrayList<Texto> listaLibrosDisponibles = mostrarLibrosDisponibles();
            	//voa tratar de imprimir todos los libros disponibles
            	//se revisa si se puede reservar)?
            	//hay cantidad limite de libros?
            	//o hay como un solo libro por biblioteca
            	//nose
            	//se reserva
            	JFrame frame = new JFrame("Menu Ingreso");
            	frame.setSize(400, 300);
            	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	frame.setLocationRelativeTo(null);
            	JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 50));
            	frame.add(panel);
            	placeComponentsReserva(panel,fecha,listaLibrosDisponibles,rut);
            	frame.setVisible(true);
            	
            	
            	}
            	else {
            		//imprimir en la interfaz 
            		//usted no puede pedir mas libros
            	}
            	
            	
                
            }

			private boolean revisarSiUsuarioPuedeReservar(String rut) {
				for (Persona p : listaPersonas) {
					if(p.getRut().equals(rut)) {
						ArrayList<Texto> t = ((Usuario) p).getListaLibrosReservados();
						int cont=0;
						for (Texto texto : t) {
							System.out.println(t);
							cont++;
						}
						if(cont<=2) {
							return true;
						}
					}
					
				}
				return false;
			}

			private void placeComponentsReserva(JPanel panel, String fecha, ArrayList<Texto> listaLibrosDisponibles, String rut) {
				panel.setLayout(null);
		    	
		    	JLabel mensajeLabel = new JLabel("Ingresar Codigo del Texto que quiera Reservar");
		        mensajeLabel.setBounds(70, 20, 800, 25);
		        panel.add(mensajeLabel);
		    	
		    	
		        JLabel CodigoLabel = new JLabel("Codigo: ");
		        CodigoLabel.setBounds(100, 50, 80, 25);
		        panel.add(CodigoLabel);

		        JTextField CodigoText = new JTextField(20);
		        CodigoText.setBounds(200, 50, 165, 25);
		        panel.add(CodigoText);
		        
		        
		        JLabel fechaPedidoLabel = new JLabel("Fecha de Retiro: ");
		        fechaPedidoLabel.setBounds(70, 80, 808, 25);
		        panel.add(fechaPedidoLabel);

		        JTextField fechaPedidoText = new JTextField(20);
		        fechaPedidoText.setBounds(200, 80, 165, 25);
		        panel.add(fechaPedidoText);
		        
		        
		        JLabel fechaEntregaLabel = new JLabel("Fecha de Entrega: ");
		        fechaEntregaLabel.setBounds(70, 110, 808, 25);
		        panel.add(fechaEntregaLabel);

		        JTextField fechaEntregaText = new JTextField(20);
		        fechaEntregaText.setBounds(200, 110, 165, 25);
		        panel.add(fechaEntregaText);
		        
		        JCheckBox useFechaActualCheckBox = new JCheckBox("Usar Fecha Actual");
		        useFechaActualCheckBox.setBounds(60, 140, 140, 25);
		        panel.add(useFechaActualCheckBox);

		        useFechaActualCheckBox.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		                if (useFechaActualCheckBox.isSelected()) {
		                	Component[] components = panel.getComponents();
		                    for (Component component : components) {
		                        if (component instanceof JLabel) {
		                            String text = ((JLabel) component).getText();
		                            if (text.startsWith("Fecha de Retiro: ")) {
		                                panel.remove(component);
		                            }
		                        }
		                    }
		                    fechaPedidoText.setVisible(false);
		                    panel.revalidate();
		                	
		                	
		                	
		                	JLabel fechaHoyLabel = new JLabel("Fecha de hoy:              "+fecha);
		    		        fechaHoyLabel.setBounds(80, 80, 800, 25);
		    		        panel.add(fechaHoyLabel);
		    		        panel.repaint();

		                }
		                else {
		                    // Si el JLabel fue agregado anteriormente, se debe quitar antes de volver a pintar el panel
		                    Component[] components = panel.getComponents();
		                    for (Component component : components) {
		                        if (component instanceof JLabel) {
		                            String text = ((JLabel) component).getText();
		                            if (text.startsWith("Fecha de hoy:")) {
		                                panel.remove(component);
		                            }
		                        }
		                    }
		                    JLabel fechaPedidoLabel = new JLabel("Fecha de Retiro: ");
		    		        fechaPedidoLabel.setBounds(70, 80, 808, 25);
		    		        panel.add(fechaPedidoLabel);
		    		        fechaPedidoText.setVisible(true);
		                    
		                    
		                    
		                    panel.revalidate();
		                    panel.repaint();
		                }
		            }
		        });
		        
		        JButton button = new JButton("Submit");
		        button.setBounds(200, 140, 80, 25);
		        panel.add(button);
		        
		        
		        
		        
		        // Acción al presionar el botón
		        button.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                String codigoReserva = CodigoText.getText();
		                String fechaEntrega = fechaEntregaText.getText();
		                
		                if (useFechaActualCheckBox.isSelected()) {
		                	String fechaPedido = fecha;
		                	if (revisarDatosReserva(codigoReserva,fechaPedido,fechaEntrega,listaLibrosDisponibles)) {
		                		reservarTexto(rut,codigoReserva,fechaPedido,fechaEntrega,listaLibrosDisponibles);
		                		
		                		
		                		
		                		/*
								for (Persona p : listaPersonas) {
									if (p instanceof Usuario && p.getRut().equals(rut) && p.getTipoPersona().equals("Usuario")) {
										ArrayList<Texto> minilista =((Usuario) p).getListaLibrosReservados();
										for (Texto texto : minilista) {
											System.out.println(texto);
										}
									}
								}
								esto es para probar si se agrega
		                		 */
		                		//se modifica el txt de reservas
		                		
		                	}
		                }
		                else {
		                	String fechaPedido = fechaPedidoText.getText();
		                	if (revisarDatosReserva(codigoReserva,fechaPedido,fechaEntrega,listaLibrosDisponibles)) {
		                		reservarTexto(rut,codigoReserva,fechaPedido,fechaEntrega,listaLibrosDisponibles);
		                		
		                		
		                		/*
		                		for (Persona p : listaPersonas) {
									if (p instanceof Usuario && p.getRut().equals(rut) && p.getTipoPersona().equals("Usuario")) {
										ArrayList<Texto> minilista =((Usuario) p).getListaLibrosReservados();
										for (Texto texto : minilista) {
											System.out.println(texto);
										}
									}
								}
								lo mismo q arriba
								*/
		                		
		                		
		                		
		                		
		                		//se modifica el txt de reservas
		                	}
		                }
		            	
		                
		                
		                
		                
		                /*
		                String codigoElegido = CodigoText.getText();
	            		JOptionPane.showMessageDialog(null, codigoElegido);
		            	*/
		                
						frameListaTextos.dispose();
		                SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
						String mensaje = "Libro Correctamente Reservado";
	            		JOptionPane.showMessageDialog(null, mensaje);
	            		//Sistema s = SistemaImpls.getInstancia();
						//s.menuCliente(rut,fecha);
		                
		                
		            }

					

					private void reservarTexto(String rut, String codigoReserva, String fechaPedido,
							String fechaEntrega, ArrayList<Texto> listaLibrosDisponibles) {
						for (Persona p : listaPersonas) {
							if (p instanceof Usuario && p.getRut().equals(rut) && p.getTipoPersona().equals("Usuario")) {
								for (Texto texto : listaLibrosDisponibles) {
									if (texto.getCodigo()==Integer.parseInt(codigoReserva)) {
										//se agrega el texto a la lista personal de textos
										((Usuario) p).agregarLibro(texto);
										//se agrega la nueva reserva a la lista reservas
										int contReservas = contadorReservas();
										Reserva r = new Reserva(contReservas,rut,texto.getCodigo(),fechaPedido,fechaEntrega);
										listaReservas.add(r);
									}
								}
							}
						}
					}



					private int contadorReservas() {
						int cont=0;
						for (Reserva r : listaReservas) {
							cont++;
						}
						cont+=100000;
						return cont;
					}



					private boolean revisarDatosReserva(String codigoReserva, String fechaPedido, String fechaEntrega, ArrayList<Texto> listaLibrosDisponibles) {
						for (Texto t : listaLibrosDisponibles) {
							if (t.getCodigo()==Integer.parseInt(codigoReserva) && !fechaPedido.isBlank() && !fechaEntrega.isBlank()) {
								
								String[] partesFechaPedido = fechaPedido.split("/");
					            String[] partesFechaEntrega = fechaEntrega.split("/");

					            int anioPedido = Integer.parseInt(partesFechaPedido[0]);
					            int mesPedido = Integer.parseInt(partesFechaPedido[1]);
					            int diaPedido = Integer.parseInt(partesFechaPedido[2]);

					            int anioEntrega = Integer.parseInt(partesFechaEntrega[0]);
					            int mesEntrega = Integer.parseInt(partesFechaEntrega[1]);
					            int diaEntrega = Integer.parseInt(partesFechaEntrega[2]);

					            // Comparación de fechas
					            if (anioPedido < anioEntrega || 
					                (anioPedido == anioEntrega && mesPedido < mesEntrega) || 
					                (anioPedido == anioEntrega && mesPedido == mesEntrega && diaPedido <= diaEntrega)) {
					                return true; // O realiza alguna acción si se cumple la condición
					            } else {
					            	System.out.println("Ingresaste mal los datos");
					                return false; // O realiza alguna acción si no se cumple la condición
					            }
					        }
					    }
					    return false;
								
								
							
						
					}
		        });
		        
		        
			}

			private ArrayList<Texto> mostrarLibrosDisponibles() {
				ArrayList<Texto> listaLibrosDisponibles = new ArrayList<>();
				
				//cont para revisar si alguna vez fue pedido el libro
				int contPeticion=0;
				
				
				for (Texto texto : listaTextos) {
					for (Reserva r: listaReservas) {
						
						//se revisa si el libro fue alguna vez reservado
						if (texto.getCodigo()==r.getCodigoObjeto()) {
							//si entra aca es pq fue reservado
							//ahora hay q revisar si lo devolvieron
							contPeticion++;
							for (Devolucion d: listaDevoluciones) {
								if (d.getCodigoReserva()==r.getCodigoReserva()) {
									//se pidio el libro y se devolvio
									listaLibrosDisponibles.add(texto);
								}
								
							}
							
						}
					}
					if(contPeticion==0) {
						//si entra aca es pq nunca lo reservaron antes
						listaLibrosDisponibles.add(texto);
					}
					contPeticion=0;
				}
				
				
				// Crear una nueva ventana (JFrame)

		        frameListaTextos = new JFrame("Lista De textos Disponibles");
		        frameListaTextos.setSize(800, 300);
		        frameListaTextos.setLocationRelativeTo(null);

		        // Crear un JTextArea para mostrar la lista
		        JTextArea textArea = new JTextArea();
		        textArea.setEditable(false); // Hacer el área de texto no editable

		        // Agregar los elementos del ArrayList al JTextArea
		        
		        for (Texto elemento : listaLibrosDisponibles) {
		            textArea.append(elemento + "\n"); // Agregar cada elemento seguido de un salto de línea
		        }
		        
		        // Agregar el JTextArea a un JScrollPane para permitir el desplazamiento si hay muchos elementos
		        JScrollPane scrollPane = new JScrollPane(textArea);

		        // Agregar el JScrollPane al JFrame
		        frameListaTextos.add(scrollPane, BorderLayout.CENTER);

		        // Mostrar la ventana
		        frameListaTextos.setVisible(true);
		        return listaLibrosDisponibles;
			}
        });
        
        
        buttonDevolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				//se revisa si se puede devolver
				//se devuelve
				//o tal vez no
            	ArrayList<Texto> listaLibrosReservados = null;
            	for (Persona p : listaPersonas) {
					if (p instanceof Usuario && p.getRut().equals(rut) && p.getTipoPersona().equals("Usuario")) {
						listaLibrosReservados= ((Usuario) p).getListaLibrosReservados();
					}
				}
            	boolean VoF = mostrarLibrosReservados(listaLibrosReservados);
            	
            	if (VoF) {
            		JFrame frame = new JFrame("Menu Devolucion");
            		frame.setSize(400, 300);
            		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            		frame.setLocationRelativeTo(null);
            		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 50));
            		frame.add(panel);
            		placeComponentsDevolucion(panel,fecha,listaLibrosReservados,rut);
            		frame.setVisible(true);
            	}
            	
            	
            }

			private void placeComponentsDevolucion(JPanel panel, String fecha, ArrayList<Texto> listaLibrosReservados,
					String rut) {
				panel.setLayout(null);
		    	
		    	JLabel mensajeLabel = new JLabel("Ingresar Codigo del Texto que quiera Devolver");
		        mensajeLabel.setBounds(70, 20, 800, 25);
		        panel.add(mensajeLabel);
		    	
		    	
		        JLabel CodigoLabel = new JLabel("Codigo: ");
		        CodigoLabel.setBounds(100, 50, 80, 25);
		        panel.add(CodigoLabel);

		        JTextField CodigoText = new JTextField(20);
		        CodigoText.setBounds(200, 50, 165, 25);
		        panel.add(CodigoText);
		        
		        JButton button = new JButton("Submit");
		        button.setBounds(200, 140, 80, 25);
		        panel.add(button);
		        
		        
		        
		        
		        // Acción al presionar el botón
		        button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String codigo = CodigoText.getText();
						boolean revisarCodigo = revisarCodigo(codigo);
						if (revisarCodigo) {
							//llegamos hasta el texto en cuestion dentro de la lista
							//ahora hay q revisar si esta dentro del plazo
							String fechaDevolucion="";
							int codigoReserva=0;
							String devolucionOriginal="";
							for (Reserva r: listaReservas) {
								if (r.getCodigoObjeto()==Integer.parseInt(codigo)) {
									fechaDevolucion = r.getFechaDevolucion();
									codigoReserva=r.getCodigoReserva();
									devolucionOriginal=r.getFechaDevolucion();
								}
							}
							boolean revisarFechaCorrecta = revisarFecha(fecha,fechaDevolucion);
							if (revisarFechaCorrecta) {
								//el libro se entrego a tiempo
								//por ende se elimina de manera normal
								//se agrega al txt de devoluciones con un "pagado"
								int contDevoluciones = contDevoluciones();
								Devolucion d = new Devolucion(contDevoluciones,codigoReserva,devolucionOriginal,fecha,"pagado");
								eliminarLibroListaPersonal(rut,codigo);
								frameListaTextos.dispose();
								SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
								String mensaje= "Libro Devuelto Correctamente";
				        		JOptionPane.showMessageDialog(null, mensaje);
							}
							else {
								//el libro se retraso
								//por ende el usuario no puede entregarlo directamente
								//debe entregarlo un Trabajador
								//se agrega al txt de devoluciones con un pendiente
								SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
								frameListaTextos.dispose();
								String mensaje= "ERROR, Su entrega se ha retrasado, por ende, debe acercarse a un Trabajador para la devolucion";
				        		JOptionPane.showMessageDialog(null, mensaje);
				        		
				        		/* de todos modos se usaria algo como esto
								int contDevoluciones = contDevoluciones();
								Devolucion d = new Devolucion(contDevoluciones,codigoReserva,devolucionOriginal,fecha,"pendiente");
								*/

							}
						}
						
					}

					private void eliminarLibroListaPersonal(String rut, String codigo) {
						for (Persona p: listaPersonas) {
							if (p instanceof Usuario && p.getRut().equals(rut) && p.getTipoPersona().equals("Usuario")) {
								Texto t =null;
								for (Texto texto: listaTextos) {
									if(texto.getCodigo()==Integer.parseInt(codigo)) {
										t = texto;
									}
								}
								
								((Usuario) p).eliminarLibro(t);
							}
						}
					}

					private int contDevoluciones() {
						int cont=0;
						for (Devolucion d: listaDevoluciones) {
							cont++;
						}
						cont+=1000000;
						return cont;
					}

					private boolean revisarFecha(String fecha, String fechaDevolucion) {
						String[] partesFechaActual = fecha.split("/");
						String[] partesFechaReserva =fechaDevolucion.split("-");
						
						int anioPedido = Integer.parseInt(partesFechaActual[0]);
			            int mesPedido = Integer.parseInt(partesFechaActual[1]);
			            int diaPedido = Integer.parseInt(partesFechaActual[2]);

			            int anioEntrega = Integer.parseInt(partesFechaReserva[2]);
			            int mesEntrega = Integer.parseInt(partesFechaReserva[1]);
			            int diaEntrega = Integer.parseInt(partesFechaReserva[0]);

			            // Comparación de fechas
			            if (anioPedido < anioEntrega || 
			                (anioPedido == anioEntrega && mesPedido < mesEntrega) || 
			                (anioPedido == anioEntrega && mesPedido == mesEntrega && diaPedido <= diaEntrega)) {
			                return true; // O realiza alguna acción si se cumple la condición
			            }
						
						return false;
					}

					private boolean revisarCodigo(String codigo) {
						for (Persona p : listaPersonas) {
							if (p instanceof Usuario && p.getRut().equals(rut) && p.getTipoPersona().equals("Usuario")) {
								ArrayList<Texto> minilista =((Usuario) p).getListaLibrosReservados();
								for (Texto texto : minilista) {
									if (Integer.parseInt(codigo)==texto.getCodigo()) {
										return true;
									}
									
									
								}
							}
						}
						return false;
					}
		        	
		        });
		        
		        
			}

			private boolean mostrarLibrosReservados(ArrayList<Texto> listaLibrosReservados) {
				frameListaTextos = new JFrame("Lista De textos Disponibles");
				frameListaTextos.setSize(800, 300);
				frameListaTextos.setLocationRelativeTo(null);
		        //ola
		        
		        
		        //alo
		        
		        if (listaLibrosReservados.size()==0) {
		        	String mensaje= "Usted no Posee Libros en Reserva";
	        		JOptionPane.showMessageDialog(null, mensaje);
	        		return false;
		        }
		        else {
		        	// Crear un JTextArea para mostrar la lista
		        	JTextArea textArea = new JTextArea();
		        	textArea.setEditable(false); // Hacer el área de texto no editable

		        	// Agregar los elementos del ArrayList al JTextArea
		        
		        	for (Texto elemento : listaLibrosReservados) {
		        		textArea.append(elemento + "\n"); // Agregar cada elemento seguido de un salto de línea
		        	}
		        
		        	// Agregar el JTextArea a un JScrollPane para permitir el desplazamiento si hay muchos elementos
		        	JScrollPane scrollPane = new JScrollPane(textArea);

		        	// Agregar el JScrollPane al JFrame
		        	frameListaTextos.add(scrollPane, BorderLayout.CENTER);

		        	// Mostrar la ventana
		        	frameListaTextos.setVisible(true);
		        	return true;
		        }
			}
        });
        
        
        buttonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				//imprime la lista de libros que posee la persona
            	ArrayList<Texto> listaLibrosReservados = null;
            	for (Persona p : listaPersonas) {
					if (p instanceof Usuario && p.getRut().equals(rut) && p.getTipoPersona().equals("Usuario")) {
						listaLibrosReservados= ((Usuario) p).getListaLibrosReservados();
					}
				}
            	boolean VoF = mostrarLibrosReservados(listaLibrosReservados);
            	
            	
            	
            }


			private boolean mostrarLibrosReservados(ArrayList<Texto> listaLibrosReservados) {
				frameListaTextos = new JFrame("Lista De textos Disponibles");
				frameListaTextos.setSize(800, 300);
				frameListaTextos.setLocationRelativeTo(null);
		        //ola
		        
		        
		        //alo
		        
		        if (listaLibrosReservados.size()==0) {
		        	String mensaje= "Usted no Posee Libros en Reserva";
	        		JOptionPane.showMessageDialog(null, mensaje);
	        		return false;
		        }
		        else {
		        	// Crear un JTextArea para mostrar la lista
		        	JTextArea textArea = new JTextArea();
		        	textArea.setEditable(false); // Hacer el área de texto no editable

		        	// Agregar los elementos del ArrayList al JTextArea
		        
		        	for (Texto elemento : listaLibrosReservados) {
		        		textArea.append(elemento + "\n"); // Agregar cada elemento seguido de un salto de línea
		        	}
		        
		        	// Agregar el JTextArea a un JScrollPane para permitir el desplazamiento si hay muchos elementos
		        	JScrollPane scrollPane = new JScrollPane(textArea);

		        	// Agregar el JScrollPane al JFrame
		        	frameListaTextos.add(scrollPane, BorderLayout.CENTER);

		        	// Mostrar la ventana
		        	frameListaTextos.setVisible(true);
		        	return true;
		        }
			}
        });
        
        
        buttonPagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				//le cobras un riñon
				//pero antes le muestras cuanto debe y porque
            	
            	
            	
            	
            	if (pendientesPorPersona.size()==0) {
            		String mensaje= "Usted no Posee Libros Morosos";
	        		JOptionPane.showMessageDialog(null, mensaje);
            	}
            	else {
            		
            		mostrarCodigoDiasMorosos();
            		
            		JFrame frame = new JFrame("Menu Deudas");
            		frame.setSize(400, 300);
            		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           			frame.setLocationRelativeTo(null);
           			JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 50));
           			frame.add(panel);
            		placeComponentsDeudas(panel,fecha,rut);
           			frame.setVisible(true);
            		
            	}
            }

			private void placeComponentsDeudas(JPanel panel, String fecha,
					String rut) {
				panel.setLayout(null);
		    	
		    	JLabel mensajeLabel = new JLabel("Ingresar Codigo de la deuda que quiera pagar");
		        mensajeLabel.setBounds(70, 20, 800, 25);
		        panel.add(mensajeLabel);
		    	
		    	
		        JLabel CodigoLabel = new JLabel("Codigo: ");
		        CodigoLabel.setBounds(100, 50, 80, 25);
		        panel.add(CodigoLabel);

		        JTextField CodigoText = new JTextField(20);
		        CodigoText.setBounds(200, 50, 165, 25);
		        panel.add(CodigoText);
		        
		        
		        
		        
		        JCheckBox useFechaActualCheckBox = new JCheckBox("Pagar todas las Deudas");
		        useFechaActualCheckBox.setBounds(60, 140, 140, 25);
		        panel.add(useFechaActualCheckBox);
		        
		        useFechaActualCheckBox.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (useFechaActualCheckBox.isSelected()) {
				            CodigoLabel.setVisible(false);
				            CodigoText.setVisible(false);
				            
				            JLabel fechaHoyLabel = new JLabel("Se Cancelaran Todas Las Deudas");
				            fechaHoyLabel.setBounds(80, 80, 800, 25);
				            panel.add(fechaHoyLabel);
				            panel.repaint();
				        } else {
				            CodigoLabel.setVisible(true);
				            CodigoText.setVisible(true);

				            Component[] components = panel.getComponents();
				            for (Component component : components) {
				                if (component instanceof JLabel) {
				                    String text = ((JLabel) component).getText();
				                    if (text.startsWith("Se Cancelaran Todas Las Deudas")) {
				                        panel.remove(component);
				                    }
				                }
				            }
				            panel.revalidate();
				            panel.repaint();
				        }
					}
		        	
		        });

		        JButton button = new JButton("Submit");
		        button.setBounds(200, 140, 80, 25);
		        panel.add(button);
		        
		        // Acción al presionar el botón
		        button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (useFechaActualCheckBox.isSelected()) {
			                SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
				        	frameListaMorosos.setVisible(false);

							int pagoTotal = pagoTotalTotales();
							
							boolean pago = true;
							realmentePago(pagoTotal);
	                		if (pago) {
	                			//ahora se elimina de la lista de la persona
	                			
	                			
	                			//y se modifica el txt devoluciones
	                			//cambiando el "pendiente" por un "pagado"
	                			modificarTxtDevoluciones(pendientesPorPersona);
	                			
	                			pendientesPorPersona.clear();
	                		}
	                		return;
						}
						else {
			                String codigoDeuda = CodigoText.getText();
			                SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
				        	frameListaMorosos.setVisible(false);
			                int contError =0;
			                for (Devolucion devolucion : pendientesPorPersona) {
			                	if (Integer.parseInt(codigoDeuda)==devolucion.getCodigoDevolucion()) {
				                	//Ya encontraste la deuda de la persona en especifico
			                		//por ende ahora se paga
			                		//Como se paga?
			                		//nose
									int pagoTotal = pagoTotalUnoSolo(codigoDeuda);

			                		boolean pago = true;
			                		realmentePago(pagoTotal);
			                		//ahora se elimina de la lista de la persona
			                		if (pago) {
			                			pendientesPorPersona.remove(devolucion);
			                			//y se modifica el txt devoluciones
			                			//cambiando el "pendiente" por un "pagado"
			                			int d = listaDevoluciones.indexOf(devolucion);
			                			Devolucion de = listaDevoluciones.get(d);
			                			listaDevoluciones.remove(d);
			                			Devolucion devo = new Devolucion(de.getCodigoDevolucion(),de.getCodigoReserva(),de.getFechaDevolucionOriginal(),de.getFechaDevolucionReal(),"pagado");
			                			listaDevoluciones.add(devo);
			                			
			                		}
			                		contError++;
			                		return;
			                		
				                }
							}
			                if (contError==0) {
			                	//ingreso mal el codigo
			                	String mensaje= "Codigo Incorrecto, Ingrese el codigo nuevamente";
			                	JOptionPane.showMessageDialog(null, mensaje);
			                	
			                }
			                
			                
						}

					}

					private void modificarTxtDevoluciones(ArrayList<Devolucion> pendientesPorPersona) {
						for (Devolucion devolucion : pendientesPorPersona) {
							int d = listaDevoluciones.indexOf(devolucion);
                			Devolucion de = listaDevoluciones.get(d);
                			listaDevoluciones.remove(d);
                			Devolucion devo = new Devolucion(de.getCodigoDevolucion(),de.getCodigoReserva(),de.getFechaDevolucionOriginal(),de.getFechaDevolucionReal(),"pagado");
                			listaDevoluciones.add(devo);
						}
					}

					private int pagoTotalUnoSolo(String codigoDeuda) {
			        	for (Devolucion d: pendientesPorPersona) {
			        		if (Integer.parseInt(codigoDeuda)==d.getCodigoDevolucion()) {
			        			int diasMorosos=contDiasMorosos(d,"");
				        		int valor = diasMorosos*500;
			        			
			        			return valor;
			        		}
			        	}
						
						return 0;
					}

					private int pagoTotalTotales() {
						int valorTotal=0;
			        	for (Devolucion d: pendientesPorPersona) {
			        		int diasMorosos=contDiasMorosos(d,"");
			        		int valor = diasMorosos*500;
			        		valorTotal+=valor;
			        	}
						
						return valorTotal;
					}

					private void realmentePago(int pagoTotal) {
						String mensaje = "Son "+pagoTotal;
	            		JOptionPane.showMessageDialog(null, mensaje);
					}

					
		        	
		        });
			}

			private boolean mostrarCodigoDiasMorosos() {
				frameListaMorosos = new JFrame("Lista De textos Disponibles");
				frameListaMorosos.setSize(800, 300);
				frameListaMorosos.setLocationRelativeTo(null);
				
				JTextArea textArea = new JTextArea();
	        	textArea.setEditable(false); // Hacer el área de texto no editable

	        	// Agregar los elementos del ArrayList al JTextArea
	        
	        	for (Devolucion d: pendientesPorPersona) {
	        		int diasMorosos=contDiasMorosos(d,"");
	        		int valor = diasMorosos*500;
	        		String nombreLibro = obtenerNombreLibro(d);
	        		textArea.append("Codigo: "+d.getCodigoDevolucion()+", Nombre Libro: "+nombreLibro+", Dias Morosos: "+diasMorosos +", Valor: "+valor+ "\n"); // Agregar cada elemento seguido de un salto de línea
	        	}
	        
	        	// Agregar el JTextArea a un JScrollPane para permitir el desplazamiento si hay muchos elementos
	        	JScrollPane scrollPane = new JScrollPane(textArea);

	        	// Agregar el JScrollPane al JFrame
	        	frameListaMorosos.add(scrollPane, BorderLayout.CENTER);

	        	// Mostrar la ventana
	        	frameListaMorosos.setVisible(true);
				
				
				return false;
				
			}

			private String obtenerNombreLibro(Devolucion d) {
				for (Reserva r: listaReservas) {
					if(r.getCodigoReserva()==d.getCodigoReserva()) {
						int codigoTexto=r.getCodigoObjeto();
						for (Texto t: listaTextos) {
							if(t.getCodigo()==codigoTexto) {
								return t.getNombre_texto();
							}
						}
					}
				}
				return null;
			}

			private int contDiasMorosos(Devolucion devolucion, String fecha) {
				String fechaOriginal = devolucion.getFechaDevolucionOriginal();
				String fechaReal = devolucion.getFechaDevolucionReal();

				String[] partesFechaOriginal = fechaOriginal.split("-");
				String[] partesFechaActual = fechaReal.split("-");
				

			    int anioOriginal = Integer.parseInt(partesFechaOriginal[2]);
			    int mesOriginal = Integer.parseInt(partesFechaOriginal[1]);
			    int diaOriginal = Integer.parseInt(partesFechaOriginal[0]);

			    int anioActual = Integer.parseInt(partesFechaActual[2]);
			    int mesActual = Integer.parseInt(partesFechaActual[1]);
			    int diaActual = Integer.parseInt(partesFechaActual[0]);

			    // Cálculo de la diferencia de días
			    int diffAnios = anioActual - anioOriginal;
			    int diffMeses = mesActual - mesOriginal;
			    int diffDias = diaActual - diaOriginal;

			    int totalDias = diffAnios * 365 + diffMeses * 30 + diffDias;
			    
			    return totalDias; // Devuelve la diferencia en días
				
				
			}
        });
        
	}

	
	
	
	
	
}
