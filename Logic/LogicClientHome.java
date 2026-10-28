package Logic;

import net.Stream.ByteStream;

public class LogicClientHome {

    public String STARTER_BUILDINGS = "[{\"data\":1000015,\"lvl\":0,\"x\":9,\"y\":15},{\"data\":1000015,\"lvl\":0,\"x\":17,\"y\":15},{\"data\":1000015,\"lvl\":0,\"x\":23,\"y\":15},{\"data\":1000015,\"lvl\":0,\"x\":31,\"y\":15},{\"data\":1000015,\"lvl\":0,\"x\":9,\"y\":17},{\"data\":1000015,\"lvl\":0,\"x\":17,\"y\":17},{\"data\":1000015,\"lvl\":0,\"x\":21,\"y\":17},{\"data\":1000015,\"lvl\":0,\"x\":23,\"y\":17},{\"data\":1000015,\"lvl\":0,\"x\":29,\"y\":17},{\"data\":1000015,\"lvl\":0,\"x\":31,\"y\":17},{\"data\":1000015,\"lvl\":0,\"x\":9,\"y\":19},{\"data\":1000015,\"lvl\":0,\"x\":17,\"y\":19},{\"data\":1000015,\"lvl\":0,\"x\":23,\"y\":19},{\"data\":1000015,\"lvl\":0,\"x\":31,\"y\":19},{\"data\":1000015,\"lvl\":0,\"x\":9,\"y\":21},{\"data\":1000015,\"lvl\":0,\"x\":17,\"y\":21},{\"data\":1000015,\"lvl\":0,\"x\":23,\"y\":21},{\"data\":1000015,\"lvl\":0,\"x\":31,\"y\":21},{\"data\":1000015,\"lvl\":0,\"x\":11,\"y\":23},{\"data\":1000015,\"lvl\":0,\"x\":15,\"y\":23},{\"data\":1000015,\"lvl\":0,\"x\":23,\"y\":23},{\"data\":1000015,\"lvl\":0,\"x\":31,\"y\":23},{\"data\":1000015,\"lvl\":0,\"x\":11,\"y\":25},{\"data\":1000015,\"lvl\":0,\"x\":15,\"y\":25},{\"data\":1000015,\"lvl\":0,\"x\":23,\"y\":25},{\"data\":1000015,\"lvl\":0,\"x\":31,\"y\":25},{\"data\":1000015,\"lvl\":0,\"x\":13,\"y\":27},{\"data\":1000015,\"lvl\":0,\"x\":21,\"y\":27},{\"data\":1000015,\"lvl\":0,\"x\":23,\"y\":27},{\"data\":1000015,\"lvl\":0,\"x\":25,\"y\":27},{\"data\":1000015,\"lvl\":0,\"x\":29,\"y\":27},{\"data\":1000015,\"lvl\":0,\"x\":31,\"y\":27},{\"data\":1000015,\"lvl\":0,\"x\":33,\"y\":27}]";

    public void Encode(ByteStream stream) {
        stream.WriteInt(0);

        stream.WriteLong(1);

        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteBoolean(true);
        stream.WriteCompressedString(
                "{\"exp_ver\":1,\"last_news_seen\":0,\"active_layout\":0,\"buildings\":"
                        + STARTER_BUILDINGS
                        + ",\"obstacles\":[],\"decos\":[],\"traps\":[],\"vobjs\":[],\"buildings2\":[],\"obstacles2\":[],\"decos2\":[],\"traps2\":[],\"vobjs2\":[],\"skins\":[52000001,52000004,52000005,52000006,52000007,52000008,52000009,52000010,52000011,52000012,52000013,52000014,52000015,52000016,52000017,52000018,52000019,52000020,52000021,52000022,52000023,52000024,52000025,52000026,52000027,52000029,52000030,52000031,52000032,52000033,52000034,52000035,52000036,52000037,52000038,52000039,52000040,52000041,52000042,52000043,52000044,52000045,52000046,52000047,52000048,52000049,52000050,52000051,52000052,52000053,52000054,52000055,52000056,52000057,52000058,52000059,52000060,52000061,52000062,52000063,52000064,52000065,52000066,52000067,52000068,52000069,52000070,52000071,52000072,52000073,52000074,52000075,52000076,52000077,52000078,52000079,52000080]}");

        stream.WriteBoolean(true);
        stream.WriteCompressedString("{\"event\":[]}");

        stream.WriteBoolean(true);
        stream.WriteCompressedString(
                "{\"Village2\":{\"TownHallMaxLevel\":9,\"ScoreChangeForLosing\":[{\"Milestone\":0,\"Percentage\":0},{\"Milestone\":400,\"Percentage\":30},{\"Milestone\":800,\"Percentage\":55},{\"Milestone\":1200,\"Percentage\":70},{\"Milestone\":1600,\"Percentage\":85},{\"Milestone\":2000,\"Percentage\":95},{\"Milestone\":2400,\"Percentage\":100}],\"StrengthRangeForScore\":[{\"Milestone\":0,\"Percentage\":60},{\"Milestone\":200,\"Percentage\":80},{\"Milestone\":400,\"Percentage\":100},{\"Milestone\":600,\"Percentage\":120},{\"Milestone\":800,\"Percentage\":140},{\"Milestone\":1000,\"Percentage\":160},{\"Milestone\":1200,\"Percentage\":180},{\"Milestone\":1400,\"Percentage\":200},{\"Milestone\":1600,\"Percentage\":400},{\"Milestone\":1800,\"Percentage\":600},{\"Milestone\":2000,\"Percentage\":1000}]},\"KillSwitches\":{\"TestValue\":true}}");
    }
}
