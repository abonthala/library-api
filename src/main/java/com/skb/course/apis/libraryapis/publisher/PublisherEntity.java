package com.skb.course.apis.libraryapis.publisher;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="\"PUBLISHER\"", schema="public")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PublisherEntity {
	
	@Column(name="\"Publisher_Id\"", nullable=false)
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="publisherid_generator")
	@SequenceGenerator(name="publisherid_generator", sequenceName="\"PUBLISHER_SEQUENCE\"", allocationSize=50)
	private Integer publisherId;
	
	@Column(name="\"Name\"", nullable=false, unique=true)
	private String name;
	
	@Column(name="\"Email_Id\"")
	private String emailId;
	
	@Column(name="\"Phone_Number\"")
	private String phoneNumber;

	public PublisherEntity(String name, String emailId, String phoneNumber) {
		super();
		this.name = name;
		this.emailId = emailId;
		this.phoneNumber = phoneNumber;
	}

}
