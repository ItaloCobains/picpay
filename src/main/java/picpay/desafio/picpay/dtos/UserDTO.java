package picpay.desafio.picpay.dtos;

import java.math.BigDecimal;

import picpay.desafio.picpay.domain.user.UserType;


public record UserDTO(
    String firstName,
    String lastName,
    String document,
    String email,
    String password,
    BigDecimal balance,
    UserType userType
) {
    
}
