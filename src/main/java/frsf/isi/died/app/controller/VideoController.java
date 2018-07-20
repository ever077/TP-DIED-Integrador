package frsf.isi.died.app.controller;


import java.io.IOException;
import java.util.ArrayList;

import frsf.isi.died.app.dao.MaterialCapacitacionDao;
import frsf.isi.died.app.dao.MaterialCapacitacionDaoDefault;
import frsf.isi.died.app.excepciones.MaterialNotFoundException;
import frsf.isi.died.app.vista.material.VPanel;
import frsf.isi.died.tp.modelo.productos.Video;

public class VideoController {

	private VPanel panelVideo;
	private MaterialCapacitacionDao materialDAO;
	
	public VideoController(VPanel panel) {
		this.panelVideo = panel;
		this.panelVideo.setController(this);
		materialDAO = new MaterialCapacitacionDaoDefault();
	}

	public void agregarVideo(String titulo,Double costo,Integer duracion) {	
		Video v = new Video(0,titulo, costo, duracion);
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
	 
	public void modificarVideo(Integer idVideoSeleccionado, String titulo, Double costo, Integer duracion) throws MaterialNotFoundException, IOException {
		try {
			Video v = (Video) materialDAO.buscarMaterial(idVideoSeleccionado);
			v.setTitulo(titulo);
			v.setCosto(costo);
			v.setDuracion(duracion);
			;

			materialDAO.modificarVideo(v);
			this.panelVideo.setListaVideos(materialDAO.listaVideos(),true);
		}
		catch(MaterialNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void eliminarVideo(Integer id) throws MaterialNotFoundException {
		try {
			materialDAO.eliminarVideo(id);
			this.panelVideos.setListaVideos(materialDAO.listaVideos(),true);
		}catch(MaterialNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	 