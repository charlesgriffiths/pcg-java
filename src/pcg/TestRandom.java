package pcg;

import java.math.BigInteger;
import java.util.Random;


public class TestRandom
{
  public static void main( String[] args )
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

