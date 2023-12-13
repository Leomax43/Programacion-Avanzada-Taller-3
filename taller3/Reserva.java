package taller3;

public class Reserva {
	private int codigoReserva, codigoObjeto;
	private String rut, fechaPedido, fechaDevolucion;
	public Reserva(int codigoReserva, String rut,  int codigoObjeto, String fechaPedido, String fechaDevolucion) {
		this.codigoReserva = codigoReserva;
		this.codigoObjeto = codigoObjeto;
		this.rut = rut;
		this.fechaPedido = fechaPedido;
		this.fechaDevolucion = fechaDevolucion;
	}
	public int getCodigoReserva() {
		return codigoReserva;
	}
	public void setCodigoReserva(int codigoReserva) {
		this.codigoReserva = codigoReserva;
	}
	public int getCodigoObjeto() {
		return codigoObjeto;
	}
	public void setCodigoObjeto(int codigoObjeto) {
		this.codigoObjeto = codigoObjeto;
	}
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public String getFechaPedido() {
		return fechaPedido;
	}
	public void setFechaPedido(String fechaPedido) {
		this.fechaPedido = fechaPedido;
	}
	public String getFechaDevolucion() {
		return fechaDevolucion;
	}
	public void setFechaDevolucion(String fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}
	
}
