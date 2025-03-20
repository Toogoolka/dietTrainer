package ru._1221systems.trainer.util.exceptions;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String msg;
    private String status;
    private String details;

}
