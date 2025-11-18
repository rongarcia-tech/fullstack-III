#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dtos;

public record UserRegistrationRequest(
     String username,
     String rawPassword,
     String email,
     String rol

) {
} 
