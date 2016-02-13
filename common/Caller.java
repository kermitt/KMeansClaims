package common;

public class Caller {
    
    public static void log( String msg ){
        StackTraceElement[] ste = new Throwable().getStackTrace();

        String line = " ln: " + ste[1].getLineNumber();
        String clazz = " c: " + ste[1].getClassName();
        String method = " m: " + ste[1].getMethodName();

        String out = msg + " |\t" + line + clazz + method;

        System.out.println( out );
    }

    public static void note( String msg ) {
    	System.out.println("\t" + msg );
    }
    public static void log( boolean b, String msg ){
        StackTraceElement[] ste = new Throwable().getStackTrace();

        String line = " ln: " + ste[1].getLineNumber();
        String clazz = " c: " + ste[1].getClassName();
        String method = " m: " + ste[1].getMethodName();
        String m = "FAIL";
        if ( b ) {
            m = "PASS";
        }

        String out = m + " " + msg + " |\t" + line + clazz + method;

        System.out.println( out );
    }
    public static void log( boolean b  ){
        StackTraceElement[] ste = new Throwable().getStackTrace();

        String line = " ln: " + ste[1].getLineNumber();
        String clazz = " c: " + ste[1].getClassName();
        String method = " m: " + ste[1].getMethodName();
        String m = "FAIL";
        if ( b ) {
            m = "PASS";
        }

        String out = m + "|\t" + line + clazz + method;

        System.out.println( out );
    }


    
    public static boolean doNotNotPayAttention(String candidate) {
        if ( candidate.startsWith("sun") || candidate.startsWith("com.sun")  || candidate.startsWith("com.ibm") || candidate.startsWith("java.") || candidate.startsWith("org.junit") || candidate.startsWith("org.eclipse")) {
            return false;
        }
        return true;
    }
}
