package frsf.isi.died.app.controller;

import java.io.IOException;
import java.util.ArrayList;

import frsf.isi.died.app.dao.MaterialCapacitacionDao;
import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.app.excepciones.MaterialNotFoundException;
import frsf.isi.died.app.vista.material.LPanel;
import frsf.isi.died.app.vista.material.LibroPanel;
import frsf.isi.died.app.vista.material.LibroPanelModificacion;
import frsf.isi.died.tp.modelo.productos.Libro;

public class LibroController {

	private LPanel panelLibro;
	private MaterialCapacitacionDao materialDAO;
	
	public LibroController(LPanel panel) {
		this.panelLibro = panel;
		this.panelLibro.setController(this);
		materialDAO = new MaterialCapacitacionDaoDefault();
	}

	public void agregarLibro(String titulo,Double costo,Double precio,Integer paginas) {	
		Libro l = new Libro(0,titulo, costo, precio, paginas) ;
		materialDAO .agregarLibro(l);
		this.panelLibro.setListaLibros(materialDAO.listaLibros(),true);
	}
	
	public void crearPanel() {		
		this.panelLibro.setListaLibros(materialDAO.listaLibros(),false);
		this.panelLibro.construir();
	}
	
	public LPanel getPanelLibro() {
		return panelLibro;
	}

	public void setPanelLibro(LibroPanel panelLibro) {
		this.panelLibro = panelLibro;
	}
	
	public ArrayList getFilaSeleccionada() {
		return (panelLibro.getFilaSeleccionada());
	}
	 
	public void cargarCampos(ArrayList lista) {
		 panelLibro.cargarCampos(lista);
	}
	 
	public void modificarLibro(Integer idLibroSeleccionado, String titulo, Double costo, Double precio, Integer paginas) throws MaterialNotFoundException, IOException {
		try {
			Libro l = (Libro) materialDAO.buscarMaterial(idLibroSeleccionado);
			l.setTitulo(titulo);
			l.setCosto(costo);
			l.setPrecioCompra(precio);
			l.setPaginas(paginas);
			// Para no modificar l
		/*	Libro aux = l;
			if(!(l.getTitulo().equals(titulo))) {
				aux.setTitulo(titulo);
			}
			if(!(l.getCosto().equals(costo))) {
				aux.setCosto(costo);
			}
			if(!(l.getPrecioCompra().equals(precio))) {
				aux.setPrecioCompra(precio);
			}
			if(!(l.getPaginas().equals(paginas))){
				aux.setPaginas(paginas);
			}
		*/
			materialDAO.modificarLibro(l);
			this.panelLibro.setListaLibros(materialDAO.listaLibros(),true);
		}
		catch(MaterialNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	 
}
