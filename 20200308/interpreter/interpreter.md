# Interpreter Pattern (해석자 패턴) 

##  의도

- 어떤 언어에 대해, 그 언어의 **문법에 대한 표현(Expression)을 정의**하고 기술된 **문장을 해석하는 해석자(Interpreter)를 함께 정의**  
- Interpreter pattern is used to defines a grammatical representation for a language and provides an interpreter to deal with this grammar.

## 동기
- 문장의 표현과 해석을 정의함으로써 알고리즘을 매번 작성할 필요성을 없앤다.

### 예시 - 정규표현식과 패턴 매칭

- 정규 표현식(Regular Expression) 
    - [특정한 규칙을 가진 문자열의 집합을 표현하는 데 사용하는 형식 언어](https://ko.wikipedia.org/wiki/%EC%A0%95%EA%B7%9C_%ED%91%9C%ED%98%84%EC%8B%9D)

![./regular-expression.png](reqular-expression.png)    
- expression 은 문법의 시작 기호
- literal 은 간단한 단어를 정의하는 terminal 기호 
- 클래스를 이용하여 각각의 문법이 갖는 규칙을 정의

![./regular-expression-class.png](regular-expression-class.png)
- raining & (dogs | cats) *

![./raining-cats-and-dogs.png](raining-cats-and-dogs.png)    
- LiteralExpression 은 자신이 정의한 문자와 일치하는 정보가 입력 매개변수에 있는지 확인 
- AlternationExpression 은 입력 매개변수에 따른 대안 문자들과 일치하는 것이 있는지 확인
- RepetitionExpression 은 입력에 자신이 반복하는 표현에 대한 여러개의 본사본이 존재하는지 확인

```
    Expression dogsExpression = new LiteralExpression("dogs");
    Expression catsExpression = new LiteralExpression("cats");
    Expression catsOrDogsExpression = new AlternationExpression(dogsExpression, catsExpression);
    Expression repetitionExpression = new RepetitionExpression(catsOrDogsExpression);
    Expression rainingExpression = new LiteralExpression("raining");
    Expression finalExpression = new SequenceExpression(rainingExpression, repetitionExpression);
    
    boolean result = finalExpression.interpret(context) 

```
    
### 활용성
- 해석이 필요한 언어가 존재하거나 추상구문 트리로서 언어의 문장을 표현하고자 할 때
- **[추상 구문 트리 (Abstract Syntax Tree)](https://en.wikipedia.org/wiki/Abstract_syntax_tree)**


###  구조 및 참여자
![./interpreter-structure.png](interpreter-structure.png)  
- AbstractExpression (RegularExpression): 
    - 추상 구문 트리에 속한 모든 노드에 해당하는 클래스들이 공통으로 가져야할 interpret() 를 추상으로 정의
- TerminalExpression (LiteralExpression): 
    - 문법에 정의한 터미널 기호와 관련된 해석 방법을 구현
- NonterminalExpression (AlternationExpression, RepetitionExpression, SequenceExpression): 
    - 문법의 오른편에 나타나는 모든 기호에 대해서 클래스를 정의
- Context: 번역기에 제공되는 정보를 포함한다
- Client: 추상 구문 트리로 만든다. interpret() 를 실행한다  

    
### 협력 방법
- 사용자는 NonterminalExpression 과 TerminalExpression 인스턴스들로 해당 문장에 대한 추상 구문 트리를 만든다
- 각 NonterminalExpression 는 또 다른 서브표현식에 대한 interpret()를 이용하여 자신의 interpret()를 정의
- 각 노드에 정의한 interpret() 연산은 해석자의 상태를 저장하거나 그걸을 알기 위해 문맥(context)를 이용 
  
### 결과
- pros
    - 문법의 변경과 확징이 쉽다 
        - 문법에 정의된 규칙을 클래스로 표현했기 때문에 문법의 변경이나 확장은 상속을 통해 가능
        - 확장을 통해 기존의 표현식을 수정하거나 새로운 서브클래스 정의를 통해 새로운 표현식 정의 가능
    - 문법의 구현이 용이하다
        - 추상 구문트리의 노드에 해당하는 클래스들은 비슷한 구현방법을 갖기 때문에 작성하기 쉽고 컴파일러나 파서 생성기로 자동 생성도 가능
    - 표현식을 해석하는 새로운 방법 추가 가능
        - 새로운 연산을 정의하며 새로운 방식으로 해석하는 방법을 추가 할 수 있다
- cons: 
    - 복잡한 문법은 관리 어려움
        - 각 규칙별로 적어도 하나의 클래스를 정의해야하고 BNF로 정의한 문법은 훨씬 많은 클래스 필요
            -문법이 복잡하면 클래스 계통이 복잽해지기 때문에 파서생성기를 쓰는 것이 낫다
                - 파서생성기 = 추상 구문 트리를 생성하지 않고 문장 해석이 가능

### 구현
- Composite 패턴과 겹치는 사항들이 많다 (composite-pattern 을 이용하여 interpreter-pattern 의 일부분 부현 가능)
    - Structural Design Patterns
        - Generally deal with **relationships** between entities, making it easier for these entities to work together.
    - Behavioral Design Patterns:
        - Used in **communications** between entities and make it easier and more flexible for these entities to communicate.
    - Interpreter 패턴에만 해당 되는 사항
        - 추상 구문 트리를 생성 
            - 해석자 패턴은 어떻게 추상 구문 트리를 생성하는 지는 다루지 않음 (파싱 과정에 관한 패턴이 아님)
        - Interpret() 연산을 정의 
        - Flyweight 패턴을 적용하여 terminal 기호를 공유 가능
        
### 잘 알려진 사용 예
- 객체지향 컴파일러 구현

- 가장 일반적인 형태는 복합체 패턴이 사용되는 곳에 해석자 패턴을 사용 가능
    - 그러나 복합체 패턴으로 정의한 클래스들이 하나의 언어구조를 정의 할때만 해석자 패턴 이라고 한다
    - AST는 Composite pattern의 instance 이다 


### 예제 코드
- Assume 
    - input: “<Number> in Binary” 또는 “<Number> in Hexadecimal”
    - output: “<Number> in Hexadecimal= <Number_Binary_String>” 또는" <Number> in Binary= <Number_Binary_String>” 


Step 0. Create Required Dto
```

/**
 * Simple entity / DTO for this example.
 *
 * @author GHajba
 *
 */
public class Book {

    private final String title;
    private final String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getTitel() {
        return this.title;
    }

    @Override
    public String toString() {
        return MessageFormat.format("''{0}'' by {1}", this.title, this.author);
    }
}

/**
 * A mock web service, actually does nothing useful. In a real world example you would abstract the functionality.
 *
 * @author GHajba
 *
 */
public class LeanPubWebService {

    public LeanPubWebService(String endpoint) {
        // we should initialize our web service here with the endpoint string
    }

    /**
     * @return list of all books in the web service
     */
    public List<Book> getAllBooks() {
        return Arrays.asList(
                new Book("Website Scraping with Python", "Gabor Laszlo Hajba"),
                new Book("Java 8 in Action", "Raul-Gabriel Urma"),
                new Book("Python 3 in Anger", "Gabor Laszlo Hajba"),
                new Book("XML Processing and Website Scraping with Java", "Gabor Laszlo Hajba"));
    }
}


```
Step 1. Create Interpreter Context 
```
/**
 * The context of the interpreter.
 *
 * @author GHajba
 *
 */
public class InterpreterContext {

    private final LeanPubWebService webService;

    /**
     * Constructor. Here we initialize our mock web service.
     *
     * @param endpoint
     *            the endpoint of the web service
     */
    public InterpreterContext(String endpoint) {
        this.webService = new LeanPubWebService(endpoint);
    }

    /**
     * @return all books from the web service
     */
    public List<Book> getAllBooks() {
        return this.webService.getAllBooks();
    }
}
```


Step 2. Create an Expression interface that will consume the interpreter context.
```
/**
 * This is the base class for all expressions.
 *
 * @author GHajba
 *
 */
public abstract class AbstractExpression {

    /**
     * This function interprets the given context to fit our purposes.
     *
     * It does not need to have always a String as a return value -- you can adapt the functionality as you need.
     *
     * @param context
     *            the context of the interpreter
     * @return an interpreted result
     */
    public abstract String interpret(InterpreterContext context);
}
```

Step 3. Create concrete classes implementing the above interface.
```
/**
 * Implementation of the {@link AbstractExpression}. This implementation interprets our book author search expression.
 *
 * @author GHajba
 *
 */
public class BookAuthorExpression extends AbstractExpression {

    private final String searchString;

    public BookAuthorExpression(String searchString) {
        this.searchString = searchString;
    }

    @Override
    public String interpret(InterpreterContext context) {
        final List<Book> books = context.getAllBooks();
        final StringBuilder result = new StringBuilder();
        for (final Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(this.searchString)) {
                result.append(book.toString());
                result.append("\n");
            }
        }
        return result.toString();
    }
}
```
Step 4. Create client application that will have the logic to **parse** the user input and pass it to correct expression and then use the output to generate the user response
```
/**
 * Our client and the main entry point of the application.
 *
 * @author GHajba
 *
 */
public class LeanPubClient {

    private final InterpreterContext context;

    public LeanPubClient(InterpreterContext context) {
        this.context = context;
    }

    /**
     * Interprets a string input of the form books by author '<string>'.
     *
     * As you can see, the expression is in the following format <searchType> by <searchAttribute> '<value>' where
     * <searchType> is the type of the search, in this particular case books; <searchAttribute> is the attribute where
     * to filter, in this example it is author; and <value> is the value of the filter criteria, in this example the
     * author's name.
     */
    public String interpret(String expression) {

        AbstractExpression exp = null;

        final String[] stringParts = expression.split(" ");
        final String searchType = stringParts[0];
        final String searchAttribute = stringParts[2];

        final String query = expression.substring(expression.indexOf("'") + 1, expression.lastIndexOf("'"));

        if (searchType.equals("books")) {
            if (searchAttribute.equals("author")) {
                exp = new BookAuthorExpression(query);
            }
        }
        if (exp != null) {
            return exp.interpret(this.context);
        }

        return "--";
    }

    public static void main(String... args) {
        final InterpreterContext context = new InterpreterContext("http://api.leanpub.com/");
        final LeanPubClient client = new LeanPubClient(context);

        final String result = client.interpret("books by author 'Gabor Laszlo Hajba'");
        System.out.println(result);
    }
}
```

### 참고자료
[Let’s Build A Simple Interpreter. Part 7: Abstract Syntax Trees](https://ruslanspivak.com/lsbasi-part7/)

[What is an Abstract Syntax Tree](https://blog.bitsrc.io/what-is-an-abstract-syntax-tree-7502b71bde27)

[On the Composite and Interpreter Design Patterns](https://dzone.com/articles/on-the-composite-and-interpreter-design-patterns)

[Design Patterns - Interpreter Pattern](https://www.tutorialspoint.com/design_pattern/interpreter_pattern.htm)

[Interpreter Design Pattern in Java](https://www.journaldev.com/1635/interpreter-design-pattern-java)

[Interpreter Design Pattern](https://javabeginnerstutorial.com/design-pattern/interpreter-design-pattern/https://javabeginnerstutorial.com/design-pattern/interpreter-design-pattern/)

[Easy patterns: Interpreter](https://itnext.io/easy-patterns-interpreter-58434c94304d)
