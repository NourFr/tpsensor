package sensornetwork;
import snifc.PacketIfc;
import snifc.Identifiable;

public class Packet implements PacketIfc, Identifiable, java.lang.Comparable {

	private int id;
	private int ttl;
	
	/*private int mesure;

	public int getMesure(){
		return this.mesure;
	}

	public void setMesure(int newMesure){
		this.mesure=newMesure;
	}*/	

	public Packet (int id, int ttl){
		this.id = id;
		this.ttl = ttl;
	}	
	
	public Packet (Packet p){
		this.id = p.id;
		this.ttl = p.ttl -1;
	}

	public int compareTo(java.lang.Object o){
		try{
			Packet p=(Packet)o;
			return new Integer(this.id).compareTo(new Integer(p.id));
		}catch(ClassCastException e){
			throw e;
		}
	}	

	public boolean isTimeToLiveOK(){
		if(this.ttl > 0){
			return true;
		}else{
			return false;
		}
	}

	public String toString(){
		return "Packet "+this.id+" ttl = "+this.ttl;
	}

	public int getId(){
		return this.id;
	}	

}
