package taller3;

import java.util.ArrayList;

public class Usuario extends Persona{
	ArrayList<Texto> listaLibrosReservados;
	
	
	public Usuario(String nombre, String rut, String pass,String tipoPersona) {
		super(nombre, rut, pass,tipoPersona);
		listaLibrosReservados = new ArrayList<>();
	}

	public void agregarLibro(Texto t){
		listaLibrosReservados.add(t);
	}
	
	public ArrayList<Texto> getListaLibrosReservados() {
		return listaLibrosReservados;
	}


	public void setListaLibrosReservados(ArrayList<Texto> listaLibrosReservados) {
		this.listaLibrosReservados = listaLibrosReservados;
	}
	
	
	
	
	
	
	
}
