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
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.WindowConstants;
import java.lang.Integer;


public class Simulator implements SimulatorIfc, ActionListener{

	private int topology; // 1 : anneau  2 : central  3 : maille  4 : asymetrique
	private Vector listeSensor;
	private Vector stats;
	private Vector listeLink;
	private JFrame fenetre;
	private JLabel ttlLabel,nbSensorsLabel, nbStepsLabel;
	public static JTextField ttlText, sensorsText, stepsText;
	private JPanel north, south, center, panel;
	private JButton anneau, central, maille, asymetrique;
	public static JTextArea afficheStat, afficheSteps;


	public Simulator(){

		this.fenetre = new JFrame("Reseau de sensors");

		this.north = new JPanel();
		this.center = new JPanel();
		this.south = new JPanel();
		this.panel = new JPanel();

		this.anneau = new JButton("Topologie en anneau");
		this.central = new JButton("Topologie centralisee");
		this.maille = new JButton("Topologie entierement maillee");
		this.asymetrique = new JButton("Topologie asymetrique");

		this.ttlLabel = new JLabel("ttl :");
		this.nbSensorsLabel = new JLabel("Nombre de sensors :");
		this.nbStepsLabel = new JLabel("Nombre d'etapes :");
		this.ttlText = new JTextField("10",6);
		this.sensorsText = new JTextField("4",6);
		this.stepsText = new JTextField("4",6);

		listeSensor = new Vector(Integer.parseInt(this.sensorsText.getText()));
		listeLink = new Vector((Integer.parseInt(this.sensorsText.getText()))^2);

		this.afficheStat = new JTextArea("Statistiques de la simulation :\n", 10, 50);
		this.afficheSteps = new JTextArea("Deroulement de la simulation : \n", 25, 50);

		this.south.setLayout(new BorderLayout(0,40));
		this.center.setLayout(new FlowLayout(1,40,50));
		this.north.setLayout(new GridLayout(1,4,60,50));
		this.panel.setLayout(new BorderLayout());

		this.anneau.addActionListener(this);
		this.central.addActionListener(this);
		this.maille.addActionListener(this);
		this.asymetrique.addActionListener(this);

		south.add(this.afficheSteps, BorderLayout.NORTH);
		south.add(this.afficheStat, BorderLayout.SOUTH);
		center.add(this.ttlLabel);
		center.add(this.ttlText);
		center.add(this.nbSensorsLabel);
		center.add(this.sensorsText);
		center.add(this.nbStepsLabel);
		center.add(this.stepsText);
		north.add(this.anneau);
		north.add(this.central);
		north.add(this.maille);
		north.add(this.asymetrique);

		this.panel.add(this.south, BorderLayout.SOUTH);
		this.panel.add(this.center, BorderLayout.CENTER);
		this.panel.add(this.north, BorderLayout.NORTH);
		this.fenetre.getContentPane().add(panel);

		fenetre.pack();
		fenetre.setSize(1100,800);
		fenetre.setVisible(true);

	}	

	public void actionPerformed(ActionEvent e){

		if (e.getSource() == this.anneau) this.topology = 1;
		if (e.getSource() == this.central) this.topology = 2;
		if (e.getSource() == this.maille) this.topology = 3;
		if (e.getSource() == this.asymetrique) this.topology = 4;

		try{
			this.reset();
			this.createSensors();
			this.linkSensors();
			this.runSensors();
			this.showStat();
		}
		catch(Exception huhu){
			afficheSteps.append("probleme\n");
			huhu.printStackTrace();
		}	
	}

	public void reset() {
		this.listeSensor.clear();
		this.listeLink.clear();
		this.afficheSteps.setText("Déroulement de la simulation :\n");
		this.afficheStat.setText("Statistiques de la simulation :\n");
	}	

	public void createSensors() throws Exception{
		int i;
		afficheSteps.append("Creation des senseurs\n");
		for(i=0;i<Integer.parseInt(this.sensorsText.getText());i++){
			if(listeSensor.add(new Sensor(i))==false){
				throw new Exception("Erreur a l'ajout d'un sensor");
			}	
		}
	}	


	public void linkSensors() throws Exception{
		int i,j;

		switch (this.topology){

			case 1 :

				// Cree une topologie en anneau
				afficheSteps.append("Creation des liens\n");
				try{
					for(i=0;i<(Integer.parseInt(this.sensorsText.getText())-1);i++){
						listeLink.add(new Link(((Sensor)listeSensor.elementAt(i)).getPort(), ((Sensor)listeSensor.elementAt(i+1)).getPort(), (i+1)*10+i));
						afficheSteps.append("Ajout du lien "+((LinkIfc)listeLink.elementAt(i)).getId()+"\n");
						((Sensor)listeSensor.elementAt(i)).getPort().addLink((LinkIfc)(listeLink.elementAt(i)));
						((Sensor)listeSensor.elementAt(i+1)).getPort().addLink((LinkIfc)(listeLink.elementAt(i)));
					}	
					listeLink.add(new Link(((Sensor)listeSensor.elementAt(Integer.parseInt(this.sensorsText.getText())-1)).getPort(), ((Sensor)listeSensor.elementAt(0)).getPort(), (Integer.parseInt(this.sensorsText.getText())-1)*10));
					afficheSteps.append("Ajout du lien "+((LinkIfc)listeLink.elementAt(Integer.parseInt(this.sensorsText.getText())-1)).getId()+"\n");
					((Sensor)listeSensor.elementAt(0)).getPort().addLink((LinkIfc)(listeLink.elementAt(Integer.parseInt(this.sensorsText.getText())-1)));
					((Sensor)listeSensor.elementAt(Integer.parseInt(this.sensorsText.getText())-1)).getPort().addLink((LinkIfc)(listeLink.elementAt(Integer.parseInt(this.sensorsText.getText())-1)));
				}
				catch(Exception e){
					afficheSteps.append(((Link)listeLink.elementAt(1)).getId()+"\n");
					afficheSteps.append("La capacite max du IOport a ete atteinte\n");
				}
				break;

			
			
			
			case 2 :

				// Cree une topologie centree
				try{
					for(i=1;i<(Integer.parseInt(this.sensorsText.getText()));i++){ //le nb de sensor - 1 = nb de link
						listeLink.add(new Link(((Sensor)listeSensor.elementAt(0)).getPort(), ((Sensor)listeSensor.elementAt(i)).getPort(), i*10));
						afficheSteps.append("Ajout du lien "+((LinkIfc)listeLink.elementAt(i-1)).getId()+"\n");
						((Sensor)listeSensor.elementAt(0)).getPort().addLink((LinkIfc)(listeLink.elementAt(i-1)));
						((Sensor)listeSensor.elementAt(i)).getPort().addLink((LinkIfc)(listeLink.elementAt(i-1)));
					}	
				}
				catch(Exception e){
					//afficheSteps.append(((Link)listeLink.elementAt(1)).getId()+"\n");
					afficheSteps.append("La capacite max du IOport a ete atteinte\n");
					e.printStackTrace();
				}
				break;
		
		
			case 3 :

				// Cree une topologie entierement maille
				try{
					for(i=0;i<(Integer.parseInt(this.sensorsText.getText())-1);i++){ 
						for(j=i+1;j<(Integer.parseInt(this.sensorsText.getText()));j++){ 
							listeLink.add(new Link(((Sensor)listeSensor.elementAt(i)).getPort(), ((Sensor)listeSensor.elementAt(j)).getPort(), j*10+i));
							afficheSteps.append("Ajout du lien "+((LinkIfc)listeLink.lastElement()).getId()+"\n");
							((Sensor)listeSensor.elementAt(i)).getPort().addLink((LinkIfc)(listeLink.lastElement()));
							((Sensor)listeSensor.elementAt(j)).getPort().addLink((LinkIfc)(listeLink.lastElement()));
						}
					}	
				}
				catch(Exception e){
					//afficheSteps.append(((Link)listeLink.elementAt(1)).getId()+"\n");
					afficheSteps.append("La capacite max du IOport a ete atteinte\n");
					e.printStackTrace();
				}
				break;
		
		}	

		}

		public void runSensors() {

			int i,j;

			//test avec 4 etapes

			afficheSteps.append("Debut de la simulation pour "+this.stepsText.getText()+" etapes\n");

			for(j=0; j<Integer.parseInt(this.stepsText.getText()); j++){
				afficheSteps.append("----->>>>Etape " + j + " de la simulation<<<<-----\n");

				//Activation capture sur le sensor 1
				//for(i=0; i<listeSensor.size(); i++){
				//afficheSteps.append("\n\nSimulation des captures\n");
				((Sensor)listeSensor.elementAt(0)).simulateCapture();
				//	((Sensor)listeSensor.elementAt(i)).simulateCapture();
				//}

				//Activation capteurs
				//afficheSteps.append("\n\nActivation des capteurs\n");
				for(i=0; i<listeSensor.size(); i++){
					((Sensor)listeSensor.elementAt(i)).activateCaptor();
				}

				//Activation ports
				//afficheSteps.append("\n\nActivation des ports\n");
				for(i=0; i<listeSensor.size(); i++){
					((Sensor)listeSensor.elementAt(i)).activatePort();
				}	

				//Activation files d'attentes
				//afficheSteps.append("\n\nActivation des queues\n");
				for(i=0; i<listeSensor.size(); i++){
					((Sensor)listeSensor.elementAt(i)).activateQueue();
				}	

			}

		}

		public void showStat(){

			int i;
			for(i=0; i<this.listeSensor.size(); i++){
				afficheStat.append(((Sensor)listeSensor.elementAt(i)).memoire.toString());
			}	


		}

		public static void main(String [] args){

			Simulator s = new Simulator();
			while(true){}
			/*try{
			  s.reset();
			  s.createSensors();
			  s.linkSensors();
			  s.runSensors();
			  s.showStat();
			  }
			  catch (Exception e){
			  e.printStackTrace();
			  }*/	
		}	

	}
