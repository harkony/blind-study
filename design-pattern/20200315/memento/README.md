# Memento 패턴

> 메멘토 패턴은 구현의 세부사항을 노출하지 않고 객체의 상태를 저장하거나 이전 상태로 복원할 수 있는 설계 패턴이다.

## Why

* 객체의 내부 상태가 외부적으로 저장되어야 이후에 객체가 그 상태를 복구할 수 있다.
  * 객체의 내부상태가 private이라면? 외부에 저장할 수 없다.
* 객체의 캡슐화가 훼손되지 않아야 한다.
  * 객체의 내부상태가 public이라면? unsafe

## 구조

![uml](https://upload.wikimedia.org/wikipedia/commons/3/38/W3sDesign_Memento_Design_Pattern_UML.jpg)

* Originator: 상태를 가지고 있는 객체.
  * `createMemento()`: 내부 상태를 가진 메멘토 객체를 생성한다.
  * `restore()`: 메멘토 객체를 인자로 받아 이전 상태로 복원한다.
* Memento: 특정 시점의 Originator 상태를 저장
  * Memento가 가진 내부 상태는 Originator만이 접근할 수 있다. (optional?)
  * 불변객체(Immutable)여야 함
* Caretaker: Originator의 상태 이력을 알고 있다.
  * Originator 객체를 조작한다. (상태 변경, 이력 저장, ...)
  * 특정 시점의 상태를 기억하고 원하는 시점으로 Originator 객체의 상태를 복원한다.

### 메멘토를 인터페이스로 설계헤야 하는가?

* 인터페이스로 설계하면: 상태를 반환하는 메서드가 외부에 공개된다.
* 이너 클래스로 설계하면: 코드가 지저분해진다. (`Foo.Memento`) 대신 메멘토가 가진 상태는 Originator만 접근 가능

## 구현: TextEditor

[소스코드](https://github.com/sshplendid/design-pattern-study/blob/master/src/main/java/shawn/designpattern/memento/EditorOriginator.java)

```java
public class EditorOriginator {
    private LocalDateTime savedDateTime;
    private String content = "";

    public EditorOriginator() {
    }

    public EditorOriginator(LocalDateTime savedDateTime, String content) {
        this.savedDateTime = savedDateTime;
        this.content = content;
    }

    /**
     * 메멘토(스냅샷)을 생성한다.
     * @return 현재 상태의 메멘토 객체
     */
    public EditorMemento createMemento() {
        System.out.println("create a snapshot.");
        return new EditorMemento(LocalDateTime.now(), this.content);
    }

    /**
     * 메멘토(스냅샷)을 인자로 받아 이전 상태로 복구한다.
     * @param memento 복구하려는 메멘토 객체
     */
    public void restoreFrom(EditorMemento memento) {
        this.savedDateTime = memento.getState().savedDateTime;
        this.content = memento.getState().content;

        System.out.println("Restore state from " + savedDateTime);
    }

    public void write(String newContent) {
        this.content += newContent;
    }

    public void viewContent() {
        System.out.println("===content: START===");
        System.out.println(this.content);
        System.out.println("===content: END===");
    }

    class EditorMemento {

        private LocalDateTime savedDateTime;
        private String content;

        private EditorMemento(LocalDateTime savedDateTime, String content) {
            this.savedDateTime = savedDateTime;
            this.content = content;
        }

        // 메멘토가 가진 상태는 아우터 클래스에서만 접근 가능하다.
        private EditorOriginator getState() {
            return new EditorOriginator(this.savedDateTime, this.content);
        }
    }
}
```

## 특징

* 중첩 클래스(nested class)를 언어 레벨에서 지원하는 경우 Memento 클래스를 Originator의 중첩 클래스로 선언한다.
  * 그렇지 않은 경우, 메멘토 객체를 생성할 때 오리지네이터 객체를 인자로 받아서 복원을 수행한다.

* 장점
  * 캡슐화를 깨지 않고 스냅샷을 생성할 수 있다.
  * Caretaker가 상태를 관리하기 때문에 Originator 코드가 간결해진다.
* 단점
  * 메멘토의 메모리, 라이프사이클 관리가 필요하다.
  * 언어레벨의 지원 필요(동적 언어에서 접근제어 방식이 있는지?)

## 다른 패턴과의 관계

* **명령(Command)** 패턴과 책임을 나눌 수 있다.
  * Undo(Command), Save(Memento)
* **원형(Prototype)** 패턴은 메멘토의 대안이 될 수 있다.
  * 저장하려는 상태가 매우 간단하거나, 외부 자원에 대한 링크가 없을 때