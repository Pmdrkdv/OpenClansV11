package net.Stream;

import net.Stream.Logic.LogicLong;

public class ChecksumEncoder {
    private int checksum;

    private boolean enabled;

    public ChecksumEncoder() {
        this.enabled = true;
    }



    public void ResetCheckSum() {
        this.checksum = 0;
    }

    public boolean WriteBoolean(boolean value) {
        this.checksum = (value ? 13 : 7) + rotateRight(this.checksum, 31);
        return value;
    }

    public void WriteByte(byte value) {
        this.checksum = (value & 0xFF) + rotateRight(this.checksum, 31) + 11;
    }

    public void WriteShort(short value) {
        this.checksum = value + rotateRight(this.checksum, 31) + 19;
    }

    public void WriteInt(int value) {
        this.checksum = value + rotateRight(this.checksum, 31) + 9;
    }

    public void WriteVInt(int value) {
        this.checksum = value + rotateRight(this.checksum, 31) + 33;
    }

    public void WriteLong(LogicLong value) {
        value.encode(this);
    }

    public void WriteLong(long value) {
        WriteInt((int) (value >> 32));
        WriteInt((int) value);
    }

    public void WriteBytes(byte[] value, int length) {
        this.checksum = (((value != null ? length + 28 : 27) + (this.checksum >>> 31)) | (this.checksum << 1));
    }

    public void WriteString(String value) {
        this.checksum = (value != null ? value.length() + 28 : 27) + rotateRight(this.checksum, 31);
    }

    public void WriteStringReference(String value) {
        this.checksum = value.length() + rotateRight(this.checksum, 31) + 38;
    }

    private int rotateRight(int value, int count) {
        return (value >>> count) | (value << (32 - count));
    }

    public void WriteVLong(LogicLong value) {
        WriteVInt(value.getHigherInt());
        WriteVInt(value.getLowerInt());
    }

    public void WriteVLong(long value) {
        WriteVInt((int) (value >> 32));
        WriteVInt((int) value);
    }




  
    public int GetCheckSum() {
        return this.checksum;
    }
}
