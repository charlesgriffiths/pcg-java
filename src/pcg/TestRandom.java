package pcg;

import java.util.Random;


public class TestRandom
{
  public static void main( String[] args )
  {
  Random r = new Random(), pcgr = new PCGRandom();

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
  }
}

