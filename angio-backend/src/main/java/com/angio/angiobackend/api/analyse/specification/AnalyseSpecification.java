package com.angio.angiobackend.api.analyse.specification;

import com.angio.angiobackend.api.analyse.embeddable.AdditionalInfo;
import com.angio.angiobackend.api.analyse.embeddable.AdditionalInfo_;
import com.angio.angiobackend.api.analyse.embeddable.AnalyseStatus_;
import com.angio.angiobackend.api.analyse.embeddable.BloodFlowAnalyse;
import com.angio.angiobackend.api.analyse.embeddable.BloodFlowAnalyse_;
import com.angio.angiobackend.api.analyse.embeddable.GeometricAnalyse;
import com.angio.angiobackend.api.analyse.embeddable.GeometricAnalyse_;
import com.angio.angiobackend.api.analyse.entity.AnalyseEntity;
import com.angio.angiobackend.api.analyse.entity.AnalyseEntity_;
import com.angio.angiobackend.api.analyse.entity.VesselEntity_;
import com.angio.angiobackend.api.analyse.type.AnalyseStatusType;
import com.angio.angiobackend.api.analyse.type.AnalyseType;
import com.angio.angiobackend.api.common.embeddable.FullName_;
import com.angio.angiobackend.api.patient.entity.PatientEntity_;
import com.angio.angiobackend.api.security.entity.User_;
import com.angio.angiobackend.api.user.entities.UserInfoEntity_;
import com.angio.angiobackend.util.EnumUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Fetch;
import java.util.Date;

import static com.angio.angiobackend.util.DateUtils.atEndOfDay;
import static com.angio.angiobackend.util.DateUtils.atStartOfDay;
import static com.angio.angiobackend.util.SpecificationUtils.substringPattern;
import static javax.persistence.criteria.JoinType.LEFT;

/**
 * Specifications for analyse entity.
 */
@Component
public class AnalyseSpecification {

    /**
     * Find analyse by id.
     *
     * @param id id
     * @return specification
     */
    public Specification<AnalyseEntity> analyseId(Long id) {
        return (root, query, cb) -> {
            if (id != null) {
                return cb.equal(root.get(AnalyseEntity_.id), id);
            }
            return null;
        };
    }

    /**
     * Find analyses by analyse date.
     *
     * @param date analyse date
     * @return specification
     */
    public Specification<AnalyseEntity> analyseDate(Date date) {
        return (root, query, cb) -> {
            if (date != null) {
                return cb.between(root.get(AnalyseEntity_.analyseDate), atStartOfDay(date), atEndOfDay(date));
            }
            return null;
        };
    }

    /**
     * Find analyses by name substring.
     *
     * @param name name
     * @return specification
     */
    public Specification<AnalyseEntity> name(String name) {
        return (root, query, cb) -> {
            if (name != null) {
                return cb.like(root.get(AnalyseEntity_.additionalInfo)
                        .get(AdditionalInfo_.name), substringPattern(name));
            }
            return null;
        };
    }

    /**
     * Find analyses by short description substring.
     *
     * @param shortDescription description
     * @return specification
     */
    public Specification<AnalyseEntity> shortDescription(String shortDescription) {
        return (root, query, cb) -> {
            if (shortDescription != null) {
                return cb.like(root.get(AnalyseEntity_.additionalInfo)
                        .get(AdditionalInfo_.shortDescription), substringPattern(shortDescription));
            }
            return null;
        };
    }

    /**
     * Find analyses by analyse type string.
     *
     * @param analyseType analyse type string
     * @return specification
     */
    public Specification<AnalyseEntity> analyseType(String analyseType) {
        return (root, query, cb) -> {
            AnalyseType castedAnalyseType = EnumUtils.getIfPresent(analyseType, AnalyseType.class);
            if (castedAnalyseType != null) {
                return cb.equal(root.get(AnalyseEntity_.additionalInfo)
                        .get(AdditionalInfo_.type), castedAnalyseType);
            }
            return null;
        };
    }

    /**
     * Find analyses by diagnostician firstname substring.
     *
     * @param firstname firstname
     * @return specification
     */
    public Specification<AnalyseEntity> userInfoFirstname(String firstname) {
        return (root, query, cb) -> {
            if (firstname != null) {
                return cb.like(root.get(AnalyseEntity_.additionalInfo)
                        .get(AdditionalInfo_.diagnostician)
                        .get(User_.userInfo)
                        .get(UserInfoEntity_.fullName)
                        .get(FullName_.firstname), substringPattern(firstname));
            }
            return null;
        };
    }

    /**
     * Find analyses by diagnostician lastname substring.
     *
     * @param lastname lastname
     * @return specification
     */
    public Specification<AnalyseEntity> userInfoLastname(String lastname) {
        return (root, query, cb) -> {
            if (lastname != null) {
                return cb.like(root.get(AnalyseEntity_.additionalInfo)
                        .get(AdditionalInfo_.diagnostician)
                        .get(User_.userInfo)
                        .get(UserInfoEntity_.fullName)
                        .get(FullName_.lastname), substringPattern(lastname));
            }
            return null;
        };
    }

    /**
     * Find analyses by diagnostician patronymic substring.
     *
     * @param patronymic patronymic
     * @return specification
     */
    public Specification<AnalyseEntity> userInfoPatronymic(String patronymic) {
        return (root, query, cb) -> {
            if (patronymic != null) {
                return cb.like(root.get(AnalyseEntity_.additionalInfo)
                        .get(AdditionalInfo_.diagnostician)
                        .get(User_.userInfo)
                        .get(UserInfoEntity_.fullName)
                        .get(FullName_.patronymic), substringPattern(patronymic));
            }
            return null;
        };
    }

    /**
     * Find analyses by patient firstname substring.
     *
     * @param firstname firstname
     * @return specification
     */
    public Specification<AnalyseEntity> patientFirstname(String firstname) {
        return (root, query, cb) -> {
            if (firstname != null) {
                return cb.like(root.get(AnalyseEntity_.additionalInfo)
                        .get(AdditionalInfo_.patient)
                        .get(PatientEntity_.fullName)
                        .get(FullName_.firstname), substringPattern(firstname));
            }
            return null;
        };
    }

    /**
     * Find analyses by patient lastname substring.
     *
     * @param lastname lastname
     * @return specification
     */
    public Specification<AnalyseEntity> patientLastname(String lastname) {
        return (root, query, cb) -> {
            if (lastname != null) {
                return cb.like(root.get(AnalyseEntity_.additionalInfo)
                        .get(AdditionalInfo_.patient)
                        .get(PatientEntity_.fullName)
                        .get(FullName_.lastname), substringPattern(lastname));
            }
            return null;
        };
    }

    /**
     * Find analyses by patient patronymic substring.
     *
     * @param patronymic patronymic
     * @return specification
     */
    public Specification<AnalyseEntity> patientPatronymic(String patronymic) {
        return (root, query, cb) -> {
            if (patronymic != null) {
                return cb.like(root.get(AnalyseEntity_.additionalInfo)
                        .get(AdditionalInfo_.patient)
                        .get(PatientEntity_.fullName)
                        .get(FullName_.patronymic), substringPattern(patronymic));
            }
            return null;
        };
    }

    /**
     * Find analyses by policy number substring.
     *
     * @param policy policy number
     * @return specification
     */
    public Specification<AnalyseEntity> patientPolicy(String policy) {
        return (root, query, cb) -> {
            if (policy != null) {
                return cb.like(root.get(AnalyseEntity_.additionalInfo)
                        .get(AdditionalInfo_.patient)
                        .get(PatientEntity_.policy), substringPattern(policy));
            }
            return null;
        };
    }

    /**
     * Find analyses which have given status.
     *
     * @param statuses statuses
     * @return specification
     */
    public Specification<AnalyseEntity> inStatus(AnalyseStatusType... statuses) {
        return (root, query, cb) -> {
            if (statuses != null && statuses.length > 0) {
                return root.get(AnalyseEntity_.status).get(AnalyseStatus_.type).in((Object[]) statuses);
            }
            return null;
        };
    }

    /**
     * Find analyses which have not given status.
     *
     * @param statuses statuses
     * @return specification
     */
    public Specification<AnalyseEntity> notInStatus(AnalyseStatusType... statuses) {
        return (root, query, cb) -> {
            if (statuses != null && statuses.length > 0) {
                return cb.not(root.get(AnalyseEntity_.status).get(AnalyseStatus_.type).in((Object[]) statuses));
            }
            return null;
        };
    }

    /**
     * Find analyses which have any status except DELETED.
     *
     * @return specification
     */
    public Specification<AnalyseEntity> notDeleted() {
        return (root, query, cb) -> cb.notEqual(root.get(AnalyseEntity_.status)
                .get(AnalyseStatus_.type), AnalyseStatusType.DELETED);
    }

    /**
     * Fetch original info.
     *
     * @return specification
     */
    public Specification<AnalyseEntity> fetchOriginalImage() {
        return (root, query, cb) -> {
            root.fetch(AnalyseEntity_.originalImage, LEFT);
            return cb.conjunction();
        };
    }

    /**
     * Fetch additional info.
     *
     * @return specification
     */
    public Specification<AnalyseEntity> fetchAdditionalInfo() {
        return (root, query, cb) -> {
            Fetch<AnalyseEntity, AdditionalInfo> entityFetch = root.fetch(AnalyseEntity_.additionalInfo, LEFT);
            entityFetch.fetch(AdditionalInfo_.patient, LEFT);
            entityFetch.fetch(AdditionalInfo_.diagnostician, LEFT).fetch(User_.userInfo, LEFT);
            return cb.conjunction();
        };
    }

    /**
     * Fetch blood flow analyse.
     *
     * @return specification
     */
    public Specification<AnalyseEntity> fetchBloodFlowAnalyse() {
        return (root, query, cb) -> {
            Fetch<AnalyseEntity, BloodFlowAnalyse> analyseFetch = root.fetch(AnalyseEntity_.bloodFlowAnalyse, LEFT);
            analyseFetch.fetch(BloodFlowAnalyse_.ischemiaImage, LEFT);
            analyseFetch.fetch(BloodFlowAnalyse_.densityImage, LEFT);
            analyseFetch.fetch(BloodFlowAnalyse_.ischemias, LEFT);
            analyseFetch.fetch(BloodFlowAnalyse_.densities, LEFT);
            return cb.conjunction();
        };
    }

    /**
     * Fetch geometric analyse.
     *
     * @return specification
     */
    public Specification<AnalyseEntity> fetchGeometricAnalyse() {
        return (root, query, cb) -> {
            Fetch<AnalyseEntity, GeometricAnalyse> analyseFetch = root.fetch(AnalyseEntity_.geometricAnalyse, LEFT);
            analyseFetch.fetch(GeometricAnalyse_.binarizedImage, LEFT);
            analyseFetch.fetch(GeometricAnalyse_.skeletonizedImage, LEFT);
            analyseFetch.fetch(GeometricAnalyse_.vessels, LEFT).fetch(VesselEntity_.mainVesselImage);
            analyseFetch.fetch(GeometricAnalyse_.vessels, LEFT).fetch(VesselEntity_.vesselImage);
            return cb.conjunction();
        };
    }

    /**
     * Fetch (join column) all nested entities for analyse. Prevent n+1 problem when entity map to dto.
     *
     * @return specification
     */
    public Specification<AnalyseEntity> fetchAll() {
        return fetchOriginalImage()
                .and(fetchAdditionalInfo())
                .and(fetchGeometricAnalyse())
                .and(fetchBloodFlowAnalyse());
    }

    /**
     * Find entities by query substring in analyse fields.
     *
     * @param queryString query string
     * @return specification
     */
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
