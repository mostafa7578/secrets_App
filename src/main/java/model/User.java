package model;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String email; // email of the user

    private String password; // password of the user

    private String name; // name of the user

}
