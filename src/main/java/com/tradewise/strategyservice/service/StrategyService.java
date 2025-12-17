package com.tradewise.strategyservice.service;

import com.tradewise.strategyservice.dto.strategy.CreateStrategyRequest;
import com.tradewise.strategyservice.dto.response.StrategyResponse;
import com.tradewise.strategyservice.model.Strategy;
import com.tradewise.strategyservice.repository.StrategyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StrategyService {

    private final StrategyRepository strategyRepository;

    @Transactional
    public Strategy createStrategy(CreateStrategyRequest request, String userEmail) {
        Strategy strategy = new Strategy();
        strategy.setUserEmail(userEmail);
        strategy.setName(request.getName());
        strategy.setDescription(request.getDescription());
        // The logic to handle rules and conditions will be added later
        return strategyRepository.save(strategy);
    }

    @Transactional(readOnly = true)
    public List<StrategyResponse> getStrategiesByUser(String userEmail) {
        List<Strategy> strategies = strategyRepository.findByUserEmail(userEmail);
        return strategies.stream()
                .map(strategy -> new StrategyResponse(
                        strategy.getId(),
                        strategy.getName(),
                        strategy.getDescription(),
                        strategy.getCreatedAt(),
                        strategy.getUserEmail()
                ))
                .collect(Collectors.toList());
    }
}
