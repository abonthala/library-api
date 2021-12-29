package com.skb.course.apis.libraryapis.publisher;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<PublisherEntity, Integer>{

	List<PublisherEntity> findByName(String name);

}
