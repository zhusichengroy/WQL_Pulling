package woopra.wqlpulling;

import java.util.Queue;
import java.util.LinkedList;

class WQLPGroup
{
    private String groupName;
    private int colNum;
    private int [] colCountArr;
    private Queue <WQLPDay> dayQ;
   
    WQLPGroup (int colNum)
    {
        this.colNum = colNum;
        colCountArr = new int [colNum];
        dayQ = new LinkedList <WQLPDay>();
    }
    
    void setName(String name) {this.groupName = name;}
    
    void setColCount(int colCount, int colIdx) {colCountArr[colIdx] = colCount;}
    
    void addDay(WQLPDay day) {dayQ.offer(day);}
    
    String getName() {return groupName;}
    
    WQLPDay getDay() {return dayQ.poll();}
    
    int getColCount(int idx) {return colCountArr[idx];}
    
    void print()
    {
        System.out.print(groupName + "\tSum up");
        for (int i = 0; i < colNum; i ++) 
            System.out.print("\t" + colCountArr[i]);
        System.out.print("\n");
        while (dayQ.peek() != null)
            dayQ.remove().print(colNum);
    }
}