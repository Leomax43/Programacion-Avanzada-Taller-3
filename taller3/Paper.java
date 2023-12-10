package taller3;

public class Paper extends Texto{
	private String areaInvestigacion,DOI;

	public Paper(int ano_publicacion, int codigo, String autor, String nombre_texto, String areaInvestigacion,
			String dOI) {
		super(ano_publicacion, codigo, autor, nombre_texto);
		this.areaInvestigacion = areaInvestigacion;
		DOI = dOI;
	}
	
	
	
	
	
	
	
	
}
