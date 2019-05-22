package com.angio.angiobackend.api.patient.specification;

import static com.angio.angiobackend.util.SpecificationUtils.substringPattern;

import com.angio.angiobackend.api.common.embeddable.FullName_;
import com.angio.angiobackend.api.patient.entity.Patient;
import com.angio.angiobackend.api.patient.entity.Patient_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * PatientSpecification.
 *
 * @author Nikon_Petrunin
 */
@Component
public class PatientSpecification {

    /**
     * Find analyse by id.
     *
     * @param id id
     * @return specification
     */
    public Specification<Patient> patientId(Long id) {
        return (root, query, cb) -> {
            if (id != null) {
                return cb.equal(root.get(Patient_.id), id);
            }
            return null;
        };
    }

    /**
     * Find patient by firstname substring.
     *
     * @param firstname firstname
     * @return specification
     */
    public Specification<Patient> patientFirstname(String firstname) {
        return (root, query, cb) -> {
            if (firstname != null) {
                return cb.like(cb.upper(root.get(Patient_.fullName)
                        .get(FullName_.firstname)), substringPattern(firstname).toUpperCase());
            }
            return null;
        };
    }

    /**
     * Find patient by lastname substring.
     *
     * @param lastname lastname
     * @return specification
     */
    public Specification<Patient> patientLastname(String lastname) {
        return (root, query, cb) -> {
            if (lastname != null) {
                return cb.like(cb.upper(root.get(Patient_.fullName)
                        .get(FullName_.lastname)), substringPattern(lastname).toUpperCase());
            }
            return null;
        };
    }

    /**
     * Find patient by patronymic substring.
     *
     * @param patronymic patronymic
     * @return specification
     */
    public Specification<Patient> patientPatronymic(String patronymic) {
        return (root, query, cb) -> {
            if (patronymic != null) {
                return cb.like(cb.upper(root.get(Patient_.fullName)
                        .get(FullName_.patronymic)), substringPattern(patronymic).toUpperCase());
            }
            return null;
        };
    }

    /**
     * Find entities by query substring in patient fields.
     *
     * @param queryString query string
     * @return specification
     */
    public Specification<Patient> getAnalyseInfoFilter(String queryString) {
        return patientId(parseLong(queryString))
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
