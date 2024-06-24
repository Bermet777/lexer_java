import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Lexer implements Iterable<Lexer.Token>{

  private final String input;
  private final List<Token> tokens;
  private int current;

  public Lexer(String input) {
    this.input = input;
    this.tokens = new ArrayList<Token>();
    this.current = 0;
    tokenize();
  }

  private void tokenize() {
    while (current < input.length()) {
      char ch = input.charAt(current);
      switch (ch) {
        case ' ':
        case '\t':
        case '\n':
        case '\r':
            current++;
            break;
        case '=':
             tokens.add(new Token(TokenType.ASSIGNMENT, "="));
             current++;
             break;
        case '+':
          
        case '-':
            
        case '*':
             
        case '/':

              tokens.add(new Token(TokenType.OPERATOR,  Character.toString(ch)));
              current++;
              break;
        case '>':

        case '<':
              tokens.add(new Token(TokenType.COMPARISON_OPERATOR,  Character.toString(ch)));
              current++;
              break;      
        case ';':
              tokens.add(new Token(TokenType.SEPARATOR, ";"));
              current++;
              break;  
        case '(':
              tokens.add(new Token(TokenType.PARENTHESES, "("));
              current++;
              break;

        case ')':
              tokens.add(new Token(TokenType.PARENTHESES, ")"));
              current++;
              break;  
        case '}':
              tokens.add(new Token(TokenType.BRACES, "}"));
              current++;
              break;
        case '{':
               tokens.add(new Token(TokenType.BRACES, "{"));
               current++;
               break;              
    
        default:
              if (isDigit(ch)) {
                tokens.add(new Token(TokenType.NUMBER, readNumber()));
              } else if (isAlpha(ch)) {
                String word = readWord();
                tokens.add(new Token(deriveTokenType(word), word));
              }   else {
                throw new LexerException("Unsupported character: " + ch);
              }         
                               
      }
    }

  }

  private TokenType deriveTokenType(String word) {
    switch (word) {
      case "if":
        return TokenType.IF;
      case "else":
        return TokenType.ELSE;
      case "print":
        return TokenType.PRINT;
            
      default:
        return TokenType.IDENTIFIER;  
    }
  }

  private String readWord() {
    StringBuilder builder = new StringBuilder();
    while (current < input.length() && isAlphaNumeric(input.charAt(current))) {
      builder.append(input.charAt(current));
      current++;
    }
    return builder.toString();
  }

  private String readNumber() {
    StringBuilder builder = new StringBuilder();
    while (current < input.length() && isDigit(input.charAt(current))) {
      builder.append(input.charAt(current));
      current++;
    }
    return builder.toString();
  }

 
  private boolean isAlphaNumeric(char c) {
    return isAlpha(c) | isDigit(c);
  }

  private boolean isAlpha(char c) {
    return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || (c == '_');
  }

  private boolean isDigit(char c) {
    return '0' <= c && c <= '9';
  }

  @Override
  public Iterator<Token> iterator() {
    return tokens.iterator();
  }

  static class Token {
    final TokenType type;
    final String value;

    Token(TokenType type, String value) {
      this.type = type;
      this.value = value;
    }

    @Override
    public String toString() {
      return "Token{" +
              "type=" + type +
              ", value='" + value + '\'' +
              '}';
    }

  }

  enum TokenType {
      NUMBER, IDENTIFIER, OPERATOR, ASSIGNMENT, VARIABLE, SEPARATOR,PARENTHESES,BRACES,IF,ELSE,PRINT, COMPARISON_OPERATOR
  }

}
