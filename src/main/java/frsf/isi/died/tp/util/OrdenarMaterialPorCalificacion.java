package frsf.isi.died.tp.util;

import java.util.Comparator;

import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;
import frsf.isi.died.tp.modelo.productos.Video;

public class OrdenarMaterialPorCalificacion implements Comparator<MaterialCapacitacion>{
	
	@Override
	public int compare(MaterialCapacitacion m1, MaterialCapacitacion m2) {
        
        int aux = m2.getCalificacion().compareTo(m1.getCalificacion());
        if(aux == 0) {
        	return m1.getId().compareTo(m2.getId());
        }else {
        	return aux;
        }
        
    }
	
}
