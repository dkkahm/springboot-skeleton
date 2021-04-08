package com.example.homegym.ui.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {
    @Length(min = 4, max = 64)
    String accountId;
}
