#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.fullstack.dtos;

public record UserRegistrationRequest(
     String username,
     String rawPassword,
     String email,
     String rol

) {
} 
