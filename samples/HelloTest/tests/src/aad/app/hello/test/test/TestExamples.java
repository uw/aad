package aad.app.hello.test.test;

import android.test.AndroidTestCase;

import junit.framework.Assert;


public class TestExamples extends AndroidTestCase {
    
    public void testSomething() throws Throwable {
        Assert.assertTrue(1 + 1 == 2); // I know we are comparing identical expressions...
     }

     public void testSomethingElse() throws Throwable {
        Assert.assertTrue(1 + 1 == 3);
     }   
      
     public void testFail() throws Throwable {
         throw new Exception(); // This should give us an error in our output
     }
}
