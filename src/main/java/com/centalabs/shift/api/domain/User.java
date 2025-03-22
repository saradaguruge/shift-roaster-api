package com.centalabs.shift.api.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "user",uniqueConstraints = @UniqueConstraint(columnNames = "external_id"))
public class User extends TenantBaseEntity implements UserDetails {

    private String name;
    private String email;
    @Column(name = "external_id")
    private String externalId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return externalId;
    }

//    @OneToMany(mappedBy = "owner")
//    private List<Place> ownedPlaces;

}