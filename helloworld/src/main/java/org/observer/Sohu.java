package org.observer;

public class Sohu implements Observer{
    @Override
    public void update(String qiwen, String qiya, String shidu) {
        System.out.println("Sohu网站数据天气-->qiwen="+qiwen+",qiya="+qiya+",shidu=="+shidu);
    }
}
