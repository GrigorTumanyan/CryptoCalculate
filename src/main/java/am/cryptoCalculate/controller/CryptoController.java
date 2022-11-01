package am.cryptoCalculate.controller;

import am.cryptoCalculate.dto.CryptoDto;
import am.cryptoCalculate.exception.ErrorResponse;
import am.cryptoCalculate.model.Crypto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "CryptoController", description = "The methods for working with cryptos")
public interface CryptoController {

    @Operation(summary = "The endpoint for saving a new crypto", operationId = "new crypto")
    @RequestBody(description = "Body Example", required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = CryptoDto.class)))
    @ApiResponses(value = {
            @ApiResponse(
                    description = "Crypto is successfully saved",
                    responseCode = "201",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CryptoDto.class))}),

            @ApiResponse(
                    description = "Parameter is invalid",
                    responseCode = "400",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    description = "Conflict",
                    responseCode = "409",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<CryptoDto> add();

    @Operation(summary = "The endpoint for get a crypto by id", responses = {
            @ApiResponse(
                    description = "success",
                    responseCode = "200",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CryptoDto.class))}),
            @ApiResponse(
                    description = "Record is not found",
                    responseCode = "404",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<CryptoDto> getById(@PathVariable("id") Long id);

    @Operation(summary = "The endpoint for get all cryptos")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CryptoDto.class)))),
    })
    ResponseEntity<List<CryptoDto>> getAll();

    @Operation(summary = "The endpoint for getting the min price by symbol")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CryptoDto.class))),
            @ApiResponse(
                    description = "Record is not found",
                    responseCode = "404",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<CryptoDto> getMinPriceBySymbol(@RequestParam("symbol")
                             @Parameter(name = "symbol", description = "Specify the crypto type",
                                     required = true, example = "BTC") String symbol);
    @Operation(summary = "The endpoint for getting the oldest crypto by symbol")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CryptoDto.class))),
            @ApiResponse(
                    description = "Record is not found",
                    responseCode = "404",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<CryptoDto> getOldestBySymbol(@RequestParam("symbol")
                                             @Parameter(name = "symbol", description = "Specify the crypto type",
                                                     required = true, example = "BTC") String symbol);

    @Operation(summary = "The endpoint for getting the newest crypto by symbol")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CryptoDto.class))),
            @ApiResponse(
                    description = "Record is not found",
                    responseCode = "404",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<CryptoDto> getNewestBySymbol(@RequestParam("symbol")
                                                @Parameter(name = "symbol", description = "Specify the crypto type",
                                                        required = true, example = "BTC") String symbol);

    @Operation(summary = "The endpoint for getting the max price by symbol")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CryptoDto.class))),
            @ApiResponse(
                    description = "Record is not found",
                    responseCode = "404",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<CryptoDto> getMaxPriceBySymbol(@RequestParam("symbol")
                                                  @Parameter(name = "symbol", description = "Specify the crypto type",
                                                          required = true, example = "BTC") String symbol);









    @Operation(summary = "The endpoint for getting the min price with passed symbol and date ")
    @ApiResponses(value = {
            @ApiResponse(
                    description = "success",
                    responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CryptoDto.class))),
            @ApiResponse(
                    description = "Record is not found",
                    responseCode = "404",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<CryptoDto> getMinPriceBySymbolAndGivenDate(@RequestParam("symbol")
                                                              @Parameter(name = "symbol", description = "Specify the crypto type",
                                                                      required = true, example = "BTC")
                                                              String symbol,
                                                              @RequestParam("start")
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                              @Parameter(name = "startDay", description = "Specify the start date",
                                                                      required = true, example = "2022-10-24T08:48:30.569200")
                                                              LocalDateTime start,
                                                              @RequestParam("end")
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                              @Parameter(name = "endDay", description = "Specify the end date",
                                                                      required = true, example = "2022-10-24T08:48:30.569200")
                                                              LocalDateTime end);

}
