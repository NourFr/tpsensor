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

import snifc.PacketIfc;

/**
 *
 *  The memory interface represents the memory of the Sensor. The sensor memory  has two
 * behaviors. It can store <b>all</b> new incoming packets, but it can only remember 
 * MEMORY_SIZE packet. Every new packet (not in memory)  is stored in memory. When it is 
 * stored, the oldest packet is removed from memory (so packets are forgotten)
 *  
 * @author sfrenot
 * 
 */
public interface MemoryIfc {
    /**
     * The sensor asks the memory to store packet p. If the packet is already in memory
     * the packet is not stored and the method returns false, else the method return true. 
     * 
     * TODO : the packet should be made younger in the memory stack.
     * @param p
     * @return false is the packet has not been stored in memory, else otherwise 
     */
	public abstract boolean store(PacketIfc p);
	/**
	 * The toString method is called by the simulator to display statistical results of the simulation. 
	 * @return the string representing the result.
	 * It shoudl returns something like
	 * <pre>
	 *   SortedSet ss=new TreeSet(allPackets); 
	 *	 StringBuffer sb=new StringBuffer("Sensor # ");
	 *	 sb.append(this.sensor.id);
	 *	 sb.append(" has seen packets id ");
	 *	 for (Iterator i=ss.iterator(); i.hasNext();) {
	 *	 	sb.append(((Packet)i.next()).getId());
	 * 	 	sb.append("-");
	 *	 }
	 *	 return sb.toString();
	 * }
	 * </pre>
	 */
	public abstract String toString();
}