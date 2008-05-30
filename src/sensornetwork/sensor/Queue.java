import java.util.Vector;
import snifc.sensor.QueueIfc;
import snifc.PacketIfc;

public class Queue extends Vector implements QueueIfc{

	// Definition de la constante de taille max de la file
	private final int QUEUE_SIZE = 10;
	
	// Constructeur de la file qui la crée direct avec une taille max
	public Queue(){
		super(QUEUE_SIZE);
	}	

	// Ajout d'un element dans la file
	public void enQueue(PacketIfc p){
		if(super.size() <= QUEUE_SIZE){
			super.add(p);
		}else{
			return null;
		}	
	}

	// Retrait d'un element 
	public PacketIfc deQueue(){
		try{
			return super.remove(0);
		}	
		catch(ArrayINdexOutOfBoundsException e){/*impossible*/
		}
		
	}	
		
}		
