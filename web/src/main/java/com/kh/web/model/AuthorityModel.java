package com.kh.web.model;


import jakarta.validation.constraints.Size;
import lombok.*;


import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class AuthorityModel implements Serializable {

    private Integer id;

    @NonNull
    @Size
    private String name;
}
