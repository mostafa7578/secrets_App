package model;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "blacklist")
@NoArgsConstructor
public class BlackList {

    @Id
    private String id;

    private String email; // email of the user


}
