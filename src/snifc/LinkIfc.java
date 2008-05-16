/*
 * Created on 14 févr. 2004
 *
 *  Copyright (C) 2004  Stéphan Frénot, Stéphane Ubéda Département Télécom, INSA Lyon
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
 *
 */
package snifc;

import snifc.sensor.IOPortsIfc;

/**
 * 
 * Link interface represents a link in the network of sensors.
 * A link is passive, it is used by a sensor when it needs to transmit a new Packet on the network
 * and passes a pending packet to the client sensor.
 * A link connects to sensors, through a full-duplex connexion.
 * A link has a one step tranmition delay
 * 
 * Sensor --> (transmit) --> Link
 * Sensor <-- (getPendingPacket) -- Link

 * @author sfrenot
 *
 */
public interface LinkIfc extends Identifiable {
	
	/** 
	 * Transmits the packet "p" from IOPortIfc "from".   
	 * @param p PacketIfc to be tranmited
	 * @param from IOPortsIfc the packet is comming from
	 */
	public abstract void transmit(PacketIfc p, IOPortsIfc from);
	/** 
	 * Gets the next packet from the port     
	 * @param s IOPortsIfc The port which asks for the next packet
	 * @return The next PacketIfc available
	 */
	public abstract PacketIfc getPendingPacket(IOPortsIfc s);
}