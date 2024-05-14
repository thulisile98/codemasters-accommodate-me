package com.codemasters.accommodateme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDTO {
    private Integer locationId;

    private String city;
    private String province;
    private String area;
    private String streetName;
    private int zipCode;
    private int streetNumber;
    private String Residence;
}
