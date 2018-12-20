public class Vertex {
    private int key;
    private String value;

    public Vertex(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(Vertex other) {
        return this.key == other.getKey();
    }

    public int hashCode() {
        return this.key;
    }
}
