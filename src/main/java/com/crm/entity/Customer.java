package com.crm.entity;

import com.crm.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Order> orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_rule_id")
    @JsonBackReference
    private CampaignRule campaignRule;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private CustomerLoginActivity loginActivity;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
