/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package org.omg.CORBA;

/**
 * The representation of a <code>DynAny</code> object that is associated
 *  with an IDL struct.
 * @deprecated Use the new <a href="../DynamicAny/DynStruct.html">DynStruct</a> instead
 */

// @Deprecated
public interface DynStruct extends org.omg.CORBA.Object, org.omg.CORBA.DynAny
{
    /**
     * During a traversal, returns the name of the current member.
     *
     * @return the string name of the current member
     */
    public String current_member_name();

    /**
     * Returns the <code>TCKind</code> object that describes the kind of
         * the current member.
     *
     * @return the <code>TCKind</code> object that describes the current member
     */
    public org.omg.CORBA.TCKind current_member_kind();

    /**
     * Returns an array containing all the members of the stored struct.
     *
     * @return the array of name-value pairs
         * @see #set_members
     */
    public org.omg.CORBA.NameValuePair[] get_members();

    /**
     * Set the members of the struct.
     *
     * @param value the array of name-value pairs.
         * @throws org.omg.CORBA.DynAnyPackage.InvalidSeq if the given argument
         *         is invalid
         * @see #get_members
     */
    public void set_members(org.omg.CORBA.NameValuePair[] value)
        throws org.omg.CORBA.DynAnyPackage.InvalidSeq;
}
