package test;

import rngtools.BitStream;
import rngtools.IntegerSourceBitStream;


public class TestBitStream
{

  public TestBitStream()
  {
  }


  public static void testIntegerSourceBitStream()
  {
  BitStream bs = IntegerSourceBitStream.create( 32 );
  
    for (int i=1; i<32; i++)
    {
      for (long j=0; j<(1L<<i); j++)
      {
      long got = bs.nextl( i );
      
        if (j == got)
        {
          System.out.print( " " + got + "," );
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

