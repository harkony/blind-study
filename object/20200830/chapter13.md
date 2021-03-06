# 서브클래싱과 서브타이핑

#### 상속의 용도를 2가지로 볼 수 있다.
  * 타입 계층을 구현한 것
    * 타입 계층 안에서 부모 클래스는 일반적인 개념을 구현하고, 자식 클래스는 특수한 개념을 구현한다.
  * 코드를 재사용하는 것
    * 상속은 간단한 선언만으로 부모 클래스의 코드를 재사용할 수 있다.
    * 하지만, 재사용을 위해 상속을 사용할 경우 부모 클래스와 자식 클래스가 강하게 결합되기 때문에 변경하기 어려운 코드를 얻게 될 확률이 높다.

```
상속을 사용하는 일차적인 목표는 코드 재사용이 아니라 타입 계층을 구현하는 것이어야 한다.
타입 계층을 목표로 상속을 사용하면 다형적으로 동작하는 객체들의 관계에 기반해 확장 가능하고 유연한 설계를 얻을 수 있게 된다.
```

#### 상속의 가치
* 결론부터 말하자면 동일한 메시지(동일한 퍼블릭 메소드)에 대해 서로 다르게 행동할 수 있는 
다형적인 객체를 구현하기 위해서는 객체의 행동을 기반으로 타입 계층을 구성해야한다.
* 상속의 가치는 이러한 타입 계층을 구현할 수 있는 쉽고 편안한 방법을 제공하는 데 있다.
* 타입 사이의 관계를 고려하지 않은 채 단순히 코드를 재사용하기 위해 상속을 사용해서는 안된다.


## 1. 타입
### 객체 관점의 타입
* 타입은 사물을 분류하기 위한 틀이다.
* 같은 관점에서, 가령 자바, 루비, 자바스크립트, C를 `프로그래밍 언어`라는 타입으로 분류할 수 있다.
* 어떤 대상이 타입으로 분류될 때 그 대상을 타입의 `인스턴스(객체)`라고 부른다.
#### 타입의 요소
* 심볼(Symbol): 타입에 이름을 붙인 것이다. `프로그래밍 언어`는 심볼이다.
* 내연(Intention): 타입의 정의로, 타입에 속한 객체들이 가지는 공통적인 속성이나 행동을 가리킨다. 
* 외연(Extension): 타입에 속한 객체들의 집합이다.

### 프로그래밍 언어 관점의 타입
* 프로그래밍 언어 관점에서 타입은 연속적인 비트에 의미와 제약을 부여하기 위해 사용된다.
* 비트에 담긴 데이터를 문자열로 다룰지, 정수로 다룰지는 전적으로 데이터를 사용하는 애플리케이션에 의해 결정된다.
* 타입은 비트 묶음에 의미를 부여하기 위해 정의된 제약과 규칙이다.

#### 타입을 사용하는 두 가지 목적
##### 타입에 수행될 수 있는 유효한 오퍼레이션의 집합을 정의
* 객체의 타입에 따라 적용 가능한 연산자의 종류를 제한함
* 프로그래머의 실수를 막아줄 수 있음

##### 타입에 수행되는 오퍼레이션에 대해 미리 약속된 문맥을 제공함
* 객체에 부여된 타입이 연산자(예, `+`)의 문맥을 정의함
* 어떤 연산이 실행될지는 객체의 타입이 결정함

**타입은 적용 가능한 오퍼레이션의 종류와 의미를 정의함**


### 객체지향 패러다임 관점의 타입
타입을 두가지 관점에서 정의할 수 있음
* 객념 관점에서 타입이란 공통의 특징을 공유하는 대상들의 분류
* 프로그래밍 언어 관점에서 타입이란 동일한 오퍼레이션을 적용할 수 있는 인스턴스들의 집합

이들을 객체지향 패러다임의 관점에서 조합해보면,
* 타입은 호출 가능한 오퍼레이션의 집합을 정의한다.
* 오퍼레이션은 객체가 수신할 수 있는 메시지를 의미
* 객체의 타입이란 객체가 수신할 수 있는 메시지의 종류를 정의함

`퍼블릭 인터페이스`가 바로 객체가 수신할 수 있는 메시지의 집합을 가리키는 용어
* 동일한 퍼블릭 인터페이스를 가지는 객체들은 동일한 타입으로 분류할 수 있다.

객체에게 중요한 것은 속성이 아니라 행동이라는 사실이다. 
* 어떤 객체들이 동일한 상태를 가지고 있더라도 퍼블릭 인터페이스가 다르다면 이들은 서로 다른 타입으로 분류된다.
* 반대로 어떤 객체들이 내부 상태는 다르지만 동일한 퍼블릭 인터페이스를 공유한다면 이들은 동일한 타입으로 분류된다.

> 객체를 바라볼 때는 항상 객체가 외부에 제공하는 행동에 초점을 맞춰야한다.
> 객체의 타입을 결정하는 것은 내부 속성이 아니라 객체가외부에 제공하는 행동이라는 사실을 기억하라.

## 2. 타입 계층
### 타입 사이의 포함관계
* 포함하는 타입은 외연 관점에서는 더 크고, 내연 관점에서는 더 일반적
* 포함되는 타입은 외연 관점에서는 더 작고, 내연 관점에서는 더 특수함
* 포함 관계로 연결된 타입 사이에 개념적으로 일반화와 특수화 관계가 존재한다. (계층이 존재한다.) 
* 더 일반적인 타입을 슈퍼타입(supertype)
* 더 특수한 타입을 서브타입(subtype)

내연과 외연의 관점에서 일반화와 특수화를 정의해보면,
* 일반화란, (내연)어떤 타입의 정의를 좀 더 보편적이고 추상적적으로 만드는 과정 / (외연)특수타입을 포함하는 수퍼셋(superset)을 만드는 과정
  * 다른 타입을 완전히 포함하거나 내포하는 타입을 식별하는 행위 또는 그 행위의 결과
* 특수화란, (내연)어떤 타입의 정의를 좀 더 구체적이고 문맥 종속적으로 만드는 과정 / (외연)일반적인 타입에 포함되는 서브셋(subset)을 만드는 과정
  * 다른 타입 안에 전체적으로 포함되거나 완전히 내포되는 타입을 식별하는 행위 또는 그 행위의 결과

### 객체지향 프로그래밍과 타입 계층
* 일반적인 타입이란 비교하려는 타입에 속한 객체들의 퍼블릭 인터페이스보다 더 일반적인 퍼블릭 인터페이스를 가지는 객체들의 타입을 의미
  * 슈퍼타입이란 서브타입이 정의한 퍼블릭 인터페이스를 일반화시켜 상대적으로 범용적이고 넓은 의미로 정의한 것이다.
* 특수한 타입이란 비교하려는 타입에 속한 객체들의 퍼블릭 인터페이스보다 더 특수한 퍼블릭 인터페이스를 가지는 객체들의 타입을 의미
  * 서브타입이란 슈퍼타입이 정의한 퍼블릭 인터페이스를 특수화시켜 상대적으로 구체적이고 좁은 의미로 정의한 것이다.

```
서브타입의 인스턴스는 슈퍼타입의 인스터스로 간주될 수 있다.
```

## 3. 서브클래싱과 서브타이핑
* 객체지향 프로그래밍 언어에서 타입을 구현하는 일반적인 방법은 클래스를 이용하는 것
* 타입 계층을 구현하는 일반적인 방법은 상속을 이용하는 것
* 타입 계층을 구현한다는 것은 부모 클래스가 슈퍼타입의 역할을, 자식 클래스가 서브타입의 역할을 수행하도록 클래스 사이의 관계를 정의한다는 것을 의미

### 언제 상속을 사용해야 하는가? (언제 타입 계층을 구성해야 하는가?)
* 상속 관계가 is-a 관계를 모델링하는가?
* 클라이언트 입장에서 부모 클래스의 타입으로 자식 클래스를 사용해도 무방한가?
  * 상속 계층을 사용하는 클라이언트의 입장에서 부모 클래스와 자식 클래스의 차이점을 몰라야한다.
  * 자식 클래스와 부모 클래스 사이의 행동 호환성이라한다.
* 클라이언트의 관점에서 두 클래스에 대해 기대하는 행동이 다르면 비록 그것이 어휘적으로 is-a 관계로 표현할 수 있더라도 상속을 사용해서는 안된다.

### is-a 관계
* 펭귄 - 새 반례(?)
  * 펭귄은 새임
  * 새는 날 수 있음
  * 하지만 펭귄은 날 수 없음
  * 팽귄과 새는 타입 계층을 이룰 수 있을까?
* 행동에 따라 타입 계층을 구성해야 한다는 사실을 보여줌. (언어에 현혹되지 말자.)
* 타입 계층의 의미는 행동이라는 문맥에 따라 달라질 수 있음

### 행동 호환성
* 타입이 행동과 관련있다는 것에 주목
* 새와 펭귄은 서로 다른 행동 방식을 가지고 있기 때문에 이 둘은 행동이 호환되지 않음

그렇다면, 행동이 호환된다는 것은 무슨 의미(?)
* 호환이 되는지 아닌지는 `클라이언트의 관점`에서 결정되는 것이다.
* 클라이언트가 두 타입이 동일하게 행동할 것이라고 기대한다면 두 타입을 타입 계층으로 묶을 수 있음
* 타입 계층을 이해하기 위해서는 그 타입 계층이 사용될 문맥을 이해하는 것이 중요함

여전히 펭귄 클래스가 새 클래스를 상속하게 하고 싶다면,
* 아래 메소드를 사용하기 위해,
```java
public void flyBird(Bird bird) {
  bird.fly();
}
```

3가지 해결책이 있다. (하지만 3가지 모두 현실적으로 좋지 않은 방법.)
1. 구현하지 않기
```java
public class Penguin extends Bird {
  @Override
  public void fly() { }
}
```
- Client는 fly메소드가 호출되었을 때 객체가 어떤 동작을 하길 기대함
  - 어떤한 일도 일어나지 않았고 그걸 Client가 알 수 없음
 
2. 예외 던져버리기
```java
public class Penguin extends Bird {
  @Override
  public void fly() { 
    throw new UnsupportedOperationException();
  }
}
```
- Client는 fly메소드가 호출되었을 때 객체가 어떤 동작을 하길 기대함
 - 예외가 던져짐

3. 구체 클래스 노출시키고 분기치기
```java
public void flyBird(Bird bird) {
  if (!(bird instanceof Penguin)) {
    bird.fly();
  }
}
```
- 새로 클래스가 추가되면 분기가 추가되어야함


### 클라이언트의 기대에 따라 계층 분리하기
* 행동 호환성을 만족시키는 방법
* 클라이언트의 기대에 맞게 상속 계층을 분리하는 것 뿐
* 궁극적으로 원하는 것은 `flyBird` 메소드의 인자로 `fly`메소드가 구현된 객체만 넘어왔으면 하는 것
* 해결 방법은 `Penguin`이 `fly` 메소드를 가지고 있지 않게 하는 것
```java
public class Bird {
}

public class FlyingBird extends Bird {
  public void fly() {}
}

public class Penguin extends Bird {
}

public void flyBird(FlyingBird bird) {
  bird.fly();
}
```

#### 만약 새로 walk와 같은 메소드가 필요하다면?
* 인터페이스는 클라이언트가 기대하는 바에 따라 분리돼야 한다는 것을 기억
* 하나의 클라이언트가 오직 fly 메시지만 전송하기 원한다면 이 클라이언트에게는 fly 메시지만 보여야함
* 하나의 클라이언트가 오직 walk 메시지만 전송하기 원한다면 이 클라이언트에게는 walk 메시지만 보여야함
```java
interface Flyer {
  void fly();
}

interface Walker {
  void walk();
}

public class Bird implements Flyer, Walker {
  public void fly() {}
  public void walk() {}
}
public class Penguin implements Walker {
  public void walk() {}
}

public void doFly(Flyer flyer) {
  flyer.fly();
}

public void doWalk(Walker walker) {
  walker.walk();
}
```

> 세훈님: Java에 -able로 끝나는 인터페이스들이 많았는데 결국 이런식으로 행동을 분리하기 위함이었던 것으로 이해된다.
>

* 만약 한 Walker 구현체가 Bird의 구현체를 재사용하고 싶다면, 합성을 사용하면 된다.
* ISP (Interface Segregation Principle) 인터페이스 분리 원칙을 통해 변경에 의해 영향을 제어할 수 있음
> 설계가 꼭 현실 세계를 반영할 필요는 없다.
>
> 중요한 것은 설계가 반영할 도메인의 요구사항이고 그 안에서 클라이언트가 객체에게 요구하는 행동이다.
>
> 요구사항을 실용적으로 수용하는 것을 목표로 삼아야한다.
>
>최고의 설계는 제작하려는 소프트웨어 시스템이 기대하는 바에 따라 달라진다.

### 서브클래싱과 서브타이핑
* 코드 재사용을 위한 상속: 서브 클래싱(구현 상속 혹은 클래스 상속)
* 타입 계층을 위한 상속: 서브 타이핑(인터페이스 상속)
  * 부모 클래스의 인스턴스 대신 자식 클래스의 인스턴스를 사용할 목적으로 상속
  * 서브타이핑 관계가 유지되기 위해서는 서브타입이 슈퍼타입이 하는 모든 행동을 동일하게 할 수 있어 한다.
  * 즉, `행동 호환성`을 만족시켜야 서브타입이 된다. (이는 `대체 가능성`과 같은 말)

> 관수님: 상속이라는 기능을 프로그래머들이 서브클래싱과 서브타이핑으로 나눠서 사용하고 있었다. 굉장히 인상적이었다.
> 
> 학현님: 클래스를 나눌 때 타입을 생각해서(그러니까 리스코프 치환 원칙을 고려해서) 잘 안하게 되었는데 반성하게 되었다.
>
> 관수님: 구현 상속을 절대로 해서는 안된다. (학현님, 구현이 상속관계가 되어도 좋은 케이스가 있는데, 생각보다 그런 경우가 많지가 않다.)
>
> 학현님: Effective Java에서도 구현 상속을 사용할 수 있는 조건을 나열하고 있는데 거의 만족하기가 힘듦.
  
  
## 리스코프 치환 원칙
* 서브타입은 그것의 기반 타입에 대해 대체 가능해야 한다
* 클라이언트가 차이점을 인식하지 못한 채 기반 클래스의 인터페이스를 통해 서브클래스를 사용할 수 있어햐 한다.
* 리스코프 치환 원칙에 따르면 자식 클래스가 부모 클래스와 행동 호환성을 유지함으로써 부모 클래스를 대체할 수 있도록 구현된 상속 관계만을 서브타이핑이라고 부름

### 리스코프 치환 원칙 적용해보자
* 정사각형은 직사각형이다. (As-is 관계)
* 하지만 클라이언트의 문맥에서 바라보았을 때 대체 가능한지를 평가해야함
* 직사각형의 경우 가로와 새로의 크기를 다시 조정하는 컨택스트가 클라이언트에서 가질 수 있음
* 정사격형의 경우 가로와 새로 크기를 동일하게 유지해야하기 때문에 이와 같은 컨택스트에 사용될 수 없음
```
중요한 것은 클라이언트 관점에서 행동이 호환되는지 여부
행동이 호환될 경우에만 자식 클래스가 부모 클래스 대신 사용될 수 있음
```

### 클라이언트와 대체 가능성
* 클라이언트 관점에서 직사각형과 정사각형은 다르기 때문에 대체 불가
* 클라이언트의 코드는 가로와 새로의 크기가 다를거라는 가정을 가졌음
* 정사각형이 되면서 클라이언트가 세운 가정이 위반됨
* 리스코프 치환 원칙은 자식 클래스가 부모 클래스를 대체하기 위해서는 부모 클래스에 대한 클라이언트의 가정을 준수해야 한다는 것

### is-a 관계 다시 살펴보기
* is-a는 클라이언트 관점에서 is-a일 때만 참
* 중요한 것은 객체의 속성이 아니라 객체의 행동임
* 일반적으로 클라이언트를 고려하지 않은 채 개념과 속성의 측면에서 상속 관계를 정할 경우 리스코프 치환 원칙을 위반하는 서브클래싱에 이르게 됨
* 슈퍼타입과 서브타입이 클라이언트 입장에서 행동이 호환된다면 두 타입을 is-a로 연결해 문장을 만들어도 어색하지 않은 단어로 타입의 이름을 정리하는 것이 좋다

### 리스코프 치환 원칙은 유연한 설계의 기반이다.
* 리스코프 치환 원칙은 클라이언트가 어떤 자식 클래스와도 안정적으로 협력할 수 있는 상속 구조를 구현할 수 있는 가이드라인을 제공
* 새로운 자식 클래스를 추가하더라도 클라이언틔 입장에서 동일하게 행동하기만 한다면 클라이언트를 수정하지 않고도 상속 계층을 확장할 수 있음

## 5. 계약에 의한 설계와 서브타이핑
계약에 의한 설계(Design By Contract, DBC)란,
* 클라이언트가 정상적으로 메서드를 실행하기 위해 만족시켜야하는 `사전조건`
* 메서드가 실행된 후에 서버가 클라이언트에게 보장해야 하는 `사후조건`
* 메서드 실행 전과 실행 후에 인스턴스가 만족시켜야 하는 `클래스 불변식`
```
서브타입이 리스코프 치환 원칙을 만족시키기 위해서는 클라이언트와 슈터파입 간에 체결된 계약을 준수해야 한다.
```

* 서브타입에 더 강력한 사전조건을 정의할 수 없다. 
* 서브타입에 더 약한 사후조건을 정의할 수 없다.
* 가령 예를 들어,
```java
public abstract class DiscountPolicy {
  public Money calculateDiscountAmount(Screening screening) {
    for(DiscountCondition each : conditions) {
        if (each.isSatisfiedBy(screening)) {
            return getDiscountAmount(screening);
        }
    }
    return screening.getMovieFree();
  }

  abstract protected Money getDiscountAmount(Screening screening);
}

public class Movie {
  public Money calculateMovieFee(Screening screening) {
    return fee.minus(discountPolicy.calculateDiscountAmount(screening));
  }
}


// 사전 조건
assert screening != null && screening.getStartTime().isAfter(LocalDateTime.now());

// 사후 조건
assert amount != null && amount.isGreaterThanOrEqual(Money.ZERO);)

```

* 새로운 BrokenDiscountPolicy가 있다 했을 때, 이 클래스가 DiscountPolicy의 서브타이핑이 되려면 더 강력한 사전조건을 가질 수 없다.
* 만약 더 강력한 사전조건을 충족하게되면 DiscountPolicy로 완전 대체될 수 없기에 리스코프 치환 원칙 위반이다.
* 마찬가지로 더 약한 사후조건을 충족하게면, 클라이언트와 완전히 협력할 수 없게되어, 리스코프 치환 원칙 위반이 된다. 


 