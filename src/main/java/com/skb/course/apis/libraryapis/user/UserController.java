package com.skb.course.apis.libraryapis.user;

import com.skb.course.apis.libraryapis.user.User;
import com.skb.course.apis.libraryapis.user.UserService;
import com.skb.course.apis.libraryapis.exceptions.LibraryResourceAlreadyExistsException;
import com.skb.course.apis.libraryapis.exceptions.LibraryResourceBadRequestException;
import com.skb.course.apis.libraryapis.exceptions.LibraryResourceNotFoundException;
import com.skb.course.apis.libraryapis.utils.LibraryApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/users")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Integer userId,
                                          @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId)
            throws LibraryResourceNotFoundException {

        if(!LibraryApiUtils.doesStringValueExists(traceId)) {
            traceId = UUID.randomUUID().toString();
        }

        return new ResponseEntity<>(userService.getUser(userId, traceId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody User user,
                                          @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId)
            throws LibraryResourceAlreadyExistsException {

        logger.debug("Request to add User: {}", user);
        if(!LibraryApiUtils.doesStringValueExists(traceId)) {
            traceId = UUID.randomUUID().toString();
        }
        logger.debug("Added TraceId: {}", traceId);
        userService.addUser(user, traceId);

        logger.debug("Returning response for TraceId: {}", traceId);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Integer userId,
                                             @Valid @RequestBody User user,
                                             @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId)
            throws LibraryResourceNotFoundException {

        if(!LibraryApiUtils.doesStringValueExists(traceId)) {
            traceId = UUID.randomUUID().toString();
        }

        user.setUserId(userId);
        userService.updateUser(user, traceId);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId,
                                             @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId)
            throws LibraryResourceNotFoundException {

        if(!LibraryApiUtils.doesStringValueExists(traceId)) {
            traceId = UUID.randomUUID().toString();
        }

        userService.deleteUser(userId, traceId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> searchUser(@RequestParam String firstName, @RequestParam String lastName,
                                             @RequestHeader(value = "Trace-Id", defaultValue = "") String traceId)
            throws LibraryResourceBadRequestException {

        if(!LibraryApiUtils.doesStringValueExists(traceId)) {
            traceId = UUID.randomUUID().toString();
        }

        if(!LibraryApiUtils.doesStringValueExists(firstName) && !LibraryApiUtils.doesStringValueExists(lastName)) {
            logger.error("TraceId: {}, Please enter at least one search criteria to search Users!!", traceId);
            throw new LibraryResourceBadRequestException("Please enter a name to search User.", traceId, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userService.searchUser(firstName, lastName, traceId), HttpStatus.OK);
    }
}
