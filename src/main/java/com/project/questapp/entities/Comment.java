package com.project.questapp.entities;

import java.sql.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name="comment")
@Data
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // database otomatik olarak id üretecek
	Long id;
	
	@ManyToOne(fetch=FetchType.LAZY) // Bir post altında birden fazla yorum olabilir // Fetchtype : post çekilince userı çekme
	@JoinColumn(name="post_id",nullable = false) // Post ile user tablosu bağlandı ve null olamaz
	@OnDelete(action = OnDeleteAction.CASCADE) // Bir user silinince postları da silinir
	@JsonIgnore 
	Post post;
	
	@ManyToOne(fetch=FetchType.LAZY) // Bir user birden fazla post atabilir. // Fetchtype : post çekilince userı çekme
	@JoinColumn(name="user_id",nullable = false) // Post ile user tablosu bağlandı ve null olamaz
	@OnDelete(action = OnDeleteAction.CASCADE) // Bir user silinince postları da silinir
	@JsonIgnore 
	User user;
	
	@Lob
	@Column(columnDefinition = "text") // String i text olarak algılaması için yapıldı
	String Text;
	
	@Temporal(TemporalType.TIMESTAMP) // Ne zaman gerçekleştiğini söyler
	Date createDate;
}
