package frsf.isi.died.app.controller;

public enum TiposDeOrden {
	
	TITULO("titulo alfabeticamente"),
	CALIFICACION("calificacion"),
	PRECIO("precio"),
	FECHA("fecha de publicacion"),
	RELEVANCIA("relevancia");
	
	private String nombre;
	
	
	private TiposDeOrden (String nombre){
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public static TiposDeOrden getEnum(String titulo){
		TiposDeOrden enumDevolver = null;
	    for(TiposDeOrden enumTemp: TiposDeOrden.values()){
	        if((enumTemp.getNombre()).equals(titulo)){
	            enumDevolver = enumTemp;
	            break;
	        }
	    }
	    return enumDevolver;
	}
	
}
