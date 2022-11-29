package com.communitygaming.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("tournaments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tournament {

    @Id
    private String id;
    private String name;
    private User creator;
    private List<User> participants;
}
