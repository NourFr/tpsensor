package sensornetwork;

import snifc.SimulatorIfc;
import java.util.Vector;
import snifc.PacketIfc;
import snifc.sensor.CaptorIfc;
import snifc.sensor.QueueIfc;
import snifc.sensor.MemoryIfc;
import snifc.sensor.IOPortsIfc;
import snifc.sensor.SensorIfc;
import sensornetwork.Packet;
import sensornetwork.sensor.Sensor;

public class Simulator implements SimulatorIfc{

	private int nbSensors;
	private int nbSteps;
	private Vector listeSensor;
	private Vector listeLink;

	
	public Simulator(){
		listeSensor = new Vector(nbSensors);
		listeLink = new Vector();
	}	
	
	public void reset() {
		this.nbSensors = 10;
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


	public void linkSensors() {
		int i;
		// Cree une topologie en anneau
		System.out.println("Creation des liens\n");
		listeLink.setSize(nbSensors);
		for(i=0;i<(nbSensors-1);i++){
			listeLink.add(new Link(((Sensor)listeSensor.elementAt(i)).getPort(), ((Sensor)listeSensor.elementAt(i+1)).getPort(), i*10+i+1));
		}	
		listeLink.add(new Link(((Sensor)listeSensor.elementAt(nbSensors-1)).getPort(), ((Sensor)listeSensor.elementAt(0)).getPort(), (nbSensors-1)*10));

	}	
		
	
	public void runSensors() {

		int i;

		//test avec 4 etapes
		this.nbSteps = 4;

		System.out.println("Debut de la simulation\n");

		for(i=0; i<this.nbSteps; i++){
			System.out.println("----->>>>Etape " + i + " de la simulation<<<<-----\n");
			
			//Activation captures
			for(i=0; i<listeSensor.size(); i++){
				System.out.println("Simulation des captures\n");
				((Sensor)listeSensor.elementAt(i)).simulateCapture();
			}

			//Activation capteurs
			for(i=0; i<listeSensor.size(); i++){
				System.out.println("Activation des capteurs\n");
				((Sensor)listeSensor.elementAt(i)).activateCaptor();
			}
			
			//Activation ports
			for(i=0; i<listeSensor.size(); i++){
				System.out.println("Activation des ports\n");
				((Sensor)listeSensor.elementAt(i)).activatePort();
			}	

			//Activation files d'attentes
			for(i=0; i<listeSensor.size(); i++){
				System.out.println("Activation des queues\n");
				((Sensor)listeSensor.elementAt(i)).activateQueue();
			}	
		
		}

	}

	public void showStat(){}

	public static void main(String [] args){
		
		Simulator s = new Simulator();
		try{
		s.reset();
		s.createSensors();
		s.linkSensors();
		s.runSensors();
		}
		catch (Exception e){
			e.printStackTrace();
		}	
	}	

}
