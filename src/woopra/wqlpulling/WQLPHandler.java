package woopra.wqlpulling;

import org.xml.sax.*;
import java.util.LinkedList;

public class WQLPHandler implements ContentHandler
{   
    private WQLPData myData;
    private LinkedList <String> eleList;
    private int colIdx;
    private int groupIdx;
    
    WQLPHandler(WQLPData data) 
    {
        this.myData = data;
        eleList = new LinkedList <String>();
        colIdx = 0;
        groupIdx = 0;
    }
    
    private void processDataFromAtts(Attributes atts)
    {
        if (atts.getLength() == 0)
            return;
        if (eleList.getLast().equals("total"))
        {
            int groupNum = Integer.parseInt(atts.getValue(0));
            int colNum = atts.getLength() - 1;
            int [] arr = new int [colNum];
            for (int i = 0; i < colNum; i ++)
                arr[i] = Integer.parseInt(atts.getValue(i+1));
            this.myData.setTotal(groupNum, colNum, arr);
        }
        else if (eleList.getLast().equals("day"))
        {
            String date = atts.getValue(0);
            int [] dataArr = new int [atts.getLength() - 1]; 
            for (int i = 1; i < atts.getLength(); i ++)
                dataArr[i-1] = Integer.parseInt(atts.getValue(i));
            WQLPDay day = new WQLPDay(date, dataArr);
            myData.addGroupDay(day, groupIdx);
        }
    }
    
    private void processDataFromChars(String receivedStr)
    {
        //System.out.print("\n" + receivedStr + "\n");
        if (eleList.getLast().equals("name"))
        {
            if (eleList.get(eleList.size() - 2) == "column")
                myData.setColName(receivedStr, colIdx);
            else if (eleList.get(eleList.size() - 2) == "item")
                myData.setGroupName(receivedStr, groupIdx);
        }
        else if (eleList.getLast().equals("title"))
        {
            if (eleList.get(eleList.size() - 2) == "column")
                myData.setColTitle(receivedStr, colIdx);
        }
        for (int i = 0; i < myData.getColNum(); i++)
        {
            if (eleList.getLast().equals(myData.getColName(i)))
                myData.setGroupColCount(Integer.parseInt(receivedStr), groupIdx, i);
        }
    }
    
    public void setDocumentLocator (Locator locator) {}
    
    public void startDocument ()
    throws SAXException  { 
        System.out.print("\n*** startDocument() ***\n"); 
    }
    
    public void endDocument()
    throws SAXException {  
        System.out.print("\n*** endDocument() ***\n"); 
    }

    public void startPrefixMapping (String prefix, String uri)
    throws SAXException {}

    public void endPrefixMapping (String prefix)
    throws SAXException  {}

    public void startElement (String uri, String localName,
                  String qName, Attributes atts)
    throws SAXException  {  
        //System.out.println("Hello from startElement()!"); 
        //System.out.println(uri);
        //System.out.println(localName);
        System.out.print("<" + qName);
        int num = atts.getLength();
        for (int i = 0; i < num; i ++)
        {
            System.out.print(" " + atts.getLocalName(i) + "=\"" + atts.getValue(i) + "\"");
            //System.out.print(atts.getType(i));
        }
        System.out.print(">");
        
        this.eleList.addLast(qName);
        processDataFromAtts(atts);
    }

    public void endElement (String uri, String localName,
                String qName)
    throws SAXException {  
        //System.out.println("Hello from endElement()!"); 
        //System.out.println(uri);
        //System.out.println(localName);
        System.out.print("</");
        System.out.print(qName);
        System.out.print(">");
        
        if (this.eleList.getLast().equals("column")) 
            this.colIdx++;
        else if (this.eleList.getLast().equals("item")) 
            this.groupIdx++;
        this.eleList.removeLast();
    }

    public void characters (char ch[], int start, int length)
    throws SAXException {  
        //System.out.println("Hello from characters()!"); 
        for (int i = start; i < start + length; i++)
            System.out.print(ch[i]);
        
        processDataFromChars(new String(ch, start, length));
    }

    public void ignorableWhitespace (char ch[], int start, int length)
    throws SAXException {  
        /*
        System.out.println("Hello from ignorableWhitespace()!"); 
        for (int i = start; i < start + length; i++)
            System.out.print(ch[i]);
        */
    }

    public void processingInstruction (String target, String data)
    throws SAXException {  
        /*
        System.out.println("Hello from processingInstruction()!"); 
        System.out.println(target);
        System.out.println(data);
        */
    }

    public void skippedEntity (String name)
    throws SAXException {  
        /*
        System.out.println("Hello from skippedEntity()!"); 
        System.out.println(name);
        */
    }
}