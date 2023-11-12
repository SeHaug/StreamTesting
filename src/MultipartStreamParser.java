import java.io.*;
import java.util.Arrays;

public class MultipartStreamParser {
    private final byte CR = 13;
    private final byte LF = 10;
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private final byte[] boundryWithPrefix;
    private final byte[] buffer;
    private int head;
    private int tail;

    public MultipartStreamParser(InputStream inputStream, byte[] boundary, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        byte[] boundaryPrefix = new byte[]{CR, LF, 45, 45};
        this.buffer = new byte[boundaryPrefix.length + boundary.length + 2];
        this.head = 0;
        this.tail = 0;
        boundryWithPrefix = new byte[boundaryPrefix.length + boundary.length];
        System.arraycopy(boundaryPrefix, 0, boundryWithPrefix, 0, boundaryPrefix.length);
        System.arraycopy(boundary, 0, boundryWithPrefix, boundaryPrefix.length, boundary.length);


    }

    public void stripFistPart() {
        try {
            findBoundary();
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }

    }

    private void findBoundary() throws IOException {
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
        if (!found) {
            throw new RuntimeException("Boundary not found");
        } else {
            copy(inputStream, outputStream);
        }
    }

    public void extractMessage() {
        byte[] target = new byte[] {CR, LF, CR, LF};

    }

    public void removeEndBoundary() {

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

    private void copy(InputStream source, OutputStream target) throws IOException {
        byte[] buff = new byte[8192];
        int length;
        while ((length = source.read(buff)) > 0) {
            target.write(buff, 0, length);
        }
    }
}
