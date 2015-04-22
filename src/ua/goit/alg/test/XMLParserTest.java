package ua.goit.alg.test;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import ua.goit.alg.parser.Handler;
import ua.goit.alg.parser.Tag;
import ua.goit.alg.parser.XmlParser;
import ua.goit.alg.parser.XmlParserImpl;

public class XMLParserTest {

  @Test
  public void parserTest() throws IOException {
    XmlParser parser = new XmlParserImpl();
    parser.onOpenTag(new Handler() {

      public void handle(Tag tag) {
        System.out.println(tag.getTagName());
        Set<Map.Entry<String, String>> attributeEntry = tag.getAttributes().entrySet();
        Map.Entry<String, String> entry = attributeEntry.iterator().next();
        String attributeNameExpected = entry.getKey();
        String attributeValueExpected = entry.getValue();
        System.out.println(attributeNameExpected);
        System.out.println(attributeValueExpected);
      }
    });

    parser.onTextValue(new Handler() {

      public void handle(Tag tag) {
        System.out.println(tag.getTagText());
      }
    });

    parser.onCloseTag(new Handler() {

      public void handle(Tag tag) {
        System.out.println("</" + tag.getTagName() + ">");
      }
    });

//	parser.parse("<?xml version=\"1.0\"><form></form>");
    parser.parse("<?><note w=\"fff\">text</note> ");
  }


}


