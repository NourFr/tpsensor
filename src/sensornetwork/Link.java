package sensornetwork;
import snifc.sensor.IOPortsIfc;
import snifc.LinkIfc;
import java.util.Vector;
import snifc.Identifiable;
import snifc.PacketIfc;

public class Link implements LinkIfc, Identifiable{

	private int id;
	private IOPortsIfc left;
	private IOPortsIfc right;
	private PacketIfc fromLeft;
	private PacketIfc fromRight;


	public Link (Link l){
		this.id=l.id;
		this.left=l.left;
		this.right=l.right;
		this.fromLeft=l.fromLeft;
		this.fromRight=l.fromRight;
	}	
	
	public Link (IOPortsIfc left_sensor, IOPortsIfc right_sensor, int id){
		this.id = id;
		this.left = left_sensor;
		this.right = right_sensor;
	}

	public void transmit(PacketIfc p, IOPortsIfc from){
		
		if(this.left == from){	
			fromLeft = p;
		}else{
			fromRight = p;
		}	
	}

	public PacketIfc getPendingPacket(IOPortsIfc s){
		
		if(this.left == s){	
			PacketIfc p;
			p=fromRight;
			fromRight=null;
			return (p);
		}else{
			PacketIfc p;
			p=fromLeft;
			fromLeft=null;
			return (p);			
			//return (fromLeft);
		}	
	}

	public int getId(){
		return this.id;
	}	

}	
