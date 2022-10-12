package com.library.DTO;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.Category;
import com.library.domain.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.File;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class BookDTO {

    private Long id;

    @NotNull(message="Please provide book name")
    @Size(min=2, max=80,message="Book name '${validatedValue}' must be between {min} and {max} chars long")
    private String name;

    @NotNull(message="Please provide isbn name")
    @Size(min=17, max=17,message="Isbn '${validatedValue}' must be between {min} and {max} chars long")
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d$",message = "Please provide valid isbn")
    private String isbn;

    private Integer pageCount;

    @NotNull(message="Please provide publish date")
    private Integer publishDate;

    private File image;

    @NotNull(message="Please provide loanable")
    private Boolean loanable = true;

    @NotNull(message="Please provide shelf code ")
    @Size(min=6, max=6,message="ShelfCode '${validatedValue}' must be {max} chars long")
    @Pattern(regexp = "^[A-Z]{2}-\\d{3}$",message = "Please provide valid shelf code")
    private String shelfCode;

    @NotNull(message="Please provide active")
    private Boolean active = true;

    @NotNull(message="Please provide featured")
    private Boolean featured = false;

    private LocalDateTime createDate;

    private Boolean builtIn = false;

}
