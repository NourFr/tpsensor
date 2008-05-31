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
		PacketIfc nouveauPaquet = new Packet(0,0);
		try{
			nouveauPaquet = this.capteur.capture();
		}
		catch(Exception e){
			e.printStackTrace();
		}		
		if (this.memoire.store(nouveauPaquet) != false) {
			this.ports.writePacket(nouveauPaquet);
		}
	}
	
	
	public void activatePort(){
		this.ports.getPackets();
	}
	

	public void activateQueue(){
		PacketIfc paquetCourant;
		while ((paquetCourant = this.queue.deQueue())!=null) {
			if (this.memoire.store(paquetCourant) != false) {
				this.ports.writePacket(paquetCourant);
			}
		}
	}
	

	public IOPortsIfc getPort(){
		return this.ports;
	}
	
}
