package com.example.demoapplication;

import java.util.List;

public class HotPepperGourmetListHolder {

    private List<HotPepperGourmet> hotPepperGourmetList;

    public HotPepperGourmetListHolder(List<HotPepperGourmet> hotPepperGourmetList){
        this.hotPepperGourmetList = hotPepperGourmetList;
    }

    public void setHotPepperGourmetList(List<HotPepperGourmet> hotPepperGourmetList) {
        this.hotPepperGourmetList = hotPepperGourmetList;
    }

    public List<HotPepperGourmet> getHotPepperGourmetList(){
        return this.hotPepperGourmetList;
    }

    public int getHotPepperGourmetListSize(){
        return this.hotPepperGourmetList.size();
    }

    public HotPepperGourmet getHotPepperGourmetByIndex(int index){
        return this.hotPepperGourmetList.get(index);
    }

}
