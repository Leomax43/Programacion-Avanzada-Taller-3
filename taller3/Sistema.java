package taller3;

import java.io.FileNotFoundException;

public interface Sistema {
	void leerArch() throws FileNotFoundException;
	void ingresarRegistrarse();
	void Interfaz();
	void registroCliente();
	void menuTrabajador(String rut, String fecha);
	
	//se puede hacer esto???
	void menuCliente(String rut, String fecha);
	
	
	
	
	
}
