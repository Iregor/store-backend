package ru.acceleration.store.dto.sale;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewSaleDto {

    @NotBlank
    @Positive
    private Long modelId;

    @NotBlank
    @Positive
    private Integer percent;
}
