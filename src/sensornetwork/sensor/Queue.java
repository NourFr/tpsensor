package sensornetwork.sensor;
import java.util.Vector;
import snifc.sensor.QueueIfc;
import snifc.PacketIfc;
import sensornetwork.Packet;

public class Queue extends Vector implements QueueIfc{

	// Definition de la constante de taille max de la file
	private static int QUEUE_SIZE = 10;
	
	// Constructeur de la file qui la crée direct avec une taille max
	public Queue(){
		super(Queue.QUEUE_SIZE);
	}	

	// Ajout d'un element dans la file
	public void enQueue(PacketIfc p){
		if(this.size() <= QUEUE_SIZE){
			this.add(p);
			System.out.println("Le packet " + ((Packet)p).getId() + " a ete ajoute a la queue\n");
		}else{
			System.out.println("la file d'attente du sensor est pleine\n");
		}	
	}

	// Retrait d'un element 
	public PacketIfc deQueue(){
		try{
			return (PacketIfc)this.remove(0);
		}	
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Pas de packet present dans la file\n");
		}
		return null;	
	}	
		
}		
