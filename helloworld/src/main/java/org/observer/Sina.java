package org.observer;

public class Sina implements Observer{
    @Override
    public void update(String qiwen, String qiya, String shidu) {
        System.out.println("Sina网站数据天气-->qiwen="+qiwen+",qiya="+qiya+",shidu=="+shidu);
    }
}
