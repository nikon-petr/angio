package com.angio.angiobackend.api.patient.specification;

import static com.angio.angiobackend.util.SpecificationUtils.substringPattern;

import com.angio.angiobackend.api.common.embeddable.FullName_;
import com.angio.angiobackend.api.organization.entity.Organization_;
import com.angio.angiobackend.api.patient.entity.Patient;
import com.angio.angiobackend.api.patient.entity.Patient_;
import com.angio.angiobackend.api.user.entities.User_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PatientSpecification {

    /**
     * Find patient by id.
     *
     * @param id id
     * @return specification
     */
    public Specification<Patient> patientId(Long id) {
        if (id != null) {
            return (root, query, cb) -> cb.equal(root.get(Patient_.id), id);
        }
        return null;
    }

    /**
     * Find patient by firstname substring.
     *
     * @param firstname firstname
     * @return specification
     */
    public Specification<Patient> patientFirstname(String firstname) {
        if (firstname != null) {
            return (root, query, cb) ->
                    cb.like(cb.upper(root.get(Patient_.fullName)
                            .get(FullName_.firstname)), substringPattern(firstname).toUpperCase());
        }
        return null;
    }

    /**
     * Find patient by lastname substring.
     *
     * @param lastname lastname
     * @return specification
     */
    public Specification<Patient> patientLastname(String lastname) {
        if (lastname != null) {
            return (root, query, cb) ->
                    cb.like(cb.upper(root.get(Patient_.fullName)
                            .get(FullName_.lastname)), substringPattern(lastname).toUpperCase());
        }
        return null;
    }

    /**
     * Find patient by patronymic substring.
     *
     * @param patronymic patronymic
     * @return specification
     */
    public Specification<Patient> patientPatronymic(String patronymic) {
        if (patronymic != null) {
            return (root, query, cb) ->
                    cb.like(cb.upper(root.get(Patient_.fullName)
                            .get(FullName_.patronymic)), substringPattern(patronymic).toUpperCase());
        }
        return null;
    }

    /**
     * Find patient by organization.
     *
     * @param organizationId organization id
     * @return specification
     */
    public Specification<Patient> patientOrganization(Long organizationId) {
        if (organizationId != null) {
            return (root, query, cb) -> cb.equal(root.get(Patient_.organization).get(Organization_.id), organizationId);
        }
        return null;
    }

    /**
     * Find patient by user.
     *
     * @param userId user id
     * @return specification
     */
    public Specification<Patient> patientUser(UUID userId) {
        if (userId != null) {
            return (root, query, cb) -> cb.equal(root.get(Patient_.user).get(User_.id), userId);
        }
        return null;
    }

    /**
     * Find entities by query substring in patient fields.
     *
     * @param queryString query string
     * @return specification
     */
    public Specification<Patient> getPatientFilter(String queryString) {
        return Specification.where(patientId(parseLong(queryString)))
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
