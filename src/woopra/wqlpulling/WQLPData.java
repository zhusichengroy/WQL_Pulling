package woopra.wqlpulling;

class WQLPData
{
    private int colNum;
    private String [] colNameArr;
    private String [] colTitleArr;
    private int groupNum;
    private WQLPGroup [] groupArr;
    private int [] totalSummaryArr;
    
    // ### set
    void setTotal(int groupNum, int colNum, int [] sumArr)
    {
        this.groupNum = groupNum;
        this.colNum = colNum;
        this.totalSummaryArr = sumArr;
        colNameArr = new String [colNum];
        colTitleArr = new String [colNum];
        groupArr = new WQLPGroup [groupNum];
        
        for (int i = 0; i < groupNum; i ++)
            groupArr[i] = new WQLPGroup(colNum);
    }
    
    void setColName(String name, int colIdx) {colNameArr[colIdx] = name;}
    
    void setColTitle(String name, int colIdx) {colTitleArr[colIdx] = name;}
    
    void setGroupName(String name, int groupIdx) {System.out.print(groupIdx);groupArr[groupIdx].setName(name);}
    
    void setGroupColCount(int colCount, int groupIdx, int colIdx) 
    {
        groupArr[groupIdx].setColCount(colCount, colIdx);
    }
    
    void addGroupDay(WQLPDay day, int groupIdx) {groupArr[groupIdx].addDay(day);}
    
    // ### get
    int getColNum() {return colNum;}
    
    int getGroupNum() {return groupNum;}
    
    String getColName(int idx) {return colNameArr[idx];}
    
    String getColTitle(int idx) {return colTitleArr[idx];}
    
    String getGroupName(int idx) {return groupArr[idx].getName();}
    
    WQLPDay getGroupDay(int idx) {return groupArr[idx].getDay();}
    
    int getGroupColCount(int groupIdx, int colIdx) {return groupArr[groupIdx].getColCount(colIdx);}
    
    int getTotal(int idx) {return totalSummaryArr[idx];}
    
    // ### DEBUG print
    void print()
    {
       System.out.print("\t");
       for (int i = 0; i < colNum; i ++)
           System.out.print("\t" + colTitleArr[i]);
       System.out.print("\n");
       
       for (int i = 0; i < groupNum; i++)
           groupArr[i].print();
       
       System.out.print("Total\t" + groupNum);
       for (int i = 0; i < colNum; i++)
           System.out.print("\t" + totalSummaryArr[i]);
    }
}