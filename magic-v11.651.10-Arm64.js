//script by @pmdrkdv

const base = Process.getModuleByName('libg.so').base;

const Armceptor = {
    jumpout: function (addr, target) {
        Memory.patchCode(addr, 4, (code) => {
            const writer = new Arm64Writer(code, {
                pc: addr
            });

            writer.putBranchAddress(target);
            writer.flush();
        });
    },
    ret: function (addr) {
        Memory.patchCode(addr, 4, (code) => {
            const writer = new Arm64Writer(code, {
                pc: addr
            });

            writer.putRet();
            writer.flush();
        });
    },
    nop: function (addr) {
        Memory.patchCode(addr, 4, (code) => {
            const writer = new Arm64Writer(code, {
                pc: addr
            });

            writer.putNop();
            writer.flush();
        });
    },
    replace: function (address, newInsn) {
        Memory.protect(address, newInsn.length, 'rwx');
        address.writeByteArray(newInsn);
        Memory.protect(address, newInsn.length, 'rx');
    }
}

const ArhanKilled = {
    Init() {
        Armceptor.ret(base.add(0x4E0900)); // AntiCheat::AntiHackFlags        
        Armceptor.jumpout(base.add(0x5465C0), base.add(0x133A68)); // g_createGameInstance
        Armceptor.jumpout(base.add(0x5B029C), base.add(0x5CB750)); // GameMain constructor
        Armceptor.jumpout(base.add(0x10B8B0), base.add(0x10C90C)); // InputSystem::update
        Armceptor.jumpout(base.add(0x4303E8), base.add(0x43128C)); // TcpSocket::create
        Armceptor.jumpout(base.add(0x46C0A4), base.add(0x46E2C8)); // TcpSocket::read
        Armceptor.jumpout(base.add(0x755354), base.add(0x756170)); // LoginMessage::decode
        Armceptor.jumpout(base.add(0x565178), base.add(0x566400)); // LoginMessage::encode
        Armceptor.ret(base.add(0x71C4CC));  // AntiCheat::AntiCheat
        Armceptor.ret(base.add(0x61FDBC));  // AntiCheat::deepScanThreadFunc
    }
}
const ServerConnect = {
    init() {
        Interceptor.attach(Module.findGlobalExportByName('getaddrinfo'), {
            onEnter(args) {
                this.host = Memory.allocUtf8String("192.168.0.106")
                args[0] = this.host
                this.port = Memory.allocUtf8String("9339")
                args[1] = this.port
            }
        })
    }
}

const Messaging = {

    init() {
        this.socketChecks();
        this.reloadFix();
        this.pepperKiller();
        this.sendPepperAuthentication();
        this.encryptAndWrite();
    },
    socketChecks() {
        Armceptor.nop(base.add(0x4DA234));
        Armceptor.nop(base.add(0x4DA23C));
        Armceptor.nop(base.add(0x4DA250));
        Armceptor.nop(base.add(0x4DA25C));
    },
    reloadFix() {
        Armceptor.nop(base.add(0x670118));
        Armceptor.nop(base.add(0x67011C));
        Armceptor.nop(base.add(0x67012C));
        Armceptor.nop(base.add(0x670130));
    },
    pepperKiller() {
        Armceptor.nop(base.add(0x301A5C));
        Armceptor.nop(base.add(0x4EA6EC));
    },
    sendPepperAuthentication() {
        Interceptor.attach(base.add(0x43B98C), {
            onEnter(args) {
                this.state = args[0].add(0x254);
                this.state.writeInt(5);
                args[1] = args[2];
            },
            onLeave() {
                this.state.writeInt(5)
            }
        });
    },
    encryptAndWrite() {
        Interceptor.attach(base.add(0x32A0CC), {
            onEnter() {
                this.context.x0 = ptr(0x2774);
            }
        });
    }
}

function init() {
    ArhanKilled.Init();
    ServerConnect.init();
    Messaging.init();
    base.add(0xDA7979).writeUtf8String("ReversedOfClans");
}
setImmediate(init);
