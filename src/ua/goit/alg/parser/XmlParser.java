package ua.goit.alg.parser;

import java.io.File;
import java.io.IOException;

public interface XmlParser {

  public void parse(String xml) throws IOException;

  public void parse(File file) throws IOException;

  public void onOpenTag(Handler handler);

  public void onCloseTag(Handler handler);

  public void onTextValue(Handler handler);

  public void onStart(Handler handler);

  public void onEnd(Handler handler);

  public void onError(Handler handler);

  public void handle(HandlerNames handlerNames, Tag tag);



}