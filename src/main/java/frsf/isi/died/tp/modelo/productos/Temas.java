package frsf.isi.died.tp.modelo.productos;

public enum Temas {
	
	PROGRAMACION("Programacion"),
	MATEMATICA("Matematica"),
	FISICA("Fisica"),
	LENGUA("Lengua"),
	BIOLOGIA("Biologia");
	
	private String nombre;
	
	
	private Temas (String nombre){
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public static Temas getEnum(String titulo){
		Temas enumDevolver = null;
	    for(Temas enumTemp: Temas.values()){
	        if((enumTemp.getNombre()).equals(titulo)){
	            enumDevolver = enumTemp;
	            break;
	        }
	    }
	    return enumDevolver;
	}
}
