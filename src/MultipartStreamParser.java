import java.io.*;

public class MultipartStreamParser {
    private final byte CR = 13;
    private final byte LF = 10;
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private final byte[] boundaryWithPrefix;
    private final byte[] buffer;
    private int head;
    private int tail;

    public MultipartStreamParser(InputStream inputStream, byte[] boundary, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        byte[] boundaryPrefix = new byte[]{CR, LF, 45, 45};
        this.buffer = new byte[boundaryPrefix.length + boundary.length];
        this.head = 0;
        this.tail = 0;
        boundaryWithPrefix = new byte[boundaryPrefix.length + boundary.length];
        System.arraycopy(boundaryPrefix, 0, boundaryWithPrefix, 0, boundaryPrefix.length);
        System.arraycopy(boundary, 0, boundaryWithPrefix, boundaryPrefix.length, boundary.length);
    }

    public void extractMessage() {
        try {
            byte[] target = new byte[] {CR, LF, CR, LF};
            findTarget(boundaryWithPrefix);
            findTarget(target);
            copy(inputStream, outputStream);
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
    // jeg har lavet en mere generisk metode - findTarget
    private void findBoundary() throws IOException {
        boolean found = false;
        do {
            tail = inputStream.read(buffer, head, buffer.length - head);
            if (tail > 0) {
                if (arrayEqualsHits(buffer, 0, boundaryWithPrefix, tail + head) == boundaryWithPrefix.length) {
                    found = true;
                } else {

                    int pos = findByte(buffer, CR, head, tail);
                    if (pos >= 0) {
                        int hits = arrayEqualsHits(buffer, pos, boundaryWithPrefix, tail - pos);
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

    /**
     * Used to search the stream for the target sequence of bytes
     * @param target
     * @throws IOException is thrown if target is not found
     */
    private void findTarget(byte[] target) throws IOException {
        byte[] buff = new byte[target.length];
        boolean found = false;
        int head = 0;
        int tail;
        do {
            // Read up to target.length of bytes into the buffer and set tail to be the number of bytes read
            tail = inputStream.read(buff, head, buff.length - head);
            if (tail > 0) {
                // Check if the target is in the buffer begining from index 0 of the buffer
                if (arrayEqualsHits(buff, 0, target, tail + head) == target.length) {
                    found = true;
                } else {
                    // Check if the first byte of the target is in the buffer
                    //TODO - CR should not be hardcoded but instead index 0 of target.
                    int pos = findByte(buff, CR, head, tail);
                    if (pos >= 0) {
                        // Check if the sequence after the first byte of the target matches the target
                        int hits = arrayEqualsHits(buff, pos, target, tail - pos);
                        // if the tail of the buffer is reached before target.length in the check
                        // we need to move it to the front of the buffer and set the head to the number
                        // of hits in the check
                        if (pos + hits == tail) {
                            head = hits;
                            System.arraycopy(buff, pos, buff, 0, hits);
                        }
                    }
                }
            }
        } while (tail >= 0 && !found);
        if (!found) {
            throw new RuntimeException("Target not found");
        }
    }

    private void removeEndBoundary() {

    }

    private int findByte(byte[] a, byte target, int head, int tail) {
        for (int i = head; i < tail; i++) {
            if (a[i] == target) {
                return i;
            }
        }
        return -1;
    }

    private int arrayEqualsHits(byte[] a, int offset, byte[] b, int count) {
        int hits = 0;
        for (int i = 0; i < Math.min(count, boundaryWithPrefix.length); i++) {
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
