package test;

import rngtools.BitStream;
import rngtools.IntegerSourceBitStream;


public class TestBitStream
{

  public TestBitStream()
  {
  }

  public static void intSource()
  {
    testIntegerSourceBitStream( IntegerSourceBitStream.create( 32 ));
  }

  public static void bigIntSource()
  {
    testIntegerSourceBitStream( IntegerSourceBitStream.create());
  }

  public static void testIntegerSourceBitStream( BitStream bs )
  {
    for (int i=1; i<32; i++)
    {
System.out.print( "" + i + ": " );
      for (long j=0; j<(1L<<i); j++)
      {
//System.out.println( "["+i+","+j+"]" );
      long got = bs.nextl( i );
      
        if (j == got)
        {
          if (j < 100) System.out.print( " " + got + "," );
        }
        else
        {
          System.out.println();
          System.out.println( "i: " + i );
          System.out.println( "Expected: " + j );
          System.out.println( "Got: " + got );
          return;
        }
      }
      System.out.println();
    }
  }
}

