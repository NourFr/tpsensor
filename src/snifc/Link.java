package snifc;
import snifc.sensor.IOPortsIfc;

public class Link implements LinkIfd, Identifiable{

	private int id;
	private IOPortsIfc left;
	private IOPortsIfc right;


	public void transmit(PacketIfc p, IOPortsIfc from){
		if (from==this.left){
			//ecrire le packet dans le sensor droit
		}
		else{
			//l'inverse
		}
	}

}	
