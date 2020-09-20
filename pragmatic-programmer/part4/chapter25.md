## 25. 리소스 사용의 균형

- 코딩할 때 우리는 리소스를 관리함 (메모리, 트랜잭션, 스레드, 파일, 타이머)
    - 일반적으로 리소스 사용은 예측할 수 있는 패턴을 따름 (할당 / 사용 / 해제)
    - 많은 개발자들은 리소스 할당과 해제를 다루는 일관된 계획을 갖고 있지 않음

시작한 것은 끝내라 

- 리소스를 할당하는 루틴이나 객체가 리소스를 해제하는 책임 역시 저야함
- 나쁜 예제
    - 파일 열고 > 고객 정보 읽고 > 필드 하나를 업데이트 하고 > 결과를 다시 기록

```cpp
void readCustomer(const char *fName, Customer *cRec) {
	cFile = fopen(fName, "r+");
	fread(cRec, sizeof(*cRec), 1, cFile);
}

void writeCustomer(Customer *cRec) {
	rewind(cFile);
	fwrite(cRec, sizeof(*cRec), 1, cFile);
	fclose(cFile);
}

void updateCustomer(const char *fName, double newBalance) {
	Customer cRec;
	readCustomer(fName, &cRec);
	cRec.balance = newBalance;
	writeCustomer(&cCustomer);
}
```

- updateCustomer 루틴은 괜찮아 보임
    - But, 큰 문제가 숨어있음 > readCustomer와 writeCustomer가 긴밀하게 coupling되어 있음
    - 양자는 전역 변수 cFile을 공유하고, updateCustomer 루틴에서는 보이지도 않음
- 요구 사항이 변경됨
    - balance가 양수일 때에만 업데이트 하자

```cpp
void updateCustomer(const char *fName, double newBalance) {
	Customer cRec;
	readCustomer(fName, &cRec);
	cRec.balance = newBalance;
  if (newBalance >= 0.0) {
		writeCustomer(&cCustomer);
	}
}
```

- file을 제대로 안닫아주기 때문에 문제가 됨
- 하지만 다음과 같이 해결하는 것은 아주 나쁜 해법임

```cpp
void updateCustomer(const char *fName, double newBalance) {
	Customer cRec;
	readCustomer(fName, &cRec);
	cRec.balance = newBalance;
  if (newBalance >= 0.0) {
		writeCustomer(&cCustomer);
	} else {
		fclose(cFile); // 이 수정으로 인해 세 개의 루틴이 coupling 되어 함정에 빠지는 것
  }
}
```

- 시작한 것은 끝내라 : 리소스를 할당하는 루틴이 해제 역시 책임 지자

```cpp
void readCustomer(const char *fName, Customer *cRec) {
	fread(cRec, sizeof(*cRec), 1, cFile);
}

void writeCustomer(Customer *cRec) {
	rewind(cFile);
	fwrite(cRec, sizeof(*cRec), 1, cFile);
}

// 이제 파일에 대한 모든 책임은 updateCustomer 루틴에 있음
void updateCustomer(const char *fName, double newBalance) {
	FILE*	cFile = fopen(fName, "r+");
	Customer cRec;
	readCustomer(fName, &cRec);
	if (newBalance >= 0.0) {
		cRec.balance = newBalance;
		writeCustomer(&cCustomer);
	}
	fclose(cFile);
}
```

중첩할당 

- 하나 이상의 리소스를 필요로 하는 추틴에 적용
    - 리소스를 할당한 순서의 반대로 해제하라
        - 이렇게 해야 한 리소스가 다른 리소스를 참조하는 경우에도 리소스를 고아로 만들지 않는다. (dangling 방지?)
    - 할당 순서를 언제나 같게하자
        - deadlock 가능성이 줄어듦

객체와 예외

- 할당과 해제의 균형은 클래스의 생정자와 소멸자를 생각나게 함
    - 클래스는 하나의 리소스를 대표
    - 생성자는 그 리소스 타입의 특정 객체를 제공
    - 소멸자는 현 스코프에서 리소스 제거
- 객체지향 언어로 프로그래밍 시, 리소스를 클래스 안에 캡슐화하는 것이 유용할 것
    - 이러한 접근법은 C++과 같이 예외 때문에 리소스 해제가 방해받을 수 있는 언어로 작업할 때 유용

균형과 예외 

- 예외를 지원하는 언어는 리소스 해제에 복잡한 문제가 있을 수 있음
- 예외가 던져진 경우, 그 예외 이전의 할당된 모든 것이 깨끗이 청소된다고 보장해야 함
    - 언어에 따라 이 물음에 대한 답이 달라짐

C++에서의 예외와 리소스 사용의 균형 

```cpp
void doSomething() {
	Node *n = new Node();
	try {
		// do something
	} catch (...) {
		delete n;
		throw;
	}
	delete n;
}
```

- 생성한 노드가 해제하는 장소가 두 군데 → DRY 위반 (언제 터질지 모르는 유지보수의 문제)
- C++의 작동방식을 이용할 수 있음
    - local variable은 블록에서 나갈 때 자동으로 파괴 됨
- 다음과 같이 local variable을 이용하는 방법을 고려할 수 있음

```cpp
void doSomething() {
	Node n;
	try {
		// do something
	} catch (...) {
		throw;
	}
}
```

- 포인터에서 다른 것으로 바꾸는 일이 불가능하다면, 리소스를 다른 클래스로 감싸서 해결 (RAII)

```cpp
class NodeResource {
	Node *n;
public:
	NodeResource() { n = new Node };
	~NodeResource() { delete n; };
	Node *operator->() {return n };
};

void doSomething() {
	NodeResource n;
	try {
		// do something
	} catch (...) {
		throw;
	}
}
```

- 이 기법은 너무 유용하여 C++ 라이브러리의 auto_ptr에도 사용됨

```cpp
void doSomething(void) {
	auto_ptr<Node> p (new Node);
}
```

자바에서의 리소스 사용의 균형 

- C++과 달리 Lazy 방식의 자동 객체 삭제를 사용
    - 참조가 없는 객체들은 GC의 candidate가 됨
    - 편하긴 하지만 C++ 방식대로 자원 청소하기 어려워짐
        - 자바 언어 설계자들은 사려 깊어서 이를 보상하기 위하여 finally 절을 만들어 둠

```java
public void doSomething() throws IOException {
	File tmpeFile = new File(tmpFileName);
	FileWriter tmp = new FileWriter(tmpFile);
	try {
		// do something
	} 
	finally {
		tmpFile.delete();
	}
}
```

리소스 사용의 균형을 잡을 수 없는 경우 

- 기본적인 리소스 할당 방식이 아예 적절하지 않은 경우도 존재
    - 보통 동적 자료 구조형을 사용하는 프로그램에서 이런 일이 많이 생김
    - 한 루틴에서 메모리의 일정 영역을 할당한 다음 어떤 더 큰 구조에 그것을 연결하며 한동안 그대로 쓰이는 식
- 집합적인 자료구조 안의 자료에 대해 책임을 지는 게 누구인지 정해야 함
    - 최상위 구조의 할당을 해제할 경우 어떤 일이 일어나는가?
- 세 가지 방법
    - 최상위 구조 자신이 자기 안에 들어있는 하위 구조들의 할당을 해제할 책임이 있음
    - 최상위 구조에서 그냥 할당이 해제되고, 그 안에서 참조하던 구조들은 모두 고아가 됨
    - 최상위 구조는 하나라도 하위 구조를 가지고 있을 경우 자기의 할당 해제를 거부함
- 세 가지 방법 중의 선택은 각 데이터 구조의 상황에 따라 달라짐
    - 어떤 경우라도 어떤 것을 선택할 지 명백하게 결정하고 그에 따라 일관성 있게 구현해야 함
- 리소스 사용의 기록을 남기는 것이 까다로워진다면 동적으로 할당된 객체의 reference counting도 고려해볼 수 있음

균형을 점검하기

- 실용주의 프로그래머는 자신을 포함해서 아무도 믿지 않으므로, 정말로 리소스가 적절하게 해제되었는지 실제로 점검하는 코드를 늘 작성하는 것이 언제나 좋다고 생각함
    - 래퍼들을 사용해서 정말 그런지 확인 하라
- 요청이 들어오면 처리하는 실행 시간이 긴 프로그램은 주처리 루프 맨 위에 다음 요청이 도착하기를 기다리는 단일 지점이 있을 것
    - 이 지점은 지난번 처리가 끝났을 때 리소스 사용량이 증가하지 않았는지 검사하기 좋은 장소임
- 메모리 leak이 없는지 도구를 적절히 잘 사용해보자
    - Purify
    - Insuire++
    - Valgrinder
