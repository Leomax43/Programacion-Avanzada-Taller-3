package taller3;

public class Devolucion {
	private int codigoDevolucion, codigoReserva;
	private String fechaDevolucionOriginal, fechaDevolucionReal, estadoDeuda;
	public Devolucion(int codigoDevolucion, int codigoReserva, String fechaDevolucionOriginal,
			String fechaDevolucionReal, String estadoDeuda) {
		this.codigoDevolucion = codigoDevolucion;
		this.codigoReserva = codigoReserva;
		this.fechaDevolucionOriginal = fechaDevolucionOriginal;
		this.fechaDevolucionReal = fechaDevolucionReal;
		this.estadoDeuda = estadoDeuda;
	}
	
}
