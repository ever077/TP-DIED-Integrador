package frsf.isi.died.app.vista;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import frsf.isi.died.app.controller.TiposAcciones;
import frsf.isi.died.app.controller.MenuController;

public class Principal {
	private static JMenuItem menuItem_NuevoVideo;
	private static JMenuItem menuItem_NuevoLibro;
	private static JMenuItem menuItem_Salir;
	private static JMenuItem menuItem_BuscarCamino;
	private static JMenuItem menuItem_ModificarLibro;
	public static void main(String[] args) {
	    javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	          createAndShowGUI();
	        }
	    });
	}

	 private static void createAndShowGUI() {
		 JFrame f = new JFrame();
		 MenuController controller = new MenuController(f);
				    
	        JMenuBar menuBar;
	        JMenu menu;
	        JMenuItem menuItem;

	        menuBar = new JMenuBar();

	        menu = new JMenu("Sistema");
	        menuBar.add(menu);
	        
	        JMenu MenuLibro = new JMenu("Libro");
	        menu.add(MenuLibro);
	        
	        menuItem_NuevoLibro = new JMenuItem("Nuevo Libro");
	        MenuLibro.add(menuItem_NuevoLibro);
	        
	        menuItem_ModificarLibro = new JMenuItem("Modificar Libro");
	        MenuLibro.add(menuItem_ModificarLibro);
	        
	        menuItem_NuevoLibro.addActionListener(e -> controller.showView(TiposAcciones.ALTA_LIBROS));
	        menuItem_ModificarLibro.addActionListener(e -> controller.showView(TiposAcciones.MODIFICACION_LIBROS));
	        
	        JMenu MenuVideo = new JMenu("Video");
	        menu.add(MenuVideo);

	        menuItem_NuevoVideo = new JMenuItem("Nuevo Video");
	        MenuVideo.add(menuItem_NuevoVideo);
	        menu.addSeparator();
	        menuItem_Salir = new JMenuItem("Salir");
	        menuItem_Salir.addActionListener(e->System.exit(99));
	        menu.add(menuItem_Salir);

	        menuBar.add(menu);
	        
	        menu = new JMenu("Opciones");
	        menuBar.add(menu);
	        
	        menuItem_BuscarCamino = new JMenuItem("Buscar Camino");
	        menuItem_BuscarCamino.addActionListener(e -> controller.showView(TiposAcciones.VER_GRAFO));
	        menu.add(menuItem_BuscarCamino);
	        menuBar.add(menu);
	        
	        f.setJMenuBar(menuBar);
	        
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        f.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
	        f.pack();
	        f.setVisible(true);
	    }

}
