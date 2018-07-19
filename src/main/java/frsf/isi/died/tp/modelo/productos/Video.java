package frsf.isi.died.tp.modelo.productos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Video extends MaterialCapacitacion {
	
	public Integer duracion;  // SI NO PASAN LAS PRUEBAS; CAMBIAR POR INT Y CASTEARLO ABAJO (donde tire error)
	public static Double costo=0.15;
	
	//CONSTRUCTORES 
	
	public Video() {
		super();
	}

	public Video(Integer id, String titulo) {
		super(id, titulo);
	}
	
	public Video(Integer id, String titulo, Double costo) {
		super(id, titulo, costo);
	}
	
	public Video(Integer id, String titulo, Double costo, Integer duracion) {
		super(id, titulo, costo);
		this.duracion = duracion;
	}
	
	public Video(Integer id, String titulo, Double costo, Integer duracion, Integer calificacion, Date fechaPublicacion, Relevancia relevancia) {
		super(id, titulo, costo, calificacion, fechaPublicacion, relevancia);
		this.duracion = duracion;
	}
	
	
	//METODOS 
	
	@Override
	public List<String> asCsvRow() {
		List<String> lista = new ArrayList<String>();
		lista.add(this.id+"");
		lista.add("\""+this.titulo.toString()+"\"");
		lista.add(this.costo.toString());
		lista.add(this.duracion.toString());
		return lista;
	}
	

	@Override
	public void loadFromStringRow(List<String> datos) {
		this.id =Integer.valueOf(datos.get(0));
		this.titulo = datos.get(1);
		this.costo =Double.valueOf(datos.get(2));
		this.duracion =Integer.valueOf(datos.get(3));
	}

	public Boolean esLibro() {
		return false;
	}
	
	public Boolean esVideo() {
		return true;
	}
	
	public Double precio() {
		return (super.costo + (costo * duracion));
	}
	
	public boolean equals(Object m) {
		if(m instanceof Video && super.equals(m)) {
			return true;
		}
		return false;
	}
	
	
}
