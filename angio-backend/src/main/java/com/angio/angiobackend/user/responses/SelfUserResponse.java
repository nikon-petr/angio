package com.angio.angiobackend.user.responses;

import com.angio.angiobackend.security.entities.AuthorityEntity;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

public class SelfUserResponse implements Serializable {
    private Data data;

    public SelfUserResponse(String email, String firstName, String lastName, Set<AuthorityEntity> authorities, Long activeToken) {
        data = new Data();
        data.setEmail(email);
        data.setFirstName(firstName);
        data.setLastName(lastName);
        data.setAuthorities(authorities);
        data.setActiveToken(activeToken);
    }

    public class Data{
        private String email;
        private String firstName;
        private String lastName;
        private Set<String> authorities;
        private long activeToken;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Set<String> getAuthorities() {
            return authorities;
        }

        public void setAuthorities(Set<AuthorityEntity> authorities) {
            this.authorities = authorities.stream()
                    .map(AuthorityEntity::getAuthority)
                    .collect(Collectors.toSet());
        }

        public long getActiveToken() {
            return activeToken;
        }

        public void setActiveToken(long activeToken) {
            this.activeToken = activeToken;
        }
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
