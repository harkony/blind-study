import java.util.Hashtable;

public class PrototypeExample {
    public static void main(String[] args) {
        ShapePrototype.loadCache();

        Shape clonedShape = (Shape) ShapePrototype.getShape("1");
        System.out.println("Shape : " + clonedShape.getType());

        Shape clonedShape2 = (Shape) ShapePrototype.getShape("2");
        System.out.println("Shape : " + clonedShape2.getType());

        Shape clonedShape3 = (Shape) ShapePrototype.getShape("3");
        System.out.println("Shape : " + clonedShape3.getType());

        // 추가적인 원형의 등록.
        Shape clonedShape4 = (Shape) ShapePrototype.getShape("3");
        clonedShape4.setColor("red");
        ShapePrototype.saveProto(clonedShape4);
    }
}

// Prototype manager
class ShapePrototype{
    // 등록된 원형을 저장하는 map
    private static Hashtable<String, Shape> shapeMap = new Hashtable<String, Shape>();

    // 원형 호출
    public static Shape getShape(String shapeId) {
        Shape cachedShape = shapeMap.get(shapeId);
        return (Shape) cachedShape.clone();
    }

    // 초기 원형 등록
    public static void loadCache() {
        Circle circle = new Circle();
        circle.setId("1");
        shapeMap.put(circle.getId(),circle);

        Square square = new Square();
        square.setId("2");
        shapeMap.put(square.getId(),square);

        Rectangle rectangle = new Rectangle();
        rectangle.setId("3");
        shapeMap.put(rectangle.getId(), rectangle);
    }

    // 추가 원형 등록
    public static void saveProto(Shape shape) {
        // todo-list
    }

    // todo-list 원형 삭제 및 원형 초기화 메서드 구현

}

// Prototype
abstract class Shape implements Cloneable {
    private String id;
    protected String type;
    protected String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    protected String size;

    abstract void draw();

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return clone;
    }
}

// ConcretePrototype1
class Rectangle extends Shape {

    public Rectangle() {
        type = "Rectangle";
    }

    @Override
    void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }

    // 자신을 복제하는 clone 메소드 구현
    @Override
    public Object clone() {
        return super.clone();
    }
}
// ConcretePrototype2

class Square extends Shape {

    public Square() {
        type = "Square";
    }

    @Override
    void draw() {
        System.out.println("Inside Square::draw() method.");
    }

    // 자신을 복제하는 clone 메소드 구현
    @Override
    public Object clone() {
        return super.clone();
    }
}

// ConcretePrototype3
class Circle extends Shape {

    public Circle() {
        type = "Circle";
    }

    @Override
    void draw() {
        System.out.println("Inside Circle::draw() method.");
    }

    // 자신을 복제하는 clone 메소드 구현
    @Override
    public Object clone() {
        return super.clone();
    }
}

