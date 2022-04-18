package org.lukaszse.carRental.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "message")
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(generator = "inc")
    @GenericGenerator(name = "inc", strategy = "increment")
    private Integer id;

    private String userName;

    @NotBlank(message = "Subject cannot be empty")
    private String subject;

    @NotBlank(message = "Message cannot be empty")
    private String content;

    private LocalDate sentDate;

    private Boolean isRead;

    public static Message of(final Message message, @NotBlank final String userName, @NotNull final LocalDate sentDate) {
        return new Message(message.getId(), userName, message.getSubject(), message.getContent(), sentDate, false);
    }
}
