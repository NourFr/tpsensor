/*
 * Created on 13 f�vr. 2004
 * 
 *  Copyright (C) 2004  St�phan Fr�not, St�phane Ub�da D�partement T�l�com, INSA Lyon
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
 * Contributor(s): Stephane Frenot, St�phane Ub�da
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
