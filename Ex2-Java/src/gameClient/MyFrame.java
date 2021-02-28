package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a very simple GUI class to present a
 * game on a graph ,
 *
 */
public class MyFrame extends JFrame
{
	private int _ind;
	private MyArena _ar;
	private gameClient.util.Range2Range _w2f;
	public MyPanel myPanel;


	/**
	 * frame constructor,create new one and new panel and merge the panel on the frame
	 * @param a
	 * @throws IOException
	 */
	MyFrame(String a) throws IOException
	{
		super(a);
		int _ind = 0;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.myPanel=new MyPanel(this.getWidth(),this.getHeight());
		this.add(myPanel);
	}

	/**
	 * update the current arena and the frame size according the current situation of the game
	 * @param ar
	 */
	public void update(MyArena ar)
	{
		this._ar = ar;
		updateFrame();
	}

	private void updateFrame()
	{
		Range rx = new Range(20,this.getWidth()-20);
		Range ry = new Range(this.getHeight()-10,150);
		Range2D frame = new Range2D(rx,ry);
		directed_weighted_graph g = _ar.getGraph();
		_w2f = MyArena.w2f(g,frame);
	}


	//===============================================inner class J panel=============================================//


	private class MyPanel extends JPanel
	{
		public BufferedImage background;
		public BufferedImage agent;
		public BufferedImage pokemon;
		public BufferedImage rotated_pokemon;

		public MyPanel(int x,int y) throws IOException
		{
			this.background= ImageIO.read(new File("doc/background2.png"));
			this.agent= ImageIO.read(new File("doc/agent.png"));
			this.pokemon= ImageIO.read(new File("doc/pokemon.png"));
			this.rotated_pokemon= ImageIO.read(new File("doc/rotated_pokemon.png"));

          this.setSize(x,y);
		}

		/**
		 * this method manage the acctual paint (and repaint) on the frame by calling to all painting functions
		 * @param g
		 */
		public void paint(Graphics g)
		{
			int w = this.getWidth();
			int h = this.getHeight();
			g.clearRect(0, 0, w, h);
			drawBackGround(g);
			updateFrame();
			drawPokemons(g);
			drawGraph(g);
			drawAgants(g);
			//drawInfo(g);
			g.setFont(new Font("",1,10));
			g.drawString("time to end :"+(int)(_ar.getTime()/1000),this.getWidth()-100,this.getHeight()-50);

		}

		/**
		 * set background(pokemon logo picture)
		 * @param g
		 */
		private void drawBackGround(Graphics g)
		{
			g.drawImage(background,0,0,getWidth(),getHeight(),null);
		}

		/**
		 * this method iterate over all nodes and edges of the graph and call to
		 * "draw node" and "draw edge" functions for each one of them
		 * @param g
		 */
		private void drawGraph(Graphics g)
		{
			directed_weighted_graph gg = _ar.getGraph();
			Iterator<node_data> iter = gg.getV().iterator();
			while(iter.hasNext()) {
				node_data n = iter.next();
				g.setColor(Color.blue);
				drawNode(n,(this.getWidth()/200),g);
				Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
				while(itr.hasNext()) {
					edge_data e = itr.next();
					g.setColor(Color.gray);
					drawEdge(e, g);
				}
			}
		}

		/**
		 *this method iterate over all current pokemons and draw them as image(pokemon ball) on the current location
		 *  according the type,
		 * if the type is 1  - it draws the regular image
		 * -f the type is -1 - it draws the rotated (180) image
		 * @param g
		 */
		private void drawPokemons(Graphics g)
		{
			java.util.List<CL_Pokemon> fs = _ar.getPokemons();
			if(fs!=null) {
				Iterator<CL_Pokemon> itr = fs.iterator();

				while(itr.hasNext())
				{

					CL_Pokemon f = itr.next();
					Point3D c = f.getLocation();
					int r=(this.getWidth()/100);
					if(f.getType()<0)
					{
						if(c!=null)
						{
							geo_location fp = _w2f.world2frame(c);
							g.drawImage(rotated_pokemon,(int)fp.x()-10-r,(int)(fp.y()-(100+r)),2*r,2*r,null);
						}
					}
					else if(c!=null)
					{
						geo_location fp = _w2f.world2frame(c);
						g.drawImage(pokemon,(int)fp.x()-10-r,(int)(fp.y()-(100+r)),2*r,2*r,null);
					}
				}
			}
		}

		/**
		 *this method iterate over all current agents and draw them as image(pikachu) on the current location,
		 * this methos also draw the value of each agent above him
		 * @param g
		 */
		private void drawAgants(Graphics g)
		{
			List<CL_Agent> rs = _ar.getAgents();
			g.setColor(Color.red);
			int i=0;
			while(rs!=null && i<rs.size())
			{
				geo_location c = rs.get(i).getLocation();
				int r=(this.getWidth()/125);
				if(c!=null)
				{

					geo_location fp = _w2f.world2frame(c);
					g.drawImage(agent,(int)fp.x()-10-r,(int)(fp.y()-(100+r)),3*r,3*r,null);
					g.setFont(new Font("",1,15));
					g.drawString(""+rs.get(i).getValue(),(int)fp.x(),(int)(fp.y()-(100)));
				}
				i++;
			}
		}

		/**
		 * this method draws node on the graph(at node's location)
		 * @param n
		 * @param r
		 * @param g
		 */
		private void drawNode(node_data n, int r, Graphics g)
		{
			geo_location pos = n.getLocation();
			geo_location fp =_w2f.world2frame(pos);
			g.setColor(Color.red);
			g.fillOval((int)fp.x()-10-r, (int)(fp.y()-(100+r)), 2*r, 2*r);
			g.setFont(new Font("",1,10));
			g.drawString(""+n.getKey(), (int)fp.x()-10-r, (int)(fp.y()-(100+(4*r))));
		}

		/**
		 * this method draws edge on the graph at the edge's location, between the src and dest nodes of this edge
		 * @param e
		 * @param g
		 * @param g
		 */
		private void drawEdge(edge_data e, Graphics g)
		{
			g.setColor(Color.BLACK);
			directed_weighted_graph gg = _ar.getGraph();
			geo_location s = gg.getNode(e.getSrc()).getLocation();
			geo_location d = gg.getNode(e.getDest()).getLocation();
			geo_location s0 =_w2f.world2frame(s);
			geo_location d0 =_w2f.world2frame(d);
			g.drawLine((int)s0.x()-10, (int)s0.y()-100, (int)d0.x()-10, (int)d0.y()-100);
			//	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
		}

	}


}