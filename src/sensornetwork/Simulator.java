package sensornetwork;

import snifc.SimulatorIfc;
import java.util.Vector;
import snifc.PacketIfc;
import snifc.LinkIfc;
import snifc.sensor.CaptorIfc;
import snifc.sensor.QueueIfc;
import snifc.sensor.MemoryIfc;
import snifc.sensor.IOPortsIfc;
import snifc.sensor.SensorIfc;
import sensornetwork.Packet;
import sensornetwork.sensor.Sensor;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.WindowConstants;


public class Simulator implements SimulatorIfc, ActionListener{

	private int nbSensors;
	private int nbSteps;
	private Vector listeSensor;
	private Vector stats;
	private Vector listeLink;
	private JFrame fenetre;
	private JPanel north, south, east, panel;
	private JButton anneau, central, maille, asymetrique;
	private JTextField afficheStat, afficheSteps;

	
	public Simulator(){
		listeSensor = new Vector(nbSensors);
		listeLink = new Vector(nbSensors^2);

		this.fenetre = new JFrame("Reseau de sensors");

		this.north = new JPanel();
		this.south = new JPanel();
		this.east = new JPanel();
		this.panel = new JPanel();

		this.anneau = new JButton("Topologie en anneau");
		this.central = new JButton("Topologie centralisee");
		this.maille = new JButton("Topologie entierement maillee");
		this.asymetrique = new JButton("Topologie asymetrique");

		this.afficheStat = new JTextField();
		this.afficheSteps = new JTextField();

		this.north.setLayout(new BorderLayout());
		this.south.setLayout(new BorderLayout());
		this.panel.setLayout(new BorderLayout());
		this.east.setLayout(new GridLayout(1,4));

		this.anneau.addActionListener(this);
		this.central.addActionListener(this);
		this.maille.addActionListener(this);
		this.asymetrique.addActionListener(this);

		south.add(this.afficheStat, BorderLayout.NORTH);
		south.add(this.afficheSteps, BorderLayout.SOUTH);
		east.add(this.anneau);
		east.add(this.central);
		east.add(this.maille);
		east.add(this.asymetrique);

		this.panel.add(this.south, BorderLayout.SOUTH);
		this.panel.add(this.east, BorderLayout.EAST);
		this.fenetre.getContentPane().add(panel);

		fenetre.pack();
		fenetre.setVisible(true);

	}	

	public void actionPerformed(ActionEvent e){}

	public void reset() {
		this.nbSensors = 4;
		this.nbSteps = 0;
		listeSensor.clear();
	}	

	public void createSensors() throws Exception{
		int i;
		System.out.println("Creation des senseurs\n");
		for(i=0;i<nbSensors;i++){
			if(listeSensor.add(new Sensor(i))==false){
				throw new Exception("Erreur a l'ajout d'un sensor");
			}	
		}
	}	


	public void linkSensors() throws Exception{
		int i;
		// Cree une topologie en anneau
		System.out.println("Creation des liens\n");
		try{
		for(i=0;i<(nbSensors-1);i++){
			listeLink.add(new Link(((Sensor)listeSensor.elementAt(i)).getPort(), ((Sensor)listeSensor.elementAt(i+1)).getPort(), i*10+i+1));
			System.out.println("Ajout du lien "+((LinkIfc)listeLink.elementAt(i)).getId()+"\n");
			((Sensor)listeSensor.elementAt(i)).getPort().addLink((LinkIfc)(listeLink.elementAt(i)));
			((Sensor)listeSensor.elementAt(i+1)).getPort().addLink((LinkIfc)(listeLink.elementAt(i)));
		}	
		listeLink.add(new Link(((Sensor)listeSensor.elementAt(nbSensors-1)).getPort(), ((Sensor)listeSensor.elementAt(0)).getPort(), (nbSensors-1)*10));
		System.out.println("Ajout du lien "+((LinkIfc)listeLink.elementAt(nbSensors-1)).getId()+"\n");
		((Sensor)listeSensor.elementAt(0)).getPort().addLink((LinkIfc)(listeLink.elementAt(nbSensors-1)));
		((Sensor)listeSensor.elementAt(nbSensors-1)).getPort().addLink((LinkIfc)(listeLink.elementAt(nbSensors-1)));
		}
		catch(Exception e){
			System.out.println(((Link)listeLink.elementAt(1)).getId()+"\n");
			System.out.println("La capacite max du IOport a ete atteinte\n");
		}	
	}	
		
	
	public void runSensors() {

		int i,j;

		//test avec 4 etapes
		this.nbSteps = 4;

		System.out.println("Debut de la simulation pour "+this.nbSteps+" etapes\n");

		for(j=0; j<this.nbSteps; j++){
			System.out.println("----->>>>Etape " + j + " de la simulation<<<<-----\n");
			
			//Activation capture sur le sensor 1
			//for(i=0; i<listeSensor.size(); i++){
				System.out.println("\n\nSimulation des captures\n");
				((Sensor)listeSensor.elementAt(0)).simulateCapture();
			//	((Sensor)listeSensor.elementAt(i)).simulateCapture();
			//}

			//Activation capteurs
			System.out.println("\n\nActivation des capteurs\n");
			for(i=0; i<listeSensor.size(); i++){
				((Sensor)listeSensor.elementAt(i)).activateCaptor();
			}
			
			//Activation ports
			System.out.println("\n\nActivation des ports\n");
			for(i=0; i<listeSensor.size(); i++){
				((Sensor)listeSensor.elementAt(i)).activatePort();
			}	

			//Activation files d'attentes
			System.out.println("\n\nActivation des queues\n");
			for(i=0; i<listeSensor.size(); i++){
				((Sensor)listeSensor.elementAt(i)).activateQueue();
			}	
		
		}

	}

	public void showStat(){
	
		int i;
		System.out.println("\n\nStatistiques de fin de simulation :\n");
		for(i=0; i<this.listeSensor.size(); i++){
			System.out.println(((Sensor)listeSensor.elementAt(i)).memoire);
		}	
			

	}

	public static void main(String [] args){
		
		Simulator s = new Simulator();
		try{
		s.reset();
		s.createSensors();
		s.linkSensors();
		s.runSensors();
		s.showStat();
		}
		catch (Exception e){
			e.printStackTrace();
		}	
	}	

}
