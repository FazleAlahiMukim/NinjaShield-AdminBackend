package javafest.dlpadmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javafest.dlpadmin.model.Rule;
import javafest.dlpadmin.repository.RuleRepository;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    public List<Rule> findByDataId(String dataId) {
        return ruleRepository.findByDataId(dataId);
    }

    public Rule save(Rule rule) {
        return ruleRepository.save(rule);
    }

}
