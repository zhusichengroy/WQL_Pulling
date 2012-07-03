package woopra.wqlpulling;

import java.io.FileOutputStream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;

class WQLPPdfCreator
{   
    private Document doc;
    
    void createPDF(String filePath)
    {
        try {
            doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(filePath));
            doc.open();
            addMetaData(doc);
            //doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void close() {doc.close();}
    
    private void addMetaData(Document document) {
        document.addTitle("Website Analytical Data");
        document.addSubject("Woopro Query Language");
        document.addKeywords("Woopra, WQL");
        document.addAuthor("Woopra");
        document.addCreator("Roy Zhu");
    }
    
    void addParagraph(String title, boolean isTitle)
    {
        try {
            if (isTitle)
            {
                Paragraph paragraph = new Paragraph(title, 
                        new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD));
                paragraph.setAlignment(Element.ALIGN_CENTER);
                doc.add(paragraph);
                doc.add(new Paragraph(" "));
            }
            else 
            {
                Paragraph paragraph = new Paragraph(title, 
                        new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD));
                paragraph.setAlignment(Element.ALIGN_LEFT);
                doc.add(paragraph);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void drawDataTable (WQLPData myData)
    throws Exception {
        int colNum = myData.getColNum();
        int groupNum = myData.getGroupNum();
        
        PdfPTable table = new PdfPTable(colNum + 2);
        PdfPCell c;
        WQLPDay day;
        
        // for Header Rows
        table.addCell(" ");
        table.addCell(" ");
        for (int i = 0; i < colNum; i ++)
        {
            c = new PdfPCell(new Phrase(myData.getColTitle(i)));
            c.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c);
        }
        table.setHeaderRows(1);

        c = new PdfPCell(new Phrase(" "));
        c.setColspan(colNum + 2);
        table.addCell(c);

        for (int i = 0; i < groupNum; i++)
        {
            table.addCell(myData.getGroupName(i));
            day = myData.getGroupDay(i);
            while (day != null)
            {
                table.addCell(day.getDate());
                for (int j = 0; j < colNum; j++)
                    table.addCell(Integer.toString(day.getData(j)));
                table.addCell(" ");
                day = myData.getGroupDay(i);
            }
            table.addCell("Sum");
            for (int j = 0; j < colNum; j++)
                table.addCell(Integer.toString(myData.getGroupColCount(i, j)));
            
            c = new PdfPCell(new Phrase(" "));
            c.setColspan(colNum + 2);
            table.addCell(c);
        }
        
        table.addCell("Total");
        table.addCell(" ");
        for (int i = 0; i < colNum; i++)
            table.addCell(Integer.toString(myData.getTotal(i)));
        
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));
        doc.add(table);
    }
}