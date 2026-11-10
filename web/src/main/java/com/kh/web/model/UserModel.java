package com.kh.web.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class UserModel implements Serializable {


    private Integer id;

    @NonNull
    @Size(min = 3)
    @NotBlank
    private String username;
    @NonNull
    @NotBlank
    private String password;
    @NonNull
    @NotBlank
    private String firstName;
    @NonNull
    @NotBlank
    private String lastName;

    @ToString.Exclude
    List<AuthorityModel> authorities = new ArrayList();


}
