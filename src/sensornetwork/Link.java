package sensornetwork;
import snifc.sensor.IOPortsIfc;
import snifc.LinkIfc;
import java.util.Vector;

public class Link implements LinkIfc, Identifiable{

	private final int PACKET_COUNT = 10;
	private int id;
	private IOPortsIfc left;
	private IOPortsIfc right;
	private Vector packets;
	private Vector ports;


	public Link (IOPortsIfc left_sensor, IOPortsIfc right_sensor, int id){
		this.id = id;
		this.packets = new Vector(PACKET_COUNT);
		this.left = left_sensor;
		this.right = right_sensor;
	}

	public void transmit(PacketIfc p, IOPortsIfc from){
		
		if(this.packets.size() <= PACKET_COUNT){
			// On ajoute le paquet et on dit d'ou il vient
			this.packets.add(p);
			this.ports.add(from);
		}else{
			System.out.println("Le link est plein\n");
		}	
	}

	public PacketIfc getPendingPacket(IOPortsIfc s){
		int index;
		index = this.ports.indexOf(s);
		if(index != -1){
			return this.packets.removeElementAt(index);
		}else{
			System.out.println("Pas de packet à récupérer\n");
			return null;
		}
	}

	public int getId(){
		return this.id;
	}	

}	
