package com.example.demo.db1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class OUser {
  @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private LocalDateTime updatedAt;
}
