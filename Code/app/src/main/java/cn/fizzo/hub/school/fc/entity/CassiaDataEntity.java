package cn.fizzo.hub.school.fc.entity;

public class CassiaDataEntity {


    public String macaddr;
    public int bpm;
    public int stepcount;
    public int stridefrequencie;
    public int rssis;

    public CassiaDataEntity() {
    }

    public CassiaDataEntity(String macaddr, int bpm, int stepcount, int stridefrequencie, int rssis) {
        super();
        this.macaddr = macaddr;
        this.bpm = bpm;
        this.stepcount = stepcount;
        this.stridefrequencie = stridefrequencie;
        this.rssis = rssis;
    }
}
