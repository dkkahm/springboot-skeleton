package com.example.homegym.ui.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {
    @NotNull
    @Length(min = 4, max = 64)
    String accountId;
}
