package org.opensmartgridplatform.dto.valueobjects.smartmetering;

import java.io.Serializable;

public class AdministrativeStatusDto implements Serializable {

	private static final long serialVersionUID = -6238335091357924447L;

	private final AdministrativeStatusTypeDto administrativeStatusTypeDto;

    private final String message;

    public AdministrativeStatusDto(final AdministrativeStatusTypeDto administrativeStatusTypeDto, final String message) {
        this.administrativeStatusTypeDto = administrativeStatusTypeDto;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("AdministrativeStatusTypeAndMessage[%s %s]", this.administrativeStatusTypeDto, this.message);
    }

    public AdministrativeStatusTypeDto getAdministrativeStatusTypeDto() {
        return this.administrativeStatusTypeDto;
    }

    public String getMessage() {
        return this.message;
    }

}
