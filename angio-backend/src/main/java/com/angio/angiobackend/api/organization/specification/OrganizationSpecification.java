package com.angio.angiobackend.api.organization.specification;

import static com.angio.angiobackend.util.SpecificationUtils.substringPattern;

import com.angio.angiobackend.api.organization.entity.Organization;
import com.angio.angiobackend.api.organization.entity.Organization_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class OrganizationSpecification {

    /**
     * Find organization by id.
     *
     * @param id id
     * @return specification
     */
    public Specification<Organization> organizationId(Long id) {
        return (root, query, cb) -> {
            if (id != null) {
                return cb.equal(root.get(Organization_.id), id);
            }
            return null;
        };
    }

    /**
     * Find organization by name substring.
     *
     * @param name organization name
     * @return specification
     */
    public Specification<Organization> organizationName(String name) {
        return (root, query, cb) -> {
            if (name != null) {
                return cb.like(cb.upper(root.get(Organization_.name)), substringPattern(name).toUpperCase());
            }
            return null;
        };
    }

    /**
     * Find entities by query substring in organization fields.
     *
     * @param queryString query string
     * @return specification
     */
    public Specification<Organization> getOrganizationFilter(String queryString) {
        return Specification.where(organizationId(parseLong(queryString)))
                .or(organizationName(queryString));
    }

    private Long parseLong(String number) {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
