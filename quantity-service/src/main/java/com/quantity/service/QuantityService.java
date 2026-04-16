package com.quantity.service;

import com.quantity.dto.QuantityInputDTO;
import com.quantity.dto.QuantityMeasurementDTO;
import com.quantity.models.MeasurementHistory;
import com.quantity.repository.MeasurementHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuantityService {

    private final QuantityCalculationService calcService;
    private final MeasurementHistoryRepository historyRepository;

    public QuantityMeasurementDTO convert(QuantityInputDTO input,
                                          Long userId,
                                          String username) {

        QuantityMeasurementDTO result = calcService.convert(input);
        saveHistory(input, result, userId, username);
        return result;
    }

    public QuantityMeasurementDTO compare(QuantityInputDTO input,
                                          Long userId,
                                          String username) {

        QuantityMeasurementDTO result = calcService.compare(input);
        saveHistory(input, result, userId, username);
        return result;
    }

    public QuantityMeasurementDTO add(QuantityInputDTO input,
                                      Long userId,
                                      String username) {

        QuantityMeasurementDTO result = calcService.add(input);
        saveHistory(input, result, userId, username);
        return result;
    }

    public QuantityMeasurementDTO addWithTarget(QuantityInputDTO input,
                                                Long userId,
                                                String username) {

        QuantityMeasurementDTO result = calcService.addWithTarget(input);
        saveHistory(input, result, userId, username);
        return result;
    }

    public QuantityMeasurementDTO subtract(QuantityInputDTO input,
                                           Long userId,
                                           String username) {

        QuantityMeasurementDTO result = calcService.subtract(input);
        saveHistory(input, result, userId, username);
        return result;
    }

    public QuantityMeasurementDTO subtractWithTarget(QuantityInputDTO input,
                                                     Long userId,
                                                     String username) {

        QuantityMeasurementDTO result = calcService.subtractWithTarget(input);
        saveHistory(input, result, userId, username);
        return result;
    }

    public QuantityMeasurementDTO multiply(QuantityInputDTO input,
                                           Long userId,
                                           String username) {

        QuantityMeasurementDTO result = calcService.multiply(input);
        saveHistory(input, result, userId, username);
        return result;
    }

    public QuantityMeasurementDTO divide(QuantityInputDTO input,
                                         Long userId,
                                         String username) {

        QuantityMeasurementDTO result = calcService.divide(input);
        saveHistory(input, result, userId, username);
        return result;
    }

    // GET HISTORY
    public List<MeasurementHistory> getHistory(Long userId) {
        return historyRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // FILTER BY OPERATION
    public List<MeasurementHistory> getHistoryByOperation(Long userId, String operation) {
        return historyRepository.findByUserIdAndOperationOrderByCreatedAtDesc(userId, operation);
    }

    // SAVE HISTORY
    private void saveHistory(QuantityInputDTO input,
                             QuantityMeasurementDTO result,
                             Long userId,
                             String username) {

        MeasurementHistory history = MeasurementHistory.builder()
                .userId(userId)
                .username(username)
                .operation(result.getOperation())
                .measurementType(input.getType())
                .inputValue(input.getValue())
                .inputUnit(input.getUnit())
                .secondValue(input.getSecondValue())
                .secondUnit(input.getSecondUnit())
                .targetUnit(input.getTargetUnit())
                .result(result.getResult())
                .resultUnit(result.getUnit())
                .description(result.getDescription())
                .build();

        historyRepository.save(history);
    }
}