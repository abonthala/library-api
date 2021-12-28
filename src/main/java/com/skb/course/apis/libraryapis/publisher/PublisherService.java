package com.skb.course.apis.libraryapis.publisher;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.skb.course.apis.libraryapis.exceptions.LibraryResourceAlreadyExistsException;
import com.skb.course.apis.libraryapis.exceptions.LibraryResourceNotFoundException;
import com.skb.course.apis.libraryapis.utils.LibraryApiUtils;

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

	public Publisher updatePublisher(Integer publisherId, Publisher publisher) throws LibraryResourceNotFoundException{
		Optional<PublisherEntity> publisherEntity = publisherRepository.findById(publisherId);
		Publisher publish = null;
		if(publisherEntity.isPresent())
		{
			PublisherEntity entity = publisherEntity.get();
			if(LibraryApiUtils.doesStringValueExists(publisher.getEmailId()))
			{
				entity.setEmailId(publisher.getEmailId());
			}
			if(LibraryApiUtils.doesStringValueExists(publisher.getPhoneNumber()))
			{
				entity.setPhoneNumber(publisher.getPhoneNumber());
			}
			entity.setName(publisher.getName());
			publisherRepository.save(entity);
			publish = new Publisher(entity.getPublisherId(), entity.getName(), entity.getEmailId(), entity.getPhoneNumber());
		}
		else
		{
			throw new LibraryResourceNotFoundException("Publisher_Id "+publisherId+" Not Found");
		}
		return publish;	
	}
	
}
