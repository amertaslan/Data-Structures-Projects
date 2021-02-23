
public class SingleLinkedListNode {
    Object data;
    SingleLinkedListNode link;
    int x,y;

    public SingleLinkedListNode(Object dataToAdd,int x,int y) {
        data = dataToAdd;
        link = null;
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Object getData() {
        return data;
    }

    public SingleLinkedListNode getLink() {
        return link;
    }

    public void setLink(SingleLinkedListNode link) {
        this.link = link;
    }
}
