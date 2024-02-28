package org.segregation;

/**
 * 接口隔离原则
 * 一个类对另一个类依赖应该建立在最小接口上
 */
public class Segregation {
    public static void main(String[] args) {
        A a = new A();
        a.depend1(new B());
        C c = new C();
        c.depend4(new D());
    }
}

interface Segregationinterface{
    public void operation1();
    public void operation2();
    public void operation3();
}

interface Segregationinterface2{
    public void operation4();
    public void operation5();
}

class A {
    void depend1(Segregationinterface segregationinterface){
        segregationinterface.operation1();
    }
    void depend2(Segregationinterface segregationinterface){
        segregationinterface.operation2();
    }
    void depend3(Segregationinterface segregationinterface){
        segregationinterface.operation3();
    }
}

class C {
    void depend4(Segregationinterface2 segregationinterfac2){
        segregationinterfac2.operation4();
    }
    void depend5(Segregationinterface2 segregationinterface2){
        segregationinterface2.operation5();
    }
}



class B implements Segregationinterface{

    @Override
    public void operation1() {

    }

    @Override
    public void operation2() {

    }

    @Override
    public void operation3() {

    }
}

class D implements Segregationinterface2{
    @Override
    public void operation4() {

    }

    @Override
    public void operation5() {

    }
}
