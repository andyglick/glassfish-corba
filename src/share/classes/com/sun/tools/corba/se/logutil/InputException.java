/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2008-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 *
 * Contributor(s):
 *
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
package com.sun.tools.corba.se.logutil;

import java.util.LinkedList;
import java.util.Queue;

public class InputException {

  /**
   * The name of this exception.
   */
  private final String name;

  /**
   * The codes associated with this exception.
   */
  private final Queue<InputCode> codes;

  /**
   * Constructs a new {@link InputException} with the
   * specified name.
   *
   * @param name the name of the new exception;
   */
  public InputException(final String name) {
    this.name = name;
    codes = new LinkedList<InputCode>();
  }

  /**
   * Adds a new code to this exception.
   *
   * @param c the code to add.
   */
  public void add(InputCode c)
  {
    codes.offer(c);
  }

  /**
   * Returns the name of this exception.
   *
   * @return the exception's name.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the codes associated with this exception.
   *
   * @return the exception's codes.
   */
  public Queue<InputCode> getCodes() {
    return codes;
  }

  /**
   * Returns a textual representation of this exception.
   *
   * @return a textual representation.
   */
  public String toString() {
    return getClass().getName()
      + "[name=" + name
      + ",codes=" + codes
      + "]";
  }

}