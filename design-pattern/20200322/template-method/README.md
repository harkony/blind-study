# 템플릿 메서드 패턴

> 템플릿 메소드 패턴은 상위 클래스에서 기본적인 구조를 정의하고, 상속받은 하위 클래스에서 특정 부분을 구현하도록 해 동한 구조를 유지하게 하는 디자인 패턴이다.

## Why

* 유사한 기능을 가진 여러 클래스가 존재하고, 조건에 따른 분기 처리된 코드가 발생한다.

## 구조

![uml](https://refactoring.guru/images/patterns/diagrams/template-method/structure-2x.png)

* 추상 클래스
  * 변하지 않는 템플릿 메서드가 존재한다. 템플릿 메서드는 변하지 않지만, 특정 부분이나 세부 구현은 여러가지 알고리즘으로 구현이 가능하다.
    * 템플릿 메서드는 상속 불가능한 `final`로 선언되기도 한다.
  * 세부적인 구현 중 변경이 가능한 메서드가 존재한다. 보통 추상 메서드로 선언되어 있거나, 디폴트 구현이 존재할 수도 있다.
* 구체 클래스:
  * 템플릿 메서드의 '변경 가능한 부분'을 구현한다.
  * 템플릿 메서드 자체를 오버라이드해서는 안된다.

## 구현: 데이터 마이너

Word, Excel, CSV, PDF 등의 파일을 읽어서 데이터 마이닝 후 리포트를 생성하는 애플리케이션을 설계한다고 가정하자. 각 파일의 포맷은 다르지만 데이터를 분석하고 리포트를 생성하는 기능은 동일하게 사용한다.

[Refactoring Guru](https://refactoring.guru/design-patterns/template-method)

## 예제: `java.util.AbstractList`

엄밀히 말하면 템플릿 메서드 패턴이라고 보기 어려운 부분이 존재한다.

* 템플릿 메서드의 부재: 어떤 메서드를 템플릿 메서드라고 부를 수 있을까?
  * `addAll(Collection<E> c)`: 이마저도 모두 오버라이드하고 있음
* 추상 메서드는 `get(int index)` 한 건만 존재한다.
  * add, remove 등은 디폴트로 예외처리

`ArrayList`와 `LinkedList`는 `get(int index)`의 구현이 다르다.

* ArrayList: 배열에서 인덱스로 조회, O(1)
* LinkedList: 노드의 전/후에서 순회조회, O(N)

동일한 기능을 구현하지만 알고리즘을 다르게 구현할 수 있다.

```java
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{

    public E get(int index) {
        rangeCheck(index);

        return elementData(index);
    }
    
    E elementData(int index) {
        return (E) elementData[index];
    }
}
```

```java
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
{
    public E get(int index) {
        checkElementIndex(index);
        return node(index).item;
    }

    Node<E> node(int index) {
        // assert isElementIndex(index);

        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }
}
```

```java
package com.sun.jmx.remote.internal;

public class ArrayQueue<T> extends AbstractList<T> {
      public T get(int i) {
        int size = size();
        if (i < 0 || i >= size) {
            final String msg = "Index " + i + ", queue size " + size;
            throw new IndexOutOfBoundsException(msg);
        }
        int index = (head + i) % capacity;
        return queue[index];
    }
}
```

## 특징

* 장점
  * 클라이언트가 특정 부분을 오버라이드 해서 변경에 대한 영향도가 덜하다.
  * 여러 클라이언트가 공통으로 사용하는 코드를 상위클래스에서 정의할 수 있다.
* 단점
  * 기본 구조를 강제하기 때문에 클라이언트에 따라 기능의 제한이 발생할 수 있다.
  * 기본 구현을 강제하여 LSP를 위반할 여지가 있다.
    * 설계에 따라 달라지지 않을까?
  * 세부 구현이 많아질수록 유지보수가 어렵다.

## 다른 패턴과의 관계

* **팩토리 메서드 패턴**은 템플릿 메서드 패턴의 특수한 형태이다. 팩토리 메서드 패턴이 템플릿 메서드의 내부 구현으로 동작할 수 있다.
* 템플릿 메서드 패턴은 상속에 기반한다. **전략(Strategy) 패턴**은 **컴포지션**에 기반한다.
  * 템플릿 메서드 패턴은 클래스 레벨에서 동작하고, 전략 패턴은 객체 레벨에서 동작하기 때문에 런타임에 변경할 수 있다.

## 참고

* [Refactoring Guru](https://refactoring.guru/design-patterns/template-method)
* [상속의 위험성](http://redutan.github.io/2018/04/21/dangers-of-inheritance)