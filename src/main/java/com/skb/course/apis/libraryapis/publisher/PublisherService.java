package com.skb.course.apis.libraryapis.publisher;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.skb.course.apis.libraryapis.exceptions.LibraryControllerExceptionHandler;
import com.skb.course.apis.libraryapis.exceptions.LibraryResourceAlreadyExistsException;
import com.skb.course.apis.libraryapis.exceptions.LibraryResourceNotFoundException;
import com.skb.course.apis.libraryapis.utils.LibraryApiUtils;

@Service
public class PublisherService {
	
	@Autowired
	private PublisherRepository publisherRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(PublisherService.class);

	public PublisherService(PublisherRepository publisherRepository) {
		super();
		this.publisherRepository = publisherRepository;
	}

	public Publisher addPublisher(Publisher publisher, String traceId) throws LibraryResourceAlreadyExistsException{
		
		PublisherEntity publisherEntity = new PublisherEntity(publisher.getName(), 
															  publisher.getEmailId(), 
															  publisher.getPhoneNumber());
		PublisherEntity addedPublisher = null;
		try {
			addedPublisher = publisherRepository.save(publisherEntity);
		}
		catch(DataIntegrityViolationException e)
		{
			logger.error("TraceId: {} Publisher: {}",traceId,publisher, e);
			throw new LibraryResourceAlreadyExistsException("Publisher already exists.", traceId, HttpStatus.CONFLICT);
		}
		publisher.setPublisherId(addedPublisher.getPublisherId());
		return publisher;
	}

	public Publisher getPublisher(Integer publisherId, String traceId) throws LibraryResourceNotFoundException{
		Optional<PublisherEntity> publisher = publisherRepository.findById(publisherId);
		Publisher publish = null;
		if(publisher.isPresent())
		{
			PublisherEntity entity = publisher.get();
			publish = new Publisher(entity.getPublisherId(), entity.getName(), entity.getEmailId(), entity.getPhoneNumber());
		}
		else
		{
			logger.error("TraceId: {} PublisherId: {}",traceId,publisherId);
			throw new LibraryResourceNotFoundException("Publisher_Id "+publisherId+" Not Found", traceId, HttpStatus.NOT_FOUND);
		}
		return publish;	
	}

	public Publisher updatePublisher(Integer publisherId, Publisher publisher, String traceId) throws LibraryResourceNotFoundException{
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
			throw new LibraryResourceNotFoundException("Publisher_Id "+publisherId+" Not Found", traceId, HttpStatus.NOT_FOUND);
		}
		return publish;	
	}

	public void deletePublisher(Integer publisherId, String traceId) throws LibraryResourceNotFoundException{
		Optional<PublisherEntity> publisherEntity = publisherRepository.findById(publisherId);
		if(publisherEntity.isPresent())
		{
			PublisherEntity entity = publisherEntity.get();
			publisherRepository.delete(entity);
		}
		else
		{
			throw new LibraryResourceNotFoundException("Publisher_Id "+publisherId+" Not Found", traceId, HttpStatus.NOT_FOUND);
		}
		return;
	}

	public List<Publisher> searchPublisher(String name, String traceId) {
		List<PublisherEntity> publisherEntities = null;
		if(LibraryApiUtils.doesStringValueExists(name))
		{
			publisherEntities = publisherRepository.findByName(name);
		}
		if(publisherEntities != null && publisherEntities.size() > 0)
		{
			return createPublishersForSearchResponse(publisherEntities);
		}
		else
		{
			return Collections.emptyList();
		}
	}

	private List<Publisher> createPublishersForSearchResponse(List<PublisherEntity> publisherEntities) {
		return publisherEntities.stream().map((publisherEntity) -> new Publisher(publisherEntity.getPublisherId(),
																				 publisherEntity.getName(),
																				 publisherEntity.getEmailId(),
																				 publisherEntity.getPhoneNumber())).
				collect(Collectors.toList());
	}
	
}
