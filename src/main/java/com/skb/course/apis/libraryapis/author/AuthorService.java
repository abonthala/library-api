package com.skb.course.apis.libraryapis.author;

import com.skb.course.apis.libraryapis.exceptions.LibraryResourceAlreadyExistsException;
import com.skb.course.apis.libraryapis.exceptions.LibraryResourceNotFoundException;
import com.skb.course.apis.libraryapis.author.Author;
import com.skb.course.apis.libraryapis.author.AuthorEntity;
import com.skb.course.apis.libraryapis.author.AuthorRepository;
import com.skb.course.apis.libraryapis.utils.LibraryApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private static Logger logger = LoggerFactory.getLogger(AuthorService.class);

    private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void addAuthor(Author authorToBeAdded, String traceId)
            throws LibraryResourceAlreadyExistsException {

        logger.debug("TraceId: {}, Request to add Author: {}", traceId, authorToBeAdded);
        AuthorEntity authorEntity = new AuthorEntity(
                authorToBeAdded.getFirstName(),
                authorToBeAdded.getLastName(),
                authorToBeAdded.getDateOfBirth(),
                authorToBeAdded.getGender()
        );

        AuthorEntity addedAuthor = null;

        try {
            addedAuthor = authorRepository.save(authorEntity);
        } catch (DataIntegrityViolationException e) {
            logger.error("TraceId: {}, Author already exists!!", traceId, e);
            throw new LibraryResourceAlreadyExistsException("Author already exists!!", traceId, HttpStatus.BAD_REQUEST);
        }

        authorToBeAdded.setAuthorId(addedAuthor.getAuthorId());
        logger.info("TraceId: {}, Author added: {}", traceId, authorToBeAdded);
    }

    public Author getAuthor(Integer authorId, String traceId) throws LibraryResourceNotFoundException {

        Optional<AuthorEntity> authorEntity = authorRepository.findById(authorId);
        Author author = null;

        if(authorEntity.isPresent()) {
            AuthorEntity pe = authorEntity.get();
            author = createAuthorFromEntity(pe);
        } else {
            throw new LibraryResourceNotFoundException("Author Id: " + authorId + " Not Found", traceId, HttpStatus.NOT_FOUND);
        }

        return author;
    }

    public void updateAuthor(Author authorToBeUpdated, String traceId) throws LibraryResourceNotFoundException {

        Optional<AuthorEntity> authorEntity = authorRepository.findById(authorToBeUpdated.getAuthorId());
        Author author = null;

        if(authorEntity.isPresent()) {

            AuthorEntity ae = authorEntity.get();
            if(authorToBeUpdated.getDateOfBirth() != null) {
                ae.setDateOfBirth(authorToBeUpdated.getDateOfBirth());
            }
            if(authorToBeUpdated.getGender() != null) {
                ae.setGender(authorToBeUpdated.getGender());
            }
            authorRepository.save(ae);
            authorToBeUpdated = createAuthorFromEntity(ae);
        } else {
            throw new LibraryResourceNotFoundException("Author Id: " + authorToBeUpdated.getAuthorId() + " Not Found", traceId, HttpStatus.NOT_FOUND);
        }

    }


    public void deleteAuthor(Integer authorId, String traceId) throws LibraryResourceNotFoundException {

        try {
            authorRepository.deleteById(authorId);
        } catch(EmptyResultDataAccessException e) {
            logger.error("TraceId: {}, Author Id: {} Not Found", traceId, authorId, e);
            throw new LibraryResourceNotFoundException("Author Id: " + authorId + " Not Found", traceId, HttpStatus.NOT_FOUND);
        }
    }

    public List<Author> searchAuthor(String firstName, String lastName, String traceId) {

        List<AuthorEntity> authorEntities = null;
        if(LibraryApiUtils.doesStringValueExists(firstName) && LibraryApiUtils.doesStringValueExists(lastName)) {
            authorEntities = authorRepository.findByFirstNameAndLastNameContaining(firstName, lastName);
        } else if(LibraryApiUtils.doesStringValueExists(firstName) && !LibraryApiUtils.doesStringValueExists(lastName)) {
            authorEntities = authorRepository.findByFirstNameContaining(firstName);
        } else if(!LibraryApiUtils.doesStringValueExists(firstName) && LibraryApiUtils.doesStringValueExists(lastName)) {
            authorEntities = authorRepository.findByLastNameContaining(lastName);
        }
        if(authorEntities != null && authorEntities.size() > 0) {
            return createAuthorsForSearchResponse(authorEntities);
        } else {
            return Collections.emptyList();
        }
    }

    private Author createAuthorFromEntity(AuthorEntity ae) {
        return new Author(ae.getAuthorId(), ae.getFirstName(), ae.getLastName(),
                ae.getDateOfBirth(), ae.getGender());
    }

    private List<Author> createAuthorsForSearchResponse(List<AuthorEntity> authorEntities) {
        return authorEntities.stream()
                .map(ae -> new Author(ae.getAuthorId(), ae.getFirstName(), ae.getLastName(), ae.getDateOfBirth(), ae.getGender()))
                .collect(Collectors.toList());
    }
}
