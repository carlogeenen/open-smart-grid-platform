/**
 * Copyright 2019 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package org.opensmartgridplatform.simulator.protocol.iec60870.server;

import org.openmuc.j60870.TypeId;

public class Iec60870ASduHandlerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final TypeId typeId;

    public Iec60870ASduHandlerNotFoundException(final TypeId typeId) {
        super();
        this.typeId = typeId;
    }

    public TypeId getTypeId() {
        return this.typeId;
    }
}
