package com.library.DTO.requests;

import com.library.domain.Book;
import com.library.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequest {

    private Long id;

    @NotNull(message="Please provide load date")
    private LocalDateTime loanDate;

    @NotNull(message="Please provide expire date")
    private LocalDateTime expireDate;

    @NotNull(message="Please provide return")
    private LocalDateTime returnDate;

    @Size(min = 10,max = 200, message = "Message must be between ${min} and ${max} chars long")
    private String notes;


    private User user;


    private Book book;

}
