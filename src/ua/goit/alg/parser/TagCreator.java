package ua.goit.alg.parser;

import java.util.HashMap;
import java.util.Map;

public class TagCreator {

  private Map<String, String> attributeHolder = new HashMap<String, String>();
  private StringBuilder tagName = new StringBuilder();
  private StringBuilder attributeName = new StringBuilder();
  private StringBuilder attributeValue = new StringBuilder();
  private StringBuilder tagText = new StringBuilder();

  public void addAttribute() {
    attributeHolder.put(attributeName.toString(), attributeValue.toString());
    attributeName.delete(0,attributeName.capacity());
    attributeValue.delete(0,attributeValue.capacity());
  }
  public Tag createTag() {
    Tag tag = new Tag();
    tag.setTagName(tagName.toString());
    tag.setAttributes(new HashMap<String, String>(attributeHolder));
    tag.setTagText(tagText.toString());
    clearTagCreator();
    return tag;
  }

  public void appendAttributeName(char c) {
    attributeName.append(c);
  }

  public void appendAttributeValue(char c) {
    attributeValue.append(c);
  }

  public void appendTagName(char c) {
    tagName.append(c);
  }

  public void appendTagText(char c) {
    tagText.append(c);
  }

  private void clearTagCreator() {
    tagName.delete(0, tagName.capacity());
    tagText.delete(0, tagText.capacity());
    attributeHolder.clear();
  }


}
