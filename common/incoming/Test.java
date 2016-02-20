package common.incoming;

import common.Caller;

public class Test {
	public static void main ( String ...strings ) {
		
		double r = 100; 
		int d = 3; 
		
		double result = r / d; 
		
		Caller.note( "Result " + ( r/  d ));
		
		
	}
}
