package br.com.micro.bookservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Entity(name="book")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author", nullable = false, length = 180)
    private String author;

    @Column(name = "launch_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date launchDate;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false, length = 250)
    private String title;

    @Transient
    private String currency;

    @Transient
    private String environment;
}
