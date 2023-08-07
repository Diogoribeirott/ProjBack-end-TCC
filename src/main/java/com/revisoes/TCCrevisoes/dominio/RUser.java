package com.revisoes.TCCrevisoes.dominio;

import lombok.Data;
import java.util.List;
import lombok.Builder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import com.revisoes.TCCrevisoes.enums.RUserEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class RUser implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotEmpty(message = "Name cannot be empty")
  @Column(unique = true)
  private String name;

  @Email
  @NotEmpty(message = "email cannot be empty")
  private String email;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateOfBirth;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true )
  @JoinColumn(name = "ruser_id")
  @Builder.Default
  private List<Subjects> subjects = new ArrayList<>();

  @NotEmpty
  @Size(min = 5, max= 70)
  @Column(unique = true)
  private String login;

  @NotEmpty
  @Size(min = 5, max= 70)
  private String password;

  private RUserEnum role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if(this.role == RUserEnum.ADMIN){
      return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
    }else{
      return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
  }

  @Override
  public String getUsername() {
    return login;
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