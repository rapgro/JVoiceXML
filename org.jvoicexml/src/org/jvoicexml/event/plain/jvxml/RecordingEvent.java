/*
 * File:    $HeadURL$
 * Version: $LastChangedRevision$
 * Date:    $Date$
 * Author:  $LastChangedBy$
 *
 * JVoiceXML - A free VoiceXML implementation.
 *
 * Copyright (C) 2008-2014 JVoiceXML group - http://jvoicexml.sourceforge.net
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Library General Public
 *  License as published by the Free Software Foundation; either
 *  version 2 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Library General Public License for more details.
 *
 *  You should have received a copy of the GNU Library General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package org.jvoicexml.event.plain.jvxml;

import org.jvoicexml.event.PlainEvent;

/**
 * Event indicating the end of a recording.
 * 
 * @author Dirk Schnelle-Walka
 * @version $Revision$
 * @since 0.6
 */
public final class RecordingEvent extends PlainEvent
        implements InputEvent {
    /** The serial version UID. */
    private static final long serialVersionUID = -6541907735772622981L;

    /** The detail message. */
    public static final String EVENT_TYPE = RecordingEvent.class.getName();

    /** The output buffer of the recording. */
    private final byte[] buffer;

    /** The input result. */
    private Object result;

    /**
     * Constructs a new object.
     * 
     * @param recordingBuffer
     *            output buffer of the recording.
     */
    public RecordingEvent(final byte[] recordingBuffer) {
        buffer = recordingBuffer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }

    /**
     * Sets the input result.
     * 
     * @param inputResult
     *            new value for the input result.
     */
    public void setInputResult(final Object inputResult) {
        result = inputResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getInputResult() {
        return result;
    }

    /**
     * Retrieves the buffer of the recording.
     * 
     * @return buffer of the recording.
     */
    public byte[] getRecordingBuffer() {
        return buffer;
    }
}
