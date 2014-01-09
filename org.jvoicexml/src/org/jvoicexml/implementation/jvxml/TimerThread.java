/*
 * File:    $HeadURL$
 * Version: $LastChangedRevision$
 * Date:    $Date$
 * Author:  $LastChangedBy$
 *
 * JVoiceXML - A free VoiceXML implementation.
 *
 * Copyright (C) 2005-2014 JVoiceXML group - http://jvoicexml.sourceforge.net
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package org.jvoicexml.implementation.jvxml;

import org.apache.log4j.Logger;
import org.jvoicexml.event.EventBus;
import org.jvoicexml.event.plain.NoinputEvent;

/**
 * Timer to send timeout events if the user did not say anything.
 *
 * @author Dirk Schnelle-Walka
 * @version $Revision$
 */
final class TimerThread
        extends Thread {
    /** Logger for this class. */
    private static final Logger LOGGER =
        Logger.getLogger(TimerThread.class);

    /** Default timeout for the timer thread. */
    private static final long DEFAULT_TIMEOUT = 30000;

    /** Flag, if the thread has been stopped. */
    private boolean stopped;

    /** The event bus to notify when the timeout expired. */
    private final EventBus eventbus;

    /** Semaphore to handle the wait/notify mechanism. */
    private final Object semaphor;

    /** Timeout to wait in msec. */
    private final long timeout;

    /**
     * Constructs a new object.
     * @param bus the event bus to notify when the timeout expired.
     * @param delay milliseconds to wait, a value <code>&lt;0</code> indicates
     *              that the default timeout should be taken.
     */
    public TimerThread(final EventBus bus, final long delay) {
        setDaemon(true);
        setName("noinput-TimerThread");

        eventbus = bus;
        if (delay > 0) {
            timeout = delay;
        } else {
            timeout = DEFAULT_TIMEOUT;
        }
        semaphor = new Object();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        LOGGER.info("timer thread started with a delay of "
                + timeout + " msec");

        try {
            synchronized (semaphor) {
                semaphor.wait(timeout);
            }
        } catch (InterruptedException ie) {
            LOGGER.error("error waiting for input timeout");
        }

        if (!stopped) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("sending no input event");
            }

            final NoinputEvent event = new NoinputEvent();
            eventbus.publish(event);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("timer thread terminated");
        }
    }

    /**
     * Stops this timer thread.
     */
    public void stopTimer() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("stopping timer...");
        }

        if (eventbus != null) {
            stopped = true;
        }

        synchronized (semaphor) {
            semaphor.notifyAll();
        }
    }
}
