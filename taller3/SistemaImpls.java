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
			    if (p instanceof Usuario && p.getRut().equals(partesReserva[1]) && p.getTipoPersona().equals("Usuario")) {
					//podriamos agregar solo el codigo del libro?
					//o el libro entero nose
					for (Texto t: listaTextos) {
						if (Integer.parseInt(partesReserva[2])==t.getCodigo()) {
							((Usuario) p).agregarLibro(t);
						}
					}
				}
			}
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
                System.out.println(fecha);
                if(revisarDatosCorrectos && !fecha.isBlank()) {
					SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
                	String nombre = buscarNombreConRut(rut);
                	String mensajeBienvenida = "¡Bienvenido Usuario: "+nombre+ " con RUT: " + rut + "!";
                	JOptionPane.showMessageDialog(null, mensajeBienvenida);
                	//aca se prosigue con lo q se deba hacer
                	//comandos para los clientes y/o trabajadores
                	//menuCliente();
                	//menuTrabajador();
                	String a = getTipoPersona(rut);
                	if (a.equals("Usuario")) {
                		
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
	public void menuTrabajador() {
		System.out.println("yo trabajo");
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
		                	System.out.println(codigoReserva);
		                	System.out.println(fechaEntrega);
		                	System.out.println(fechaPedido+" fechapedido");
		                	if (revisarDatosReserva(codigoReserva,fechaPedido,fechaEntrega,listaLibrosDisponibles)) {
		                		reservarTexto(rut,codigoReserva,fechaPedido,fechaEntrega,listaLibrosDisponibles);
		                		
								for (Persona p : listaPersonas) {
									if (p instanceof Usuario && p.getRut().equals(rut) && p.getTipoPersona().equals("Usuario")) {
										ArrayList<Texto> minilista =((Usuario) p).getListaLibrosReservados();
										for (Texto texto : minilista) {
											System.out.println(texto);
										}
									}
								}

		                		//se modifica el txt
		                		
		                	}
		                }
		                else {
		                	String fechaPedido = fechaPedidoText.getText();
		                	System.out.println(codigoReserva);
		                	System.out.println(fechaEntrega);
		                	System.out.println(fechaPedido+" fechapedido");
		                	if (revisarDatosReserva(codigoReserva,fechaPedido,fechaEntrega,listaLibrosDisponibles)) {
		                		reservarTexto(rut,codigoReserva,fechaPedido,fechaEntrega,listaLibrosDisponibles);
		                		
		                		for (Persona p : listaPersonas) {
									if (p instanceof Usuario && p.getRut().equals(rut) && p.getTipoPersona().equals("Usuario")) {
										ArrayList<Texto> minilista =((Usuario) p).getListaLibrosReservados();
										for (Texto texto : minilista) {
											System.out.println(texto);
										}
									}
								}
		                		
		                		
		                		
		                		
		                		//se modifica el txt
		                	}
		                }
		            	
		                
		                
		                
		                
		                /*
		                String codigoElegido = CodigoText.getText();
	            		JOptionPane.showMessageDialog(null, codigoElegido);
		            	*/
		                
		                
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
										((Usuario) p).agregarLibro(texto);
									}
								}
							}
						}
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
		        JFrame frame = new JFrame("Lista De textos Disponibles");
		        frame.setSize(800, 300);
		        frame.setLocationRelativeTo(null);

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
		        frame.add(scrollPane, BorderLayout.CENTER);

		        // Mostrar la ventana
		        frame.setVisible(true);
		        return listaLibrosDisponibles;
			}
        });
        
        
        buttonDevolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				//se revisa si se puede devolver
				//se devuelve
				//o tal vez no
				String mensaje= "Devolucionado";
        		JOptionPane.showMessageDialog(null, mensaje);
            }
        });
        
        
        buttonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				//imprime la lista de libros que posee la persona
				String mensaje= "Vericionado";
        		JOptionPane.showMessageDialog(null, mensaje);
            }
        });
        
        
        buttonPagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				//le cobras un riñon
				//pero antes le muestras cuanto debe y porque
				String mensaje= "Riñonizado";
        		JOptionPane.showMessageDialog(null, mensaje);
            }
        });
        
	}

	
	
	
	
}
