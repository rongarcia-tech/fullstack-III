#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.fullstack.exceptions;

public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) { super(message); }
}