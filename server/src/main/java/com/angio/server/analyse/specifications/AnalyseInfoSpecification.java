package com.angio.server.analyse.specifications;

import com.angio.server.analyse.entities.AnalyseInfoEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.angio.server.util.DateUtil.atEndOfDay;
import static com.angio.server.util.DateUtil.atStartOfDay;
import static com.angio.server.util.SpecificationUtil.substringPattern;

@Component
public class AnalyseInfoSpecification {

    public Specification<AnalyseInfoEntity> analyseDate(Date date) {
        return (root, query, cb) -> {
            if (date != null) {
                return cb.between(root.get("analyseDate"), atStartOfDay(date), atEndOfDay(date));
            }
            return null;
        };
    }

    public Specification<AnalyseInfoEntity> name(String name) {
        return (root, query, cb) -> {
            if (name != null) {
                return cb.like(root.get("name"), substringPattern(name));
            }
            return null;
        };
    }

    public Specification<AnalyseInfoEntity> shortDescription(String shortDescription) {
        return (root, query, cb) -> {
            if (shortDescription != null) {
                return cb.like(root.get("shortDescription"), substringPattern(shortDescription));
            }
            return null;
        };
    }

    public Specification<AnalyseInfoEntity> analyseType(String analyseType) {
        return (root, query, cb) -> {
            if (analyseType != null) {
                return cb.like(root.get("analyseType"), substringPattern(analyseType));
            }
            return null;
        };
    }

    public Specification<AnalyseInfoEntity> userInfoFirstname(String firstname) {
        return (root, query, cb) -> {
            if (firstname != null) {
                return cb.like(root.get("user").get("userInfo").get("fullName").get("firstname"), substringPattern(firstname));
            }
            return null;
        };
    }

    public Specification<AnalyseInfoEntity> userInfoLastname(String lastname) {
        return (root, query, cb) -> {
            if (lastname != null) {
                return cb.like(root.get("user").get("userInfo").get("fullName").get("lastname"), substringPattern(lastname));
            }
            return null;
        };
    }

    public Specification<AnalyseInfoEntity> userInfoPatronymic(String patronymic) {
        return (root, query, cb) -> {
            if (patronymic != null) {
                return cb.like(root.get("user").get("userInfo").get("fullName").get("patronymic"), substringPattern(patronymic));
            }
            return null;
        };
    }

    public Specification<AnalyseInfoEntity> patientFirstname(String firstname) {
        return (root, query, cb) -> {
            if (firstname != null) {
                return cb.like(root.get("patient").get("fullName").get("firstname"), substringPattern(firstname));
            }
            return null;
        };
    }

    public Specification<AnalyseInfoEntity> patientLastname(String lastname) {
        return (root, query, cb) -> {
            if (lastname != null) {
                return cb.like(root.get("patient").get("fullName").get("lastname"), substringPattern(lastname));
            }
            return null;
        };
    }

    public Specification<AnalyseInfoEntity> patientPatronymic(String patronymic) {
        return (root, query, cb) -> {
            if (patronymic != null) {
                return cb.like(root.get("patient").get("fullName").get("patronymic"), substringPattern(patronymic));
            }
            return null;
        };
    }

    public Specification<AnalyseInfoEntity> patientPolicy(String policy) {
        return (root, query, cb) -> {
            if (policy != null) {
                return cb.like(root.get("patient").get("policy"), substringPattern(policy));
            }
            return null;
        };
    }

    public Specification<AnalyseInfoEntity> getAnalyseInfoFilter(String queryString) {
        return name(queryString)
                .or(shortDescription(queryString))
                .or(analyseType(queryString))
                .or(userInfoFirstname(queryString))
                .or(userInfoLastname(queryString))
                .or(userInfoPatronymic(queryString))
                .or(patientFirstname(queryString))
                .or(patientLastname(queryString))
                .or(patientPatronymic(queryString))
                .or(patientPolicy(queryString));
    }
}
