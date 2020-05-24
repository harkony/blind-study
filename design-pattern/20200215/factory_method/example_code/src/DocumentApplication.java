import java.util.List;

// Creator
public abstract class DocumentApplication {
    List<Document> docs;

    // 팩토리 메소드
    abstract Document createDocument();

    void newDocument(){
        Document doc = createDocument();
        docs.add(doc);
    }

    void openDocument(Document doc){
        doc.open();
    }
}

// ConcreteCreator
class DrawingApplication extends DocumentApplication {

    @Override
    Document createDocument() {
        return new DrawingDocument();
    }
}

class CoddingApplication extends DocumentApplication {

    @Override
    Document createDocument() {
        return new DrawingDocument();
    }
}

// Product
interface Document {
    void open ();
    void close ();
    void save ();
    void Revert ();
}

// ConcreteProduct
class DrawingDocument implements Document{

    @Override
    public void open() {
    }

    @Override
    public void close() {

    }

    @Override
    public void save() {

    }

    @Override
    public void Revert() {

    }
}

class JavaDocument implements Document{

    @Override
    public void open() {
    }

    @Override
    public void close() {

    }

    @Override
    public void save() {

    }

    @Override
    public void Revert() {

    }
}

class MovieDocument implements Document{

    @Override
    public void open() {
    }

    @Override
    public void close() {

    }

    @Override
    public void save() {

    }

    @Override
    public void Revert() {

    }
}