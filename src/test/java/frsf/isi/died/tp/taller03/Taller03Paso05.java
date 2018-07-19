package frsf.isi.died.tp.taller03;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import frsf.isi.died.tp.estructuras.Arbol;
import frsf.isi.died.tp.estructuras.ArbolBinarioBusqueda;
import frsf.isi.died.tp.estructuras.ArbolVacio;
import frsf.isi.died.tp.modelo.Biblioteca;
import frsf.isi.died.tp.modelo.BibliotecaABB;
import frsf.isi.died.tp.modelo.productos.Libro;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;
import frsf.isi.died.tp.modelo.productos.Video;

public class Taller03Paso05 {

	private ArbolBinarioBusqueda a1,a2,a3,a4;
	private Libro libroA;
	private Libro libroB;
	private Libro libroC;
	private Libro libroD;
	private Libro libroE;
	private Libro libroF;
	private Video videoA;
	private Video videoB;
	private Video videoC;
	private Video videoD;
	private Video videoE;
	private Video videoF;
	@Before
	public void init() {
		libroA = new Libro(1, "libroA", 10.0, 20.0, 10);
		libroB = new Libro(2, "libroB", 20.0, 10.0, 10);
		libroC = new Libro(3, "libroC", 30.0, 20.0, 10);
		libroD = new Libro(4, "libroD", 40.0, 30.0, 10);
		libroE = new Libro(5, "libroE", 50.0, 25.0, 10);
		libroF = new Libro(6, "libroF", 60.0, 20.0, 10);
		videoA = new Video(7, "videoA", 70.0, 10);
		videoB = new Video(8, "videoB", 80.0, 10);
		videoC = new Video(9, "videoC", 30.0, 10);
		videoD = new Video(10, "videoD", 40.0, 10);
		videoE = new Video(11, "videoE", 50.0, 10);
		videoF = new Video(12, "videoF", 90.0, 10);
		
		a1 = new ArbolBinarioBusqueda((matCap1,matCap2)->(matCap1.precio().intValue() - matCap2.precio().intValue()));
		a2 = new ArbolBinarioBusqueda((matCap1,matCap2)->(matCap1.precio().intValue() - matCap2.precio().intValue()));
		a3 = new ArbolBinarioBusqueda((matCap1,matCap2)->(matCap1.precio().intValue() - matCap2.precio().intValue()));
		a4 = new ArbolBinarioBusqueda((matCap1,matCap2)->(matCap1.precio().intValue() - matCap2.precio().intValue()));
		
	}
	
	@Test
	public void testBuscarPorRango() {
	/*	a.add(libroA);
		a.add(libroB);
		a.add(libroC);
		a.add(libroD);
		a.add(libroE);
		a.add(libroF);
		a.add(videoA);
		a.add(videoB);
		a.add(videoC);
		a.add(videoD);
		a.add(videoE);
		a.add(videoF);
	*/
		
		// TEST 1 - Dos libros no pertenecen al rango.
		a1.add(libroA);
		a1.add(libroB);
		a1.add(libroC);
		a1.add(libroD);
		a1.add(libroE);
		Double min = 0.0;
		Double max = 60.0;
		List<MaterialCapacitacion> lista1 = a1.rango(min, max);
		List<MaterialCapacitacion> lista2 = new ArrayList<>();
		System.out.println("Los precios son:");
		for(MaterialCapacitacion m : a1.inOrden()) {
			if(m.precio() >= min && m.precio() <= max){
				lista2.add(m);
				System.out.println("  " + m.precio());
			}
		}
		assertTrue((lista1.size() == lista2.size()) && (lista1.containsAll(lista2)));
		assertFalse(a1.inOrden().size() == lista1.size());
		
		// TEST 2 - Todos pertenecen al rango.
		a2.add(libroC);
		a2.add(libroD);
		a2.add(libroE);
		a2.add(libroF);
		min = 40.0;
		max = 100.0;
		lista1 = a2.rango(min, max);
		lista2 = new ArrayList<>();
		System.out.println("Los precios son:");
		for(MaterialCapacitacion m : a2.inOrden()) {
			if(m.precio() >= min && m.precio() <= max){
				lista2.add(m);
				System.out.println("  " + m.precio());
			}
		}
		assertFalse(a2.inOrden().size() != lista1.size());
	
		// TEST 3 - Todos pertenecen al rango (se prueba que el 
		//          precio puede ser igual al los limites del rango.
		a3.add(libroC);
		a3.add(libroD);
		a3.add(libroE);
		a3.add(libroF);
		min = 50.0;
		max = 80.0;
		lista1 = a3.rango(min, max);
		lista2 = new ArrayList<>();
		System.out.println("Los precios son:");
		for(MaterialCapacitacion m : a3.inOrden()) {
			if(m.precio() >= min && m.precio() <= max){
				lista2.add(m);
				System.out.println("  " + m.precio());
			}
		}
		assertTrue((lista1.size() == lista2.size()) && (lista1.containsAll(lista2)));

		
	}



}
