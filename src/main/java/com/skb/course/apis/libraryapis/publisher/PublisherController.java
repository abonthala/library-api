package com.skb.course.apis.libraryapis.publisher;

import java.util.UUID;

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
import com.skb.course.apis.libraryapis.exceptions.LibraryResourceNotFoundException;
import com.skb.course.apis.libraryapis.utils.LibraryApiUtils;

@RestController
@RequestMapping(path = "/v1/publishers")
public class PublisherController {
	
	@Autowired
	private PublisherService publisherService;

    @GetMapping(path = "/{publisherId}")
    public ResponseEntity<?> getPublisher(@PathVariable("publisherId") Integer publisherId,
    									  @RequestHeader(value="Trace-Id", defaultValue="") String traceId) {
    	if(!LibraryApiUtils.doesStringValueExists(traceId))
    	{
    		traceId = UUID.randomUUID().toString();
    	}
    	Publisher publisher = null;
    	try {
    		publisher = publisherService.getPublisher(publisherId, traceId);
    	}
    	catch(LibraryResourceNotFoundException e) {
    		return new ResponseEntity<>("Trace-Id "+traceId+" "+e.getMessage(), HttpStatus.NOT_FOUND);
    	}
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }
    
    @PostMapping(path = "/add")
    public ResponseEntity<?> addPublisher(@RequestBody Publisher publisher, 
    									  @RequestHeader(value="Trace-Id", defaultValue="") String traceId)
    {
    	if(!LibraryApiUtils.doesStringValueExists(traceId))
    	{
    		traceId = UUID.randomUUID().toString();
    	}
    	try 
    	{
    		publisher = publisherService.addPublisher(publisher, traceId);
    	}
    	catch(LibraryResourceAlreadyExistsException e)
    	{
    		return new ResponseEntity<>("Trace-Id "+traceId+" "+e.getMessage(), HttpStatus.CONFLICT);
    	}
		return new ResponseEntity<>(publisher, HttpStatus.CREATED);
    }
    
    @PutMapping(path = "/{publisherId}")
    public ResponseEntity<?> getPublisher(@PathVariable("publisherId") Integer publisherId,
    									  @RequestBody Publisher publisher,
    									  @RequestHeader(value="Trace-Id", defaultValue="") String traceId) {
    	if(!LibraryApiUtils.doesStringValueExists(traceId))
    	{
    		traceId = UUID.randomUUID().toString();
    	}
    	Publisher updatedPublisher = null;
    	try {
    		updatedPublisher = publisherService.updatePublisher(publisherId, publisher, traceId);
    	}
    	catch(LibraryResourceNotFoundException e) {
    		return new ResponseEntity<>("Trace-Id "+traceId+" "+e.getMessage(), HttpStatus.NOT_FOUND);
    	}
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
    	try {
    		publisherService.deletePublisher(publisherId, traceId);
    	}
    	catch(LibraryResourceNotFoundException e) {
    		return new ResponseEntity<>("Trace-Id "+traceId+" "+e.getMessage(), HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<>("Publisher Successfully Deleted.", HttpStatus.OK);
    }
    
    @GetMapping("/search")
    public ResponseEntity<?> searchPublisher(@RequestParam("Name") String name,
    		                                 @RequestHeader(value="Trace-Id", defaultValue="") String traceId)
    {
    	if(!LibraryApiUtils.doesStringValueExists(traceId))
    	{
    		traceId = UUID.randomUUID().toString();
    	}
    	if(!(LibraryApiUtils.doesStringValueExists(name)))
    	{
    		return new ResponseEntity<>("Trace-Id "+traceId+" Please enter a name to search publisher.", HttpStatus.BAD_REQUEST);
    	}
    	return new ResponseEntity<>(publisherService.searchPublisher(name, traceId), HttpStatus.OK);
    }
}
