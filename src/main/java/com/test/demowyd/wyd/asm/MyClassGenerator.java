package com.test.demowyd.wyd.asm;


import org.springframework.asm.ClassWriter;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @program: spring-wyd
 * @description: 第二个asm的demo类
 * @author: Stone
 * @create: 2023-11-08 09:49
 **/
public class MyClassGenerator extends ClassLoader implements Opcodes {

    public static void main(String[] args) throws Exception {
        byte[] code = generateClass();

        FileOutputStream fos = new FileOutputStream("MyClass.class");
        fos.write(code);
        fos.close();

        MyClassGenerator loader = new MyClassGenerator();
        Class myClass = loader.defineClass("MyClass", code, 0, code.length);
        Object o = myClass.getConstructor().newInstance();
        System.out.println(o);

    }

    public static byte[] generateClass() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "MyClass", null, "java/lang/Object", null);

        // 添加成员属性
        cw.visitField(Opcodes.ACC_PRIVATE, "myField", "Ljava/lang/String;", null, null).visitEnd();

        // 添加构造函数
        MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        constructor.visitCode();
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(1, 1);
        constructor.visitEnd();

        // 添加方法
        MethodVisitor method = cw.visitMethod(Opcodes.ACC_PUBLIC, "myMethod", "()V", null, null);
        method.visitCode();
        method.visitLdcInsn("Hello, world!");
        method.visitVarInsn(Opcodes.ASTORE, 1);
        method.visitInsn(Opcodes.RETURN);
        method.visitMaxs(2, 2);
        method.visitEnd();

        cw.visitEnd();
        return cw.toByteArray();
    }
}
