package frsf.isi.died.tp.util;

import java.util.Comparator;
import java.util.Date;

import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

public class OrdenarMaterialPorFecha implements Comparator<MaterialCapacitacion>{
	
	@Override
	public int compare(MaterialCapacitacion m1, MaterialCapacitacion m2) {
        Date d1 = ManipularFecha.getDateFromString(m1.getFechaPublicacion());
        Date d2 = ManipularFecha.getDateFromString(m2.getFechaPublicacion());
        int aux = d1.compareTo(d2);
        if(aux == 0) {
        	return m1.getId().compareTo(m2.getId());
        }else {
        	return aux;
        }
    }
	
}
