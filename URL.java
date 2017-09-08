

package URL;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;





public class URL {
    public static void main(String[] args) {

    
    
    URL url = new URL("http://www.utdallas.edu/~axn112530/cs6375/assignment5/Tweets.json");
  try (InputStream is = url.openStream();
       JsonReader rdr = Json.createReader(is)) {
 
      JsonObject obj = rdr.readObject();
     JsonArray results = obj.getJsonArray("text");
//     for (JsonObject result : results.getValuesAs(JsonObject.class)) {
//          System.out.print(result.getJsonObject("from").getString("name"));
//          System.out.print(": ");
//         System.out.println(result.getString("message", ""));
//         System.out.println("-----------");
     //}
 }
  catch(IOException ie)
  
    }
}
    
//  public static <T> Set<T> union(Set<T> setA, Set<T> setB) {
//    Set<T> tmp = new TreeSet<T>(setA);
//    tmp.addAll(setB);
//    return tmp;
//  }
//
//  public static <T> Set<T> intersection(Set<T> setA, Set<T> setB) {
//    Set<T> tmp = new TreeSet<T>();
//    for (T x : setA)
//      if (setB.contains(x))
//        tmp.add(x);
//    return tmp;
//  }
//
//  public static <T> Set<T> difference(Set<T> setA, Set<T> setB) {
//    Set<T> tmp = new TreeSet<T>(setA);
//    tmp.removeAll(setB);
//    return tmp;
//  }
//
//  public static <T> Set<T> symDifference(Set<T> setA, Set<T> setB) {
//    Set<T> tmpA;
//    Set<T> tmpB;
//
//    tmpA = union(setA, setB);
//    tmpB = intersection(setA, setB);
//    return difference(tmpA, tmpB);
//  }
//
//  public static <T> boolean isSubset(Set<T> setA, Set<T> setB) {
//    return setB.containsAll(setA);
//  }
//
//  public static <T> boolean isSuperset(Set<T> setA, Set<T> setB) {
//    return setA.containsAll(setB);
//  }
//
//  public static void main(String args[]) {
//    TreeSet<Character> set1 = new TreeSet<Character>();
//    TreeSet<Character> set2 = new TreeSet<Character>();
//
//    set1.add('A');
//    set1.add('B');
//    set1.add('C');
//    set1.add('D');
//
//    set2.add('C');
//    set2.add('D');
//    set2.add('E');
//    set2.add('F');
//
//    System.out.println("set1: " + set1);
//    System.out.println("set2: " + set2);
//
//    System.out.println("Union: " + union(set1, set2));
//    System.out.println("Intersection: " + intersection(set1, set2));
//    System.out.println("Difference (set1 - set2): " + difference(set1, set2));
//    
//
//    TreeSet<Character> set3 = new TreeSet<Character>(set1);
//
//    
//  }


   
    
    