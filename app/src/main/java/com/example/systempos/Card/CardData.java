package com.example.systempos.Card;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Card")
public class CardData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int Cardid;

    @ColumnInfo
    String pro_cardNameEng;
    @ColumnInfo
    String Pro_cardNameKH;
    @ColumnInfo
    int Pro_cardQty;
    @ColumnInfo
    double Pro_cardPrice;

    double Pro_cardTax;
    String Pro_cardimg;

    public CardData() {
    }

    public CardData(int cardid, String pro_cardNameEng, String pro_cardNameKH, int pro_cardQty, double pro_cardPrice, double pro_cardTax, String pro_cardimg) {
        Cardid = cardid;
        this.pro_cardNameEng = pro_cardNameEng;
        Pro_cardNameKH = pro_cardNameKH;
        Pro_cardQty = pro_cardQty;
        Pro_cardPrice = pro_cardPrice;
        Pro_cardTax = pro_cardTax;
        Pro_cardimg = pro_cardimg;
    }

    public int getCardid() {
        return Cardid;
    }

    public void setCardid(int cardid) {
        Cardid = cardid;
    }

    public String getPro_cardNameEng() {
        return pro_cardNameEng;
    }

    public void setPro_cardNameEng(String pro_cardNameEng) {
        this.pro_cardNameEng = pro_cardNameEng;
    }

    public String getPro_cardNameKH() {
        return Pro_cardNameKH;
    }

    public void setPro_cardNameKH(String pro_cardNameKH) {
        Pro_cardNameKH = pro_cardNameKH;
    }

    public int getPro_cardQty() {
        return Pro_cardQty;
    }

    public void setPro_cardQty(int pro_cardQty) {
        Pro_cardQty = pro_cardQty;
    }

    public double getPro_cardPrice() {
        return Pro_cardPrice;
    }

    public void setPro_cardPrice(double pro_cardPrice) {
        Pro_cardPrice = pro_cardPrice;
    }

    public double getPro_cardTax() {
        return Pro_cardTax;
    }

    public void setPro_cardTax(double pro_cardTax) {
        Pro_cardTax = pro_cardTax;
    }

    public String getPro_cardimg() {
        return Pro_cardimg;
    }

    public void setPro_cardimg(String pro_cardimg) {
        Pro_cardimg = pro_cardimg;
    }
}
