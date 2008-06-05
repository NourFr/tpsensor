package sensornetwork.sensor;

import snifc.LinkIfc;
import snifc.PacketIfc;
import snifc.sensor.QueueIfc;
import sensornetwork.sensor.Queue;
import sensornetwork.Packet;
import sensornetwork.Link;
import snifc.sensor.IOPortsIfc;
import java.util.Vector;

public class IOPorts implements IOPortsIfc{

	private final int LINKS_SIZE = 10;
	private Vector links;
	private QueueIfc queue;

	public IOPorts(QueueIfc sensorQueue){
		this.queue = new Queue();
		this.queue = sensorQueue;
		this.links = new Vector(LINKS_SIZE);
	}

	public void addLink(LinkIfc l) throws Exception {
		if(links.size() >= LINKS_SIZE){
			throw new Exception("Vous avez depace la capacite du IOPort\n");
		}else{	
			this.links.add(l);
		}
	}

	
	public void writePacket(PacketIfc p){
		LinkIfc l;
		int i;

		if(p.isTimeToLiveOK()){
			for(i=0; i<this.links.size(); i++){
				l=(LinkIfc)this.links.elementAt(i);
				if(l!=null){
					l.transmit(p, this);
					sensornetwork.Simulator.afficheSteps.append("Packet "+((Packet)p).getId()+" transmited on link "+((Link)l).getId()+"\n");
				}
			}
		}
	}

	public void getPackets(){
		LinkIfc l;
		int i;
		boolean paquetConnu=false;

		for(i=0; i<this.links.size(); i++){
			l=(LinkIfc)this.links.elementAt(i);
			if(l!=null){
				PacketIfc p = l.getPendingPacket(this);
				if(p!=null){
					System.out.println("Packet "+((Packet)p).getId()+" recupere\n");
					for(i=0; i<((Queue)this.queue).size(); i++){
						if(((Packet)((Queue)this.queue).elementAt(i)).compareTo(p)==0){
							paquetConnu=true;
						}
					}	
					if(paquetConnu==true){
						System.out.println("Paquet "+((Packet)p).getId()+" deja connu donc detruit\n");
									/*	try{Thread.sleep(10000);}
								catch(Exception e){
					e.printStackTrace();
				}*/
					}else{	
						PacketIfc pbis = new Packet(p);
						this.queue.enQueue(pbis);
					}
				}
			}
		}
	}	

}	
