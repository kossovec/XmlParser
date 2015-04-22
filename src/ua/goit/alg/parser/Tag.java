package ua.goit.alg.parser;

import java.util.Map;

public class Tag {
  private String tagName;
  private Map<String, String> attributes;
  private String tagText;

  public void setTagName(String tagName) {
    this.tagName = tagName;
  }

  public String getTagName() {
    return tagName;
  }

  public void setTagText(String tagText) {
    this.tagText = tagText;
  }

  public String getTagText() {
    return tagText;
  }

  public void setAttributes(Map<String, String> attributes) {
    this.attributes = attributes;
  }

  public Map getAttributes() {
    return attributes;
  }

  public boolean isAttributePresent() {
    return (attributes != null);
  }

  public boolean isTagNamePresent() {
    return (tagName != null);
  }

  public boolean isTagTextPresent() {
    return (tagText != null);
  }
}

