package frsf.isi.died.app.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import frsf.isi.died.app.dao.MaterialCapacitacionDao;
import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.app.excepciones.DataOutOfBoundException;
import frsf.isi.died.app.excepciones.MaterialNotFoundException;
import frsf.isi.died.app.vista.arbol.ArbolPanel;
import frsf.isi.died.app.vista.grafo.ControlPanel;
import frsf.isi.died.app.vista.grafo.GrafoPanel;
import frsf.isi.died.app.vista.material.BuscarMaterialPanel;
import frsf.isi.died.app.vista.wishlist.WishListPanel;
import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;
import frsf.isi.died.tp.modelo.productos.Video;
import frsf.isi.died.tp.util.ManipularFecha;
import frsf.isi.died.tp.util.ManipularListas;
import frsf.isi.died.tp.util.OrdenarMaterialPorCalificacion;
import frsf.isi.died.tp.util.OrdenarMaterialPorFecha;
import frsf.isi.died.tp.util.OrdenarMaterialPorPrecio;
import frsf.isi.died.tp.util.OrdenarMaterialPorRelevancia;

public class BuscarController {
	
	private BuscarMaterialPanel buscarMaterialPanel;
	private MaterialCapacitacionDao materialDAO;
	private WishListController wishListController;
	private GrafoController grafoController;
	private ArbolController arbolController;
	
	public BuscarController(BuscarMaterialPanel buscarMaterialPanel) {
		this.buscarMaterialPanel = buscarMaterialPanel;
		this.buscarMaterialPanel.setController(this);
		this.wishListController = new WishListController(new WishListPanel());
		this.grafoController = new GrafoController(new GrafoPanel(), new ControlPanel());
		this.arbolController = new ArbolController(new ArbolPanel());
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

	
	public List<MaterialCapacitacion> buscarMateriales(String titulo, Integer calificacion, String fechaI, String fechaF, String tema, String flag){
		List<MaterialCapacitacion> listaFinal = new ArrayList<MaterialCapacitacion>();
		List<MaterialCapacitacion> listaMateriales = new ArrayList<MaterialCapacitacion>();
		if(flag.equals("LIBRO")) {
			listaMateriales.addAll(materialDAO.listaLibros());
		}else if(flag.equals("VIDEO")) {
			listaMateriales.addAll(materialDAO.listaVideos());
		}else {
			listaMateriales.addAll(materialDAO.listaMateriales());
		}
		
		
		if(!titulo.isEmpty()) {
			for(MaterialCapacitacion m : listaMateriales) {
				if(!(m.getTitulo().equals(titulo))) {
					//listaFinal.add(m);
					//listaMateriales.remove(m);
					listaMateriales = ManipularListas.eliminarElemento(listaMateriales, m);
				}
			}
		}
		if(calificacion != -1) {
			for(MaterialCapacitacion m : listaMateriales) {
				if(m.getCalificacion() != calificacion) {
					////listaFinal.add(m);
					listaMateriales = ManipularListas.eliminarElemento(listaMateriales, m);
				}
			}	
		}
		if( !(fechaI.isEmpty()) && !(fechaF.isEmpty()) ) {
			Date fInicio = ManipularFecha.getDateFromString(fechaI);
			Date fFin = ManipularFecha.getDateFromString(fechaF);
			Date fLibro;
			for(MaterialCapacitacion m : listaMateriales) {
				fLibro = ManipularFecha.getDateFromString(m.getFechaPublicacion());
				if( !(fLibro.after(fInicio) && fLibro.before(fFin)) ) {
					//listaFinal.add(m);
					//listaMateriales.remove(m);
					listaMateriales = ManipularListas.eliminarElemento(listaMateriales, m);
				}
			}
		}
		if(!tema.isEmpty()) {
			for(MaterialCapacitacion m : listaMateriales) {
				if(!(m.getTema().equals(tema))) {
					//listaFinal.add(m);
					//listaMateriales.remove(m);
					listaMateriales = ManipularListas.eliminarElemento(listaMateriales, m);
				}
			}
		}
		
		

		
		//return listaFinal;
		return listaMateriales;
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
	
	public void cargarTablaMateriales(Set<MaterialCapacitacion> s, Boolean actualizarTabla) {
		this.buscarMaterialPanel.setListaMateriales(s,actualizarTabla);
	}
	
	public void addMaterialWishList(Integer id) {
		wishListController.addMaterial(id);
	}
	
	public List<MaterialCapacitacion> getMaterialesWithTema(Integer id){
		return this.buscarMateriales("", -1, "", "", materialDAO.findById(id).getTema(), "");
	}
	
	public void showRelaciones(Integer id, List<MaterialCapacitacion> materiales) {
		grafoController.eliminarArchivo("aristas.csv");
		grafoController.showRelaciones(id, materiales, (JFrame) SwingUtilities.getWindowAncestor(buscarMaterialPanel));
	}

	public void mostrarArbolContenido(Integer idFilaSeleccionada) {
		
		arbolController.mostraArbol(idFilaSeleccionada, (JFrame) SwingUtilities.getWindowAncestor(buscarMaterialPanel));
		
	}
	
	
}
