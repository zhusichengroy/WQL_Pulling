package woopra.wqlpulling;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

class WQLPGUI
{
    private WQLPData myData;
    
    private JFrame win;
    
    private JTextField websiteField;
    private JTextField apiKeyField;
    private JComboBox formatComboBox;
    private JTextField startDayField;
    private JTextField endDayField;
    private JTextField groupByField;
    
    private JCheckBox visitorsCheckBox;
    private JTextField visitorsRenameField;
    private JCheckBox visitsCheckBox;
    private JTextField visitsRenameField;
    private JCheckBox actionsCheckBox;
    private JTextField actionsRenameField;
    
    private JTextField filePathField;
    private JButton pullButton;
    private PullListener pListner;
    private JButton createButton;
    private CreateListener cListner;
    private JLabel displayLabel;
    
    void buildGUI()
    {
        win = new JFrame("WQL Pulling App");
        
        JPanel centerPan = new JPanel(new GridLayout(1, 2));
        JPanel southPan = new JPanel(new GridLayout(3, 1));
        
        JPanel centerLeftPan = new JPanel(new GridLayout(6,1));
        JPanel centerRightPan = new JPanel(new GridLayout(6,1));
        
        // center left panel
        JPanel lp1 = new JPanel(new GridLayout(1, 2));
        JLabel ll1 = new JLabel("Website");
        websiteField = new JTextField("perisheroy.tumblr.com");
        lp1.add(ll1);
        lp1.add(websiteField);
        centerLeftPan.add(lp1);
        
        JPanel lp2 = new JPanel(new GridLayout(1, 2));
        JLabel ll2 = new JLabel("API Key");
        apiKeyField = new JTextField("7IKORCQMSV");
        lp2.add(ll2);
        lp2.add(apiKeyField);
        centerLeftPan.add(lp2);
        
        JPanel lp3 = new JPanel(new GridLayout(1, 2));
        JLabel ll3 = new JLabel("Format");
        formatComboBox = new JComboBox();
        formatComboBox.addItem("xml");
        lp3.add(ll3);
        lp3.add(formatComboBox);
        centerLeftPan.add(lp3);
        
        JPanel lp4 = new JPanel(new GridLayout(1, 2));
        JLabel ll4 = new JLabel("Start Day");
        startDayField = new JTextField("26.06.2012");
        lp4.add(ll4);
        lp4.add(startDayField);
        centerLeftPan.add(lp4);
        
        JPanel lp5 = new JPanel(new GridLayout(1, 2));
        JLabel ll5 = new JLabel("End Day");
        endDayField = new JTextField("02.07.2012");
        lp5.add(ll5);
        lp5.add(endDayField);
        centerLeftPan.add(lp5);
        
        JPanel lp6 = new JPanel(new GridLayout(1, 2));
        JLabel ll6 = new JLabel("Group By");
        groupByField = new JTextField("info.browser");
        lp6.add(ll6);
        lp6.add(groupByField);
        centerLeftPan.add(lp6);
        
        // center right panel
        visitorsCheckBox = new JCheckBox("visitors.count");
        visitorsCheckBox.setSelected(true);
        centerRightPan.add(visitorsCheckBox);
        JPanel rp1 = new JPanel(new GridLayout(1, 2));
        JLabel rl1 = new JLabel("Rename As");
        visitorsRenameField = new JTextField("My Visitors");
        rp1.add(rl1);
        rp1.add(visitorsRenameField);
        centerRightPan.add(rp1);
        
        visitsCheckBox = new JCheckBox("visits.count");
        visitsCheckBox.setSelected(true);
        centerRightPan.add(visitsCheckBox);
        JPanel rp2 = new JPanel(new GridLayout(1, 2));
        JLabel rl2 = new JLabel("Rename As");
        visitsRenameField = new JTextField("My Visits");
        rp2.add(rl2);
        rp2.add(visitsRenameField);
        centerRightPan.add(rp2);
        
        actionsCheckBox = new JCheckBox("actions.count");
        actionsCheckBox.setSelected(true);
        centerRightPan.add(actionsCheckBox);
        JPanel rp3 = new JPanel(new GridLayout(1, 2));
        JLabel rl3 = new JLabel("Rename As");
        actionsRenameField = new JTextField("My Actions");
        rp3.add(rl3);
        rp3.add(actionsRenameField);
        centerRightPan.add(rp3);
        
        centerPan.add(centerLeftPan);
        centerPan.add(centerRightPan);
        
        // south panel
        JPanel bp1 = new JPanel(new FlowLayout());
        JLabel bl1 = new JLabel("File Path");
        filePathField = new JTextField("/Users/SichengZhu/Desktop/WQL_Data.pdf");
        bp1.add(bl1);
        bp1.add(filePathField);
        southPan.add(bp1);
        
        JPanel bp2 = new JPanel(new FlowLayout());
        pullButton = new JButton("Pull");
        pListner = new PullListener();
        pullButton.addActionListener(pListner);
        createButton = new JButton("Create");
        createButton.setEnabled(false);
        cListner = new CreateListener();
        createButton.addActionListener(cListner);
        bp2.add(pullButton);
        bp2.add(createButton);
        southPan.add(bp2);
        
        JPanel bp3 = new JPanel(new FlowLayout());
        displayLabel = new JLabel();
        bp3.add(displayLabel);
        southPan.add(bp3);
        
        win.add(centerPan, BorderLayout.CENTER);
        win.add(new JPanel(), BorderLayout.NORTH);
        win.add(new JPanel(), BorderLayout.EAST);
        win.add(new JPanel(), BorderLayout.WEST);
        win.add(southPan, BorderLayout.SOUTH);
        
        //win.setSize(1000, 500);
        win.pack();
        win.setVisible(true);
        win.setResizable(false);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public class PullListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e)
        {
            //System.out.print("\nPULL\n");
            myData = new WQLPData();
            
            String host = "api.woopra.com";
            String path = "/rest/analytics/get.jsp";
            
            String website = websiteField.getText();
            String api_key = apiKeyField.getText();
            String format = (String) formatComboBox.getSelectedItem();
            String date_format = "dd.MM.yyyy";
            String start_day = startDayField.getText();
            String end_day = endDayField.getText();
            String query = "SELECT+";
            try 
            {
            if (visitorsCheckBox.isSelected())
            {
                query += "visitors.count";
                if (visitorsRenameField.getText() != null)
                    query += "+AS+'" + URLEncoder.encode(visitorsRenameField.getText(), "UTF-8") + "'"; 
                if (visitsCheckBox.isSelected() || actionsCheckBox.isSelected())
                    query += ",+";
            }
            if (visitsCheckBox.isSelected())
            {
                query += "visits.count";
                if (visitsRenameField.getText() != null)
                    query += "+AS+'" + URLEncoder.encode(visitsRenameField.getText(), "UTF-8") + "'"; 
                if (actionsCheckBox.isSelected())
                    query += ",+";
            }
            if (actionsCheckBox.isSelected())
            {
                query += "actions.count";
                if (actionsRenameField.getText() != null)
                    query += "+AS+'" + java.net.URLEncoder.encode(actionsRenameField.getText(), "UTF-8") + "'"; 
            }
            query += "+From+cloud+GROUP+BY+'" + groupByField.getText() +"'";
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            
            /*
            String website = "perisheroy.tumblr.com";
            String api_key = "7IKORCQMSV";
            String format = "xml";
            String date_format = "dd.MM.yyyy";
            String start_day = "26.06.2012";
            String end_day = "01.07.2012";
            String query = "SELECT+visitors.count+AS+'Visitors',+visits.count+AS+'My+Visits',+actions.count+AS+'Actions'+FROM+cloud+GROUP+BY+'info.city'";
             */
            
            String wrlString = "website=" + website +
                               "&api_key=" + api_key +
                               "&format=" + format +
                               "&date_format=" + date_format +
                               "&start_day=" + start_day +
                               "&end_day=" + end_day +
                               "&query=" + query;//java.net.URLEncoder.encode(query, "UTF-8");
                
            try
            {
                URI uri = new URI("http", null, host, 80, path, wrlString, null);
                System.out.println(uri.toASCIIString());
                URL url = uri.toURL();
        
            // ### print out received msg directly
            /*
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) 
            {
                System.out.println(inputLine);
            }
            in.close();
            */
            
                // ### SAX
                ContentHandler contentHandler = new WQLPHandler(myData);
                XMLReader myReader = XMLReaderFactory.createXMLReader();
                myReader.setContentHandler(contentHandler);
                myReader.parse(new InputSource(url.openStream()));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            // ### DEBUG print
            //myData.print();
            createButton.setEnabled(true);
            displayLabel.setText("Pull for " + website + " Successfully!");
        }
    }
    
    public class CreateListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //System.out.print("\nCreate\n");
            String filePath = filePathField.getText();
            
            // ### draw in PDF 
            WQLPPdfCreator pdfCreator = new WQLPPdfCreator();
            pdfCreator.createPDF(filePath);
            pdfCreator.addParagraph("Website Analytical Data", true);
            pdfCreator.addParagraph("for " + websiteField.getText(), false);
            pdfCreator.addParagraph("by woopra.com", false);
            try 
            {
                pdfCreator.drawDataTable(myData);
            }
            catch (Exception exception0)
            {
                exception0.printStackTrace();
            }   
            pdfCreator.close();
            createButton.setEnabled(false);
            displayLabel.setText("Create " + filePath + " Successfully!");
        }    
    }
}