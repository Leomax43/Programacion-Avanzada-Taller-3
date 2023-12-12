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
	
}
