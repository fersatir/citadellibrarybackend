package com.library.service;

import com.library.dto.LoanDTO;
import com.library.domain.Book;
import com.library.domain.Loan;
import com.library.domain.User;
import com.library.dto.mapper.LoanMapper;
import com.library.exception.BadRequestException;
import com.library.exception.ResourceNotFoundException;
import com.library.exception.message.ErrorMessage;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import com.library.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class LoanService {

    LoanRepository loanRepository;
    LoanMapper loanMapper;

    BookRepository bookRepository;
    UserRepository userRepository;


    public LoanDTO createLoan(LoanDTO loanDTO){

      LocalDateTime ld = LocalDateTime.now();

        Book book = bookRepository.findById(loanDTO.getBookId()).orElseThrow(() -> new ResourceNotFoundException
                (String.format(ErrorMessage.BOOK_NOT_FOUND_MESSAGE, loanDTO.getBookId())));
      Boolean isLoanable =  book.getLoanable();
        if(!isLoanable){
            throw new BadRequestException("kitap alamazsın müsait değil.");
        }

        User user= userRepository.findById(loanDTO.getUserId()).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, loanDTO.getUserId())));


         List<Loan> expireDates = loanRepository.expireDate(loanDTO.getUserId());

           for (Loan l:expireDates) {
               if(l.getReturnDate() == null){
                 Boolean expired =  l.getExpireDate().isBefore(ld);
                   System.out.println(expired);
                 if(expired)  throw new BadRequestException("kitap alamazsın iade tarihini geciktirdiğin için kitap alamazsın.");
               }
           }




        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setExpireDate(ld.plusDays(7));
        loan.setLoanDate(ld);

       loanRepository.save(loan);

       loanDTO.setId(loan.getId());
       loanDTO.setExpireDate(loan.getExpireDate());
       book.setLoanable(false);
       bookRepository.save(book);

        return loanDTO;

    }


}
