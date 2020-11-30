package co.com.jsierra.webfluxmysql.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table("users")
public class UserModels {
    @Id
    private String id;
    private String username;
    private String password;
    private String role;
    private String state;
}