package frsf.isi.died.app.excepciones;

public class DataOutOfBoundException extends Exception {
	
	private String mensaje = "";
	
	public DataOutOfBoundException() {
		super();
		mensaje = "La calificacion debe ser de 0 a 100";
	}
	
	public DataOutOfBoundException(String msj) {
		mensaje = msj;
	}
	
	@Override
	public String getMessage(){
	        
		return mensaje;
	         
	}
		
}
