# Part 5. 구부러지거나 부러지거나

## 26. 결합도 줄이기와 디미터 법칙

- 코드를 모듈로 구성하고, 이들 간의 상호작용을 제한하라
    - 한 모듈이 변경되거나 교체된다 하더라도 다른 모듈들은 변경 없이 수행될 수 있을 것

결합도 줄이기 

- 과학 기록 정보를 그래프로 표현하는 클래스를 작성한다고 하자
    - 데이터 기록기는 세계 곳곳에 분산되어 있음
    - 각 기록기 객체는 객체가 위치한 곳의 위치와 시간대를 알려주는 장소 객체를 가지고 있음
- 사용자가 기록기를 선택하여 올바른 시간대가 표시된 데이터 그래프를 보게하고 싶다면 다음과 같이 작성할 것임

```java
public void plotDate(Data aDate, Selection aSelection) {
	TimeZone tz =
		aSelection.getRecorder().getLocation().getTimeZone();
	...
}
```

- 그래프를 그리는 루틴이 불필요하게 Selection, Recorder, Location 3개의 클래스와 결합하게 됨
- 클래스가 의존하게 되는 클래스의 숫자가 대폭 증가하게 됨
    - 의존의 증가 → 시스템 어딘가의 무관한 변화가 코드에 영향을 미칠 수 있는 위험이 커짐
- 위계구조를 직접 헤집고 다니지 말고, 필요한 정보는 직접 물어보자

```java
public void plotDate(Date aDate, Timezone aTz) {
		...
}

plotDate(someDate, someSelection.getTimeZone()); // Selection에 method를 추가하여 직접 시간대를 얻을 수 있음 
```

- 직접 객체간의 관계를 헤집고 다니면 의존 관계가 조합적으로 폭발하게될 수도 있음
- 불필요한 의존이 많은 시스템은 유지보수가 어렵고, 비용이 많이 들며 불안정함
    - 의존도를 최소화 하기 위하여 디미터 법칙을 사용하여 method, 함수를 설계하자

디미터 함수 법칙 

- 한 객체가 제공하는 method에 접근하기 위하여 또 다른 객체들을 통하는 것을 허용하지 않는다.

```java
class Demeter {
private:
	A *a;
	Int func();
public:
	// ...
	void example(B& b);
}

void Demeter::example(B& b) {
	C c;
	Int f = func(); // 자신
	b.invert();     // method로 넘어온 인자
	a = new A();
	a->setActive(); // 자신이 생성한 객체
	c.print();      // 직접 포함하고 있는 객체
}
```

- 디미터 법칙 역시 애플리케이션에 맞게 장점과 단점을 잘 고려해야함
    - DB 설계 시 반정규화를 통해 성능 개선을 하는 것은 흔한 일
