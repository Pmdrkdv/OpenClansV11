package net.Stream;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ByteStream extends ChecksumEncoder {

    private static final int MAX_COMPRESSED_STRING_BYTES = 900000;

    private int bitOffset;
    private byte[] buffer;
    private int length;
    private int offset;

    public ByteStream(int capacity) {
        this.buffer = new byte[capacity];
    }

    public ByteStream(byte[] buffer, int length) {
        this.length = length;
        this.buffer = buffer;
    }

    public int GetLength() {
        if (this.offset < this.length) {
            return this.length;
        }
        return this.offset;
    }

    public int GetOffset() {
        return this.offset;
    }

    public boolean IsAtEnd() {
        return this.offset >= this.length;
    }

    public void Clear(int capacity) {
        this.buffer = new byte[capacity];
        this.offset = 0;
    }

    public byte[] GetByteArray() {
        return this.buffer;
    }

    public boolean ReadBoolean() {
        if (this.bitOffset == 0) {
            ++this.offset;
        }
        boolean value = (this.buffer[this.offset - 1] & (1 << this.bitOffset)) != 0;
        this.bitOffset = (this.bitOffset + 1) & 7;
        return value;
    }

    public byte ReadByte() {
        this.bitOffset = 0;
        return this.buffer[this.offset++];
    }

    public short ReadShort() {
        this.bitOffset = 0;
        return (short) (((this.buffer[this.offset++] & 0xFF) << 8) | (this.buffer[this.offset++] & 0xFF));
    }

    public int ReadInt() {
        this.bitOffset = 0;
        return ((this.buffer[this.offset++] & 0xFF) << 24) | ((this.buffer[this.offset++] & 0xFF) << 16) | ((this.buffer[this.offset++] & 0xFF) << 8) | (this.buffer[this.offset++] & 0xFF);
    }

    public int ReadVInt() {
        this.bitOffset = 0;
        int value = 0;
        byte byteValue = this.buffer[this.offset++];
        if ((byteValue & 0x40) != 0) {
            value |= byteValue & 0x3F;
            if ((byteValue & 0x80) != 0) {
                value |= ((byteValue = this.buffer[this.offset++]) & 0x7F) << 6;
                if ((byteValue & 0x80) != 0) {
                    value |= ((byteValue = this.buffer[this.offset++]) & 0x7F) << 13;
                    if ((byteValue & 0x80) != 0) {
                        value |= ((byteValue = this.buffer[this.offset++]) & 0x7F) << 20;
                        if ((byteValue & 0x80) != 0) {
                            value |= ((byteValue = this.buffer[this.offset++]) & 0x7F) << 27;
                            return (int) (value | 0x80000000);
                        }
                        return (int) (value | 0xF8000000);
                    }
                    return (int) (value | 0xFFF00000);
                }
                return (int) (value | 0xFFFFE000);
            }
            return (int) (value | 0xFFFFFFC0);
        }
        value |= byteValue & 0x3F;
        if ((byteValue & 0x80) != 0) {
            value |= ((byteValue = this.buffer[this.offset++]) & 0x7F) << 6;
            if ((byteValue & 0x80) != 0) {
                value |= ((byteValue = this.buffer[this.offset++]) & 0x7F) << 13;
                if ((byteValue & 0x80) != 0) {
                    value |= ((byteValue = this.buffer[this.offset++]) & 0x7F) << 20;
                    if ((byteValue & 0x80) != 0) {
                        value |= ((byteValue = this.buffer[this.offset++]) & 0x7F) << 27;
                    }
                }
            }
        }
        return value;
    }

    public long ReadLong() {
        byte[] buff = ReadBytes(8, 8);
        if (buff == null) {
            return 0;
        }
        return ((long) (buff[0] & 0xFF) << 56) | ((long) (buff[1] & 0xFF) << 48) | ((long) (buff[2] & 0xFF) << 40) | ((long) (buff[3] & 0xFF) << 32) | ((long) (buff[4] & 0xFF) << 24) | ((long) (buff[5] & 0xFF) << 16) | ((long) (buff[6] & 0xFF) << 8) | (buff[7] & 0xFF);
    }

    public long ReadVLong() {
        int high = ReadVInt();
        int low = ReadVInt();
        return ((long) high << 32) | (low & 0xFFFFFFFFL);
    }

    public int ReadBytesLength() {
        this.bitOffset = 0;
        return ((this.buffer[this.offset++] & 0xFF) << 24) | ((this.buffer[this.offset++] & 0xFF) << 16) | ((this.buffer[this.offset++] & 0xFF) << 8) | (this.buffer[this.offset++] & 0xFF);
    }

    public byte[] ReadBytes(int length, int maxCapacity) {
        this.bitOffset = 0;
        if (length <= -1) {
            return null;
        }
        if (length <= maxCapacity) {
            byte[] array = new byte[length];
            System.arraycopy(this.buffer, this.offset, array, 0, length);
            this.offset += length;
            return array;
        }
        return null;
    }

    public String ReadString() {
        return ReadString(9000000);
    }

    public String ReadString(int maxCapacity) {
        int len = ReadBytesLength();
        if (len <= -1) {
            return null;
        }
        if (len <= maxCapacity) {
            String value = new String(this.buffer, this.offset, len, StandardCharsets.UTF_8);
            this.offset += len;
            return value;
        }
        return null;
    }

    public String ReadStringReference(int maxCapacity) {
        int len = ReadBytesLength();
        if (len <= -1) {
            return "";
        }
        if (len <= maxCapacity) {
            String value = new String(this.buffer, this.offset, len, StandardCharsets.UTF_8);
            this.offset += len;
            return value;
        }
        return "";
    }

    public String ReadCompressedString() {
        int len = ReadBytesLength();
        if (len == -1) {
            return null;
        }
        if (len < 4 || len > MAX_COMPRESSED_STRING_BYTES || len > this.length - this.offset) {
            throw new IllegalArgumentException("Invalid compressed string length: " + len);
        }
        byte[] data = ReadBytes(len, MAX_COMPRESSED_STRING_BYTES);
        long size = (data[0] & 0xFFL) | ((data[1] & 0xFFL) << 8)
                | ((data[2] & 0xFFL) << 16) | ((data[3] & 0xFFL) << 24);
        if (size > MAX_COMPRESSED_STRING_BYTES) {
            throw new IllegalArgumentException("Compressed string expands beyond the size limit");
        }
        Inflater inflater = new Inflater();
        try {
            inflater.setInput(data, 4, data.length - 4);
            ByteArrayOutputStream output = new ByteArrayOutputStream((int) size);
            byte[] chunk = new byte[8192];
            while (!inflater.finished()) {
                int count = inflater.inflate(chunk);
                if (output.size() + count > size) {
                    throw new IllegalArgumentException("Incorrect uncompressed string length");
                }
                output.write(chunk, 0, count);
                if (count == 0 && !inflater.finished()) {
                    throw new IllegalArgumentException("Incomplete compressed string or unsupported dictionary");
                }
            }
            if (output.size() != size || inflater.getRemaining() != 0) {
                throw new IllegalArgumentException("Incorrect compressed string length");
            }
            return new String(output.toByteArray(), StandardCharsets.UTF_8);
        } catch (DataFormatException e) {
            throw new IllegalArgumentException("Invalid zlib compressed string", e);
        } finally {
            inflater.end();
        }
    }

    public void WriteCompressedString(String value) {
        if (value == null) {
            WriteIntToByteArray(-1);
            return;
        }
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        if (bytes.length > MAX_COMPRESSED_STRING_BYTES) {
            throw new IllegalArgumentException("String exceeds the client size limit");
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write(bytes.length & 0xFF);
        output.write((bytes.length >>> 8) & 0xFF);
        output.write((bytes.length >>> 16) & 0xFF);
        output.write((bytes.length >>> 24) & 0x3F);
        Deflater deflater = new Deflater(Deflater.DEFAULT_COMPRESSION, false);
        try {
            deflater.setInput(bytes);
            deflater.finish();
            byte[] chunk = new byte[8192];
            while (!deflater.finished()) {
                int count = deflater.deflate(chunk);
                output.write(chunk, 0, count);
            }
        } finally {
            deflater.end();
        }
        byte[] data = output.toByteArray();
        if (data.length > MAX_COMPRESSED_STRING_BYTES) {
            throw new IllegalArgumentException("Compressed string exceeds the client size limit");
        }
        WriteBytes(data, data.length);
    }



    @Override
    public boolean WriteBoolean(boolean value) {
        if (this.bitOffset == 0) {
            EnsureCapacity(1);
            this.buffer[this.offset++] = 0;
        }
        if (value) {
            this.buffer[this.offset - 1] |= (byte) (1 << this.bitOffset);
        }
        this.bitOffset = (this.bitOffset + 1) & 7;
        return value;
    }

    @Override
    public void WriteByte(byte value) {
        EnsureCapacity(1);
        this.bitOffset = 0;
        this.buffer[this.offset++] = value;
    }

    @Override
    public void WriteShort(short value) {
        EnsureCapacity(2);
        this.bitOffset = 0;
        this.buffer[this.offset++] = (byte) (value >> 8);
        this.buffer[this.offset++] = (byte) value;
    }

    @Override
    public void WriteInt(int value) {
        EnsureCapacity(4);
        this.bitOffset = 0;
        this.buffer[this.offset++] = (byte) (value >> 24);
        this.buffer[this.offset++] = (byte) (value >> 16);
        this.buffer[this.offset++] = (byte) (value >> 8);
        this.buffer[this.offset++] = (byte) value;
    }

    public void WriteLongLong128(BigInteger value) {
        WriteIntToByteArray((int) value.longValue());
        WriteIntToByteArray((int) value.shiftRight(32).longValue());
        WriteIntToByteArray((int) value.shiftRight(64).longValue());
        WriteIntToByteArray((int) value.shiftRight(96).longValue());
    }

    public void WriteDataReference(int globalId) {
        if (globalId <= 0) {
            WriteVInt(0);
            return;
        }

        WriteVInt(globalId / 1000000);
        WriteVInt(globalId % 1000000);
    }

    public void WriteDataReference(int classId, int instanceId) {
        if (classId <= 0) {
            WriteVInt(0);
            return;
        }

        WriteVInt(classId);
        WriteVInt(instanceId);
    }

    @Override
    public void WriteVInt(int value) {
        EnsureCapacity(5);
        this.bitOffset = 0;
        if (value >= 0) {
            if (value >= 64) {
                if (value >= 0x2000) {
                    if (value >= 0x100000) {
                        if (value >= 0x8000000) {
                            this.buffer[this.offset++] = (byte) ((value & 0x3F) | 0x80);
                            this.buffer[this.offset++] = (byte) (((value >> 6) & 0x7F) | 0x80);
                            this.buffer[this.offset++] = (byte) (((value >> 13) & 0x7F) | 0x80);
                            this.buffer[this.offset++] = (byte) (((value >> 20) & 0x7F) | 0x80);
                            this.buffer[this.offset++] = (byte) ((value >> 27) & 0xF);
                        } else {
                            this.buffer[this.offset++] = (byte) ((value & 0x3F) | 0x80);
                            this.buffer[this.offset++] = (byte) (((value >> 6) & 0x7F) | 0x80);
                            this.buffer[this.offset++] = (byte) (((value >> 13) & 0x7F) | 0x80);
                            this.buffer[this.offset++] = (byte) ((value >> 20) & 0x7F);
                        }
                    } else {
                        this.buffer[this.offset++] = (byte) ((value & 0x3F) | 0x80);
                        this.buffer[this.offset++] = (byte) (((value >> 6) & 0x7F) | 0x80);
                        this.buffer[this.offset++] = (byte) ((value >> 13) & 0x7F);
                    }
                } else {
                    this.buffer[this.offset++] = (byte) ((value & 0x3F) | 0x80);
                    this.buffer[this.offset++] = (byte) ((value >> 6) & 0x7F);
                }
            } else {
                this.buffer[this.offset++] = (byte) (value & 0x3F);
            }
        } else {
            if (value <= -0x40) {
                if (value <= -0x2000) {
                    if (value <= -0x100000) {
                        if (value <= -0x8000000) {
                            this.buffer[this.offset++] = (byte) ((value & 0x3F) | 0xC0);
                            this.buffer[this.offset++] = (byte) (((value >> 6) & 0x7F) | 0x80);
                            this.buffer[this.offset++] = (byte) (((value >> 13) & 0x7F) | 0x80);
                            this.buffer[this.offset++] = (byte) (((value >> 20) & 0x7F) | 0x80);
                            this.buffer[this.offset++] = (byte) ((value >> 27) & 0xF);
                        } else {
                            this.buffer[this.offset++] = (byte) ((value & 0x3F) | 0xC0);
                            this.buffer[this.offset++] = (byte) (((value >> 6) & 0x7F) | 0x80);
                            this.buffer[this.offset++] = (byte) (((value >> 13) & 0x7F) | 0x80);
                            this.buffer[this.offset++] = (byte) ((value >> 20) & 0x7F);
                        }
                    } else {
                        this.buffer[this.offset++] = (byte) ((value & 0x3F) | 0xC0);
                        this.buffer[this.offset++] = (byte) (((value >> 6) & 0x7F) | 0x80);
                        this.buffer[this.offset++] = (byte) ((value >> 13) & 0x7F);
                    }
                } else {
                    this.buffer[this.offset++] = (byte) ((value & 0x3F) | 0xC0);
                    this.buffer[this.offset++] = (byte) ((value >> 6) & 0x7F);
                }
            } else {
                this.buffer[this.offset++] = (byte) ((value & 0x3F) | 0x40);
            }
        }
    }

    public void WriteIntToByteArray(int value) {
        EnsureCapacity(4);
        this.bitOffset = 0;
        this.buffer[this.offset++] = (byte) (value >> 24);
        this.buffer[this.offset++] = (byte) (value >> 16);
        this.buffer[this.offset++] = (byte) (value >> 8);
        this.buffer[this.offset++] = (byte) value;
    }

    public void WriteIntToByteArray(long value) {
        EnsureCapacity(4);
        this.bitOffset = 0;
        this.buffer[this.offset++] = (byte) (value >> 24);
        this.buffer[this.offset++] = (byte) (value >> 16);
        this.buffer[this.offset++] = (byte) (value >> 8);
        this.buffer[this.offset++] = (byte) value;
    }

    public void WriteLong(long value) {
        WriteIntToByteArray((int) (value >> 32));
        WriteIntToByteArray((int) value);
    }

    @Override
    public void WriteBytes(byte[] value, int length) {
        if (value == null) {
            WriteIntToByteArray(-1);
        } else {
            EnsureCapacity(length + 4);
            WriteIntToByteArray(length);
            System.arraycopy(value, 0, this.buffer, this.offset, length);
            this.offset += length;
        }
    }

    public void WriteBytesWithoutLength(byte[] value, int length) {
        if (value != null) {
            EnsureCapacity(length);
            System.arraycopy(value, 0, this.buffer, this.offset, length);
            this.offset += length;
        }
    }

    @Override
    public void WriteString(String value) {
        if (value == null) {
            WriteIntToByteArray(-1);
        } else {
            byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
            int len = bytes.length;
            if (len <= 900000) {
                EnsureCapacity(len + 4);
                WriteIntToByteArray(len);
                System.arraycopy(bytes, 0, this.buffer, this.offset, len);
                this.offset += len;
            } else {
                WriteIntToByteArray(-1);
            }
        }
    }

    @Override
    public void WriteStringReference(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        int len = bytes.length;
        if (len <= 900000) {
            EnsureCapacity(len + 4);
            WriteIntToByteArray(len);
            System.arraycopy(bytes, 0, this.buffer, this.offset, len);
            this.offset += len;
        } else {
            WriteIntToByteArray(-1);
        }
    }

    public void SetByteArray(byte[] buffer, int length) {
        this.offset = 0;
        this.bitOffset = 0;
        this.buffer = buffer;
        this.length = length;
    }

    public void ResetOffset() {
        this.offset = 0;
        this.bitOffset = 0;
    }

    public void SetOffset(int offset) {
        this.offset = offset;
        this.bitOffset = 0;
    }

    public byte[] RemoveByteArray() {
        byte[] arr = this.buffer;
        this.buffer = null;
        return arr;
    }

    public void EnsureCapacity(int capacity) {
        int bufferLength = this.buffer.length;
        if (this.offset + capacity > bufferLength) {
            byte[] tmp = new byte[this.buffer.length + capacity + 100];
            System.arraycopy(this.buffer, 0, tmp, 0, bufferLength);
            this.buffer = tmp;
        }
    }

    public void Destruct() {
        this.buffer = null;
        this.bitOffset = 0;
        this.length = 0;
        this.offset = 0;
    }

    public byte[] toByteArray() {
        return Arrays.copyOf(this.buffer, GetLength());
    }
}
