package com.example.project.model.entity;

import com.example.project.model.embeddable.DeliveryInformation;
import com.mongodb.lang.NonNull;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity {
    @Indexed(unique = true)
    @NonNull
    private String username;
    @Indexed(unique = true)
    @NonNull
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @NonNull
    private String password;
    private Set<RoleEntity> roles = new HashSet<>();
    private LocalDate lastActiveDate = LocalDate.now();
    private DeliveryInformation deliveryInformation;


}

