package Logic;

import net.Stream.ByteStream;

import net.Stream.Logic.LogicLong;

public class LogicClientAvatar {
    public void Encode(ByteStream stream) {

        stream.WriteLong(1);
        stream.WriteLong(1);
        stream.WriteBoolean(true); // IsAliveClub
        {
            stream.WriteLong(new LogicLong(0, 1));
            stream.WriteString("OpenClash");
            stream.WriteInt(1);
            stream.WriteInt(1);
            stream.WriteInt(1);
        }

        stream.WriteBoolean(true); // unk
        {
            stream.WriteLong(new LogicLong(0, 1));
        }

        stream.WriteInt(0);
        stream.WriteInt(0);

        stream.WriteInt(0);
        stream.WriteInt(1000);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);

        stream.WriteInt(0);
        stream.WriteInt(1000);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);

        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);

        stream.WriteInt(0);
        stream.WriteInt(10);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(1);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);

        stream.WriteString("v11.651.10");
        stream.WriteString(null);
        stream.WriteInt(1);
        stream.WriteInt(2);
        stream.WriteInt(1337);// gems
        stream.WriteInt(4);
        stream.WriteInt(5);
        stream.WriteInt(6);

        stream.WriteInt(0);

        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0);

        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0); 
        stream.WriteBoolean(true);
        {
            stream.WriteInt(220);
            stream.WriteInt(1828055880);
        }

        stream.WriteBoolean(false); 
        stream.WriteBoolean(false); 

        stream.WriteInt(1); // NameSetted
        stream.WriteInt(-1); // NameChanged
        stream.WriteInt(0);
        stream.WriteInt(0);
        stream.WriteInt(0); // WarState
        stream.WriteInt(0); // ShieldCost

        stream.WriteBoolean(false);
        stream.WriteBoolean(false);

        // Resource Cap
        stream.WriteInt(0);

        stream.WriteInt(1); // resources
        {
            stream.WriteInt(3000001);
            stream.WriteInt(8500000);
        }

        for (int i = 0; i < 7; i++) {
            stream.WriteInt(0);
        }

        stream.WriteInt(1);
        stream.WriteInt(28000003);
        stream.WriteInt(3);

        stream.WriteInt(0);
        stream.WriteInt(0);

        var mission = 35;
        stream.WriteInt(mission);
        for (var i = 0; i < mission; i++)
            stream.WriteInt(21000000 + i);

        stream.WriteInt(0); // Achievements - 23000000
        stream.WriteInt(0); // Completed Achievements Slots

        stream.WriteInt(94); // NPC
        for (var i = 17000000; i < 17000094; i++) {
            stream.WriteInt(i);
            stream.WriteInt(3);
        }

        stream.WriteInt(0); //  NPC Gold Gain
        stream.WriteInt(0); //  NPC Elixir Gain
        stream.WriteInt(0); 
        stream.WriteInt(0); 

        stream.WriteInt(0); 
        stream.WriteInt(0); 
        stream.WriteInt(0); 

        stream.WriteInt(0); 
        stream.WriteInt(0); 

        for (int i = 0; i < 10; i++) {
            stream.WriteInt(0);
        }
    }
}
