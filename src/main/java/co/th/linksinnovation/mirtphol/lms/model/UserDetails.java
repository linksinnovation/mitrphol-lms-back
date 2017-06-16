/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mirtphol.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@Data
@Entity
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails{
    
    @Id
    private String username;
    private String password;
    private String nameEn;
    private String nameTh;
    private String userId;
    private String photo;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities;

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    
    public void addAuthority(Authority authority){
        if(this.authorities == null){
            this.authorities = new ArrayList<>();
        }
        this.authorities.add(authority);
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
