package net.Stream.Logic;

import net.Stream.ByteStream;
import net.Stream.ChecksumEncoder;

public class LogicLong {

    private int highInteger;
    private int lowInteger;

    public LogicLong() {
    }

    public LogicLong(int highInteger, int lowInteger) {
        this.highInteger = highInteger;
        this.lowInteger = lowInteger;
    }

    public LogicLong(long value) {
        this.highInteger = (int) (value >> 32);
        this.lowInteger = (int) value;
    }

    public int getHigherInt() {
        return this.highInteger;
    }

    public int getLowerInt() {
        return this.lowInteger;
    }

    public void decode(ByteStream stream) {
        this.highInteger = stream.ReadInt();
        this.lowInteger = stream.ReadInt();
    }

    public void encode(ByteStream stream) {
        stream.WriteInt(this.highInteger);
        stream.WriteInt(this.lowInteger);
    }

    public void encode(ChecksumEncoder stream) {
        stream.WriteInt(this.highInteger);
        stream.WriteInt(this.lowInteger);
    }
}
