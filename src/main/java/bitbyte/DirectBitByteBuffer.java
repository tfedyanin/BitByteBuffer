package bitbyte;

import java.nio.*;

/**
 * Created by Timofey on 26.10.2016.
 */
public class DirectBitByteBuffer implements BitByteBuffer {
    ByteBuffer bb;
    private int bitMark = -1;
    private int bitPosition = 0;
    private static final int BITS_PER_BYTE = 8;

    private static final int bmask[] = {
            0x00, 0x01, 0x03, 0x07, 0x0f, 0x1f, 0x3f, 0x7f, 0xff,
            0x1ff, 0x3ff, 0x7ff, 0xfff, 0x1fff, 0x3fff, 0x7fff, 0xffff,
            0x1ffff, 0x3ffff, 0x7ffff, 0xfffff, 0x1fffff, 0x3fffff,
            0x7fffff, 0xffffff, 0x1ffffff, 0x3ffffff, 0x7ffffff,
            0xfffffff, 0x1fffffff, 0x3fffffff, 0x7fffffff, 0xffffffff
    };
    private static final byte headMask[] = {
            (byte) 0xFF,
            (byte) 0x7F,
            (byte) 0x3F,
            (byte) 0x1F,
            (byte) 0x0F,
            (byte) 0x07,
            (byte) 0x03,
            (byte) 0x01,
    };
    private static final byte tailMask[] = {
            (byte) 0x80,
            (byte) 0xC0,
            (byte) 0xE0,
            (byte) 0xF0,
            (byte) 0xF8,
            (byte) 0xFC,
            (byte) 0xFE,
            (byte) 0xFF,
    };


    public DirectBitByteBuffer(byte[] data, ByteOrder bo) {
        this.bb = ByteBuffer.allocateDirect(data.length);
        bb.put(data);
        bb.order(bo);
        bb.position(0);
    }

    public byte get() {
        if (bitPosition == 0) {
            return bb.get();
        } else {
            // TODO: 26.10.2016
            return bb.get();
        }
    }

    public byte touch() {
        mark();
        byte s = get();
        reset();
        return s;
    }

    public short getUnsingned() {
        return (short) Byte.toUnsignedInt(get());
    }

    public short touchUnsingned() {
        return (short) Byte.toUnsignedInt(touch());
    }

    public short getShort() {
        if (bitPosition == 0) {
            return bb.getShort();
        } else {
            // TODO: 26.10.2016
            return bb.getShort();
        }
    }

    public short touchShort() {
        mark();
        short s = getShort();
        reset();
        return s;
    }


    public int getUnsignedShort() {
        return Short.toUnsignedInt(getShort());
    }

    public int touchUnsignedShort() {
        return Short.toUnsignedInt(touchShort());
    }

    public int getInt() {
        if (bitPosition == 0) {
            return bb.getInt();
        } else {
            // TODO: 26.10.2016
            return bb.getInt();
        }
    }

    public int touchInt() {
        mark();
        int i = getInt();
        reset();
        return i;
    }

    public long getUnsignedInt() {
        return Integer.toUnsignedLong(getInt());
    }

    public long touchUnsignedInt() {
        return Integer.toUnsignedLong(touchInt());
    }

    public long getLong() {
        if (bitPosition == 0) {
            return bb.getLong();
        } else {
            // TODO: 26.10.2016
            return bb.getInt();
        }
    }

    public long touchLong() {
        mark();
        long l = getLong();
        reset();
        return l;
    }

    public final DirectBitByteBuffer mark() {
        bb.mark();
        bitMark = bitPosition;
        return this;
    }

    public final DirectBitByteBuffer reset() {
        bb.reset();
        int m = bitMark;
        if (m < 0)
            throw new InvalidMarkException();
        bitPosition = m;
        return this;
    }

    public byte readHead(int nBits) {
        int end = nBits + bitPosition;
        if (end > BITS_PER_BYTE) {
            // TODO: 26.10.2016 trow new ...
        }
        if (nBits == BITS_PER_BYTE) {
            return get();
        }
        if (nBits == 0) {
            return 0x00;
        }
        byte b;
        if (end == BITS_PER_BYTE) {
            b = (byte) (get() & headMask[bitPosition] & tailMask[end - 1]);
            bitPosition = 0;
        } else {
            b = (byte) (((touch() & headMask[bitPosition] & tailMask[end - 1]) >>> (BITS_PER_BYTE - end)) & headMask[BITS_PER_BYTE - end + bitPosition]);
            bitPosition = end;
        }
        return b;
    }

    public byte readTail(int nBits) {
        if (bitPosition != 0) {
            // TODO: 27.10.2016 throw new ...
        }
        return readHead(nBits);
    }

    public static void main(String[] args) {
        DirectBitByteBuffer buffer = new DirectBitByteBuffer(new byte[]{(byte) 0x7C, (byte) 0xAA, (byte) 0xE4, (byte) 0xFF}, ByteOrder.LITTLE_ENDIAN);
        byte x = buffer.readHead(4);
        System.out.println(x);
        byte y = buffer.readHead(4);
        System.out.println(y);
        byte z = buffer.readHead(4);
        System.out.println(z);
        byte a = buffer.readHead(4);
        System.out.println(a);
        byte e = buffer.readHead(2);
        System.out.println(e);
        e = buffer.readHead(2);
        System.out.println(e);
        e = buffer.readHead(2);
        System.out.println(e);
        e = buffer.readHead(2);
        System.out.println(e);
        e = buffer.readTail(7);
        System.out.println(e);

//        bb.getInt()
    }


}
