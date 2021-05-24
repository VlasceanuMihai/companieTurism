package com.CompanieTurism.requests.flight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BaseFlightRequest {

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String airportDeparture;

    @NotNull
    @NotBlank
    @FutureOrPresent
    private LocalDateTime dateOfDeparture;

    @NotNull
    @NotBlank
    @Size(max = 50)
    private String airportArrival;

    @NotNull
    @NotBlank
    @FutureOrPresent
    private LocalDateTime dateOfArrival;

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String company;
}
