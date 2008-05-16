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
 */
package snifc;


/**
 * PacketIfc represent the interface for a Packet. A Packet is both Identifiable
 * - it has a unique Id- and Comparable -packets can be ordered by their ids.
 * A packet has a timeToLive attribute (TTL) that is decremented each time the packet is sent
 * over a link. If the TTL is beneath 0 the packet is deleted before beeing sent. A value of 1 for 
 * the TTL implies that the packet can move 1 time over a link.
 * 
 * @author sfrenot
 * 
 */
public interface PacketIfc extends Identifiable, Comparable  {
	/* isTimeToLiveOK, test to see if the packet is still alive
	 * @returns true if the packet is still alive, false otherwise
	 */
	public abstract boolean isTimeToLiveOK();
	/* This method is the standard overloading of Object::toString. 
	 * It is used when one call System.out.println(p);
	 * @returns if should return something like   @code <pre>return "Packet "+id+ " (From "+sourceId+")";</pre>
	 */
	public abstract String toString();
	/* That method returns a standard comparison between two objects.
	 * The code should be something like
	 * 
	 * <pre>
	 *      try {
     *    Packet p=(Packet)o;
     *   return new Integer(this.id).compareTo(new Integer(p.id));
     * }catch(ClassCastException e){
     *   throw e;
     * }
     * </pre>
     * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public abstract int compareTo(Object o);
}