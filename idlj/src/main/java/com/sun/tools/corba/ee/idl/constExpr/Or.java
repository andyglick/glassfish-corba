/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 1997-1999 IBM Corp. All rights reserved.
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

package com.sun.tools.corba.ee.idl.constExpr;

// NOTES:

import com.sun.tools.corba.ee.idl.Util;

import java.math.BigInteger;

public class Or extends BinaryExpr
{
  protected Or (com.sun.tools.corba.ee.idl.constExpr.Expression leftOperand, com.sun.tools.corba.ee.idl.constExpr.Expression rightOperand)
  {
    super ("|", leftOperand, rightOperand);
  } // ctor

  public Object evaluate () throws com.sun.tools.corba.ee.idl.constExpr.EvaluationException
  {
    try
    {
      Number l = (Number)left ().evaluate ();
      Number r = (Number)right ().evaluate ();

      if (l instanceof Float || l instanceof Double || r instanceof Float || r instanceof Double)
      {
        String[] parameters = {Util.getMessage ("EvaluationException.or"), left ().value ().getClass ().getName (), right ().value ().getClass ().getName ()};
        throw new com.sun.tools.corba.ee.idl.constExpr.EvaluationException(Util.getMessage ("EvaluationException.1", parameters));
      }
      else
      {
        // or
        //daz        value (new Long (l.longValue () | r.longValue ()));
        BigInteger uL = (BigInteger)toUnsigned((BigInteger)l);
        BigInteger uR = (BigInteger)toUnsigned((BigInteger)r);
        value((BigInteger)coerceToTarget(uL.or (uR)));
      }
    }
    catch (ClassCastException e)
    {
      String[] parameters = {Util.getMessage ("EvaluationException.or"), left ().value ().getClass ().getName (), right ().value ().getClass ().getName ()};
      throw new com.sun.tools.corba.ee.idl.constExpr.EvaluationException(Util.getMessage ("EvaluationException.1", parameters));
    }
    return value ();
  } // evaluate
} // class Or
