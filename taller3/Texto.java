package taller3;

public class Texto {
	private int ano_publicacion,codigo;
	private String autor,nombre_texto,tipoTexto;
	
	
	public Texto(int ano_publicacion, int codigo, String autor, String nombre_texto, String tipoTexto) {
		this.ano_publicacion = ano_publicacion;
		this.codigo = codigo;
		this.autor = autor;
		this.nombre_texto = nombre_texto;
		this.tipoTexto = tipoTexto;
	}


	public int getAno_publicacion() {
		return ano_publicacion;
	}


	public void setAno_publicacion(int ano_publicacion) {
		this.ano_publicacion = ano_publicacion;
	}


	public int getCodigo() {
		return codigo;
	}


	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public String getAutor() {
		return autor;
	}


	public void setAutor(String autor) {
		this.autor = autor;
	}


	public String getNombre_texto() {
		return nombre_texto;
	}


	public void setNombre_texto(String nombre_texto) {
		this.nombre_texto = nombre_texto;
	}


	public String getTipoTexto() {
		return tipoTexto;
	}


	public void setTipoTexto(String tipoTexto) {
		this.tipoTexto = tipoTexto;
	}


	@Override
	public String toString() {
		return "Texto [ano_publicacion=" + ano_publicacion + ", codigo=" + codigo + ", autor=" + autor
				+ ", nombre_texto=" + nombre_texto + ", tipoTexto=" + tipoTexto + "]";
	}
	
	
		
	
	
	
	
	
}
	
	

