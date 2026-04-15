package com.quantity.controller;

import com.quantity.dto.QuantityInputDTO;
import com.quantity.dto.QuantityMeasurementDTO;
import com.quantity.models.MeasurementHistory;
import com.quantity.service.QuantityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
@RequiredArgsConstructor
@Slf4j
public class QuantityController {

    private final QuantityService quantityService;

    // ✅ Extract Username
    private String extractUsername(HttpServletRequest request) {
        String username = request.getHeader("X-Username");
        return (username != null && !username.isBlank()) ? username : "anonymous";
    }

    // ✅ Extract UserId (NEW)
    private Long extractUserId(HttpServletRequest request) {
        String userId = request.getHeader("X-User-Id");
        return (userId != null) ? Long.parseLong(userId) : null;
    }

    // ──────────────── OPERATIONS ────────────────

    @PostMapping("/convert")
    public ResponseEntity<QuantityMeasurementDTO> convert(
            @Valid @RequestBody QuantityInputDTO input,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                quantityService.convert(
                        input,
                        extractUserId(request),
                        extractUsername(request)
                )
        );
    }

    @PostMapping("/compare")
    public ResponseEntity<QuantityMeasurementDTO> compare(
            @Valid @RequestBody QuantityInputDTO input,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                quantityService.compare(
                        input,
                        extractUserId(request),
                        extractUsername(request)
                )
        );
    }

    @PostMapping("/add")
    public ResponseEntity<QuantityMeasurementDTO> add(
            @Valid @RequestBody QuantityInputDTO input,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                quantityService.add(
                        input,
                        extractUserId(request),
                        extractUsername(request)
                )
        );
    }

    @PostMapping("/add-with-target")
    public ResponseEntity<QuantityMeasurementDTO> addWithTarget(
            @Valid @RequestBody QuantityInputDTO input,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                quantityService.addWithTarget(
                        input,
                        extractUserId(request),
                        extractUsername(request)
                )
        );
    }

    @PostMapping("/subtract")
    public ResponseEntity<QuantityMeasurementDTO> subtract(
            @Valid @RequestBody QuantityInputDTO input,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                quantityService.subtract(
                        input,
                        extractUserId(request),
                        extractUsername(request)
                )
        );
    }

    @PostMapping("/subtract-with-target")
    public ResponseEntity<QuantityMeasurementDTO> subtractWithTarget(
            @Valid @RequestBody QuantityInputDTO input,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                quantityService.subtractWithTarget(
                        input,
                        extractUserId(request),
                        extractUsername(request)
                )
        );
    }

    @PostMapping("/multiply")
    public ResponseEntity<QuantityMeasurementDTO> multiply(
            @Valid @RequestBody QuantityInputDTO input,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                quantityService.multiply(
                        input,
                        extractUserId(request),
                        extractUsername(request)
                )
        );
    }

    @PostMapping("/divide")
    public ResponseEntity<QuantityMeasurementDTO> divide(
            @Valid @RequestBody QuantityInputDTO input,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                quantityService.divide(
                        input,
                        extractUserId(request),
                        extractUsername(request)
                )
        );
    }

    // ──────────────── HISTORY APIs ────────────────

    // ✅ Get all history (user-wise)
    @GetMapping("/history")
    public ResponseEntity<List<MeasurementHistory>> getHistory(HttpServletRequest request) {
        return ResponseEntity.ok(
                quantityService.getHistory(extractUserId(request))
        );
    }

    // ✅ Filter by OPERATION (UPDATED)
    @GetMapping("/history/operation/{operation}")
    public ResponseEntity<List<MeasurementHistory>> getHistoryByOperation(
            @PathVariable String operation,
            HttpServletRequest request) {

        return ResponseEntity.ok(
                quantityService.getHistoryByOperation(
                        extractUserId(request),
                        operation
                )
        );
    }
}