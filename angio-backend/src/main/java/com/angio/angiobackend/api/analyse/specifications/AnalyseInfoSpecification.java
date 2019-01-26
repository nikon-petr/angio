package com.angio.angiobackend.api.analyse.specifications;

import com.angio.angiobackend.api.analyse.entity.AnalyseEntity;
import com.angio.angiobackend.api.analyse.entity.AnalyseEntity_;
import com.angio.angiobackend.api.analyse.entity.PatientEntity_;
import com.angio.angiobackend.api.analyse.type.AnalyseType;
import com.angio.angiobackend.api.common.embeddable.FullName_;
import com.angio.angiobackend.api.security.entities.UserEntity_;
import com.angio.angiobackend.api.user.entities.UserInfoEntity_;
import com.angio.angiobackend.util.EnumUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.angio.angiobackend.util.DateUtil.atEndOfDay;
import static com.angio.angiobackend.util.DateUtil.atStartOfDay;
import static com.angio.angiobackend.util.SpecificationUtil.substringPattern;

@Component
public class AnalyseInfoSpecification {

    public Specification<AnalyseEntity> analyseDate(Date date) {
        return (root, query, cb) -> {
            if (date != null) {
                return cb.between(root.get(AnalyseEntity_.analyseDate), atStartOfDay(date), atEndOfDay(date));
            }
            return null;
        };
    }

    public Specification<AnalyseEntity> name(String name) {
        return (root, query, cb) -> {
            if (name != null) {
                return cb.like(root.get(AnalyseEntity_.name), substringPattern(name));
            }
            return null;
        };
    }

    public Specification<AnalyseEntity> shortDescription(String shortDescription) {
        return (root, query, cb) -> {
            if (shortDescription != null) {
                return cb.like(root.get(AnalyseEntity_.shortDescription), substringPattern(shortDescription));
            }
            return null;
        };
    }

    public Specification<AnalyseEntity> analyseType(String analyseType) {
        return (root, query, cb) -> {
            AnalyseType castedAnalyseType = EnumUtil.getIfPresent(analyseType, AnalyseType.class);
            if (castedAnalyseType != null) {
                return cb.equal(root.get(AnalyseEntity_.type), castedAnalyseType);
            }
            return null;
        };
    }

    public Specification<AnalyseEntity> userInfoFirstname(String firstname) {
        return (root, query, cb) -> {
            if (firstname != null) {
                return cb.like(root.get(AnalyseEntity_.user)
                        .get(UserEntity_.userInfo)
                        .get(UserInfoEntity_.fullName)
                        .get(FullName_.firstname), substringPattern(firstname));
            }
            return null;
        };
    }

    public Specification<AnalyseEntity> userInfoLastname(String lastname) {
        return (root, query, cb) -> {
            if (lastname != null) {
                return cb.like(root.get(AnalyseEntity_.user)
                        .get(UserEntity_.userInfo)
                        .get(UserInfoEntity_.fullName)
                        .get(FullName_.lastname), substringPattern(lastname));
            }
            return null;
        };
    }

    public Specification<AnalyseEntity> userInfoPatronymic(String patronymic) {
        return (root, query, cb) -> {
            if (patronymic != null) {
                return cb.like(root.get(AnalyseEntity_.user)
                        .get(UserEntity_.userInfo)
                        .get(UserInfoEntity_.fullName)
                        .get(FullName_.patronymic), substringPattern(patronymic));
            }
            return null;
        };
    }

    public Specification<AnalyseEntity> patientFirstname(String firstname) {
        return (root, query, cb) -> {
            if (firstname != null) {
                return cb.like(root.get(AnalyseEntity_.patient)
                        .get(PatientEntity_.fullName)
                        .get(FullName_.firstname), substringPattern(firstname));
            }
            return null;
        };
    }

    public Specification<AnalyseEntity> patientLastname(String lastname) {
        return (root, query, cb) -> {
            if (lastname != null) {
                return cb.like(root.get(AnalyseEntity_.patient)
                        .get(PatientEntity_.fullName)
                        .get(FullName_.lastname), substringPattern(lastname));
            }
            return null;
        };
    }

    public Specification<AnalyseEntity> patientPatronymic(String patronymic) {
        return (root, query, cb) -> {
            if (patronymic != null) {
                return cb.like(root.get(AnalyseEntity_.patient)
                        .get(PatientEntity_.fullName)
                        .get(FullName_.patronymic), substringPattern(patronymic));
            }
            return null;
        };
    }

    public Specification<AnalyseEntity> patientPolicy(String policy) {
        return (root, query, cb) -> {
            if (policy != null) {
                return cb.like(root.get(AnalyseEntity_.patient).get(PatientEntity_.policy), substringPattern(policy));
            }
            return null;
        };
    }

    public Specification<AnalyseEntity> getAnalyseInfoFilter(String queryString) {
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
