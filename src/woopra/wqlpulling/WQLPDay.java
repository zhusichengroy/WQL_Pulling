package woopra.wqlpulling;

class WQLPDay
{
    private String date;
    private int [] dataArr;
    
    WQLPDay(String date, int [] dataArr)
    {
        this.dataArr = dataArr;
        this.date = date;
    }
    
    String getDate() {return date;}
    
    int getData(int idx) {return dataArr[idx];}
    
    void print(int colNum)
    {
        System.out.print("\t" + date);
        for (int i = 0; i < colNum; i ++)
            System.out.print("\t" + dataArr[i]);
        System.out.print("\n");
    }
}