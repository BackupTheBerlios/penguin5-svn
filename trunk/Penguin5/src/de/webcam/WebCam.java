package de.webcam;

import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WebCam implements Runnable, ComponentListener{

	private static String url = "http://www.tuebingen.de/camera/markt.jpg";
	private static long sleep = 60000;
	
	private JFrame frame = new JFrame();
	private JLabel lblBild;
	private Image imgScaled;
	private Image imgUnscaled;

	
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

	public void run() {
		
		boolean ende = false;
		
		while(!ende){
		
			try {
							
				frame.setTitle("Marktplatz T\u00FCbingen");
				URL u= new URL(url);
				imgUnscaled = ImageIO.read( u );			
				//Image image = Toolkit.getDefaultToolkit().getImage("/Users/johannes/Pictures/IMAG0013.jpg");
				
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
	
	private void scaleImage(){
		
		imgScaled = imgUnscaled.getScaledInstance(lblBild.getWidth(), lblBild.getHeight(), Image.SCALE_SMOOTH);
		
	}
	
}
