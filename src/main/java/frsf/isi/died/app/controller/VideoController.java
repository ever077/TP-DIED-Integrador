package frsf.isi.died.app.controller;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import frsf.isi.died.app.dao.MaterialCapacitacionDao;
import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.app.excepciones.DataOutOfBoundException;
import frsf.isi.died.app.excepciones.MaterialNotFoundException;
import frsf.isi.died.app.vista.material.VPanel;
import frsf.isi.died.app.vista.material.VideoPanel;
import frsf.isi.died.tp.modelo.productos.Video;
import frsf.isi.died.tp.util.ManipularFecha;

public class VideoController {

	private VPanel panelVideo;
	private MaterialCapacitacionDao materialDAO;
	
	public VideoController(VPanel panel) {
		this.panelVideo = panel;
		this.panelVideo.setController(this);
		materialDAO = new MaterialCapacitacionDaoDefault();
	}

	public void agregarVideo(String titulo,Double costo,Integer duracion, Integer calificacion, String relevancia) {	
		Video v = new Video(0,titulo, costo, duracion, calificacion, ManipularFecha.getFechaActual(), relevancia);
		materialDAO .agregarVideo(v);
		this.panelVideo.setListaVideos(materialDAO.listaVideos(),true);
	}
	
	public void crearPanel() {		
		this.panelVideo.setListaVideos(materialDAO.listaVideos(),false);
		this.panelVideo.construir();
	}
	
	public VPanel getPanelVideo() {
		return panelVideo;
	}

	public void setPanelVideo(VideoPanel panelVideo) {
		this.panelVideo = panelVideo;
	}
	
	public ArrayList getFilaSeleccionada() {
		return (panelVideo.getFilaSeleccionada());
	}
	 
	public void cargarCampos(ArrayList lista) {
		 panelVideo.cargarCampos(lista);
	}
	 
	public void modificarVideo(Integer idVideoSeleccionado, String titulo, Double costo, Integer duracion, Integer calificacion, String relevancia) throws MaterialNotFoundException, IOException {
		try {
			Video v = (Video) materialDAO.buscarMaterial(idVideoSeleccionado);
			v.setTitulo(titulo);
			v.setCosto(costo);
			v.setDuracion(duracion);
			v.setCalificacion(calificacion);
			v.setRelevancia(relevancia);

			materialDAO.modificarVideo(v);
			materialDAO = new MaterialCapacitacionDaoDefault(); 
			this.panelVideo.setListaVideos(materialDAO.listaVideos(),true);
		}
		catch(MaterialNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void eliminarVideo(Integer id) throws MaterialNotFoundException {
		try {
			materialDAO.eliminarVideo(id);
			materialDAO = new MaterialCapacitacionDaoDefault(); 
			this.panelVideo.setListaVideos(materialDAO.listaVideos(),true);
		}catch(MaterialNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void verificarCalificacion(Integer calificacion) throws DataOutOfBoundException {
		if( ! (calificacion >= 0 && calificacion <= 100) ) {
			throw new DataOutOfBoundException();
		}
	}
	
	public void verificarTitulo(String titulo) throws DataOutOfBoundException {
		if(titulo.isEmpty()) {
			throw new DataOutOfBoundException("No ingreso ningun titulo");
		}
	}
	
}