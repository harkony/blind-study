import java.util.ArrayList;

// 코드예시 from https://spiralmoon.tistory.com/entry/%EB%94%94%EC%9E%90%EC%9D%B8-%ED%8C%A8%ED%84%B4-%EB%B3%B5%ED%95%A9%EC%B2%B4-%ED%8C%A8%ED%84%B4-Composite-pattern
// ppt의 도형을 움직이는 예시
public class CompositeExample {
}

abstract class GraphicComponent {
    // 기본 X 축
    public int x;
    // 기본 Y 축
    public int y;

    // 좌표 이동
    public abstract void Move(int x, int y);

    // 추가
    public abstract void add(GraphicComponent graphic);
    // 삭제
    public abstract void remove(GraphicComponent graphic);
    // 인덱스에 해당하는 자식을 반환
    public abstract GraphicComponent getChild(int index);
}

class Shape extends GraphicComponent {
    public Shape(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Shape객체는 add와 remove 메소드가 필요 없으므로, 구현을 하지 않았다.
    // 그러므로 add와 remove메소드를 사용할 때는 타입체크를 반드시 해줘야한다!
    @Override
    public void add(GraphicComponent graphic) {
    }

    @Override
    public void remove(GraphicComponent graphic) {
    }

    @Override
    public GraphicComponent getChild(int index) {
        return this;
    }

    @Override
    public void Move(int x, int y) {
        this.x += x;
        this.y += y;

        System.out.println("현재 좌표는 (" + this.x + " , " + this.y +" 입니다.");
    }
}

class Composite extends GraphicComponent {

    private ArrayList<GraphicComponent> graphicList = new ArrayList<GraphicComponent>();

    public Composite(int x, int y, ArrayList<GraphicComponent> graphicList) {
        this.x = x;
        this.y = y;
        this.graphicList = graphicList;
    }

    @Override
    public void Move(int x, int y) {
        this.x += x;
        this.y += y;

        // 포함한 도형객체를 모두 이동 -> 재귀호출
        for (int i=0; i<graphicList.size(); i++) {
            GraphicComponent graphic = graphicList.get(i);
            graphic.Move(x, y);
        }

        System.out.println("현재 좌표는 (" + this.x + " , " + this.y +" 입니다.");
    }

    @Override
    public void add(GraphicComponent graphic) {
        graphicList.add(graphic);
    }

    @Override
    public void remove(GraphicComponent graphic) {
        graphicList.remove(graphic);
    }

    @Override
    public GraphicComponent getChild(int index) {
        return graphicList.get(index);
    }
}