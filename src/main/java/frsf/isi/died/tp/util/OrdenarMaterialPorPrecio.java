package frsf.isi.died.tp.util;

import java.util.Comparator;

import frsf.isi.died.tp.modelo.productos.*;

public class OrdenarMaterialPorPrecio implements Comparator<MaterialCapacitacion>{
	
	@Override
	public int compare(MaterialCapacitacion m1, MaterialCapacitacion m2) {
        int aux = m1.precio().compareTo(m2.precio());
        if(aux == 0) {
        	return m1.getId().compareTo(m2.getId());
        }else {
        	return aux;
        }
    }
	
}
