package frsf.isi.died.app.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import frsf.isi.died.app.dao.MaterialCapacitacionDao;
import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.app.excepciones.DataOutOfBoundException;
import frsf.isi.died.app.excepciones.MaterialNotFoundException;
import frsf.isi.died.app.vista.material.BuscarMaterialPanel;
import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;
import frsf.isi.died.tp.modelo.productos.Video;
import frsf.isi.died.tp.util.ManipularFecha;
import frsf.isi.died.tp.util.OrdenarMaterialPorCalificacion;
import frsf.isi.died.tp.util.OrdenarMaterialPorFecha;
import frsf.isi.died.tp.util.OrdenarMaterialPorPrecio;
import frsf.isi.died.tp.util.OrdenarMaterialPorRelevancia;

public class BuscarController {
	
	private BuscarMaterialPanel buscarMaterialPanel;
	private MaterialCapacitacionDao materialDAO;
	
	public BuscarController(BuscarMaterialPanel buscarMaterialPanel) {
		this.buscarMaterialPanel = buscarMaterialPanel;
		this.buscarMaterialPanel.setController(this);
		materialDAO = new MaterialCapacitacionDaoDefault();
	}

	public void crearPanel() {		
	//	this.buscarMaterialPanel.setListaLibros(materialDAO.listaLibros(),false);
		this.buscarMaterialPanel.construir();
	}
	
	public BuscarMaterialPanel getPanelBuscar() {
		return buscarMaterialPanel;
	}

	public void setPanelBuscar(BuscarMaterialPanel buscarMaterialPanel) {
		this.buscarMaterialPanel = buscarMaterialPanel;
	}
	
	
	public void verificarTitulo(String titulo) throws DataOutOfBoundException {
		if(titulo.isEmpty()) {
			throw new DataOutOfBoundException("No ingreso ningun titulo");
		}
	}

	
	public List<MaterialCapacitacion> buscarMateriales(String titulo, Integer calificacion, String fechaI, String fechaF, String tipoMaterial){
		List<MaterialCapacitacion> listaFinal = new ArrayList<MaterialCapacitacion>();
		List<MaterialCapacitacion> listaMateriales = new ArrayList<MaterialCapacitacion>();
		if(tipoMaterial.equals("LIBRO")) {
			listaMateriales.addAll(materialDAO.listaLibros());
		}else {
			listaMateriales.addAll(materialDAO.listaVideos());
		}
		// Me ingresan Titulo
		if((!titulo.isEmpty()) && (calificacion == -1) && (fechaI.isEmpty()) && (fechaF.isEmpty())) {
			for(MaterialCapacitacion m : listaMateriales) {
				if(m.getTitulo().equals(titulo)) {
					listaFinal.add(m);
				}
			}
		}
		// Me ingresan Calificacion
		if((titulo.isEmpty()) && (calificacion != -1) && (fechaI.isEmpty()) && (fechaF.isEmpty())) {
			for(MaterialCapacitacion m : listaMateriales) {
				if(m.getCalificacion() == calificacion) {
					listaFinal.add(m);
				}
			}
		}
		// Me ingresan Fecha
		if((titulo.isEmpty()) && (calificacion == -1) && (!fechaI.isEmpty()) && (!fechaF.isEmpty())) {
			Date fInicio = ManipularFecha.getDateFromString(fechaI);
			Date fFin = ManipularFecha.getDateFromString(fechaF);
			Date fLibro;
			for(MaterialCapacitacion m : listaMateriales) {
				fLibro = ManipularFecha.getDateFromString(m.getFechaPublicacion());
				if(fLibro.after(fInicio) && fLibro.before(fFin)) {
					listaFinal.add(m);
				}
			}
		}
		// Me ingresan Titulo, Calificacion y Fecha
		if((!titulo.isEmpty()) && (calificacion != -1) && (!fechaI.isEmpty()) && (!fechaF.isEmpty())) {
			Date fInicio = ManipularFecha.getDateFromString(fechaI);
			Date fFin = ManipularFecha.getDateFromString(fechaF);
			Date fLibro;
			for(MaterialCapacitacion m : listaMateriales) {
				fLibro = ManipularFecha.getDateFromString(m.getFechaPublicacion());
				if((m.getTitulo().equals(titulo)) && (m.getCalificacion() == calificacion) && (fLibro.after(fInicio)) && (fLibro.before(fFin))) {
					listaFinal.add(m);
				}
			}
		}
		// Me ingresan Calificacion y Fecha
		if((titulo.isEmpty()) && (calificacion != -1) && (!fechaI.isEmpty()) && (!fechaF.isEmpty())) {
			Date fInicio = ManipularFecha.getDateFromString(fechaI);
			Date fFin = ManipularFecha.getDateFromString(fechaF);
			Date fLibro;
			for(MaterialCapacitacion m : listaMateriales) {
				fLibro = ManipularFecha.getDateFromString(m.getFechaPublicacion());
				if((m.getCalificacion() == calificacion) && (fLibro.after(fInicio)) && (fLibro.before(fFin))) {
					listaFinal.add(m);
				}
			}
		}
		// Me ingresan Titulo y Calificacion
		if((!titulo.isEmpty()) && (calificacion != -1) && (fechaI.isEmpty()) && (fechaF.isEmpty())) {
			for(MaterialCapacitacion m : listaMateriales) {
				if((m.getTitulo().equals(titulo)) && (m.getCalificacion() == calificacion)) {
					listaFinal.add(m);
				}
			}
		}
		// Me ingresan Titulo y Fecha
		if((!titulo.isEmpty()) && (calificacion == -1) && (!fechaI.isEmpty()) && (!fechaF.isEmpty())) {
			Date fInicio = ManipularFecha.getDateFromString(fechaI);
			Date fFin = ManipularFecha.getDateFromString(fechaF);
			Date fLibro;
			for(MaterialCapacitacion m : listaMateriales) {
				fLibro = ManipularFecha.getDateFromString(m.getFechaPublicacion());
				if((m.getTitulo().equals(titulo)) && (fLibro.after(fInicio) && fLibro.before(fFin))) {
					listaFinal.add(m);
				}
			}
		}
		
		return listaFinal;
	}
	
	public Set<MaterialCapacitacion> ordenarMateriales(List<MaterialCapacitacion> listaMaterialesMostrar, TiposDeOrden ordenSeleccionado){
		Set<MaterialCapacitacion> listaOrdenada = null;
		if(ordenSeleccionado.equals(TiposDeOrden.TITULO)) {
			listaOrdenada = new TreeSet<MaterialCapacitacion>();
			listaOrdenada.addAll(listaMaterialesMostrar);
		}else if(ordenSeleccionado.equals(TiposDeOrden.CALIFICACION)) {
			listaOrdenada = new TreeSet<MaterialCapacitacion>(new OrdenarMaterialPorCalificacion());
			listaOrdenada.addAll(listaMaterialesMostrar);
		}else if(ordenSeleccionado.equals(TiposDeOrden.PRECIO)) {
			listaOrdenada = new TreeSet<MaterialCapacitacion>(new OrdenarMaterialPorPrecio());
			listaOrdenada.addAll(listaMaterialesMostrar);
		}else if(ordenSeleccionado.equals(TiposDeOrden.FECHA)) {
			listaOrdenada = new TreeSet<MaterialCapacitacion>(new OrdenarMaterialPorFecha());
			listaOrdenada.addAll(listaMaterialesMostrar);
		}else if(ordenSeleccionado.equals(TiposDeOrden.RELEVANCIA)) {
			listaOrdenada = new TreeSet<MaterialCapacitacion>(new OrdenarMaterialPorRelevancia());
			listaOrdenada.addAll(listaMaterialesMostrar);
		}
		return listaOrdenada;
	}
	
	public void cargarTablaLibros(Set<MaterialCapacitacion> s, Boolean actualizarTabla) {
		this.buscarMaterialPanel.setListaLibros(s,actualizarTabla);
	}
	
	public void cargarTablaVideos(Set<MaterialCapacitacion> s, Boolean actualizarTabla) {
		this.buscarMaterialPanel.setListaVideos(s,actualizarTabla);
	}
	
}