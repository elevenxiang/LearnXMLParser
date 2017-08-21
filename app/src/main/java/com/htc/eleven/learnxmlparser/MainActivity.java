package com.htc.eleven.learnxmlparser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;

    /**
     * get system line break;
     * */
    String lineBreak = System.getProperty("line.separator");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * TextView to show XML file content.
         * */
        tv = (TextView) findViewById(R.id.tv);

        /**
         * button to trigger create XML file.
         * */
        findViewById(R.id.CreateXML).setOnClickListener(this);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder  builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(getAssets().open("test.xml"));
            Element element = document.getDocumentElement();

            NodeList list = element.getElementsByTagName("Lan");
            for (int i=0; i<list.getLength(); i++) {
                Element lan = (Element) list.item(i);
                tv.append(lan.getAttribute("id") + ".");
                tv.append(lan.getElementsByTagName("name").item(0).getTextContent() + " ");
                tv.append(lan.getElementsByTagName("ide").item(0).getTextContent() + lineBreak );
            }

            /**
             * add new element based on current content.
             * */
            Element e1 = document.createElement("Lan");
            e1.setAttribute("id", "4");
            Element sub1 = document.createElement("name");
            sub1.setTextContent("eleven");
            Element sub2 = document.createElement("ide");
            sub2.setTextContent("Home");

            e1.appendChild(sub1);
            e1.appendChild(sub2);

            element.appendChild(e1);

            /**
             * create another XML file to store new added content and previous content.
             * */
            // transfer and create xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer;

            try {
                transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty("encoding", "UTF-8");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                FileOutputStream xmlFile = openFileOutput("output_extend.xml", MODE_PRIVATE);
                transformer.transform(new DOMSource(document), new StreamResult(xmlFile));

            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.CreateXML:
            /**
             * create xml file content.
             * */
            {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = null;
                try {
                    builder = factory.newDocumentBuilder();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }

                Document myxml = builder.newDocument();
                Element Language = myxml.createElement("Language");
                Language.setAttribute("category", "IT");

                // set Lan1 content.
                Element Lan1 = myxml.createElement("Lan");
                Lan1.setAttribute("id", "1");
                Element name = myxml.createElement("name");
                name.setTextContent("Java");
                Element ide = myxml.createElement("ide");
                ide.setTextContent("Eclipse");

                Lan1.appendChild(name);
                Lan1.appendChild(ide);

                // set Lan2 content.
                Element Lan2 = myxml.createElement("Lan");
                Lan2.setAttribute("id", "2");
                Element name2 = myxml.createElement("name");
                name2.setTextContent("C++");
                Element ide2 = myxml.createElement("ide");
                ide2.setTextContent("Visual Studio");

                Lan2.appendChild(name2);
                Lan2.appendChild(ide2);

                // set Lan3 content.
                Element Lan3 = myxml.createElement("Lan");
                Lan3.setAttribute("id", "3");
                Element name3 = myxml.createElement("name");
                name3.setTextContent("Swift");
                Element ide3 = myxml.createElement("ide");
                ide3.setTextContent("Xcode");

                Lan3.appendChild(name3);
                Lan3.appendChild(ide3);

                // set into root node
                Language.appendChild(Lan1);
                Language.appendChild(Lan2);
                Language.appendChild(Lan3);

                // insert into xml file
                myxml.appendChild(Language);


                // transfer and create xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer;

                try {
                    transformer = transformerFactory.newTransformer();
                    transformer.setOutputProperty("encoding", "UTF-8");
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                    FileOutputStream xmlFile = openFileOutput("output.xml", MODE_PRIVATE);
//                    StringWriter sw = new StringWriter();
//                    StreamResult sr = new StreamResult(sw);
                    StreamResult sr = new StreamResult(xmlFile);

//                    transformer.transform(new DOMSource(myxml), new StreamResult(xmlFile));
//                    transformer.transform(new DOMSource(myxml), new StreamResult(xmlFile));
                    transformer.transform(new DOMSource(myxml), sr);

//                    tv.setText(sr.toString());


//                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(xmlFile);

//                    outputStreamWriter.write(sr.toString());
//
//                    outputStreamWriter.flush();
//                    xmlFile.flush();
//
//                    outputStreamWriter.close();
//                    xmlFile.close();
                } catch (TransformerConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
                break;
        }
    }
}
