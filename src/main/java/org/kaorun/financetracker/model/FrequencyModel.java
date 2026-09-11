package org.kaorun.financetracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "frequencies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrequencyModel implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Поле не может быть пустым")
    private String title;
}