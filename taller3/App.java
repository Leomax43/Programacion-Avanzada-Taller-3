package taller3;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
public class App {

	public static void main(String[] args) throws FileNotFoundException {
		Sistema s = SistemaImpls.getInstancia();
		s.leerArch();
		s.Interfaz();
		
		
		
		
		
	}





    
}
