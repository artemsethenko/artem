package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "News")
@Getter
@Setter
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "title")
    private String title;
    @Column(name = "text")
    private String text;
    @JoinColumn(name = "category_title")
    @ManyToOne
    private Category category;
    @CreationTimestamp
    @Column(name = "date")
    private Instant date;
}
