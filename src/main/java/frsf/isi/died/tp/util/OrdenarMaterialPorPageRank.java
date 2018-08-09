package frsf.isi.died.tp.util;

import java.util.Comparator;

import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

public class OrdenarMaterialPorPageRank implements Comparator<MaterialCapacitacion> {

	@Override
	public int compare(MaterialCapacitacion m1, MaterialCapacitacion m2) {
        
		int aux =  Double.compare(m2.getPageRank() , m1.getPageRank());
        if(aux == 0) {
        	return m1.getId().compareTo(m2.getId());
        }else {
        	return aux;
        }
    }
}
