package com.skb.course.apis.libraryapis.publisher;

import java.util.UUID;

import javax.validation.Valid;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skb.course.apis.libraryapis.exceptions.LibraryResourceAlreadyExistsException;
import com.skb.course.apis.libraryapis.exceptions.LibraryResourceBadRequestException;
import com.skb.course.apis.libraryapis.exceptions.LibraryResourceNotFoundException;
import com.skb.course.apis.libraryapis.utils.LibraryApiUtils;

@RestController
@RequestMapping(path = "/v1/publishers")
public class PublisherController {
	
	@Autowired
	private PublisherService publisherService;
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(PublisherController.class);

    @GetMapping(path = "/{publisherId}")
    public ResponseEntity<?> getPublisher(@PathVariable("publisherId") Integer publisherId,
    									  @RequestHeader(value="Trace-Id", defaultValue="") String traceId) {
    	if(!LibraryApiUtils.doesStringValueExists(traceId))
    	{
    		traceId = UUID.randomUUID().toString();
    	}
    	Publisher publisher = null;
    	publisher = publisherService.getPublisher(publisherId, traceId);
    	logger.info("getPublisher Controller {}", traceId);
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }
    
    @PostMapping(path = "/add")
    public ResponseEntity<?> addPublisher(@Valid @RequestBody Publisher publisher, 
    									  @RequestHeader(value="Trace-Id", defaultValue="") String traceId)
    {
    	if(!LibraryApiUtils.doesStringValueExists(traceId))
    	{
    		traceId = UUID.randomUUID().toString();
    	}
    	publisher = publisherService.addPublisher(publisher, traceId);
    	logger.info("addPublisher Controller {}", traceId);
		return new ResponseEntity<>(publisher, HttpStatus.CREATED);
    }
    
    @PutMapping(path = "/{publisherId}")
    public ResponseEntity<?> updatePublisher(@PathVariable("publisherId") Integer publisherId,
    									  @Valid @RequestBody Publisher publisher,
    									  @RequestHeader(value="Trace-Id", defaultValue="") String traceId) {
    	if(!LibraryApiUtils.doesStringValueExists(traceId))
    	{
    		traceId = UUID.randomUUID().toString();
    	}
    	Publisher updatedPublisher = null;
    	updatedPublisher = publisherService.updatePublisher(publisherId, publisher, traceId);
    	logger.info("updatePublisher Controller {}", traceId);
        return new ResponseEntity<>(updatedPublisher, HttpStatus.OK);
    }
    
    @DeleteMapping("{publisherId}")
    public ResponseEntity<?> deletePublisher(@PathVariable("publisherId") Integer publisherId,
    		                                 @RequestHeader(value="Trace-Id", defaultValue="") String traceId)
    {
    	if(!LibraryApiUtils.doesStringValueExists(traceId))
    	{
    		traceId = UUID.randomUUID().toString();
    	}
    	publisherService.deletePublisher(publisherId, traceId);
    	logger.info("deletePublisher Controller {}", traceId);
    	return new ResponseEntity<>("Publisher Successfully Deleted.", HttpStatus.OK);
    }
    
    @GetMapping("/search")
    public ResponseEntity<?> searchPublisher(@RequestParam("Name") String name,
    		                                 @RequestHeader(value="Trace-Id", defaultValue="") String traceId) throws LibraryResourceBadRequestException
    {
    	if(!LibraryApiUtils.doesStringValueExists(traceId))
    	{
    		traceId = UUID.randomUUID().toString();
    	}
    	if(!(LibraryApiUtils.doesStringValueExists(name)))
    	{
    		logger.error("searchPublisher Controller {}", traceId);
    		throw new LibraryResourceBadRequestException("Trace-Id "+traceId+" Please enter a name to search publisher.", traceId, HttpStatus.BAD_REQUEST);
    	}
    	logger.info("searchPublisher Controller {}", traceId);
    	return new ResponseEntity<>(publisherService.searchPublisher(name, traceId), HttpStatus.OK);
    }
}
