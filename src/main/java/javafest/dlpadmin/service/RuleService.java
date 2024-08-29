package javafest.dlpadmin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import javafest.dlpadmin.model.Rule;
import javafest.dlpadmin.repository.RuleRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RuleService {
    private RuleRepository ruleRepository;

    public List<Rule> findByDataId(String dataId) {
        return ruleRepository.findByDataId(dataId);
    }
}
