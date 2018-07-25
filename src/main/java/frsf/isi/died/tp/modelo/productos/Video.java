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
	
	public Video(Integer id, String titulo, Double costo, Integer duracion, Integer calificacion, String fechaPublicacion) {
		super(id, titulo, costo, calificacion, fechaPublicacion);
		this.duracion = duracion;
	}
	
	
	//METODOS 
	
	@Override
	public List<String> asCsvRow() {
		List<String> lista = new ArrayList<String>();
		lista.add(this.id+"");
		lista.add("\""+this.titulo.toString()+"\"");
		lista.add(super.costo.toString());            // Modifique this por super
		lista.add(this.duracion.toString());
		lista.add(this.calificacion.toString());
		lista.add(this.fechaPublicacion.toString());
		return lista;
	}
	

	@Override
	public void loadFromStringRow(List<String> datos) {
		this.id = Integer.valueOf(datos.get(0));
		this.titulo = datos.get(1);
		super.costo = Double.valueOf(datos.get(2));    // Modifique this por super
		this.duracion = Integer.valueOf(datos.get(3));
		this.calificacion = Integer.valueOf(datos.get(4));  // Agregue esta linea
		this.fechaPublicacion = datos.get(5);    // Agregue esta linea
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

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public Integer getDuracion() {
		return duracion;
	}

	
	
}
