package frsf.isi.died.tp.util;

import java.util.Comparator;

import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;
import frsf.isi.died.tp.modelo.productos.Relevancia;

public class OrdenarMaterialWishList implements Comparator<MaterialCapacitacion> {

	@Override
	public int compare(MaterialCapacitacion m1, MaterialCapacitacion m2) {
        
		// La Relevancia de menor valor es la mas alta
		// La Calificacion de mayor valor es la mas alta
		// La Precio de menor valor es el mas alto
		
        int aux = ((Relevancia.valueOf(m1.getRelevancia())).getPrioridad()) - ((Relevancia.valueOf(m2.getRelevancia())).getPrioridad());
        if(aux == 0) {
        	aux = m2.getCalificacion().compareTo(m1.getCalificacion());
        	if(aux == 0) {
        		return (m1.precio().compareTo(m2.precio()));
             /*   if(aux == 0) {
                	return m1.getId().compareTo(m2.getId());
                }else {
                	return aux;
                }*/
        	}else {
        		return aux;
        	}
        }else {
        	return aux;
        }
        
    }
	
}
