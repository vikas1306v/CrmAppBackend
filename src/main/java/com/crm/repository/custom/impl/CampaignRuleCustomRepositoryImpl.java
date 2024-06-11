package com.crm.repository.custom.impl;

import com.crm.dto.request.ConditionRequestDto;
import com.crm.dto.request.RuleRequestDto;
import com.crm.entity.Customer;
import com.crm.entity.CustomerLoginActivity;
import com.crm.repository.custom.CampaignRuleCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CampaignRuleCustomRepositoryImpl implements CampaignRuleCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Customer> findAudienceFromCampaignRule(RuleRequestDto ruleRequestDto) {
        System.out.println(ruleRequestDto.getRules().get(0).getConnector());
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<CustomerLoginActivity> loginActivity = cq.from(CustomerLoginActivity.class);
        Join<CustomerLoginActivity, Customer> customer = loginActivity.join("customer");

        // Build the initial Predicate
        Predicate finalPredicate = null;

        for (ConditionRequestDto rule : ruleRequestDto.getRules()) {
            String field = rule.getField();
            String condition = rule.getCondition();
            String value = rule.getValue();
            String connector = rule.getConnector();

            Predicate predicate = null;

            // Create Predicate for the current rule
            if ("totalSpends".equals(field)) {
                double spendValue = Double.parseDouble(value);
                predicate = switch (condition) {
                    case ">" -> cb.greaterThan(loginActivity.get("totalSpends"), spendValue);
                    case "<" -> cb.lessThan(loginActivity.get("totalSpends"), spendValue);
                    case "=" -> cb.equal(loginActivity.get("totalSpends"), spendValue);
                    default -> throw new IllegalArgumentException("Unsupported condition: " + condition);
                };
            } else if ("noOfSuccessfulLogins".equals(field)) {
                int loginValue = Integer.parseInt(value);
                predicate = switch (condition) {
                    case ">" -> cb.greaterThan(loginActivity.get("noOfSuccessfulLogins"), loginValue);
                    case "<" -> cb.lessThan(loginActivity.get("noOfSuccessfulLogins"), loginValue);
                    case "=" -> cb.equal(loginActivity.get("noOfSuccessfulLogins"), loginValue);
                    default -> throw new IllegalArgumentException("Unsupported condition: " + condition);
                };
            } else {
                throw new IllegalArgumentException("Unsupported field: " + field);
            }

            if (finalPredicate == null) {
                finalPredicate = predicate;
            } else {
                if ("AND".equalsIgnoreCase(connector)) {
                    finalPredicate = cb.and(finalPredicate, predicate);
                } else if ("OR".equalsIgnoreCase(connector)) {
                    finalPredicate = cb.or(finalPredicate, predicate);
                } else if ("null".equalsIgnoreCase(connector)) {
                    // Last rule, no connector
                    finalPredicate = cb.and(finalPredicate, predicate);
                } else {
                    throw new IllegalArgumentException("Unsupported connector: " + connector);
                }
            }
        }

        if (finalPredicate != null) {
            cq.select(customer).distinct(true).where(finalPredicate);
        } else {
            cq.select(customer).distinct(true);
        }

        return entityManager.createQuery(cq).getResultList();
    }

}

