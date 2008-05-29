package snifc;


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
	
	public int compareTo(java.lang.Object o){
		try{
			return new Integer(this.id).compareTo(new Integer((Packet)o.id));
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
