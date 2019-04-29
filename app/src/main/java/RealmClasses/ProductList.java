package RealmClasses;

import io.realm.RealmObject;

public class ProductList extends RealmObject {

   private  String name, type, rate, unit;
   private  int barcode, hsn, sale, purchase;

   public String getName(){return name;}
   public String getType(){return type;}
   public String getRate(){return rate;}
   public int getBarcode(){return barcode;}
   public int getHsn(){return hsn;}
   public int getSale(){return sale;}
   public int getPurchase(){return purchase;}
   public String getUnit(){return unit;}

    public void setName(String name)
   {
       this.name = name;
   }

    public  void setUnit(String unit)
    {
        this.unit = unit;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public void setRate(String rate)
    {
       this.rate = rate;
    }
    public void setBarcode(int barcode)
    {
        this.barcode = barcode;
    }
    public void setHsn(int hsn)
    {
        this.hsn = hsn;
    }
    public void setSale(int sale)
    {
        this.sale = sale;
    }
    public void setPurchase(int purchase)
    {
        this.purchase = purchase;
    }

}
