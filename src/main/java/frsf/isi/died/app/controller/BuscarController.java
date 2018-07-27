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
	
	public List<Libro> buscarLibros(String titulo, Integer calificacion, String fechaI, String fechaF){
		List<Libro> listaFinal = new ArrayList<Libro>();
		List<Libro> listaLibros = materialDAO.listaLibros();
		// Me ingresan Titulo
		if((!titulo.isEmpty()) && (calificacion == -1) && (fechaI.isEmpty()) && (fechaF.isEmpty())) {
			for(Libro l : listaLibros) {
				if(l.getTitulo().equals(titulo)) {
					listaFinal.add(l);
				}
			}
		}
		// Me ingresan Calificacion
		if((titulo.isEmpty()) && (calificacion != -1) && (fechaI.isEmpty()) && (fechaF.isEmpty())) {
			for(Libro l : listaLibros) {
				if(l.getCalificacion() == calificacion) {
					listaFinal.add(l);
				}
			}
		}
		// Me ingresan Fecha
		if((titulo.isEmpty()) && (calificacion == -1) && (!fechaI.isEmpty()) && (!fechaF.isEmpty())) {
			Date fInicio = ManipularFecha.getDateFromString(fechaI);
			Date fFin = ManipularFecha.getDateFromString(fechaF);
			Date fLibro;
			for(Libro l : listaLibros) {
				fLibro = ManipularFecha.getDateFromString(l.getFechaPublicacion());
				if(fLibro.after(fInicio) && fLibro.before(fFin)) {
					listaFinal.add(l);
				}
			}
		}
		// Me ingresan Titulo, Calificacion y Fecha
		if((!titulo.isEmpty()) && (calificacion != -1) && (!fechaI.isEmpty()) && (!fechaF.isEmpty())) {
			Date fInicio = ManipularFecha.getDateFromString(fechaI);
			Date fFin = ManipularFecha.getDateFromString(fechaF);
			Date fLibro;
			for(Libro l : listaLibros) {
				fLibro = ManipularFecha.getDateFromString(l.getFechaPublicacion());
				if((l.getTitulo().equals(titulo)) && (l.getCalificacion() == calificacion) && (fLibro.after(fInicio)) && (fLibro.before(fFin))) {
					listaFinal.add(l);
				}
			}
		}
		// Me ingresan Calificacion y Fecha
		if((titulo.isEmpty()) && (calificacion != -1) && (!fechaI.isEmpty()) && (!fechaF.isEmpty())) {
			Date fInicio = ManipularFecha.getDateFromString(fechaI);
			Date fFin = ManipularFecha.getDateFromString(fechaF);
			Date fLibro;
			for(Libro l : listaLibros) {
				fLibro = ManipularFecha.getDateFromString(l.getFechaPublicacion());
				if((l.getCalificacion() == calificacion) && (fLibro.after(fInicio)) && (fLibro.before(fFin))) {
					listaFinal.add(l);
				}
			}
		}
		// Me ingresan Titulo y Calificacion
		if((!titulo.isEmpty()) && (calificacion != -1) && (fechaI.isEmpty()) && (fechaF.isEmpty())) {
			for(Libro l : listaLibros) {
				if((l.getTitulo().equals(titulo)) && (l.getCalificacion() == calificacion)) {
					listaFinal.add(l);
				}
			}
		}
		// Me ingresan Titulo y Fecha
		if((!titulo.isEmpty()) && (calificacion == -1) && (!fechaI.isEmpty()) && (!fechaF.isEmpty())) {
			Date fInicio = ManipularFecha.getDateFromString(fechaI);
			Date fFin = ManipularFecha.getDateFromString(fechaF);
			Date fLibro;
			for(Libro l : listaLibros) {
				fLibro = ManipularFecha.getDateFromString(l.getFechaPublicacion());
				if((l.getTitulo().equals(titulo)) && (fLibro.after(fInicio) && fLibro.before(fFin))) {
					listaFinal.add(l);
				}
			}
		}
		
		return listaFinal;
	}
	
	
	public Set<Libro> ordenarLibros(List<Libro> listaLibrosMostrar, TiposDeOrden ordenSeleccionado){
		Set<Libro> listaOrdenada = null;
		if(ordenSeleccionado.equals(TiposDeOrden.TITULO)) {
			listaOrdenada = new TreeSet<Libro>();
			listaOrdenada.addAll(listaLibrosMostrar);
		}else if(ordenSeleccionado.equals(TiposDeOrden.CALIFICACION)) {
			listaOrdenada = new TreeSet<Libro>(new OrdenarMaterialPorCalificacion());
			listaOrdenada.addAll(listaLibrosMostrar);
		}else if(ordenSeleccionado.equals(TiposDeOrden.PRECIO)) {
			listaOrdenada = new TreeSet<Libro>(new OrdenarMaterialPorPrecio());
			listaOrdenada.addAll(listaLibrosMostrar);
		}else if(ordenSeleccionado.equals(TiposDeOrden.FECHA)) {
			listaOrdenada = new TreeSet<Libro>(new OrdenarMaterialPorFecha());
			listaOrdenada.addAll(listaLibrosMostrar);
		}else if(ordenSeleccionado.equals(TiposDeOrden.RELEVANCIA)) {
			listaOrdenada = new TreeSet<Libro>(new OrdenarMaterialPorRelevancia());
			listaOrdenada.addAll(listaLibrosMostrar);
		}
		return listaOrdenada;
	}
	
	public void cargarTablaLibros(Set<Libro> s) {
		this.buscarMaterialPanel.setListaLibros(s,false);
	}
	
}
