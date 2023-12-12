package taller3;

public class Libro extends Texto{
	
	private String editorial,generoLiterario,edicion;

	public Libro(int ano_publicacion, int codigo, String autor, String nombre_texto, String tipoTexto, String editorial,
			String generoLiterario, String edicion) {
		super(ano_publicacion, codigo, autor, nombre_texto, tipoTexto);
		this.editorial = editorial;
		this.generoLiterario = generoLiterario;
		this.edicion = edicion;
	}
	
	
	
	
	
	
	
	
	
}
