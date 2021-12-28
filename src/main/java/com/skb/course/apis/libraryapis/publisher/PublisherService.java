package com.skb.course.apis.libraryapis.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.skb.course.apis.libraryapis.exceptions.LibraryResourceAlreadyExistsException;

@Service
public class PublisherService {
	
	@Autowired
	private PublisherRepository publisherRepository;

	public Publisher addPublisher(Publisher publisher) throws LibraryResourceAlreadyExistsException{
		
		PublisherEntity publisherEntity = new PublisherEntity(publisher.getName(), 
															  publisher.getEmailId(), 
															  publisher.getPhoneNumber());
		PublisherEntity addedPublisher = null;
		try {
			addedPublisher = publisherRepository.save(publisherEntity);
		}
		catch(DataIntegrityViolationException e)
		{
			throw new LibraryResourceAlreadyExistsException("Publisher already exists.");
		}
		publisher.setPublisherId(addedPublisher.getPublisherId());
		return publisher;
	}
	
}
