package frsf.isi.died.app.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.JOptionPane;

import frsf.isi.died.app.dao.util.CsvDatasource;
import frsf.isi.died.app.dao.util.CsvRecord;
import frsf.isi.died.app.excepciones.MaterialNotFoundException;
import frsf.isi.died.tp.estructuras.Arista;
import frsf.isi.died.tp.estructuras.Grafo;
import frsf.isi.died.tp.estructuras.Vertice;
import frsf.isi.died.tp.modelo.Biblioteca;
import frsf.isi.died.tp.modelo.BibliotecaABB;
import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;
import frsf.isi.died.tp.modelo.productos.Video;
import frsf.isi.died.tp.util.OrdenarMaterialWishList;

public class MaterialCapacitacionDaoDefault implements MaterialCapacitacionDao{

	private Grafo<MaterialCapacitacion> GRAFO_MATERIAL  = new Grafo<MaterialCapacitacion>();
	private PriorityQueue<MaterialCapacitacion> wishListLibros = new PriorityQueue<MaterialCapacitacion>(new OrdenarMaterialWishList());
	private PriorityQueue<MaterialCapacitacion> wishListVideos = new PriorityQueue<MaterialCapacitacion>(new OrdenarMaterialWishList());
	private static Integer SECUENCIA_ID;
	private static Integer SECUENCIA_ID_WISHLIST = 0;
	private static Biblioteca biblioteca = new BibliotecaABB();
	
	private CsvDatasource dataSource;
	
	public MaterialCapacitacionDaoDefault() {
		dataSource = new CsvDatasource();
		if(GRAFO_MATERIAL.esVacio()) {
			cargarGrafo();
			cargarColaPrioridad();
		}
		this.cargarIdSecuencia();
	}

	private void cargarGrafo() {
		List<List<String>> libros = dataSource.readFile("libros.csv");
		for(List<String> filaLibro : libros) {
			Libro aux = new Libro();
			aux.loadFromStringRow(filaLibro);
			GRAFO_MATERIAL.addNodo(aux);
		}
		List<List<String>> videos= dataSource.readFile("videos.csv");
		for(List<String> filaVideo: videos) {
			Video aux = new Video();
			aux.loadFromStringRow(filaVideo);
			GRAFO_MATERIAL.addNodo(aux);
		}
		List<List<String>> aristas= dataSource.readFile("aristas.csv");
		for(List<String> filaArista: aristas) {
			MaterialCapacitacion n1 = this.findById(Integer.valueOf(filaArista.get(0)));
			MaterialCapacitacion n2 = this.findById(Integer.valueOf(filaArista.get(2)));
			GRAFO_MATERIAL.conectar(n1, n2);
		}
 	}
	
	private void cargarColaPrioridad() {
		List<List<String>> librosWishList = dataSource.readFile("wishListLibro.csv");
		for(List<String> libro : librosWishList) {
			Libro aux = new Libro();
			aux.loadFromStringRow(libro);
			wishListLibros.add(aux);
		}
		List<List<String>> videosWishList = dataSource.readFile("wishListVideo.csv");
		for(List<String> video : videosWishList) {
			Video aux = new Video();
			aux.loadFromStringRow(video);
			wishListVideos.add(aux);
		}
	}
	
	@Override
	public void agregarLibro(Libro mat) {
		mat.setId(++SECUENCIA_ID);
		GRAFO_MATERIAL.addNodo(mat);	
		biblioteca.agregar(mat);
		try {
			dataSource.agregarFilaAlFinal("libros.csv", mat);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void agregarVideo(Video mat) {
		mat.setId(++SECUENCIA_ID);
		GRAFO_MATERIAL.addNodo(mat);				
		biblioteca.agregar(mat);
		try {
			dataSource.agregarFilaAlFinal("videos.csv", mat);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Libro> listaLibros() {
		List<Libro> libros = new ArrayList<>();
		for(MaterialCapacitacion mat : GRAFO_MATERIAL.listaVertices()) {
			if(mat.esLibro()) libros.add((Libro)mat); 
		}
		return libros;
	}

	@Override
	public List<Video> listaVideos() {
		List<Video> vids = new ArrayList<>();
		for(MaterialCapacitacion mat : GRAFO_MATERIAL.listaVertices()) {
			if(mat.esVideo()) vids.add((Video)mat); 
		}
		return vids;
	}

	@Override
	public List<MaterialCapacitacion> listaMateriales() {
		// TODO Auto-generated method stub
		return GRAFO_MATERIAL.listaVertices();
	}

	@Override
	public MaterialCapacitacion findById(Integer id) {
		// TODO Auto-generated method stub
		for(MaterialCapacitacion mat : GRAFO_MATERIAL.listaVertices()) {
			if(mat.getId().equals(id)) return mat;
		}
		return null;
	}

	@Override
	public List<MaterialCapacitacion> buscarCamino(Integer idOrigen, Integer idDestino, Integer saltos) {
		MaterialCapacitacion n1 = this.findById(idOrigen);
		MaterialCapacitacion n2 = this.findById(idDestino);
		return GRAFO_MATERIAL.buscarCaminoNSaltos(n1, n2, saltos);
	}
	
	@Override
	public List<List<MaterialCapacitacion>> buscarCaminos(Integer idOrigen, Integer idDestino) {
		MaterialCapacitacion n1 = this.findById(idOrigen);
		MaterialCapacitacion n2 = this.findById(idDestino);
		return GRAFO_MATERIAL.buscarCaminos(n1, n2);
	}

	@Override
	public void crearCamino(Integer idOrigen, Integer idDestino) {
		MaterialCapacitacion n1 = this.findById(idOrigen);
		MaterialCapacitacion n2 = this.findById(idDestino);
		GRAFO_MATERIAL.conectar(n1, n2);
		List<String> fila = new ArrayList<>();
		fila.add(n1.getId()+"");
		fila.add(n1.getTitulo());
		fila.add(n2.getId()+"");
		fila.add(n2.getTitulo());
		try {
			dataSource.agregarFilaAlFinal("aristas.csv", fila);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public MaterialCapacitacion buscarMaterial(Integer idMaterialCapacitacion) throws MaterialNotFoundException {
		List<MaterialCapacitacion> lista = this.listaMateriales();
		for(MaterialCapacitacion l : lista) {
			if(l.getId() == idMaterialCapacitacion) {
				return l;
			}
		}
		throw new MaterialNotFoundException();
	}
	
	public void modificarLibro(Libro l) throws IOException {
		List<List<String>> listaArchivo = dataSource.readFile("libros.csv");
		FileWriter fichero = null;
	    PrintWriter escritor = null;
		try {
			fichero = new FileWriter("libros.csv");
			escritor = new PrintWriter(fichero);
			escritor.flush();
			for(List<String> fila : listaArchivo) {
				if(fila.get(0).equals(l.getId().toString())) {
					// Este es el libro a modificar
					fila.set(1, l.getTitulo());
					fila.set(2, l.getCosto().toString());
					fila.set(3, l.getPaginas().toString());
					fila.set(4, l.getPrecioCompra().toString());
					fila.set(5, l.getCalificacion().toString());
					// La posicion 6 es la fecha de publicacion
					fila.set(7, l.getRelevancia().toString());
					fila.set(8, l.getTema().toString());
				}
				// sobreescribo la linea
					dataSource.writeLine(escritor, fila);
			}
			escritor.flush();
			escritor.close();
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, "Error al escribir en el archivo de texto: "+e.getMessage());
		}
		finally {
	        if(fichero != null){
	            try {
	                fichero.close();
	            } catch (IOException e) {
	                JOptionPane.showMessageDialog(null, "Error al cerrar archivo de texto: "+e.getMessage());
	            }
	        }
	    }
	}
	
	public void eliminarLibro(Integer id) throws MaterialNotFoundException {
		List<List<String>> listaArchivo = dataSource.readFile("libros.csv");
		FileWriter fichero = null;
	    PrintWriter escritor = null;
		try {
			fichero = new FileWriter("libros.csv");
			escritor = new PrintWriter(fichero);
			escritor.flush();
			for(List<String> fila : listaArchivo) {
				if(!(fila.get(0).equals(id.toString()))) {
					// Escribimos la linea en el nuevo archivo
					dataSource.writeLine(escritor, fila);
				}
			}
			escritor.flush();
			escritor.close();
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, "Error al escribir en el archivo de texto: "+e.getMessage());
		}
		finally {
	        if(fichero != null){
	            try {
	                fichero.close();
	            } catch (IOException e) {
	                JOptionPane.showMessageDialog(null, "Error al cerrar archivo de texto: "+e.getMessage());
	            }
	        }
	    }
		
	}
	
	public void modificarVideo(Video v) throws IOException {
		List<List<String>> listaArchivo = dataSource.readFile("videos.csv");
		FileWriter fichero = null;
	    PrintWriter escritor = null;
		try {
			fichero = new FileWriter("videos.csv");
			escritor = new PrintWriter(fichero);
			escritor.flush();
			for(List<String> fila : listaArchivo) {
				if(fila.get(0).equals(v.getId().toString())) {
					// Este es el video a modificar
					fila.set(1, v.getTitulo());
					fila.set(2, v.getCosto().toString());
					fila.set(3, v.getDuracion().toString());
					fila.set(4, v.getCalificacion().toString());
					// La posicion 5 es la fecha de publicacion
					fila.set(6, v.getRelevancia().toString());
					fila.set(7, v.getTema().toString());
				}
				// sobreescribo la linea
					dataSource.writeLine(escritor, fila);
			}
			escritor.flush();
			escritor.close();
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, "Error al escribir en el archivo de texto: "+e.getMessage());
		}
		finally {
	        if(fichero != null){
	            try {
	                fichero.close();
	            } catch (IOException e) {
	                JOptionPane.showMessageDialog(null, "Error al cerrar archivo de texto: "+e.getMessage());
	            }
	        }
	    }
	}
	
	public void eliminarVideo(Integer id) throws MaterialNotFoundException {
		List<List<String>> listaArchivo = dataSource.readFile("videos.csv");
	/*	List<Video> videos = new ArrayList<Video>();
		for(List<String> filaVideo: listaArchivo) {
			if(!(filaVideo.get(0).equals(id.toString()))) {
				Video aux = new Video();
				aux.loadFromStringRow(filaVideo);
				videos.add(aux);
			}
			
		}
		List<CsvRecord> l = new ArrayList<CsvRecord>();
		for(Video v : videos) {
			l.add(v);
			
		}
	*/	
		FileWriter fichero = null;
	    PrintWriter escritor = null;
		try {
	//		dataSource.guardarColeccion("videos.csv", l);
			fichero = new FileWriter("videos.csv");
			escritor = new PrintWriter(fichero);
			escritor.flush();
			for(List<String> fila : listaArchivo) {
				if(!(fila.get(0).equals(id.toString()))) {
					// Escribimos la linea en el nuevo archivo
					dataSource.writeLine(escritor, fila);
				}
			}
			escritor.flush();
			escritor.close();
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, "Error al escribir en el archivo de texto: "+e.getMessage());
		}
		finally {
	        if(fichero != null){
	            try {
	                fichero.close();
	            } catch (IOException e) {
	                JOptionPane.showMessageDialog(null, "Error al cerrar archivo de texto: "+e.getMessage());
	            }
	        }
	    }
	}
	
	private void cargarIdSecuencia() {
		Integer maxId = 0;
		List<MaterialCapacitacion> li = this.listaMateriales();
		for(int i = 0; i < li.size(); i++) {
			if(li.get(i).getId() > maxId) {
				maxId = li.get(i).getId();
			}
		}
		SECUENCIA_ID = maxId;
	}
	
	
	public void agregarMaterialWishList(MaterialCapacitacion material) {
		// Copio el material
		MaterialCapacitacion mat;
		if(material instanceof Libro) {
			mat = new Libro(++SECUENCIA_ID_WISHLIST, material.getTitulo(), material.getCosto(), ((Libro) material).getPrecioCompra(), ((Libro) material).getPaginas(), material.getCalificacion(), material.getFechaPublicacion(), material.getRelevancia(), material.getTema());
			wishListLibros.add(mat);
			try {
				dataSource.agregarFilaAlFinal("wishListLibro.csv", mat);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			mat = new Video(++SECUENCIA_ID_WISHLIST, material.getTitulo(), material.getCosto(), ((Video)material).getDuracion(), material.getCalificacion(), material.getFechaPublicacion(), material.getRelevancia(), material.getTema());
			wishListVideos.add(mat);
			try {
				dataSource.agregarFilaAlFinal("wishListVideo.csv", mat);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public PriorityQueue<MaterialCapacitacion> listaWishList(){
		PriorityQueue<MaterialCapacitacion> pqLibrosCopy = new PriorityQueue<MaterialCapacitacion>(wishListLibros);
		PriorityQueue<MaterialCapacitacion> pqVideosCopy = new PriorityQueue<MaterialCapacitacion>(wishListVideos);
		for(int i = 0 ; i < pqVideosCopy.size() ; i++) {
			pqLibrosCopy.add(pqVideosCopy.poll());
		}
		return pqLibrosCopy;
	}
	
	public List<List<MaterialCapacitacion>> buscarCaminosHastaNSaltos(Integer nodo1, Integer nodo2, Integer n){
		
		MaterialCapacitacion n1 = this.findById(nodo1);
		MaterialCapacitacion n2 = this.findById(nodo2);
		return GRAFO_MATERIAL.buscarCaminosHastaNSaltos(n1, n2, n);
	}
	
	public void eliminarArchivo(String nombreArchivo) throws IOException {
		File fichero = new File(nombreArchivo);
		if(fichero.exists()) {
			fichero.delete();
		}else {
			throw new IOException("Error en el fichero");
		}
	}
	
	public void deleteAristas() {
		GRAFO_MATERIAL.deleteAristas();
	}
	
	
}
