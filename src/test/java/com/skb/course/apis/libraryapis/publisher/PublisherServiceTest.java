package com.skb.course.apis.libraryapis.publisher;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.skb.course.apis.libraryapis.testUtils.LibraryApiTestUtil;
import com.skb.course.apis.libraryapis.testUtils.TestConstants;

@RunWith(MockitoJUnitRunner.class)
public class PublisherServiceTest {
	
	@Mock
	PublisherRepository publisherRepository;
	
	PublisherService publisherService;

	@Before
	public void setUp() throws Exception {
		publisherService = new PublisherService(publisherRepository);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddPublisher() {
		when(publisherRepository.save(Mockito.any(PublisherEntity.class))).
		thenReturn(LibraryApiTestUtil.createPublisherEntity());
		Publisher publisher = LibraryApiTestUtil.createPublisher();
		publisherService.addPublisher(publisher, TestConstants.TEST_TRACE_ID);
		
		verify(publisherRepository, times(1)).save(Mockito.any(PublisherEntity.class));
		assertNull(publisher.getPublisherId());
		assertTrue(publisher.getName().equals(TestConstants.TEST_PUBLISHER_NAME));
	}

	//@Test
	public void testGetPublisher() {
		fail("Not yet implemented");
	}

	//@Test
	public void testUpdatePublisher() {
		fail("Not yet implemented");
	}

	//@Test
	public void testDeletePublisher() {
		fail("Not yet implemented");
	}

	//@Test
	public void testSearchPublisher() {
		fail("Not yet implemented");
	}

}
