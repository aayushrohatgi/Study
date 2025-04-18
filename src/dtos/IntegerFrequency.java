package dtos;

public class IntegerFrequency {
    int key;
    int frequency;

    public IntegerFrequency(int key, int frequency) {
        this.key = key;
        this.frequency = frequency;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
