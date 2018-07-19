package frsf.isi.died.app.excepciones;

public class MaterialNotFoundException extends Exception{

	
	public MaterialNotFoundException() {
		super();
	}

	@Override
    public String getMessage(){
        
        return "El material no se encontro";
         
    }
	
}
