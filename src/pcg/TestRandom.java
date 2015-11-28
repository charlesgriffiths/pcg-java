package pcg;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.Random;

import pcg.rng.ISeekableRNG;
import test.Ent;
import test.EstimatePi;
import test.EstimatePiPrecise;


public class TestRandom
{
  public static void main( String[] args )
  {
//    writeFile( new Random(), "jr.dat", 1L<<25 );
//    writeFile( new PCGRandom( new PCG32()), "p32r.dat", 1L<<25 );
//    writeFile( new PCGRandom( new PCG64()), "p64r.dat", 1L<<25 );
//    writeFile( new PCGRandom( new PCG128()), "p128r.dat", 1L<<25 );

//  Random r = new Random( 1 );
//  Random r = new PCGRandom( new PCG32());

//    while( true )
//      estimatePi( new SKRandom( new PCG32() ), new SKRandom( new PCG32() ));
//      runEnt( r );
//System.out.println( "PCG32" );
//    estimatePi( new SKRandom( new PCG32() ));
System.out.println( "java Random" );
    estimatePi( new Random( 1 ));
  }


  public static void estimatePi( Random r )
  {
  long print = 2;

    for (int i=1; i<10; i++)
    {
    EstimatePiPrecise ep = new EstimatePiPrecise( i );
    long j;

System.out.println();
System.out.println( "" + i + " digits." );
    
      print >>= 1;

      for (j=0; j<Math.pow( 256, i ); j++)
      {
        for (int k=0; k<i; k++)
          ep.add( r.nextInt());
        if (j >= print)
        {
System.out.println( ep.getEstimate() + " " + ep.getEstimate()/Math.PI + " " + Math.PI + " "  + j );
          print <<= 1;
        }
      }
System.out.println( ep.getEstimate() + " " + ep.getEstimate()/Math.PI + " " + Math.PI + " "  + j );
    }
  }


  public static void estimatePi( Random r1, Random r2 )
  {
  int digits1=1, digits2=5;
  EstimatePiPrecise ep1 = new EstimatePiPrecise(digits1), ep2 = new EstimatePiPrecise(digits2);

    for (int i=0; i<2000000000; i++)
    {
      for (int j=0; j<digits1; j++)
        ep1.add( r1.nextInt() );
      for (int j=0; j<digits2; j++)
        ep2.add( r2.nextInt() );
      if (0 == i%100000000)
        System.out.println( "  " + ep1.getEstimate()/Math.PI + "   " + ep2.getEstimate()/Math.PI + "   " + Math.PI );
    }

System.out.println( "* " + ep1.getEstimate()/Math.PI + "   " + ep2.getEstimate()/Math.PI + "   " + Math.PI );
  }


  public static void runEnt( Random rng )
  {
  Ent e1, e2, e3, e4;
  byte b[] = new byte[1];

    e1 = new Ent();
    e2 = new Ent();
    e3 = new Ent();
    e4 = new Ent();

    for (int i=0; i<100000000; i++)
    {
    int datum = rng.nextInt();

      b[0] = (byte) (datum >> 24);
      e1.addBytes( b );

      b[0] = (byte) (datum >> 16);
      e2.addBytes( b );

      b[0] = (byte) (datum >> 8);
      e3.addBytes( b );

      b[0] = (byte) (datum);
      e4.addBytes( b );
    }

    e1.finish();
    e2.finish();
    e3.finish();
    e4.finish();

    System.out.println( e1 );
    System.out.println( e2 );
    System.out.println( e3 );
    System.out.println( e4 );
  }


  public static void writeFile( ISeekableRNG rng, String filename, long ints )
  {
    writeFile( new SKRandom( rng ), filename, ints );
  }

  public static void writeFile( Random rng, String filename, long ints )
  {
System.out.println( "Writing " + ints + " ints to file: " + filename );
    try
    {
    RandomAccessFile raf = new RandomAccessFile( filename, "rw" );

      for (long i = 0; i < ints; i++)
      {
        raf.writeInt( rng.nextInt() );
if (0 == (i+1)%1000000) System.out.print( "." );
      }

      raf.close();
    }
    catch (FileNotFoundException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
System.out.println();
  }

  public void simpleTests()
  {
  Random r = new Random(), pcgr = new PCGRandom( new PCG64());

    new PCGRandom( 1234567890 );
    System.out.println( "booleans" );
    for (int i=0; i<10; i++)
      System.out.println( " " + r.nextBoolean() + " " + pcgr.nextBoolean());

    System.out.println( "ints" );
    for (int i=0; i<10; i++)
      System.out.println( " " + r.nextInt() + " " + pcgr.nextInt());

    System.out.println( "floats" );
    for (int i=0; i<10; i++)
      System.out.println( " " + r.nextFloat() + " " + pcgr.nextFloat());

    System.out.println( "doubles" );
    for (int i=0; i<10; i++)
      System.out.println( " " + r.nextDouble() + " " + pcgr.nextDouble());

    System.out.println( "longs" );
    for (int i=0; i<10; i++)
      System.out.println( " " + r.nextLong() + " " + pcgr.nextLong());

    System.out.println( "gaussians" );
    for (int i=0; i<10; i++)
      System.out.println( " " + r.nextGaussian() + " " + pcgr.nextGaussian());

/* from https://github.com/imneme/pcg-cpp/blob/master/include/pcg_random.hpp
    PCG_DEFINE_CONSTANT(pcg128_t, default, multiplier,
        PCG_128BIT_CONSTANT(2549297995355413924ULL,4865540595714422341ULL))
PCG_DEFINE_CONSTANT(pcg128_t, default, increment,
        PCG_128BIT_CONSTANT(6364136223846793005ULL,1442695040888963407ULL))  
*/
    BigInteger two = BigInteger.valueOf( 2 );
    BigInteger mul = new BigInteger( "2549297995355413924" ), mul2 = new BigInteger( "4865540595714422341" );
    BigInteger inc = new BigInteger( "6364136223846793005" ), inc2 = new BigInteger( "1442695040888963407" );

    mul = mul.multiply( two.pow( 64 ) ).add( mul2 );
    inc = inc.multiply( two.pow( 64 ) ).add( inc2 );
    
    System.out.println( "mul 128: " + mul );
    System.out.println( "inc 128: " + inc );
  }
}

