/*
 * Created on 13 févr. 2004
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
 * */
package snifc;

/**
 *  Identifiable interface enables the system to name entities of the sensor network
 *  Entities should be for instance : Sensor, Link...  
 * 
 *  @author sfrenot -  2004
 * 
 */
public interface Identifiable {
  public int getId();
}
