package com.angio.angiobackend.api.analyse.specification;

import com.angio.angiobackend.api.analyse.embeddable.AdditionalInfo_;
import com.angio.angiobackend.api.analyse.embeddable.AnalyseStatus_;
import com.angio.angiobackend.api.analyse.embeddable.BloodFlowAnalyse;
import com.angio.angiobackend.api.analyse.embeddable.BloodFlowAnalyse_;
import com.angio.angiobackend.api.analyse.embeddable.GeometricAnalyse;
import com.angio.angiobackend.api.analyse.embeddable.GeometricAnalyse_;
import com.angio.angiobackend.api.analyse.embeddable.ProfileAnalyse;
import com.angio.angiobackend.api.analyse.embeddable.ProfileAnalyse_;
import com.angio.angiobackend.api.analyse.entity.Analyse;
import com.angio.angiobackend.api.analyse.entity.Analyse_;
import com.angio.angiobackend.api.analyse.entity.CysticVolume_;
import com.angio.angiobackend.api.analyse.entity.RetinalPositiveExtremum_;
import com.angio.angiobackend.api.analyse.entity.Vessel_;
import com.angio.angiobackend.api.analyse.type.AnalyseStatusType;
import com.angio.angiobackend.api.analyse.type.AnalyseType;
import com.angio.angiobackend.api.common.embeddable.FullName_;
import com.angio.angiobackend.api.organization.entity.Organization_;
import com.angio.angiobackend.api.patient.entity.Patient_;
import com.angio.angiobackend.api.user.entities.User;
import com.angio.angiobackend.api.user.entities.User_;
import com.angio.angiobackend.util.EnumUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Fetch;
import java.util.Date;
import java.util.UUID;

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
    public Specification<Analyse> analyseId(Long id) {
        return (root, query, cb) -> {
            if (id != null) {
                return cb.equal(root.get(Analyse_.id), id);
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
    public Specification<Analyse> analyseDate(Date date) {
        return (root, query, cb) -> {
            if (date != null) {
                return cb.between(root.get(Analyse_.analyseDate), atStartOfDay(date), atEndOfDay(date));
            }
            return null;
        };
    }

    public Specification<Analyse> analysePeriod(Date startDate, Date endDate) {
        return (root, query, cb) -> {
            if (startDate != null && endDate != null) {
                return cb.between(root.get(Analyse_.analyseDate), atStartOfDay(startDate), atEndOfDay(endDate));
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
    public Specification<Analyse> name(String name) {
        return (root, query, cb) -> {
            if (name != null) {
                return cb.like(cb.upper(root.get(Analyse_.additionalInfo)
                        .get(AdditionalInfo_.name)), substringPattern(name).toUpperCase());
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
    public Specification<Analyse> shortDescription(String shortDescription) {
        return (root, query, cb) -> {
            if (shortDescription != null) {
                return cb.like(cb.upper(root.get(Analyse_.additionalInfo)
                        .get(AdditionalInfo_.shortDescription)), substringPattern(shortDescription).toUpperCase());
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
    public Specification<Analyse> analyseType(String analyseType) {
        return (root, query, cb) -> {
            AnalyseType castedAnalyseType = EnumUtils.getIfPresent(analyseType, AnalyseType.class);
            if (castedAnalyseType != null) {
                return cb.equal(root.get(Analyse_.additionalInfo)
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
    public Specification<Analyse> userInfoFirstname(String firstname) {
        return (root, query, cb) -> {
            if (firstname != null) {
                return cb.like(cb.upper(root.get(Analyse_.additionalInfo)
                        .get(AdditionalInfo_.diagnostician)
                        .get(User_.fullName)
                        .get(FullName_.firstname)), substringPattern(firstname).toUpperCase());
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
    public Specification<Analyse> userInfoLastname(String lastname) {
        return (root, query, cb) -> {
            if (lastname != null) {
                return cb.like(cb.upper(root.get(Analyse_.additionalInfo)
                        .get(AdditionalInfo_.diagnostician)
                        .get(User_.fullName)
                        .get(FullName_.lastname)), substringPattern(lastname).toUpperCase());
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
    public Specification<Analyse> userInfoPatronymic(String patronymic) {
        return (root, query, cb) -> {
            if (patronymic != null) {
                return cb.like(cb.upper(root.get(Analyse_.additionalInfo)
                        .get(AdditionalInfo_.diagnostician)
                        .get(User_.fullName)
                        .get(FullName_.patronymic)), substringPattern(patronymic).toUpperCase());
            }
            return null;
        };
    }

    /**
     * Find analyses by diagnostician id.
     *
     * @param id diagnostician id
     * @return specification
     */
    public Specification<Analyse> diagnosticianId(UUID id) {
        return (root, query, cb) -> {
            if (id != null) {
                return cb.equal(root.get(Analyse_.additionalInfo)
                        .get(AdditionalInfo_.diagnostician)
                        .get(User_.id), id);
            }
            return null;
        };
    }

    /**
     * Find analyses by organization id.
     *
     * @param id organization id
     * @return specification
     */
    public Specification<Analyse> organizationId(Long id) {
        return (root, query, cb) -> {
            if (id != null) {
                return cb.equal(root.get(Analyse_.additionalInfo)
                        .get(AdditionalInfo_.diagnostician)
                        .get(User_.organization)
                        .get(Organization_.id), id);
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
    public Specification<Analyse> patientFirstname(String firstname) {
        return (root, query, cb) -> {
            if (firstname != null) {
                return cb.like(cb.upper(root.get(Analyse_.additionalInfo)
                        .get(AdditionalInfo_.patient)
                        .get(Patient_.fullName)
                        .get(FullName_.firstname)), substringPattern(firstname).toUpperCase());
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
    public Specification<Analyse> patientLastname(String lastname) {
        return (root, query, cb) -> {
            if (lastname != null) {
                return cb.like(cb.upper(root.get(Analyse_.additionalInfo)
                        .get(AdditionalInfo_.patient)
                        .get(Patient_.fullName)
                        .get(FullName_.lastname)), substringPattern(lastname).toUpperCase());
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
    public Specification<Analyse> patientPatronymic(String patronymic) {
        return (root, query, cb) -> {
            if (patronymic != null) {
                return cb.like(cb.upper(root.get(Analyse_.additionalInfo)
                        .get(AdditionalInfo_.patient)
                        .get(Patient_.fullName)
                        .get(FullName_.patronymic)), substringPattern(patronymic).toUpperCase());
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
    public Specification<Analyse> inStatus(AnalyseStatusType... statuses) {
        return (root, query, cb) -> {
            if (statuses != null && statuses.length > 0) {
                return root.get(Analyse_.status).get(AnalyseStatus_.type).in((Object[]) statuses);
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
    public Specification<Analyse> notInStatus(AnalyseStatusType... statuses) {
        return (root, query, cb) -> {
            if (statuses != null && statuses.length > 0) {
                return cb.not(root.get(Analyse_.status).get(AnalyseStatus_.type).in((Object[]) statuses));
            }
            return null;
        };
    }

    /**
     * Find analyses by onlyStarred condition. Find all if {@code onlyStarred} parameter is {@code null}.
     *
     * @param user        user
     * @param onlyStarred only starred condition
     * @return specification
     */
    public Specification<Analyse> starred(User user, Boolean onlyStarred) {
        return (root, query, cb) -> {
            if (onlyStarred == null || user == null) {
                return null;
            }

            if (onlyStarred) {
                return cb.isMember(user, root.get(Analyse_.usersStarredThis));
            } else {
                return cb.not(cb.isMember(user, root.get(Analyse_.usersStarredThis)));
            }
        };
    }

    /**
     * Find analyses which have any status except DELETED.
     *
     * @return specification
     */
    public Specification<Analyse> notDeleted() {
        return (root, query, cb) -> cb.notEqual(root.get(Analyse_.status)
                .get(AnalyseStatus_.type), AnalyseStatusType.DELETED);
    }

    /**
     * Fetch original info.
     *
     * @return specification
     */
    public Specification<Analyse> fetchOriginalImage() {
        return (root, query, cb) -> {
            if (Long.class == query.getResultType()) {
                return null;
            }
            root.fetch(Analyse_.originalImage, LEFT);
            return cb.conjunction();
        };
    }

    /**
     * Fetch blood flow analyse.
     *
     * @return specification
     */
    public Specification<Analyse> fetchBloodFlowAnalyse() {
        return (root, query, cb) -> {
            if (Long.class == query.getResultType()) {
                return null;
            }
            Fetch<Analyse, BloodFlowAnalyse> analyseFetch = root.fetch(Analyse_.bloodFlowAnalyse, LEFT);
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
    public Specification<Analyse> fetchGeometricAnalyse() {
        return (root, query, cb) -> {
            if (Long.class == query.getResultType()) {
                return null;
            }
            Fetch<Analyse, GeometricAnalyse> analyseFetch = root.fetch(Analyse_.geometricAnalyse, LEFT);
            analyseFetch.fetch(GeometricAnalyse_.binarizedImage, LEFT);
            analyseFetch.fetch(GeometricAnalyse_.skeletonizedImage, LEFT);
            analyseFetch.fetch(GeometricAnalyse_.vessels, LEFT).fetch(Vessel_.mainVesselImage, LEFT);
            analyseFetch.fetch(GeometricAnalyse_.vessels, LEFT).fetch(Vessel_.vesselImage, LEFT);
            return cb.conjunction();
        };
    }

    /**
     * Fetch profile analyse.
     *
     * @return specification
     */
    public Specification<Analyse> fetchProfileAnalyse() {
        return (root, query, cb) -> {
            if (Long.class == query.getResultType()) {
                return null;
            }
            Fetch<Analyse, ProfileAnalyse> analyseFetch = root.fetch(Analyse_.profileAnalyse, LEFT);
            analyseFetch.fetch(ProfileAnalyse_.cysticVolume, LEFT).fetch(CysticVolume_.profileImage, LEFT);
            analyseFetch.fetch(ProfileAnalyse_.cysticVolume, LEFT).fetch(CysticVolume_.angiogramImage, LEFT);
            analyseFetch.fetch(ProfileAnalyse_.retinalPositiveExtremum, LEFT).fetch(RetinalPositiveExtremum_.profileImage, LEFT);
            analyseFetch.fetch(ProfileAnalyse_.retinalPositiveExtremum, LEFT).fetch(RetinalPositiveExtremum_.angiogramImage, LEFT);
            return cb.conjunction();
        };
    }

    /**
     * Fetch (join column) all nested entities for analyse. Prevent n+1 problem when entity map to dto.
     *
     * @return specification
     */
    public Specification<Analyse> fetchAll() {
        return fetchOriginalImage()
                .or(fetchGeometricAnalyse())
                .or(fetchBloodFlowAnalyse())
                .or(fetchProfileAnalyse());
    }

    /**
     * Find analyses owned by user
     *
     * @param user user
     * @return specification
     */
    public Specification<Analyse> ownedBy(User user) {
        Long organizationId = user.getOrganization() != null ? user.getOrganization().getId() : null;
        if (organizationId != null) {
            return organizationId(organizationId);
        } else {
            return diagnosticianId(user.getId());
        }
    }

    /**
     * Find entities by query substring in analyse fields.
     *
     * @param queryString query string
     * @return specification
     */
    public Specification<Analyse> getAnalyseInfoFilter(String queryString) {
        return Specification.where(name(queryString))
                .or(analyseId(parseLong(queryString)))
                .or(shortDescription(queryString))
                .or(userInfoFirstname(queryString))
                .or(userInfoLastname(queryString))
                .or(userInfoPatronymic(queryString))
                .or(patientFirstname(queryString))
                .or(patientLastname(queryString))
                .or(patientPatronymic(queryString));
    }

    private Long parseLong(String number) {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
