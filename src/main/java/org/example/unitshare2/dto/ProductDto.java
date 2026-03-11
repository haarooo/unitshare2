package org.example.unitshare2.dto;



public class ProductDto {

    private int pno;
    private String pname;
    private int pprice;
    private String pcontent;
    private String pdate;
    private String openchat;
    private int people;
    private int uno;
    private int cpeople;
    private int pstatus;

    public ProductDto(){}

    public ProductDto(int pno, String pname, int pprice, String pcontent, String pdate, String openchat, int people, int uno, int cpeople , int pstatus) {
        this.pno = pno;
        this.pname = pname;
        this.pprice = pprice;
        this.pcontent = pcontent;
        this.pdate = pdate;
        this.openchat = openchat;
        this.people = people;
        this.uno = uno;
        this.cpeople = cpeople;
        this.pstatus = pstatus;
    }

    public ProductDto(int pno, String pname, int pprice, String pcontent, String pdate, String openchat, int people , int cpeople) {
        this.pno = pno;
        this.pname = pname;
        this.pprice = pprice;
        this.pcontent = pcontent;
        this.pdate = pdate;
        this.openchat = openchat;
        this.people = people;
        this.cpeople = cpeople;
    }

    public ProductDto(int pno, String pname, int pprice, String pdate, String openchat) {
        this.pno = pno;
        this.pname = pname;
        this.pprice = pprice;
        this.pdate = pdate;
        this.openchat = openchat;
    }

    public int getPno() {return pno;}
    public void setPno(int pno) {this.pno = pno;}
    public String getPname() {return pname;}
    public void setPname(String pname) {this.pname = pname;}
    public int getPprice() {return pprice;}
    public void setPprice(int pprice) {this.pprice = pprice;}
    public String getPcontent() {return pcontent;}
    public void setPcontent(String pcontent) {this.pcontent = pcontent;}
    public String getPdate() {return pdate;}
    public void setPdate(String pdate) {this.pdate = pdate;}
    public String getOpenchat() {return openchat;}
    public void setOpenchat(String openchat) {this.openchat = openchat;}
    public int getPeople() {return people;}
    public void setPeople(int people) {this.people = people;}
    public int getUno() {return uno;}
    public void setUno(int uno) {this.uno = uno;}
    public int getCpeople() {return cpeople;}
    public void setCpeople(int cpeople) {this.cpeople = cpeople;}
    public int getPstatus() {return pstatus;}
    public void setPstatus(int pstatus) {this.pstatus = pstatus;}

    @Override
    public String toString() {
        return "ProductDto{" +
                "pno=" + pno +
                ", pname='" + pname + '\'' +
                ", pprice=" + pprice +
                ", pcontent='" + pcontent + '\'' +
                ", pdate='" + pdate + '\'' +
                ", openchat='" + openchat + '\'' +
                ", people=" + people +
                ", uno=" + uno +
                ", cpeople=" + cpeople +
                ", pstatus=" + pstatus +
                '}';
    }
}
