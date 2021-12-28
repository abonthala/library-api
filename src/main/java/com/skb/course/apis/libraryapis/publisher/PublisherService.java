package com.skb.course.apis.libraryapis.publisher;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.skb.course.apis.libraryapis.exceptions.LibraryResourceAlreadyExistsException;
import com.skb.course.apis.libraryapis.exceptions.LibraryResourceNotFoundException;

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

	public Publisher getPublisher(Integer publisherId) throws LibraryResourceNotFoundException{
		Optional<PublisherEntity> publisher = publisherRepository.findById(publisherId);
		Publisher publish = null;
		if(publisher.isPresent())
		{
			PublisherEntity entity = publisher.get();
			publish = new Publisher(entity.getPublisherId(), entity.getName(), entity.getEmailId(), entity.getPhoneNumber());
		}
		else
		{
			throw new LibraryResourceNotFoundException("Publisher_Id "+publisherId+" Not Found");
		}
		return publish;	
	}
	
}
