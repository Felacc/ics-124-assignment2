package ics124.utils;

/** 
 * A bunch of methods for doing unit-test type things.
 * 
 * Keeps the TestingObjects class tidy.
 * 
 * Version 1.1 uses string comparison in assertEquals(String, String).
 *
 * @author Gordon Broom
 * @version 1.1
 */
public class UnitTest {
    /** Should the test output include all the tests (including passed ones)? */
    public boolean Verbose;
    /** Should the test output use fancy Unicode characters? */
    public boolean Fancy;
    
    private static int numPassed;
    private static int numFailed;
    
    public UnitTest() {
        this(false, false);
    }
    public UnitTest(boolean verbose) {
        this(verbose, false);
    }
    public UnitTest(boolean verbose, boolean fancy) {
        Verbose = verbose;
        Fancy = fancy;
        initTests();
    }
    
    /**
     * (Re)initialize the test suite.  Reset the number of passed and 
     * failed assertions.
     */
    public final void initTests() { 
        numPassed = numFailed = 0;
    }
    
    /**
     * Return a summary of the unit tests (so far).
     * 
     * @return a summary string
     */
    public String summarizeTests() {
        String result = String.format("Ran %d assertions, %d passed %d failed", 
                numPassed+numFailed, numPassed, numFailed);
        if (numFailed > 0)
            result = red(result);
        return result;
    }
    
    /* Fancy colourful bells and whistles for test results */
    private String red(String s) { return ConsoleColor.RED + s + ConsoleColor.RESET; }
    private String green(String s) { return ConsoleColor.GREEN + s + ConsoleColor.RESET; }
    
    private String checkMark() {
        return green(Fancy ? "\u2714 " : ". ");
    }

    private String failMark() {
        return red(Fancy ? "\u2718 " : "X ");
    }
    
    private String getTestCaseName() {
        // Magic incantation from StackOverflow. The magic number
        // is how many levels up the stack from *here* do we need 
        // to look (somewhat brittle: you will need to change it if
        // you refactor this class).
        final int NUMFRAMES = 4;
        return Thread.currentThread().getStackTrace()[NUMFRAMES].getMethodName();
    }
    
    private void passedTest() {
        String testCase = getTestCaseName();
        if (Verbose) System.out.println(checkMark() + testCase + " passed");
        numPassed += 1;
    }
    private void failedTest(String reason) {
        String testCase = getTestCaseName();
        String failed = red(" FAILED: ");
        System.out.println(failMark() + testCase + failed + reason);
        numFailed += 1;
    }
    
    /**
     * Unconditionally pass a test.
     */
    public void pass() {
        passedTest();
    }
    /**
     * Unconditionally fail a test.
     * 
     * @param reason the reason the test failed.
     */
    public void fail(String reason) {
        failedTest(reason);
    }
    
    /**
     * Assert a result is true
     * @param result the result to examine
     * @param reason message to print if the result is false
     */
    public void assertIsTrue(boolean result, String reason) {
        if (result) {
            passedTest();
        } else {
            failedTest(reason);
        }
    }
    
    /**
     * Assert a result is false
     * @param result the result to examine
     * @param reason message to print if the result is true
     */
    public void assertIsFalse(boolean result, String reason) {
        if (!result) {
            passedTest();
        } else {
            failedTest(reason);
        }
    }
    
    /**
     * Assert an object reference is null
     * 
     * @param actual reference to examine
     */
    public void assertIsNull(Object actual) {
        if (actual == null) {
            passedTest();
        } else {
            failedTest(String.format("expected NULL, got <<%s>>", actual));
        }
    }
    
    /** 
     * Assert two strings are equal
     * 
     * @param actual the actual value generated by the unit under test
     * @param expected the value we expect to see
     */
    public void assertEquals(String actual, String expected) {
        if (actual.compareTo(expected) == 0) {
            passedTest();
        } else {
            failedTest(String.format("expected \"%s\" got \"%s\"", expected, actual));
        }
    }
    /** 
     * Assert two objects are equal
     * 
     * @param actual the actual value generated by the unit under test
     * @param expected the value we expect to see
     */
    public void assertEquals(Object actual, Object expected) {
        if (actual.equals(expected)) {
            passedTest();
        } else {
            failedTest(String.format("expected <<%s>> got <<%s>>", expected, actual));
        }
    }
    /** 
     * Assert two numbers are equal
     * 
     * @param actual the actual value generated by the unit under test
     * @param expected the value we expect to see
     */
    public void assertEquals(Number actual, Number expected) {
        if (actual.equals(expected)) {
            passedTest();
        } else {
            failedTest(String.format("expected " + expected + " got " + actual));
        }
    }    
}
