package sensornetwork.sensor;
import snifc.PacketIfc;
import snifc.sensor.CaptorIfc;


public class Captor implements CaptorIfc {
	
	// Attributs de la classe Captor
	private boolean isMesureOk; 
	private PacketIfc nouveauPacket;
	//private int valeurMin, valeurMax;
	
	// Determine si une nouvelle mesure peut etre effectuee
	public void triggerCapture(){
		this.isMesureOk = false;
		if(Math.random() > 0.5){
			this.isMesureOk = true;
			this.nouveauPacket = new PacketIfc();
		}
	}
	
	
	// Genere un packet transportant une mesure
	public PacketIfc capture() throws Exception{
		if(isMesureOk = true){
			//mesure = (int)(Math.random() * (valeurMax-valeurMin)) + valeurMin;
			return this.nouveauPacket;
		}
	}

}
