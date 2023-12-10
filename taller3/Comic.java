package taller3;

public class Comic extends Texto{
	private String companiaEditorial,generoComic,edicionComic;

	public Comic(int ano_publicacion, int codigo, String autor, String nombre_texto, String companiaEditorial,
			String generoComic, String edicionComic) {
		super(ano_publicacion, codigo, autor, nombre_texto);
		this.companiaEditorial = companiaEditorial;
		this.generoComic = generoComic;
		this.edicionComic = edicionComic;
	}
	
	
	
	
	
	
	
	
	
	
}
