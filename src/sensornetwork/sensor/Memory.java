package sensornetwork.sensor;
import snifc.PacketIfc;
import sensornetwork.Packet;
import snifc.sensor.MemoryIfc;
import java.util.Vector;

public class Memory implements MemoryIfc {

	private final int MEMORY_SIZE = 10;
	private Vector paquetsStockes;
	private int id_senseur;
	//Packet [] PacketsStockes = new Packet [MEMORY_SIZE];

	public Memory (int id_senseur){
		this.paquetsStockes = new Vector(MEMORY_SIZE);
		this.id_senseur = id_senseur;
	}

	public boolean store(PacketIfc p){
		int i;
		boolean paquetDejaLa = false;
		
		for(i=0;i<this.paquetsStockes.size();i++){
			if(p.compareTo((PacketIfc)(paquetsStockes.elementAt(i))) == 0){
				paquetDejaLa = true;
				sensornetwork.Simulator.afficheSteps.append("Packet "+((Packet)p).getId()+" droped on sensor "+this.id_senseur+"\n");
				System.out.println("Paquet "+((Packet)p).getId()+" deja present dans la memoire donc detruit\n");
			}
		}
		
		if(paquetDejaLa == false){
			
			this.paquetsStockes.add(p);
			
			if(this.paquetsStockes.size() > 10){
				this.paquetsStockes.remove(0);
				sensornetwork.Simulator.afficheSteps.append("Packet "+((Packet)this.paquetsStockes.remove(0)).getId()+" removed on sensor "+this.id_senseur+"\n");
			}else{
				this.paquetsStockes.add(p);
				sensornetwork.Simulator.afficheSteps.append("Packet "+((Packet)this.paquetsStockes.lastElement()).getId()+" stored on sensor "+this.id_senseur+"\n");
			}
			return true;
		}
		return false;
	}


	public String toString(){
		int i;
		StringBuffer sb=new StringBuffer("Sensor # ");
		sb.append(this.id_senseur);
		sb.append(" has seen packets id ");
		for (i=0;i<this.paquetsStockes.size();i++){
			if(this.paquetsStockes.get(i) != null){
				sb.append(((PacketIfc)(this.paquetsStockes.get(i))).getId());
				sb.append("-");
			}
		}
			sb.append("\n");
		return sb.toString();
	}
}
