package snifc.sensor;
import snifc.PacketIfc;

public interface MemoryIfc {

	private final int MEMORY_SIZE = 10;
	Packet [] PacketsStockes = new Packet [MEMORY_SIZE];

	public abstract boolean store(PacketIfc p){
		int i;
		boolean packetDejaLa = false;
		
		for(i=0;i<MEMORY_SIZE;i++){
			if(p.compareTo(PacketsStockes[i] == 0){
				packetDejaLa = true;
			}
		}
		
		if(packetDejaLa == false){
			for(i=MEMORY_SIZE-1;i=1;i--){
				PacketsStockes[i] = PacketsStockes[i-1];
			}
			PacketsStockes[0] = p;
		}

	}


	public abstract String toString(){
		StringBuffer sb=new StringBuffer("Sensor # ");
		sb.append(this.sensor.id);
		sb.append(" has seen packets id ");
		for (i=0;i<MEMORY_SIZE;i++){
			if(PacketsStockes[i] != null){
				sb.append(PacketsStockes[i].getId());
				sb.append("-");
			}
		}
		return sb.toString();
	}
}
