/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 1998-1999 IBM Corp. All rights reserved.
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

package javax.rmi.CORBA.serialization;

import java.util.*;
import java.io.*;

public class OBVTestObjectCustomImpl extends OBVTestObjectCustom {
    private static Random r = new Random();
    
    // We mark these as transients just so we can make sure during the test that
    // only by using the marshal and unmarshal methods could this class be serialized.
    private transient int fInt;
    private transient long fLong;
    private transient float fFloat;
    private transient double fDouble;
    private transient String fString;
    
    OBVTestObjectCustomImpl(int aInt, long aLong, float aFloat, double aDouble, String aString)
    {
        fInt = aInt;
        fLong = aLong;
        fFloat = aFloat;
        fDouble = aDouble;
        fString = aString;
    }

    public OBVTestObjectCustomImpl()
    {
        fInt = r.nextInt();
        fLong = r.nextLong();
        fFloat = r.nextFloat();
        fDouble = r.nextDouble();
        fString = new String(fInt +""+ fLong +""+ fFloat +""+ fDouble);
    }

    OBVTestObjectCustomImpl(org.omg.CORBA.portable.InputStream is)
    {
        // for unmarshalling only, called by the factory
    }

    public boolean equals(Object o)
    {
        try
            {
                OBVTestObjectCustomImpl ctbo = (OBVTestObjectCustomImpl)o;
                return ((ctbo.fString.equals(fString)) && (ctbo.fInt == fInt) &&
                        (ctbo.fLong == fLong) && (ctbo.fFloat == fFloat) && (ctbo.fDouble == fDouble)
                        );
            }
        catch(Exception e)
            {
                return false;
            }
    }

    public String toString()
    {
        return new String("fInt="+fInt+"; fLong="+fLong+"; fFloat="+fFloat+"; fDouble="+fDouble+"; fString="+fString);
    }

    public void marshal (org.omg.CORBA.DataOutputStream os)
    {
        os.write_long(fInt);
        os.write_longlong(fLong);
        os.write_float(fFloat);
        os.write_double(fDouble);
        os.write_string(fString);
    }
 
    public void unmarshal (org.omg.CORBA.DataInputStream is)
    {
        fInt = is.read_long();
        fLong = is.read_longlong();
        fFloat = is.read_float();
        fDouble = is.read_double();
        fString = is.read_string();

    }
}
