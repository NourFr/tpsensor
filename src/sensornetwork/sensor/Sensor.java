package sensornetwork.sensor;
import snifc.PacketIfc;
import snifc.sensor.CaptorIfc;
import snifc.sensor.QueueIfc;
import snifc.sensor.MemoryIfc;
import snifc.sensor.IOPortsIfc;
import snifc.sensor.SensorIfc;
import sensornetwork.Packet;
 
public class Sensor implements SensorIfc {

	private int id;
	private CaptorIfc capteur;
	private QueueIfc queue;
	private MemoryIfc memoire;
	private IOPortsIfc ports;	
	

	public Sensor(int idSensor) {
		this.id=idSensor;
		this.capteur = new Captor(this.id);
		this.queue = new Queue();
		this.memoire = new Memory(this.id);
		this.ports = new IOPorts(this.queue);
	}

	public void simulateCapture(){
		this.capteur.triggerCapture();
	}
	
	
	public void activateCaptor(){
		boolean mesureOK=true;
		PacketIfc nouveauPaquet = new Packet(0,0);
		try{
			nouveauPaquet = this.capteur.capture();
		}
		catch(Exception e){
			mesureOK=false;
			System.out.println("Erreur au niveau de l'activation du capteur sur sensor "+id+"\n");
			System.out.println("Pas de mesure generee\n");//.printStackTrace();
		}		
		if(mesureOK==true){
			if (this.memoire.store(nouveauPaquet) != false) {
				System.out.println("Stockage du paquet "+nouveauPaquet.getId()+"\n");
				this.ports.writePacket(nouveauPaquet);
				System.out.println("Packet "+nouveauPaquet.getId()+" ecrit sur les liens du sensor "+id+"\n");
			}else{
				System.out.println("Probleme a l'ecriture du paquet "+nouveauPaquet.getId()+" dans la memoire\n");
			}
		}	
	}
	
	
	public void activatePort(){
		System.out.println("Recuperation des packets latents sur le sensor "+id+"\n");
		this.ports.getPackets();
	}
	

	public void activateQueue(){
		PacketIfc paquetCourant;
		System.out.println("Depilage de la queue sensor "+this.id+"\n");
		while ((paquetCourant = this.queue.deQueue())!=null) {
			System.out.println("Stockage du paquet "+paquetCourant.getId()+"\n");
			if (this.memoire.store(paquetCourant) != false) {
				this.ports.writePacket(paquetCourant);
				System.out.println("Packet "+paquetCourant.getId()+" ecrit sur les liens du sensor "+this.id+"\n");
			}
		}
	}
	

	public IOPortsIfc getPort(){
		return this.ports;
	}
	
}
