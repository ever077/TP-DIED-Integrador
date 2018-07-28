package frsf.isi.died.tp.modelo.productos;

public enum Relevancia {
	
	ALTA(1),
	MEDIA(2),
	BAJA(3);
	
	// Prioridad 1 es la mas alta
	private Integer prioridad; 
	
	private Relevancia(Integer prioridad) {
		this.prioridad = prioridad;
	}

	public int getPrioridad() {
		return prioridad;
	}
	
}
