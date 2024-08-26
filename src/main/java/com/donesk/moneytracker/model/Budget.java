package com.donesk.moneytracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "Budgets")
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @NotBlank(message = "Title cannot be blank")
    @Column(name = "title", nullable = false)
    private String title;

    @NonNull
    @Column(name = "current_progress")
    private Double currentProgress = 0.0;

    @NonNull
    @Min(value = 0, message = "Goal might be greater than 0")
    @Column(name = "goal", nullable = false)
    private Double goal;

    @NonNull
    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> transactionList = new ArrayList<>();

    @NonNull
    @Column(name = "status", nullable = false)
    private boolean status = false;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull( message = "The user field cannot be empty")
    @JsonIgnore
    private User user;

    public void updateCurrentProgress(double amount, Category category) {
        if(String.valueOf(category.getType()).equals("INCOME")) {
            this.currentProgress += amount;
        } else {
            this.currentProgress -=amount;
        }
    }
}