/*
 * Created on 13 févr. 2004
 *
  * Copyright (C) 2004  Stéphan Frénot, Stéphane Ubéda Département Télécom, INSA Lyon
 *
 * This program is licensed under the Apache Software License
 * version 1.1; refer to the ASL-LICENSE.txt file included with
 * this program for details.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * Contact: Stephane Frenot, stephane.frenot@insa-lyon.fr
 * Contributor(s): Stephane Frenot, Stéphane Ubéda
 *
 */
package snifc.sensor;

/**
  *
 * A sensor interface, represents the interface of a sensor. A sensor is the main part of 
 * the network of sensor. It is composed of a Memory, a Queue , a Captor and an IOPorts.
 * 
 * It is a container for all these elements. The simulator triggers hooks methods of the sensor,
 * and each method is forwarded to the correspond sensor's element.
 * 
 * All hook methods are called in that order in each step of the simulation
 * - simulateCapture()
 * - activateCaptor()
 * - activatePort()
 * - activateQueue()
 *
 *  
 * - toString() method is called for statistical purpose
 *
 * @author sfrenot
 * @see snifc.sensor.MemoryIfc
 * @see snifc.sensor.QueueIfc
 * @see snifc.sensor.CaptorIfc
 * @see snifc.sensor.IOPortsIfc
 * 
 * */
public interface SensorIfc {
	/**
	 * simulateCapture, triggers a randomizer that position a boolean to know if the captor generates
	 * a new capture or not. The method is forwarded to the captor. 
	 */
	public abstract void simulateCapture();
	/**
	 * activateCaptor is forwarded to the inner captor
	 * 
	 */
	public abstract void activateCaptor();
    /**
     * The incoming packets are queued after the captor. Every packet on each link is get and 
     * eventually put in the queue.  
     *
     */
	public abstract void activatePort();
	/**
	 * That method simulates the main function of the sensor. The activateQueue concretize the
	 * following actions. 
	 * It deQueues the next Packet,  (@see QueueIfc#deQueue()
	 * it tries to store the packet, (@see Memory#store(PacketIfc)
	 * if the packet is valid, it is  transmited of the links (@see IOPorts#writePacket(Packet)
	 *
	 */
	public abstract void activateQueue();
	
	/**
	 * That method is called only in the setup phase. 
	 * @return the IOPorts representing that Sensor
	 */
	public abstract IOPortsIfc getPort();
}