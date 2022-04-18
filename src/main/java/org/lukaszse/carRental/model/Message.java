package org.lukaszse.carRental.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "message")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Message {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Integer id;

    @NotBlank(message = "UserName cannot be empty")
    private String userName;

    @NotBlank(message = "Subject cannot be empty")
    private String subject;

    @NotBlank(message = "Message cannot be empty")
    private String content;

    @NotBlank(message = "Date cannot be empty")
    private LocalDate sentDate;

    private Boolean isRead;
}
