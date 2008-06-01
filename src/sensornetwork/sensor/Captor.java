package sensornetwork.sensor;
import snifc.PacketIfc;
import snifc.sensor.CaptorIfc;
import sensornetwork.Packet;

public class Captor implements CaptorIfc {
	
	// Attributs de la classe Captor
	private boolean isMesureOk; 
	private PacketIfc nouveauPacket;
	private int idSensor;
	private int idPacket = 0;
	private static int TTL_SIZE = 10;
	//private int valeurMin, valeurMax;
	
	public Captor(int idSensor){
		this.idSensor = idSensor;
	}	


	// Determine si une nouvelle mesure peut etre effectuee
	public void triggerCapture(){
		this.isMesureOk = false;
		if(Math.random() > 0.5){
			this.isMesureOk = true;
			System.out.println("Nouvelle mesure ecrite dans le packet "+(idSensor+idPacket+1)+"\n");
			this.nouveauPacket = new Packet(idSensor + ++idPacket, TTL_SIZE);

		}
	}
	
	
	// Genere un packet transportant une mesure
	public PacketIfc capture() throws Exception{
		System.out.println("DEBUG isMesureOk "+this.isMesureOk+"\n");
		if(this.isMesureOk == true){
			System.out.println("Packet "+this.nouveauPacket.getId()+" genere sur le sensor "+idSensor+"\n");
			//mesure = (int)(Math.random() * (valeurMax-valeurMin)) + valeurMin;
			return this.nouveauPacket;
		}else{
			throw new Exception("Pas de mesure generee\n");
		}	
	}

}
