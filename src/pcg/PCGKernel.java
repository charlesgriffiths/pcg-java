package pcg;

import java.math.BigInteger;

import rngtools.RNG;


public abstract class PCGKernel extends RNG implements IPCGKernel
{
  @Override
  public void setStreamUnique()
  {
    setStream( hashCode() );
  }


  @Override
  public boolean isSeekable()
  {
    return true;
  }


  // based on advance() in https://github.com/imneme/pcg-cpp/blob/master/include/pcg_random.hpp
  // which is in turn based on Brown, "Random Number Generation
  // with Arbitrary Stride,", Transactions of the American Nuclear
  // Society (Nov. 1994).

  protected long stateoffset( long delta, long mult, long inc )
  {
  long accmult = 1, accinc = 0;

    while( 0 != delta )
    {
      if (0 != (delta & 1))
      {
        accmult *= mult;
        accinc = accinc * mult + inc;
      }

      inc *= mult+1;
      mult *= mult;
      delta >>= 1;
    }

    return accmult + accinc;
  }


  protected BigInteger stateoffset( BigInteger delta, BigInteger mult, BigInteger inc, BigInteger max )
  {
  BigInteger accmult = BigInteger.ONE, accinc = BigInteger.ZERO;

    while( !delta.equals( BigInteger.ZERO ))
    {
      if (delta.testBit( 0 ))
      {
        accmult = accmult.multiply( mult ).and( max );
        accinc = accinc.multiply( mult ).add( inc ).and( max );
      }

      inc = inc.multiply( mult.add( BigInteger.ONE )).and( max );
      mult = mult.multiply( mult ).and( max );
      delta = delta.shiftRight( 1 );
    }

    return accmult.add( accinc );
  }
}

