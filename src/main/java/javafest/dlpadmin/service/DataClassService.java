package javafest.dlpadmin.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javafest.dlpadmin.dto.DataClassAndRules;
import javafest.dlpadmin.dto.RuleDto;
import javafest.dlpadmin.model.DataClass;
import javafest.dlpadmin.model.DefaultDataClass;
import javafest.dlpadmin.model.Rule;
import javafest.dlpadmin.repository.DataClassRepository;
import javafest.dlpadmin.repository.DefaultDataClassRepository;
import javafest.dlpadmin.repository.RuleRepository;

@Service
public class DataClassService {

    @Autowired
    private DataClassRepository dataClassRepository;

    @Autowired
    private DefaultDataClassRepository defaultDataClassRepository;

    @Autowired
    private RuleRepository ruleRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public List<DataClass> findByUserId(String userId) {
        return dataClassRepository.findByUserId(userId);
    }

    public DataClass save(DataClass dataClass) {
        return dataClassRepository.save(dataClass);
    }

    public void saveDataClassAndRules(DataClassAndRules dataClassAndRules) {
        DataClass dataClass = toDataClass(dataClassAndRules);
        List<Rule> rules = toRules(dataClassAndRules);

        DataClass returnedDataClass = dataClassRepository.save(dataClass);
        for (Rule rule : rules) {
            rule.setDataId(returnedDataClass.getDataId());
        }
        ruleRepository.saveAll(rules);
    }

    public boolean validateUserAndDataId(String userId, String dataId) {
        return dataClassRepository.existsByUserIdAndDataId(userId, dataId);
    }

    @Transactional
    public void copyDefaultDataClassesToUser(String userId) {
        List<DefaultDataClass> defaultDataClasses = defaultDataClassRepository.findAll();

        List<DataClass> newDataClasses = new ArrayList<>();
        List<Rule> newRules = new ArrayList<>();

        for (DefaultDataClass defaultDataClass : defaultDataClasses) {
            DataClass newDataClass = new DataClass();
            newDataClass.setUserId(userId);
            newDataClass.setActive(true);
            newDataClass.setName(defaultDataClass.getName());
            newDataClass.setDescription(defaultDataClass.getDescription());
            newDataClass.setEvents(0);

            newDataClass = dataClassRepository.save(newDataClass);
            newDataClasses.add(newDataClass);

            for (Rule defaultRule : defaultDataClass.getRules()) {
                Rule newRule = new Rule();
                newRule.setDataId(newDataClass.getDataId());
                newRule.setName(defaultRule.getName());
                newRule.setOccurrences(1);
                newRule.setElements(defaultRule.getElements());

                newRules.add(newRule);
            }
        }

        ruleRepository.saveAll(newRules);
    }

    private DataClass toDataClass(DataClassAndRules dataClassAndRules) {
        return modelMapper.map(dataClassAndRules, DataClass.class);
    }

    private List<Rule> toRules(DataClassAndRules dataClassAndRules) {
        List<Rule> rules = new ArrayList<>();
        for (RuleDto rule : dataClassAndRules.getRules()) {
            rules.add(modelMapper.map(rule, Rule.class));
        }
        return rules;
    }
}
