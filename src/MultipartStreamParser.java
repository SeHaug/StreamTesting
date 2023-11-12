import java.io.*;
import java.util.Arrays;

public class MultipartStreamParser {
    private final byte CR = 13;
    private final byte LF = 10;
    private final byte[] BOUNDARY_PREFIX = new byte[] {CR, LF, 45, 45};
    private InputStream inputStream;
    private OutputStream outputStream;
    private final byte[] BOUNDARY;
    private byte[] boundryWithPrefix;
    private byte[] buffer;
    private int head;
    private int tail;

    public MultipartStreamParser(InputStream inputStream, byte[] boundary, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.BOUNDARY = boundary;
        this.buffer = new byte[BOUNDARY_PREFIX.length + boundary.length + 2];
        this.head = 0;
        this.tail = 0;
        boundryWithPrefix = new byte[BOUNDARY_PREFIX.length + boundary.length];
        System.arraycopy(BOUNDARY_PREFIX, 0, boundryWithPrefix, 0, BOUNDARY_PREFIX.length);
        System.arraycopy(BOUNDARY, 0, boundryWithPrefix, BOUNDARY_PREFIX.length, BOUNDARY.length);


    }

    public void stripFistPart() throws IOException {
        boolean found = false;
        do {
            tail = inputStream.read(buffer, head, buffer.length - head);
            if (tail > 0) {
                if (arrayEqualsHits(buffer, 0, boundryWithPrefix, tail + head) == boundryWithPrefix.length) {
                    found = true;
                } else {
                    int pos = findByte(CR, head);
                    if (pos >= 0) {
                        int hits = arrayEqualsHits(buffer, pos, boundryWithPrefix, tail - pos);
                        if (pos + hits == tail) {
                            head = hits;
                            System.arraycopy(buffer, pos, buffer, 0, hits);
                        }
                    }
                }
            }
        } while (tail >= 0 && !found);
        //SequenceInputStream sequenceInputStream = new SequenceInputStream(new ByteArrayInputStream(buffer), inputStream);
        //outputStream.write(sequenceInputStream.readAllBytes());
        outputStream.write(buffer);
    }

    public void extractMessage() {

    }

    public int findByte(byte target, int pos) {
        for (int i = pos; i < tail; i++) {
            if (buffer[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public int arrayEqualsHits(byte[] a, int offset, byte[] b, int count) {
        int hits = 0;
        for (int i = 0; i < Math.min(count, boundryWithPrefix.length); i++) {
            if (a[i + offset] == b[i]) {
                hits++;
            }
        }
        return hits;
    }
}
