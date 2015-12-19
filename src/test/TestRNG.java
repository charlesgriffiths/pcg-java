package test;

import rngtools.IntegerSourceRNG;
import rngtools.RNG;


public class TestRNG
{
  public TestRNG() {}


  public static void testIntegerSourceRNG()
  {
  long limit = 1;

    for (int i=1; i<5; i++)
    {
    RNG rng = IntegerSourceRNG.create( i );

      limit *= 256L;

      System.out.print( "" + i + ": " );

      for (long j=0; j<limit; j++)
      {
      long out = rng.nextl( 8 * i );

        if (j < 300) System.out.print( " " + out + "," );
      }

      System.out.println();

      System.out.print( "   " );

      for (long j=0; j<256; j++)
      {
      long out = rng.nextl( 8 * i );

        System.out.print( " " + out + "," );
      }

      System.out.println();
    }


    for (int i=5; i<9; i++)
    {
    RNG rng = IntegerSourceRNG.create( i );

      System.out.print( "" + i + ": " );

      for (long j=0; j<300; j++)
      {
        System.out.print( " " + rng.nextl( 8 * i ) + "," );
      }

      System.out.println();
    }


    for (int i=9; i<18; i++)
    {
    RNG rng = IntegerSourceRNG.create( i );

      System.out.print( "" + i + ": " );

      for (long j=0; j<300; j++)
      {
      byte data[] = rng.next();

        if (data.length > 1)
          System.out.print( " " + (data[data.length-2] & 0xff) + ":" + (data[data.length-1] & 0xff) + "," );
        else
          System.out.print( " " + (data[data.length-1] & 0xff) + "," );
      }

      System.out.println();
    }

  }
}

