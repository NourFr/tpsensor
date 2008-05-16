/*
 * Created on 16 févr. 2004
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
 */
package snifc.sensor;

import snifc.LinkIfc;
import snifc.PacketIfc;

/**
 * The IOPortsIfc represents connexion the Sensor has with the outside world (through links)
 * The IOPortIfc enables the sensor to send and receive packet through links.
 *
 * @author sfrenot -  2004
 * 
 */
public interface IOPortsIfc {
	/**
	 * That method is used to create a new connexion between the IOPorts and a link. An IOPorts
	 * belongs to the Sensor, whereas the Links is the outside world
	 * @param l the Link to connect to
	 * @throws Exception if the connexion is impossible
	 */
	public abstract void addLink(LinkIfc l) throws Exception;
	/**
	 * That methods is called by the sensor when he wants to write the next packet on the link
	 * @param p the packet to send
	 */
	public abstract void writePacket(PacketIfc p);
	/**
	 * That methods treats all incoming packets on the link. Each available packet (on each entry
	 * of the port) is enqueued on the sensor  waiting queue. If the queue is full, the packet is
	 * destroyed 
	 */
	public abstract void getPackets();
}