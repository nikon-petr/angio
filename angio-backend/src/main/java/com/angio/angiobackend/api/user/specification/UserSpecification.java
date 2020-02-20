package com.angio.angiobackend.api.user.specification;

import static com.angio.angiobackend.util.SpecificationUtils.substringPattern;

import com.angio.angiobackend.api.common.embeddable.FullName_;
import com.angio.angiobackend.api.organization.entity.Organization_;
import com.angio.angiobackend.api.user.entities.User;
import com.angio.angiobackend.api.user.entities.User_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecification {

    /**
     * Find user by id.
     *
     * @param id id
     * @return specification
     */
    public Specification<User> userId(Long id) {
        if (id != null) {
            return (root, query, cb) -> cb.equal(root.get(User_.id), id);
        }
        return null;
    }

    /**
     * Find user by email substring.
     *
     * @param email email
     * @return specification
     */
    public Specification<User> userEmail(String email) {
        if (email != null) {
            return (root, query, cb) ->
                    cb.like(cb.upper(root.get(User_.email)), substringPattern(email).toUpperCase());
        }
        ;
        return null;
    }

    /**
     * Find user by firstname substring.
     *
     * @param firstname firstname
     * @return specification
     */
    public Specification<User> userFirstname(String firstname) {
        if (firstname != null) {
            return (root, query, cb) ->
                    cb.like(cb.upper(root.get(User_.fullName)
                            .get(FullName_.firstname)), substringPattern(firstname).toUpperCase());
        }
        return null;
    }

    /**
     * Find user by lastname substring.
     *
     * @param lastname lastname
     * @return specification
     */
    public Specification<User> userLastname(String lastname) {
        if (lastname != null) {
            return (root, query, cb) ->
                    cb.like(cb.upper(root.get(User_.fullName)
                            .get(FullName_.lastname)), substringPattern(lastname).toUpperCase());
        }
        return null;
    }

    /**
     * Find user by patronymic substring.
     *
     * @param patronymic patronymic
     * @return specification
     */
    public Specification<User> userPatronymic(String patronymic) {
        if (patronymic != null) {
            return (root, query, cb) ->
                    cb.like(cb.upper(root.get(User_.fullName)
                            .get(FullName_.patronymic)), substringPattern(patronymic).toUpperCase());
        }
        return null;
    }

    /**
     * Find user by user enabled.
     *
     * @param enabled enabled
     * @return specification
     */
    public Specification<User> userEnabled(Boolean enabled) {
        if (enabled != null) {
            return (root, query, cb) -> cb.equal(root.get(User_.enabled), enabled);
        }
        return null;
    }

    /**
     * Find user by user locked.
     *
     * @param locked enabled
     * @return specification
     */
    public Specification<User> userLocked(Boolean locked) {
        if (locked != null) {
            return (root, query, cb) -> cb.equal(root.get(User_.locked), locked);
        }
        return null;
    }

    /**
     * Find user by user organization id.
     *
     * @param organizationId organization id
     * @return specification
     */
    public Specification<User> userOrganizationId(Long organizationId) {
        if (organizationId != null) {
            return (root, query, cb) ->
                    cb.equal(root.get(User_.organization).get(Organization_.id), organizationId);
        }
        return null;
    }

    /**
     * Find user by organization name substring.
     *
     * @param organization organization
     * @return specification
     */
    public Specification<User> userOrganizationName(String organization) {
        if (organization != null) {
            return (root, query, cb) ->
                    cb.like(cb.upper(root.get(User_.organization)
                            .get(Organization_.name)), substringPattern(organization).toUpperCase());
        }
        return null;
    }

    /**
     * Find entities by query substring in user fields.
     *
     * @param queryString query string
     * @return specification
     */
    public Specification<User> getUserFilter(String queryString) {
        return Specification.where(userId(parseLong(queryString)))
                .or(userEmail(queryString))
                .or(userFirstname(queryString))
                .or(userLastname(queryString))
                .or(userPatronymic(queryString))
                .or(userOrganizationName(queryString));
    }

    private Long parseLong(String number) {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}