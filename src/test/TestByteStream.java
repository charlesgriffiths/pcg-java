package test;

import rngtools.ByteStream;
import rngtools.IntegerSourceByteStream;


public class TestByteStream
{
  public TestByteStream() {}


  public static void testIntegerSourceByteStream()
  {
  ByteStream bs = IntegerSourceByteStream.create();  // unlimited BigInteger version

    for (int i=0; i<256; i++)
      System.out.print ( " " + bs.next() + "," ); // 0 ..

    System.out.println();

    for (int i=0; i<65536-256; i++)
    {
    long out = bs.next( 2 );

      if (i < 100) System.out.print ( " " + out + "," ); // 256 .. 
    }

    System.out.println();

    for (int i=0; i<256L*256L*256L-256L*256L; i++)
    {
    long out = bs.next( 3 );

      if (i<100) System.out.print ( " " + out + "," ); // 65536 ..
    }

    System.out.println();

    for (long i=0; i<256L*256L*256L*256L-256L*256L*256L; i++)
    {
    long out = bs.next( 4 );

      if (i<100) System.out.print ( " " + out + "," ); // 16777216 ..
    }

    System.out.println();

    for (long i=0; i<256L; i++)
    {
    long out = bs.next( 5 );

      if (i<100) System.out.print ( " " + out + "," ); // 4294967296 ..
    }

    System.out.println();
  }
}

