import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//import javax.swing.JOptionPane;

public class TestRunner {
   
   public static void assertEquals(String s1, String s2) {
      if (s1.equals(s2))
         return;
      
      showFailure(s1, s2);
   }

   public static void assertTrue(boolean exp, String msg) {
      if (!exp) {
         System.out.printf(" FAILED: %s\n", msg);
         throw new IllegalStateException();
      }
   }
   
   private static void showFailure(String s1, String s2) {
      String msg = String.format(" FAILED:\n  expected: %s\n    actual: %s", s1, s2);
      System.out.println(msg);
      
      // the reflection library will catch this and throw a InvocationTargetException
      throw new IllegalStateException();
   }
   
   public static void assertEquals(double d1, double d2, double tolerance) {
      double left = d1 - tolerance;
      double right = d1 + tolerance;
      if (left <= d2 && right >= d2)
         return;
      
      showFailure(d1+"", d2+"");
   }
   
   public static void assertEquals(int n1, int n2) {
      if (n1 == n2)
         return;
      
      showFailure(n1+"", n2+"");
   }
   
   public static void runTests(String className) {
      System.out.printf("Running tests in %s...\n", className);
      
      try {
         // get an instance of the class
         Class<?> testerClass = Class.forName(className);
         
         // Create an instance of our test class. Must have no arguments in constructor.
         Object tester = testerClass.getDeclaredConstructor().newInstance();
         
         // Get all methods of the Pump class
         Method[] methods = testerClass.getDeclaredMethods();
   
         int countFailed = 0;
         
         // Loop through methods to find those starting with "test"
         List<Boolean> passed = new ArrayList<>();
         List<String> methodNames = new ArrayList<>();
         for (Method method : methods) {
            if (method.getName().startsWith("test")) {
               methodNames.add(method.getName());
               // Make the method accessible if it's private
               method.setAccessible(true);
   
               // Invoke the test method
               System.out.println("Invoking: " + method.getName());
   
               try {
                  method.invoke(tester);
                  passed.add(true);
                  System.out.println("  PASSED");
               } catch (InvocationTargetException e) {
                  countFailed++;
                  passed.add(false);
                  System.out.printf("  %d Tests failed so far\n", countFailed);
               }
            }
         }
         System.out.printf("\nSummary: %d Tests. PASSED:%d   FAILED:%d\n", 
            methodNames.size(), methodNames.size() - countFailed, countFailed);
         for (int i = 0; i < methodNames.size(); i++) {
            System.out.printf("  %s: %s\n", passed.get(i) ? "passed" : "FAILED", methodNames.get(i));
         }
      } catch (ClassNotFoundException e) {
         System.out.println("Class " + className + " not found.");
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      } catch (Throwable ex) {
         // Serious issue!
         System.out.println("SERIOUS ISSUE!!");
         ex.printStackTrace();
      }
   }
}
