package com.skb.course.apis.libraryapis.testUtils;

import com.skb.course.apis.libraryapis.publisher.Publisher;
import com.skb.course.apis.libraryapis.publisher.PublisherEntity;

public class LibraryApiTestUtil {
	
	public static Publisher createPublisher()
	{
		return new Publisher(null, TestConstants.TEST_PUBLISHER_NAME, TestConstants.TEST_PUBLISHER_EMAIL, 
							TestConstants.TEST_PUBLISHER_PHONE);
	}

	public static PublisherEntity createPublisherEntity() {
		return new PublisherEntity(TestConstants.TEST_PUBLISHER_NAME, TestConstants.TEST_PUBLISHER_EMAIL, 
				TestConstants.TEST_PUBLISHER_PHONE);
	}

}
