package ua.goit.alg.parser;

public class StateMachine {
  private static State state = State.INIT;
  private XmlParserImpl xmlParser;
  private TagCreator creator = new TagCreator();

  public StateMachine(XmlParserImpl xmlParser) {
    this.xmlParser = xmlParser;
  }

  public void next(char c) {
    state = state.next(c, creator, xmlParser);
  }

  public enum State {
    INIT {
      @Override
      public State next(char c, TagCreator creator, XmlParserImpl xmlParser) {
        State result = INVALID_END;
        if (c == '<') {
          result = INIT;
        }
        if (c == '?') {
          result = VALID;
        }
        System.out.println("INIT");
        return result;
      }
    },
    VALID {
      @Override
      public State next(char c, TagCreator creator, XmlParserImpl xmlParser) {
        State result = INVALID_END;
        if (c != '>') {
          result = VALID;
        } else {
          result = START;
        }

        System.out.println("VALID " + c);
        return result;
      }
    },
    START {
      @Override
      public State next(char c, TagCreator creator, XmlParserImpl xmlParser) {
        State result = INVALID_END;
        if (c == '<') {
          result = OPEN_TAG;
        }

        if (Character.isWhitespace(c)) {
          result = END;
        }

        System.out.println("START " + c);
        return result;
      }
    },
    OPEN_TAG {
      @Override
      public State next(char c, TagCreator creator, XmlParserImpl xmlParser) {
        State result = INVALID_END;
        if (Character.isLetter(c)) {
          creator.appendTagName(c);
          result = TAG_NAME;
        }

        if (c == '/') {
          result = CLOSE_TAG;
        }

        System.out.println("OPEN_TAG " + c);
        return result;
      }
    },
    TAG_NAME {
      @Override
      public State next(char c, TagCreator creator, XmlParserImpl xmlParser) {
        State result = INVALID_END;
        if (Character.isLetterOrDigit(c)) {
          creator.appendTagName(c);
          result = TAG_NAME;
        }

        if (c == ' ') {
          result = ATTR_NAME_START;
        }

        if (c == '>') {
          Tag tag = creator.createTag();
          xmlParser.handle(HandlerNames.ON_OPEN_TAG, tag);
          result = NODE;
        }

        if (c == '/') {
          result = CLOSE_TAG;
        }

        System.out.println("TAG_NAME " + c);
        return result;
      }
    },
    ATTR_NAME_START {
      @Override
      public State next(char c, TagCreator creator, XmlParserImpl xmlParser) {
        State result = INVALID_END;
        if (Character.isLetter(c)) {
          creator.appendAttributeName(c);
          result = ATTR_NAME;
        }

        System.out.println("ATTR_NAME_START " + c);
        return result;
      }
    },
    ATTR_NAME {
      @Override
      public State next(char c, TagCreator creator, XmlParserImpl xmlParser) {
        State result = INVALID_END;
        if (Character.isLetter(c)) {
          creator.appendAttributeName(c);
          result = ATTR_NAME;
        }

        if (Character.isDigit(c)) {
          creator.appendAttributeName(c);
          result = ATTR_NAME;
        }

        if (c == ' ') {
          result = ATTR_NAME_START;
        }

        if (c == '=') {
          result = ATTR_VAL;
        }

        if (c == '>') {
          creator.addAtribute();
          Tag tag = creator.createTag();
          xmlParser.handle(HandlerNames.ON_OPEN_TAG, tag);
          result = NODE;
        }

        if (c == '/') {
          result = CLOSE_TAG;
        }

        System.out.println("ATTR_NAME " + c);
        return result;
      }
    },
    ATTR_VAL {
      @Override
      public State next(char c, TagCreator creator, XmlParserImpl xmlParser) {
        State result = INVALID_END;
        if (Character.isLetterOrDigit(c)) {
          creator.appendAttributeValue(c);
          result = ATTR_VAL;
        }

        if (c == '"') {
          result = ATTR_VAL_QUOTES;
        }

        if (c == '>') {
          Tag tag = creator.createTag();
          xmlParser.handle(HandlerNames.ON_OPEN_TAG, tag);
          result = NODE;
        }

        return result;
      }
    },
    ATTR_VAL_QUOTES {
      public State next(char c, TagCreator creator, XmlParserImpl xmlParser) {
        State result = INVALID_END;
        if (Character.isLetterOrDigit(c) || Character.isWhitespace(c)) {
          creator.appendAttributeValue(c);
          result = ATTR_VAL_QUOTES;
        }
        if (c == '"') {
          result = ATTR_NAME;
        }

        return result;
      }
    },
    NODE {
      @Override
      public State next(char c, TagCreator creator, XmlParserImpl xmlParser) {
        State result = INVALID_END;
        if (Character.isLetterOrDigit(c) || Character.isWhitespace(c)) {
          creator.appendTagText(c);
          result = ON_TEXT;
        }
        if (c == '<') {
          result = TAG_NAME;
          Tag tag = creator.createTag();
          xmlParser.handle(HandlerNames.ON_TEXT_VALUE, tag);
        }
        System.out.println("NODE " + c);
        return result;
      }
    },
    ON_TEXT {
      @Override
      public State next(char c, TagCreator creator, XmlParserImpl xmlParser) {
        State result = INVALID_END;
        if (Character.isLetterOrDigit(c) || Character.isWhitespace(c)) {

          creator.appendTagText(c);
          result = ON_TEXT;
        }

        if (c == '<') {
          Tag tag = creator.createTag();
          xmlParser.handle(HandlerNames.ON_TEXT_VALUE, tag);
          result = TAG_NAME;
        }

        System.out.println("ON_TEXT " + c);
        return result;
      }
    },
    CLOSE_TAG {
      @Override
      public State next(char c, TagCreator creator, XmlParserImpl xmlParser) {
        State result = INVALID_END;
        if (c != '>') {
          result = CLOSE_TAG;
        }
        if (c == '>') {
          result = START;
          //some error
          Tag tag = new Tag();
          xmlParser.handle(HandlerNames.ON_CLOSE_TAG, tag);
        }

        System.out.println("CLOSE_TAG");
        return result;
      }
    },
    END {
      @Override
      public State next(char c, TagCreator creator, XmlParserImpl xmlParser) {
//        xmlParser.handle(State.END, creator);
        System.out.println("END " + c);
        return END;
      }
    },
    INVALID_END {
      @Override
      public State next(char c, TagCreator creator, XmlParserImpl xmlParser) {
        System.out.println("INVALID_END " + c);
        throw new RuntimeException("Invalid END");
      }
    };

    public abstract State next(char c, TagCreator creator, XmlParserImpl xmlParser);
  }
}






