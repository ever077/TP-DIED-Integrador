package frsf.isi.died.tp.util;

import java.util.ArrayList;
import java.util.List;

import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

public class ManipularListas {

	public static List<MaterialCapacitacion> eliminarElemento(List<MaterialCapacitacion> listaElim, MaterialCapacitacion obj){
		List<MaterialCapacitacion> aux = new ArrayList<MaterialCapacitacion>();
		for(MaterialCapacitacion a: listaElim){
		    if(!a.equals(obj)){
		        aux.add(a);
		    }
		}
		return aux;
	}
	
}
