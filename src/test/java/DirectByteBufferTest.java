import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Timofey on 26.10.2016.
 */
public class DirectByteBufferTest {
    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocateDirect(10);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        byte[] bytes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        bb.put(bytes);
        int anInt = bb.getInt();

    }
}
