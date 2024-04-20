package me.erriic.labymodscripting.bridge;

public class RefelctionBridge {

  public Class getPrimitiveClass(String name) throws ClassNotFoundException {
    switch (name) {
      case "boolean":
        return boolean.class;
      case "byte":
        return byte.class;
      case "short":
        return short.class;
      case "char":
        return char.class;
      case "int":
        return int.class;
      case "long":
        return long.class;
      case "float":
        return float.class;
      case "double":
        return double.class;
      default:
        return Class.forName(name);
    }
  }

  public Class getClass(String name) throws ClassNotFoundException {
    switch (name) {
      case "boolean":
        return Boolean.class;
      case "byte":
        return Byte.class;
      case "short":
        return Short.class;
      case "char":
        return Character.class;
      case "int":
        return Integer.class;
      case "long":
        return Long.class;
      case "float":
        return Float.class;
      case "double":
        return Double.class;
      default:
        return Class.forName(name);
    }
  }

}
