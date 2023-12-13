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
	public int getCodigoDevolucion() {
		return codigoDevolucion;
	}
	public void setCodigoDevolucion(int codigoDevolucion) {
		this.codigoDevolucion = codigoDevolucion;
	}
	public int getCodigoReserva() {
		return codigoReserva;
	}
	public void setCodigoReserva(int codigoReserva) {
		this.codigoReserva = codigoReserva;
	}
	public String getFechaDevolucionOriginal() {
		return fechaDevolucionOriginal;
	}
	public void setFechaDevolucionOriginal(String fechaDevolucionOriginal) {
		this.fechaDevolucionOriginal = fechaDevolucionOriginal;
	}
	public String getFechaDevolucionReal() {
		return fechaDevolucionReal;
	}
	public void setFechaDevolucionReal(String fechaDevolucionReal) {
		this.fechaDevolucionReal = fechaDevolucionReal;
	}
	public String getEstadoDeuda() {
		return estadoDeuda;
	}
	public void setEstadoDeuda(String estadoDeuda) {
		this.estadoDeuda = estadoDeuda;
	}
	
}
