package ua.goit.alg.parser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class XmlParserImpl implements XmlParser {
  private Map<HandlerNames, Handler> handlers = new HashMap<HandlerNames, Handler>();
  private StateMachine stateMachine = new StateMachine(this);

  public void parse(String xml) throws IOException {
    parser(new ByteArrayInputStream(xml.getBytes()));
  }

  public void parse(File filePath) throws IOException {
    if (filePath.isFile() && filePath.exists()) {
      FileInputStream readFromFile = new FileInputStream(filePath);
      parser(readFromFile);
    }
  }

  private void parser(InputStream readFromStream) throws IOException {
    int i = 0;
    while ((i = readFromStream.read()) != -1) {
      stateMachine.next((char) i);
    }
    Tag emptyTag = new Tag();
    handle(HandlerNames.ON_END, emptyTag);
  }

  public void onOpenTag(Handler handler) {
    handlers.put(HandlerNames.ON_OPEN_TAG, handler);
  }

  public void onCloseTag(Handler handler) {
    handlers.put(HandlerNames.ON_CLOSE_TAG, handler);
  }

  public void onTextValue(Handler handler) {
    handlers.put(HandlerNames.ON_TEXT_VALUE, handler);
  }

  public void onStart(Handler handler) {
    handlers.put(HandlerNames.ON_START, handler);
  }

  public void onEnd(Handler handler) {
    handlers.put(HandlerNames.ON_END, handler);
  }

  public void onError(Handler handler) {
    handlers.put(HandlerNames.ON_ERROR, handler);
  }

  public void handle(HandlerNames handlerNames, Tag tag) {
    Handler handel = handlers.get(handlerNames);
    if (handel != null) {
      handel.handle(tag);
    }
  }
}