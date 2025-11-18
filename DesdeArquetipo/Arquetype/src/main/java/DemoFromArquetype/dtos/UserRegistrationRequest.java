package DemoFromArquetype.dtos;

public record UserRegistrationRequest(
     String username,
     String rawPassword,
     String email,
     String rol

) {
} 
