package com.library.controller;

import com.library.domain.Book;
import com.library.dto.BookDTO;
import com.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@AllArgsConstructor
@RequestMapping("/book")
@RestController
public class BookController {

    BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getOneBook(@PathVariable Long id){
        BookDTO book = bookService.getOneBookById(id);

        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<BookDTO>> getAllWithPage(@RequestParam("name") Optional<String> p,
                                                        @RequestParam("cat") Optional<Long> categortyId,
                                                        @RequestParam("author") Optional<Long> authorId,
                                                        @RequestParam("publisher") Optional<Long> publisherId,
                                                        @RequestParam("page") int page,
                                                        @RequestParam("size") int size,
                                                        @RequestParam("sort") String name,
                                                        @RequestParam("type") Sort.Direction direction){

        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,name));

        Page<BookDTO> bookPage = bookService.findAllWithPage(p, categortyId, authorId, publisherId,pageable);

        return new ResponseEntity<>(bookPage,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDto){

        BookDTO book = bookService.createBook(bookDto);

        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO){

       BookDTO book = bookService.updateBookById(id,bookDTO);

        return new ResponseEntity<>(book,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDTO> deleteOneBook(@PathVariable Long id){

        BookDTO book = bookService.deleteOneBookById(id);

        return new ResponseEntity<>(book,HttpStatus.OK);

    }


}
