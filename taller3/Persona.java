package taller3;

public class Persona {
	private String nombre,rut,pass,tipoPersona;

	public Persona(String nombre, String rut, String pass,String tipoPersona) {
		super();
		this.nombre = nombre;
		this.rut = rut;
		this.pass = pass;
		this.tipoPersona=tipoPersona;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", rut=" + rut + ", pass=" + pass + ", tipoPersona=" + tipoPersona + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
