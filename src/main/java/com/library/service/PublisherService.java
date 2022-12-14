package com.library.service;

import com.library.domain.Publisher;
import com.library.exception.BadRequestException;
import com.library.exception.ConflictException;
import com.library.exception.ResourceNotFoundException;
import com.library.exception.message.ErrorMessage;
import com.library.repository.BookRepository;
import com.library.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private BookRepository bookRepository;

    public void createPublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    public Page<Publisher> getAllPublishersWithPage(Pageable pageable) {
        return publisherRepository.findAllPublishersWithPage(pageable);
    }

    public Publisher getPublisherById(Long id) {
        return publisherRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.PUBLISHER_NOT_FOUND_MESSAGE, id)));
    }

    public Publisher updatePublisherById(Long id, Publisher newPublisher) {
        Publisher foundPublisher = getPublisherById(id);
        if (foundPublisher.getBuiltIn()){
            throw new BadRequestException(String.format(ErrorMessage.PUBLISHER_BUILTIN_TRUE_UPDATE_MESSAGE,id));
        }else {
            foundPublisher.setName(newPublisher.getName());
            foundPublisher.setBuiltIn(newPublisher.getBuiltIn());
            publisherRepository.save(foundPublisher);
        }
        return foundPublisher;
    }

    public Publisher deletePublisherById(Long id) {
        Publisher foundPublisher = getPublisherById(id);

        if (foundPublisher.getBuiltIn()){
            throw new BadRequestException(String.format(ErrorMessage.PUBLISHER_BUILTIN_TRUE_DELETE_MESSAGE,id));
        } else if (!bookRepository.existsBookPublisherId(id).isEmpty()) {
            throw new ConflictException(String.format(ErrorMessage.PUBLISHER_NOT_DELETE_MESSAGE, id));
        }

        publisherRepository.deleteById(id);
        return foundPublisher;
    }

    public List<Publisher> findAll() {

        return publisherRepository.findAll();
    }
}
