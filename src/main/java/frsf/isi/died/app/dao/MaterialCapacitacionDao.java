package frsf.isi.died.app.dao;

import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;

import frsf.isi.died.app.excepciones.MaterialNotFoundException;
import frsf.isi.died.tp.estructuras.Arista;
import frsf.isi.died.tp.estructuras.Vertice;
import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;
import frsf.isi.died.tp.modelo.productos.Video;

public interface MaterialCapacitacionDao {

	public void agregarLibro(Libro mat);
	public void agregarVideo(Video mat);
	public List<Libro> listaLibros();
	public List<Video> listaVideos();
	public List<MaterialCapacitacion> listaMateriales();
	public MaterialCapacitacion findById(Integer id);
	public void crearCamino(Integer idOrigen, Integer idDestino);
	public List<MaterialCapacitacion> buscarCamino(Integer idOrigen, Integer idDestino, Integer saltos);
	public MaterialCapacitacion buscarMaterial(Integer idMaterialCapacitacion) throws MaterialNotFoundException;
	public void modificarLibro(Libro l) throws IOException;
	public void eliminarLibro(Integer id) throws MaterialNotFoundException;
	public void modificarVideo(Video v) throws IOException;
	public void eliminarVideo(Integer id) throws MaterialNotFoundException;
	public void agregarMaterialWishList(MaterialCapacitacion material);
	public PriorityQueue<MaterialCapacitacion> listaWishList();
	public List<List<MaterialCapacitacion>> buscarCaminos(Integer idOrigen, Integer idDestino);
	public List<List<MaterialCapacitacion>> buscarCaminosHastaNSaltos(Integer nodo1, Integer nodo2, Integer n);
	public void eliminarArchivo(String nombreArchivo) throws IOException;
	public void deleteAristas();
}
