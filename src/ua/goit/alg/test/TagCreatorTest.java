package ua.goit.alg.test;

import org.junit.Assert;
import org.junit.Test;
import ua.goit.alg.parser.Tag;
import ua.goit.alg.parser.TagCreator;

import java.util.Map;
import java.util.Set;

public class TagCreatorTest {
  private final String attributeName = "attributeName";
  private final String attributeValue = "attributeValue";
  private final String tagName = "tagName";
  private final String tagText = "tagText";
  private TagCreator tagCreator = new TagCreator();

  @Test
  public void testAttributeAppend() {
    Tag tag;
    char[] attributeNameToCharArray = attributeName.toCharArray();
    char[] attributeValueToCharArray = attributeValue.toCharArray();
    for (char c : attributeNameToCharArray) {
      tagCreator.appendAttributeName(c);
    }

    for (char c : attributeValueToCharArray) {
      tagCreator.appendAttributeValue(c);
    }

    tagCreator.addAttribute();
    tag = tagCreator.createTag();
    Set<Map.Entry<String, String>> attributeEntry = tag.getAttributes().entrySet();
    Map.Entry<String, String> entry = attributeEntry.iterator().next();
    String attributeNameExpected = entry.getKey();
    String attributeValueExpected = entry.getValue();
    Assert.assertEquals(attributeName, attributeNameExpected);
    Assert.assertEquals(attributeValue, attributeValueExpected);
  }

  @Test
  public void testTagNameAppend() {
    char[] tagNameToChatArray = tagName.toCharArray();
    for (char c : tagNameToChatArray) {
      tagCreator.appendTagName(c);
    }

    Tag tag = tagCreator.createTag();
    String tagNameExpected = tag.getTagName();
    Assert.assertEquals(tagName, tagNameExpected);
  }

  @Test
  public void testTagTextAppend() {
    char[] tagTextToChatArray = tagText.toCharArray();
    for (char c : tagTextToChatArray) {
      tagCreator.appendTagText(c);
    }

    Tag tag = tagCreator.createTag();
    String tagTextExpected = tag.getTagText();
    Assert.assertEquals(tagText, tagTextExpected);
  }
}
