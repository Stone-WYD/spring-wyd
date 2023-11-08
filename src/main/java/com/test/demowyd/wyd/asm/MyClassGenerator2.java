package com.test.demowyd.wyd.asm;


import com.test.demowyd.wyd.web.Controller1;
import org.springframework.asm.ClassWriter;
import org.springframework.asm.Label;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;

import java.io.FileOutputStream;

/**
 * @program: spring-wyd
 * @description: 第二个asm的demo类
 * @author: Stone
 * @create: 2023-11-08 09:49
 **/
public class MyClassGenerator2 extends ClassLoader implements Opcodes {

    public static void main(String[] args) throws Exception {
        byte[] code = generateClass();

        FileOutputStream fos = new FileOutputStream("MyTargetClass.class");
        fos.write(code);
        fos.close();

        MyClassGenerator2 loader = new MyClassGenerator2();
        Class<?> myClass = loader.defineClass("MyTargetClass", code, 0, code.length);
        Object o = myClass.getConstructor(Controller1.class).newInstance(new Controller1());
        System.out.println(o);

    }

    public static byte[] generateClass() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "MyTargetClass", null, "java/lang/Object", null);

        // 添加成员变量
        cw.visitField(Opcodes.ACC_PRIVATE, "controller1", "Lcom/test/demowyd/wyd/web/Controller1;", null, null).visitEnd();

        // 添加构造函数
        MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "(Lcom/test/demowyd/wyd/web/Controller1;)V", null, null);
        constructor.visitCode();
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        constructor.visitVarInsn(Opcodes.ALOAD, 1);
        constructor.visitFieldInsn(Opcodes.PUTFIELD, "MyTargetClass", "controller1", "Lcom/test/demowyd/wyd/web/Controller1;");
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(2, 2);
        constructor.visitEnd();

        // 添加方法
        MethodVisitor method = cw.visitMethod(Opcodes.ACC_PUBLIC, "invoke", "(Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", null, null);
        method.visitCode();
        method.visitLdcInsn("/myOwnTest");
        method.visitVarInsn(Opcodes.ALOAD, 1);
        method.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);

        Label ifLable1 = new Label();
        method.visitJumpInsn(Opcodes.IFEQ, ifLable1);
        method.visitVarInsn(Opcodes.ALOAD, 2);
        method.visitTypeInsn(Opcodes.CHECKCAST, "com/test/demowyd/wyd/web/Controller1");

        method.visitVarInsn(Opcodes.ALOAD, 3);
        method.visitInsn(Opcodes.ICONST_0);
        method.visitInsn(Opcodes.AALOAD);
        method.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/String");

        method.visitVarInsn(Opcodes.ALOAD, 3);
        method.visitInsn(Opcodes.ICONST_1);
        method.visitInsn(Opcodes.AALOAD);
        method.visitTypeInsn(Opcodes.CHECKCAST, "com/test/demowyd/wyd/web/Controller1$User");
        method.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/test/demowyd/wyd/web/Controller1", "test5", "(Ljava/lang/String;Lcom/test/demowyd/wyd/web/Controller1$User;)Lcom/test/demowyd/wyd/web/Controller1$User;", false);
        method.visitInsn(Opcodes.ARETURN);

        method.visitLabel(ifLable1);
        method.visitLdcInsn("/test2");
        method.visitVarInsn(Opcodes.ALOAD, 1);
        method.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
        Label ifLabel2 = new Label();
        method.visitJumpInsn(Opcodes.IFEQ, ifLabel2);
        method.visitVarInsn(Opcodes.ALOAD, 2);
        method.visitTypeInsn(Opcodes.CHECKCAST, "com/test/demowyd/wyd/web/Controller1");
        method.visitVarInsn(Opcodes.ALOAD, 3);
        method.visitInsn(Opcodes.ICONST_0);
        method.visitInsn(Opcodes.AALOAD);
        method.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/String");
        method.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "com/test/demowyd/wyd/web/Controller1", "test2", "(Ljava/lang/String;)Lorg/springframework/web/servlet/ModelAndView;", false);
        method.visitInsn(Opcodes.ARETURN);

        method.visitLabel(ifLabel2);
        method.visitInsn(ACONST_NULL);
        method.visitInsn(Opcodes.ARETURN);

        method.visitMaxs(4, 4);
        method.visitEnd();

        cw.visitEnd();
        return cw.toByteArray();
    }
}
