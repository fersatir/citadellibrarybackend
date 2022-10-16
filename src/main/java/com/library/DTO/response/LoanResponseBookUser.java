package com.library.dto.response;

import com.library.domain.Book;
import com.library.domain.Loan;
import com.library.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoanResponseBookUser {


    private Long id;

    private LocalDateTime loanDate;

    private LocalDateTime expireDate;

    private LocalDateTime returnDate;

    private User user;

    private Book book;


    public LoanResponseBookUser(Loan loan) {
        this.id = loan.getId();
        this.loanDate = loan.getLoanDate();
        this.expireDate = loan.getExpireDate();
        this.returnDate = loan.getReturnDate();
        this.user = loan.getUser();
        this.book = loan.getBook();
    }
}
