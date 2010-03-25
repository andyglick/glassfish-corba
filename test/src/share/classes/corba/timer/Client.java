/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2006-2007 Sun Microsystems, Inc. All rights reserved.
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

package corba.timer ;

import java.util.Iterator ;
import java.util.Properties ;
import java.util.Map ;


import org.testng.Assert ;
import org.testng.annotations.Test ;
import org.testng.annotations.ExpectedExceptions ;

import com.sun.corba.se.spi.orb.ORB ;

import com.sun.corba.se.spi.orbutil.newtimer.Statistics ;
import com.sun.corba.se.spi.orbutil.newtimer.StatsEventHandler ;
import com.sun.corba.se.spi.orbutil.newtimer.Timer ;
import com.sun.corba.se.spi.orbutil.newtimer.TimerManager ;
import com.sun.corba.se.spi.orbutil.newtimer.TimerEventController ;
import com.sun.corba.se.spi.orbutil.newtimer.TimerFactory ;
import com.sun.corba.se.spi.orbutil.newtimer.TimerFactoryBuilder ;

import com.sun.corba.se.impl.orbutil.newtimer.VersionedHashSet ;
import com.sun.corba.se.impl.orbutil.newtimer.generated.TimingPoints ;

import com.sun.corba.se.spi.orbutil.ORBConstants ;

import corba.framework.TestngRunner ;

// import corba.framework.TimerUtils ;


public class Client {
    // Test TimerFactoryBuilder
    @Test()
    public void factoryBuilderCreate1() {
	String name = "TF1" ;
	String description = "First Test Factory" ;
	TimerFactory tf = TimerFactoryBuilder.make( name, description ) ;
	Assert.assertEquals( tf.name(), name ) ;
	Assert.assertEquals( tf.description(), description ) ;
	TimerFactoryBuilder.destroy( tf ) ;
    }

    @Test()
    @ExpectedExceptions( { IllegalArgumentException.class } )
    public void factoryBuilderCreate2() {
	String name = "TF1" ;
	String description = "First Test Factory" ;
	TimerFactory tf = TimerFactoryBuilder.make( name, description ) ;
	try {
	    tf = TimerFactoryBuilder.make( name, description ) ;
	} finally {
	    TimerFactoryBuilder.destroy( tf ) ;
	}
    }

    @Test()
    public void factoryBuilderCreate3() {
	String name = "TF1" ;
	String description = "First Test Factory" ;
	TimerFactory tf = TimerFactoryBuilder.make( name, description ) ;
	TimerFactoryBuilder.destroy( tf ) ;
	tf = TimerFactoryBuilder.make( name, description ) ;
	TimerFactoryBuilder.destroy( tf ) ;
    }

    @Test()
    public void testVersionedHashSet() {
	String[] data = { "red", "orange", "yellow", "green", "blue", "indigo", "violet" } ;
	String toRemove1 = "green" ;
	String toRemove2 = "blue" ;

	VersionedHashSet<String> tset = new VersionedHashSet<String>() ;
	long version = tset.version() ;

	// test add
	for (String str : data)
	    tset.add( str ) ;
	Assert.assertTrue( version != tset.version() ) ;
	version = tset.version() ;

	// Add that does not change set does not change version
	tset.add( toRemove1 ) ;
	Assert.assertEquals( version, tset.version() ) ;
	
	// test direct remove
	tset.remove( toRemove1 ) ;	
	Assert.assertTrue( version != tset.version() ) ;
	version = tset.version() ;

	// Remove that does not change set does not change version
	tset.remove( toRemove1 ) ;	
	Assert.assertEquals( version, tset.version() ) ;

	// test iterator without remove
	String rainbow = "" ;
	for (String str : tset)
	    rainbow += " " + str ;

	Assert.assertEquals( version, tset.version() ) ;

	// test iterator with remove
	Iterator<String> iter = tset.iterator() ;
	while (iter.hasNext()) {
	    String str = iter.next() ;
	    if (str.equals( toRemove2 )) 
		iter.remove() ;
	}

	Assert.assertTrue( version != tset.version() ) ;
    }

    private static void sleep( int time ) {
	try {
	    Thread.sleep( time ) ;
	} catch (Exception exc) {
	    // ignore it 
	}
    }

    private void recordCall( TimingPoints tp, Timer top, 
	TimerEventController controller, int transportDelay ) {
	
	controller.enter( top ) ;

	tp.enter_hasNextNext() ;
	sleep( 1 ) ;
	tp.exit_hasNextNext() ;

	tp.enter_connectionSetup() ;
	sleep( 4 ) ;
	tp.exit_connectionSetup() ;

	tp.enter_clientEncoding() ;
	sleep( 100 ) ;
	tp.exit_clientEncoding() ;

	tp.enter_clientTransportAndWait() ;
	sleep( transportDelay ) ;
	tp.exit_clientTransportAndWait() ;

	tp.enter_clientDecoding() ;
	sleep( 40 ) ;
	tp.exit_clientDecoding() ;

	controller.exit( top ) ;
    }

    Map<Timer,Statistics> makeData() {
	// Setup timing points and a top-level timer
	Properties props = new Properties() ;
	props.setProperty( ORBConstants.TIMING_POINTS_ENABLED,
	    "true" ) ;
	props.setProperty( "org.omg.CORBA.ORBClass", 
	    "com.sun.corba.se.impl.orb.ORBImpl" ) ;
	ORB orb = (ORB)ORB.init( new String[0], props ) ;
	try {
	    TimerManager<TimingPoints> tm = orb.getTimerManager() ;
	    TimingPoints tp = tm.points() ;
	    TimerFactory tf = tm.factory() ;
	    TimerEventController controller = tm.controller() ;
	    Timer top = tf.makeTimer( "top", "Encloses the entire operation" ) ;

	    StatsEventHandler handler = tf.makeStatsEventHandler( "TestStats" ) ;
	    controller.register( handler ) ;
	    handler.clear() ;

	    tp.transport().enable() ;
	    top.enable() ;

	    // Simulate the actions of the ORB client transport 
	    recordCall( tp, top, controller, 25 ) ;
	    recordCall( tp, top, controller, 31 ) ;
	    recordCall( tp, top, controller, 27 ) ;
	    recordCall( tp, top, controller, 42 ) ;
	    recordCall( tp, top, controller, 19 ) ;
	    recordCall( tp, top, controller, 21 ) ;
	    recordCall( tp, top, controller, 23 ) ;
	    recordCall( tp, top, controller, 25 ) ;
	    recordCall( tp, top, controller, 34 ) ;
	    recordCall( tp, top, controller, 33 ) ;
	    recordCall( tp, top, controller, 31 ) ;
	    recordCall( tp, top, controller, 28 ) ;
	    recordCall( tp, top, controller, 27 ) ;
	    recordCall( tp, top, controller, 29 ) ;
	    recordCall( tp, top, controller, 30 ) ;
	    recordCall( tp, top, controller, 31 ) ;
	    recordCall( tp, top, controller, 28 ) ;

	    return handler.stats() ;
	} finally {
	    orb.destroy() ;
	}
    }

    @Test()
    void generateStatsTable() {
	Map<Timer, Statistics> data = makeData() ;

	// TimerUtils.writeHtmlTable( data, "TimerTest.html", 
	    // "Client Test Timing Data" ) ;
    }

    public static void main( String[] args ) {
        TestngRunner runner = new TestngRunner() ;
        runner.registerClass( TimerPointsSuite.class ) ;
        runner.registerClass( Client.class ) ;
        runner.registerClass( NamedBaseSuite.class ) ;
        runner.registerClass( ControllableBaseSuite.class) ;
        runner.registerClass( TimerFactorySuite.class) ;
        runner.registerClass( StatsEventHandlerSuite.class) ;
        runner.registerClass( LogEventHandlerSuite.class) ;
        runner.registerClass( ActivationSuite.class ) ;
        runner.run() ;
        runner.systemExit() ;
    }
}
