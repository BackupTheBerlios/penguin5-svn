/*This file is part of Penguin5.
    
  Penguin5 is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  
   Penguin5 is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with Penguin5.  If not, see <http://www.gnu.org/licenses/>.
*/

package de.webcam;

import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Main class
 *
 */
public class WebCam implements Runnable, ComponentListener{

	private static String url = "http://www.tuebingen.de/camera/markt.jpg";
	private static long sleep = 60000;
	
	private JFrame frame = new JFrame();
	private JLabel lblBild;
	private Image imgScaled;
	private Image imgUnscaled;

	/**
	 * Takes arguments:
	 * refreshing interval in milli seconds
	 * URL as string
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) 
	throws Exception{
		
		if(args.length >= 1){
			
			sleep = Long.parseLong(args[0]);
			
		}
		
		if(args.length == 2){
			
			url = args[1];
			
		}
		
		new WebCam();

	}
	
	/**
	 * Constructor
	 * Creates GUI and starts thread for the viewer
	 */
	public WebCam(){
		
		try {
			
			frame= new JFrame();
			frame.setTitle("Marktplatz T\u00FCbingen");
			lblBild=new JLabel("L\u00E4d...");
			frame.add(lblBild);
			frame.setVisible(true);
			frame.setSize(640,500);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			new Thread(this).start();
			frame.setTitle("Marktplatz T\u00FCbingen");

			frame.addComponentListener(this);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Refreshes the current image periodically from the given url
	 */
	public void run() {
		
		boolean ende = false;
		
		while(!ende){
		
			try {
							
				frame.setTitle("Marktplatz T\u00FCbingen");
				
				URL u= new URL(url);
				
				imgUnscaled = ImageIO.read( u );			
				
				System.out.println("geladen");
				
				this.scaleImage();			
				
				ImageIcon i=new ImageIcon(imgScaled);
				
				lblBild.setIcon(i);
				
				frame.repaint();
				
				frame.setTitle("Marktplatz T\u00FCbingen");
				
				Thread.sleep(sleep);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				ende = true;
				
				lblBild.setText("Fehler");
				
			}
		}
	}

	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * reacts on resize event from the main frame and forces the image to scale
	 */
	public void componentResized(ComponentEvent arg0) {
		
		if(imgScaled!=null &&frame!=null){
			
			this.scaleImage();
			ImageIcon i=new ImageIcon(imgScaled);
			lblBild.setIcon(i);
			frame.repaint();
			
		}
		
		
	}

	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * scales the current image, that it fits into the frame/label
	 */
	private void scaleImage(){
		
		imgScaled = imgUnscaled.getScaledInstance(lblBild.getWidth(), lblBild.getHeight(), Image.SCALE_SMOOTH);
		
	}
	
}
