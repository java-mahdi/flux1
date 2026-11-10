package com.kh.userservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPageableDto {
 @NotNull
 @Min(0)
 private Integer number ;
 @NotNull
 @Min(1)
 private Integer size;

 private String sortBy="username";
 private Boolean asc =true;
}
