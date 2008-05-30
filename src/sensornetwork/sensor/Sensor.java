package sensornetwork.sensor;
import snifc.PacketIfc;
import snifc.sensor.CaptorIfc;
import snifc.sensor.QueueIfc;
import snifc.sensor.MemoryIfc;
import snifc.sensor.IOPortsIfc;
import snifc.sensor.SensorIfc;
 
public class Sensor implements SensorIfc {

	private CaptorIfc capteur;
	private QueueIfc queue;
	private MemoryIfc memoire;
	private IOPortsIfc ports;	
	

	public void simulateCapture(){
		this.capteur.triggerCapture();
	}
	
	
	public void activateCaptor(){
		PacketIfc nouveauPaquet;
		nouveauPaquet = this.capteur.capture();
		if (this.memoire.store(nouveauPaquet) != false) {
			this.ports.writePacket(nouveauPaquet);
		}
	}
	
	
	public void activatePort(){
		this.ports.getPackets();
	}
	

	public void activateQueue(){
		PacketIfc paquetCourant;
		while (paquetCourant = this.queue.deQueue()) {
			if (this.memoire.store(paquetCourant) != false) {
				this.ports.writePacket(paquetCourant);
			}
		}
	}
	

	public IOPortsIfc getPort(){
		return this.ports;
	}
	
}
