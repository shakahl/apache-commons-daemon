/*
 *  Copyright 2001-2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.commons.daemon;

/**
 * This interface provides support for native daemon invocation. Using
 * a platform dependant helper program classes that implement the
 * <code>Daemon</code> interface can be initialized, started and
 * stopped according to the convensions of the underlying operating
 * system.
 * <p>
 * Implementors of this interface must also provide a public constructor
 * with no arguments so that instances can be created in an automated
 * fashion.
 * </p>
 * @author Pier Fumagalli
 * @author Copyright &copy; 2000-2001 <a href="http://www.apache.org/">The
 *         Apache Software Foundation</a>. All rights reserved.
 * @version 1.0 <i>(CVS $Revision$)</i>
 */
public interface Daemon {

    /**
     * Initialize this <code>Daemon</code> instance.
     * <p>
     *   This method gets called once the JVM process is created and the
     *   <code>Daemon</code> instance is created thru its empty public
     *   constructor.
     * </p>
     * <p>
     *   Under certain operating systems (typically Unix based operating
     *   systems) and if the native invocation framework is configured to do
     *   so, this method might be called with <i>super-user</i> privileges.
     * </p>
     * <p>
     *   For example, it might be wise to create <code>ServerSocket</code>
     *   instances within the scope of this method, and perform all operations
     *   requiring <i>super-user</i> privileges in the underlying operating
     *   system.
     * </p>
     * <p>
     *   Apart from set up and allocation of native resources, this method
     *   must not start the actual operation of the <code>Daemon</code> (such
     *   as starting threads calling the <code>ServerSocket.accept()</code>
     *   method) as this would impose some serious security hazards. The
     *   start of operation must be performed in the <code>start()</code>
     *   method.
     * </p>
     *
     * @param context A <code>DaemonContext</code> object used to
     * communicate with the container.
     * 
     * @exception Exception Any exception preventing a successful
     *                      initialization.
     */
    public void init(DaemonContext context)
    throws Exception;

    /**
     * Start the operation of this <code>Daemon</code> instance. This
     * method is to be invoked by the environment after the init()
     * method has been successfully invoked and possibly the security
     * level of the JVM has been dropped.  <code>Implementors of this
     * method are free to start any number of threads, but need to
     * return control avfter having done that to enable invocation of
     * the stop()-method.
     */
    public void start()
    throws Exception;

    /**
     * Stop the operation of this <code>Daemon</code> instance. Note
     * that the proper place to free any allocated resources such as
     * sockets or file descriptors is in the destroy method, as the
     * container may restart the Daemon by calling start() after
     * stop().
     */
    public void stop()
    throws Exception;

    /**
     * Free any resources allocated by this daemon such as file
     * descriptors or sockets. This method gets called by the container
     * after stop() has been called, before the JVM exits. The Daemon
     * can not be restarted after this method has been called without a
     * new call to the init() method.
     */
    public void destroy();
}
