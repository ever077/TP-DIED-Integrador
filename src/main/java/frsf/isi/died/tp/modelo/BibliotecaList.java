package frsf.isi.died.tp.modelo;

import java.util.ArrayList;

import java.util.Collection;
import java.util.Collections;

import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;
import frsf.isi.died.tp.modelo.productos.Video;
import java.lang.RuntimeException;

public class BibliotecaList implements Biblioteca{
	
	private ArrayList<MaterialCapacitacion> materiales;
	
	
	public BibliotecaList() {
		this.materiales = new ArrayList<>();
	}
	
	@Override
	public void agregar(MaterialCapacitacion material) {
		// TODO Auto-generated method stub
		materiales.add(material);
	}
	
	@Override
	public Integer cantidadMateriales() {
		// TODO Auto-generated method stub
		return materiales.size();
	}
	
	@Override
	public Integer cantidadLibros() {
		// TODO Auto-generated method stub
		int cont=0;
		for(int i=0; i<materiales.size(); i++)
		{
			if(materiales.get(i) instanceof Libro) {
				cont++;
			}
		}
		return cont;
	}
	
	@Override
	public Integer cantidadVideos() {
		// TODO Auto-generated method stub
		int cont=0;
		for(int i=0; i<materiales.size(); i++)
		{
			if(materiales.get(i) instanceof Video) {
				cont++;
			}
		}
		return cont;
	}
	
	@Override
	public Collection<MaterialCapacitacion> materiales() {
		// TODO Auto-generated method stub
		return this.materiales;
	}
	
	@Override
	public void imprimir() {
		// TODO Auto-generated method stub
		for(int i=0; i<materiales.size(); i++)
		{
			System.out.println(materiales.get(i));
		}
	}
	
	@Override
	public void ordenarPorPrecio(Boolean b) {
		// TODO Auto-generated method stub
		
		if(b == false) {
			Collections.sort(materiales);
		}else {
			Collections.sort(materiales, ( m1 , m2 ) ->  m1.precio().compareTo( m2.precio()));
		}
	}
	
	@Override
	public MaterialCapacitacion buscar(Integer precio) {
		// TODO Auto-generated method stub
		Collections.sort(materiales, ( m1 , m2 ) ->  m1.precio().compareTo( m2.precio()));
		
		return buscadorBinario(0, materiales.size()-1,precio);
	}
	
	private MaterialCapacitacion buscadorBinario(int i, int f,  Integer costo) throws RuntimeException {
		// TODO Auto-generated method stub
		int medio=(i+f)/2;
		if((materiales.get(medio).getCosto().intValue()) == costo)
		{
			return materiales.get(medio);
		}
		else if(i == f && (materiales.get(medio).getCosto().intValue()) != costo)
		{
			throw new RuntimeException("Material de precio "+costo+" no encontrado");
		} 
		else if((materiales.get(medio).getCosto().intValue()) < costo) {		
			return buscadorBinario(medio+1, f, costo);
		}
		else {
			return buscadorBinario(i, medio, costo);
		}
	}
	
	
/*	private void RuntimeException(String string) {
		// TODO Auto-generated method stub
		
	}*/

}
