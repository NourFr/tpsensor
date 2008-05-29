package snifc.sensor;

import snifc.Link;
import snifc.Packet;
import java.util.Vector;

public class IOPorts implements IOPortsIfc{

	private Vector links;
	private final int LINKS_SIZE = 10;


	public IOPorts(){
		links = new Vector(LINKS_SIZE);
	}

	public void addLink(Link l) throws Exception {
		if(links.size() >= LINKS_SIZE){
			throw new Exception("Vous avez depace la capacite du IOPort\n");
		}else{	
			this.links.add(l)
		}
	}

	public void writePacket(Packet p){
		Link l;
		int i;

		if(p.isTimeToLiveOK()){
			for(i=0; i<this.links.size(); i++){
				l=(Link)this.links.elementAt(i);
				if(l!=null){
					l.transmit(p, this);
				}
			}
		}
	}

	public void getPackets(){
		Link l;
		int i;

		for(i=0; i<this.links.size(); i++){
			l=(Link)this.links.elementAt(i);
			if(l!=null){
				Packet p = getPendingPacket(this);
				if(p!=null){
					super.queue.enQueue(p);
				}
			}
		}
	}	

}	
