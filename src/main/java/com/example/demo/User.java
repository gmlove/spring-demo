package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class User {
  @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer id;
  private String name;
  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "owner_id")
  private Set<Book> books;
  private LocalDateTime updatedAt;
}
