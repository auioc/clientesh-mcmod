function initializeCoreMod() {
    ASMAPI = Java.type('net.minecraftforge.coremod.api.ASMAPI');

    Opcodes = Java.type('org.objectweb.asm.Opcodes');

    InsnList = Java.type('org.objectweb.asm.tree.InsnList');

    VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
    MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');

    return {
        'SubtitleOverlay#render': {
            target: {
                type: 'METHOD',
                class: 'net.minecraft.client.gui.components.SubtitleOverlay',
                methodName: ASMAPI.mapMethod('m_94642_'),
                methodDesc: '(Lcom/mojang/blaze3d/vertex/PoseStack;)V',
            },
            transformer: function (methodNode) {
                var instructions = methodNode.instructions;

                var aloadSub2 = instructions.get(
                    instructions.indexOf(
                        ASMAPI.findFirstMethodCall(
                            methodNode,
                            ASMAPI.MethodType.VIRTUAL,
                            'net/minecraft/client/gui/components/SubtitleOverlay$Subtitle',
                            ASMAPI.mapMethod('m_94659_'),
                            '()Lnet/minecraft/world/phys/Vec3;'
                        )
                    ) - 1
                );

                var istoreI2 = instructions.get(
                    instructions.indexOf(
                        ASMAPI.findFirstMethodCall(
                            methodNode,
                            ASMAPI.MethodType.VIRTUAL,
                            'com/mojang/blaze3d/vertex/PoseStack',
                            ASMAPI.mapMethod('m_85836_'),
                            '()V'
                        )
                    ) - 4
                );

                var aloadComp = instructions.get(
                    instructions.indexOf(
                        ASMAPI.findFirstMethodCallBefore(
                            methodNode,
                            ASMAPI.MethodType.VIRTUAL,
                            'net/minecraft/client/gui/Font',
                            ASMAPI.mapMethod('m_92852_'),
                            '(Lnet/minecraft/network/chat/FormattedText;)I',
                            instructions.indexOf(istoreI2)
                        )
                    ) - 1
                );

                var toInject = new InsnList();
                {
                    toInject.add(new VarInsnNode(Opcodes.ALOAD, aloadComp.var));
                    toInject.add(new VarInsnNode(Opcodes.ALOAD, aloadSub2.var));
                    toInject.add(new VarInsnNode(Opcodes.ILOAD, istoreI2.var));
                    toInject.add(
                        new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            'org/auioc/mcmod/clientesh/content/widget/SubtitleHighlight',
                            'adjustColor',
                            '(Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/components/SubtitleOverlay$Subtitle;I)I',
                            false
                        )
                    );
                    toInject.add(new VarInsnNode(Opcodes.ISTORE, istoreI2.var));
                    // toInject.add(new VarInsnNode(Opcodes.ILOAD, 7));
                    // toInject.add(new VarInsnNode(Opcodes.ILOAD, 23));
                    // toInject.add(
                    //     new MethodInsnNode(
                    //         Opcodes.INVOKESTATIC,
                    //         'java/lang/Math',
                    //         'max',
                    //         '(II)I',
                    //         false
                    //     )
                    // );
                    // toInject.add(new InsnNode(Opcodes.ICONST_2));
                    // toInject.add(new InsnNode(Opcodes.IDIV));
                    // toInject.add(new VarInsnNode(Opcodes.ISTORE, 19));
                    // // l = Math.max(j, k1) / 2;
                }

                methodNode.instructions.insert(istoreI2, toInject);

                // print(ASMAPI.methodNodeToString(methodNode));
                return methodNode;
            },
        },
    };
}

//! SRG <-> MCP
/*
    m_94642_    render      net/minecraft/client/gui/components/SubtitleOverlay/render (Lcom/mojang/blaze3d/vertex/PoseStack;)V
    m_85836_    pushPose    com/mojang/blaze3d/vertex/PoseStack/pushPose ()V
    m_94659_    getLocation net/minecraft/client/gui/components/SubtitleOverlay$Subtitle/getLocation ()Lnet/minecraft/world/phys/Vec3;
    m_92852_    width       net/minecraft/client/gui/Font/width (Lnet/minecraft/network/chat/FormattedText;)I
*/

//! LocalVariableTable
/*
    Slot    Name                         Signature
    9       subtitleoverlay$subtitle     Lnet/minecraft/client/gui/components/SubtitleOverlay$Subtitle;
    11      k                            I
~   12      component                    Lnet/minecraft/network/chat/Component;
    13      vec34                        Lnet/minecraft/world/phys/Vec3;
    14      d0                           D
    16      d1                           D
    18      flag                         Z
    19      l                            I
    20      i1                           I
    21      j1                           I
    22      f                            F
    23      k1                           I
    24      l1                           I
~   25      i2                           I
~   10      subtitleoverlay$subtitle1    Lnet/minecraft/client/gui/components/SubtitleOverlay$Subtitle;
    2       vec3                         Lnet/minecraft/world/phys/Vec3;
    3       vec31                        Lnet/minecraft/world/phys/Vec3;
    4       vec32                        Lnet/minecraft/world/phys/Vec3;
    5       vec33                        Lnet/minecraft/world/phys/Vec3;
    6       i                            I
    7       j                            I
    8       iterator                     Ljava/util/Iterator;
    0       this                         Lnet/minecraft/client/gui/components/SubtitleOverlay;
    1       p_94643_                     Lcom/mojang/blaze3d/vertex/PoseStack;
*/

//! Code
/*
    public void render(PoseStack p_94643_) {
        //_ ...
        if (this.isListening && !this.subtitles.isEmpty()) {
            //_ ...
            Component component = subtitleoverlay$subtitle1.getText();
            //_ ...
            for(SubtitleOverlay.Subtitle subtitleoverlay$subtitle1 : this.subtitles) {
                //_ ...
                int l1 = Mth.floor(Mth.clampedLerp(255.0F, 75.0F, (float)(Util.getMillis() - subtitleoverlay$subtitle1.getTime()) / 3000.0F));
                int i2 = l1 << 16 | l1 << 8 | l1;
+               i2 = org.auioc.mcmod.clientesh.content.widget.SubtitleHighlight.adjustColor(component, subtitleoverlay$subtitle1, i2);
                p_94643_.pushPose();
                //_ ...
            }
            //_ ...
        }
    }
*   ========== ByteCode ==========   *
            //_ ...
            L29
                LINENUMBER 65 L29
~ -1(aloadSub2) ALOAD 10
~  0            INVOKEVIRTUAL net/minecraft/client/gui/components/SubtitleOverlay$Subtitle.getLocation ()Lnet/minecraft/world/phys/Vec3;
                //_ ...
            //_ ...
            L39
                LINENUMBER 73 L39
                //_ ...
~ -1(aloadComp) ALOAD 12
~  0            INVOKEVIRTUAL net/minecraft/client/gui/Font.width (Lnet/minecraft/network/chat/FormattedText;)I
   ↑            //_ ...
   │        //_ ...
   │        L41
   │            LINENUMBER 75 L41
   │            //_ ...
~ -4(istoreI2)↓ ISTORE 25
+ ↑             ALOAD 12
+ │             ALOAD 10
+ │             ILOAD 25
+ │             INVOKESTATIC org/auioc/mcmod/clientesh/content/widget/SubtitleHighlight.adjustColor (Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/components/SubtitleOverlay$Subtitle;I)I
+ │             ISTORE 25
  │-3       L42
  │-2         LINENUMBER 76 L42
  │-1         ALOAD 1
~ 0           INVOKEVIRTUAL com/mojang/blaze3d/vertex/PoseStack.pushPose ()V
            //_ ...
*/
