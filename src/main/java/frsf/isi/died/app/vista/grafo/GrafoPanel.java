/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frsf.isi.died.app.vista.grafo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import frsf.isi.died.app.controller.GrafoController;
import frsf.isi.died.tp.estructuras.Arista;
import frsf.isi.died.tp.modelo.productos.MaterialCapacitacion;

/**
 *
 * @author martdominguez
 */
public class GrafoPanel extends JPanel {

    private JFrame framePadre;
    private Queue<Color> colaColores;
    private GrafoController controller;
    
    private JButton btnSiguienteCamino;
    private JButton btnPageRank;

    private List<VerticeView> vertices;
    private List<AristaView> aristas;

    private AristaView auxiliar;
    private Point posicionColocarVertice;

    public GrafoPanel() {
        this.framePadre = (JFrame) this.getParent();
        
        this.vertices = new ArrayList<>();
        this.aristas = new ArrayList<>();

        
        this.colaColores = new LinkedList<Color>();
        this.colaColores.add(Color.RED);
        this.colaColores.add(Color.BLUE);
        this.colaColores.add(Color.ORANGE);
        this.colaColores.add(Color.CYAN);
   
        btnSiguienteCamino = new JButton("Siguiente camino");
        this.add(btnSiguienteCamino);
        btnSiguienteCamino.addActionListener( e ->{
        	
        	//this.paintComponent(getGraphics());
             controller.pintarSiguienteCamino();
             this.repaint();
                	
        });
        
        btnPageRank = new JButton("Calcular PageRank");
        this.add(btnPageRank);
        btnPageRank.addActionListener( e ->{
        	List<Integer> listaIdVertice = this.getListIdFromVerticeView(this.vertices);
        	List<List<Integer>> listaIdAristas = this.getListIdFromAristaView(this.aristas);
        	controller.showPageRank(listaIdVertice, listaIdAristas);
            
        });
        
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2 && !event.isConsumed()) {
                    event.consume();
                    Object[] mats = controller.listaVertices().toArray();
                    //String text = JOptionPane.showInputDialog(, "ID del nodo");
                    Object verticeMatSeleccionado= (MaterialCapacitacion) JOptionPane.showInputDialog(framePadre, 
                            "Que material corresponde con el vertice?",
                            "Agregar Vertice",
                            JOptionPane.QUESTION_MESSAGE, 
                            null, 
                            mats, 
                            mats[0]);

                    if (verticeMatSeleccionado != null) {
                        // quito un color de la cola
                        Color aux = colaColores.remove();
                        controller.crearVertice(event.getX(), event.getY(), aux,(MaterialCapacitacion) verticeMatSeleccionado);
                        // pongo el color al final de la cola
                        colaColores.add(aux);
                    }
                }
            }
            
            
            public void mouseReleased(MouseEvent event) {
                VerticeView vDestino = clicEnUnNodo(event.getPoint());
                if (auxiliar!=null && vDestino != null) {
                    if(!auxiliar.getOrigen().equals(vDestino)) {
                    	auxiliar.setDestino(vDestino);
	                    controller.crearArista(auxiliar);
	                    auxiliar = null;
                    }
                }
            }

        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent event) {
                VerticeView vOrigen = clicEnUnNodo(event.getPoint());
                if (auxiliar==null && vOrigen != null) {
                    auxiliar = new AristaView();                    
                    auxiliar.setOrigen(vOrigen);
                }
            }
        });
        
        addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent event) {
    			if(SwingUtilities.isRightMouseButton(event)) {
    				posicionColocarVertice = event.getPoint();
    				controller.pintarVertice(event.getPoint());
    			}
    		}
        });
        
    }

    public void agregar(AristaView arista){
        this.aristas.add(arista);
    }    
    
    public void agregar(VerticeView vert){
        this.vertices.add(vert);
    }

    public void caminoPintar(List<MaterialCapacitacion> camino, Color color){
        //this.vertices.add(vert);
    	Integer idOrigen =-1;
    	Integer idDestino =-1;
    	for(MaterialCapacitacion mat : camino) {
    		if(idOrigen<0) {
    			idOrigen=mat.getId();
    		}else {
    			idDestino = mat.getId();
    			for(AristaView av : this.aristas) {
    				if(av.getOrigen().getId().equals(idOrigen) && av.getDestino().getId().equals(idDestino) ) {
    	    			av.setColor(color);
//    	    			av.getOrigen().setColor(Color.BLUE);
//    	    			av.getDestino().setColor(Color.BLUE);
    				}
    			}
    			idOrigen = idDestino;
    		}
    	}
    }
    
    private void dibujarVertices(Graphics2D g2d) {
        for (VerticeView v : this.vertices) {
            g2d.setPaint(Color.BLUE);
            g2d.drawString(v.etiqueta(),v.getCoordenadaX()-5,v.getCoordenadaY()-5);
            g2d.setPaint(v.getColor());
            g2d.fill(v.getNodo());
        }
    }

    private void dibujarAristas(Graphics2D g2d) {
      //  System.out.println(this.aristas);
        for (AristaView a : this.aristas) {
            g2d.setPaint(a.getColor());
            g2d.setStroke ( a.getFormatoLinea());
            g2d.draw(a.getLinea());
            //dibujo una flecha al final
            // con el color del origen para que se note
            g2d.setPaint(Color.MAGENTA);
            Polygon flecha = new Polygon();  
            flecha.addPoint(a.getDestino().getCoordenadaX(), a.getDestino().getCoordenadaY()+7);
            flecha.addPoint(a.getDestino().getCoordenadaX()+20, a.getDestino().getCoordenadaY()+10);
            flecha.addPoint(a.getDestino().getCoordenadaX(), a.getDestino().getCoordenadaY()+18);
            g2d.fillPolygon(flecha);
        }
    }

    private VerticeView clicEnUnNodo(Point p) {
        for (VerticeView v : this.vertices) {
            if (v.getNodo().contains(p)) {
                return v;
            }
        }
        return null;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        dibujarVertices(g2d);
        dibujarAristas(g2d);
    }

    public Dimension getPreferredSize() {
        return new Dimension(450, 400);
    }

    public GrafoController getController() {
        return controller;
    }

    public void setController(GrafoController controller) {
        this.controller = controller;
    }
    
    public Point getPosicionVertice() {
    	Point punto = new Point();
    	if(posicionColocarVertice != null) {
    		punto = posicionColocarVertice;
    		posicionColocarVertice = null;
    	}
    	return punto;
/*    	this.getRightClick();
    	
    	return posicionColocarVertice;
*/    	
    }
    
/*    private void getRightClick() {
    	addMouseListener(new MouseAdapter() {
    		public void mouseRightClicked(MouseEvent event) {
    			if(SwingUtilities.isRightMouseButton(event)) {
    				posicionColocarVertice = event.getPoint();
    			}
    		}
    	});
    }
*/    
    
    private List<Integer> getListIdFromVerticeView(List<VerticeView> listaVerticesView) {
    	List<Integer> lista = new ArrayList<Integer>();
    	for(VerticeView v : listaVerticesView) {
    		lista.add(v.getId());
    	}
    	return lista;
    }
    
    private List<List<Integer>> getListIdFromAristaView(List<AristaView> listaAristasView){
    	List<List<Integer>> listaFinal = new ArrayList<List<Integer>>();
    	
    	for(AristaView a : listaAristasView) {
    		List<Integer> lista = new ArrayList<Integer>();
    		lista.add(a.getOrigen().getId());
    		lista.add(a.getDestino().getId());
    		listaFinal.add(lista);
    	}
    	return listaFinal;
    }
   

}
