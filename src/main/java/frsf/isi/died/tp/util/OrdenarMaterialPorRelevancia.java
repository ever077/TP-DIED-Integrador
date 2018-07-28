package frsf.isi.died.tp.util;

import java.util.Comparator;

import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;
import frsf.isi.died.tp.modelo.productos.Relevancia;
import frsf.isi.died.tp.modelo.productos.Video;

public class OrdenarMaterialPorRelevancia implements Comparator<MaterialCapacitacion>{
	
	@Override
	public int compare(MaterialCapacitacion m1, MaterialCapacitacion m2) {
        int aux = ((Relevancia.valueOf(m1.getRelevancia())).getPrioridad()) - ((Relevancia.valueOf(m2.getRelevancia())).getPrioridad());
		if(aux == 0) {
        	return m1.getId().compareTo(m2.getId());
        }else {
        	return aux;
        }
	}
	
}
