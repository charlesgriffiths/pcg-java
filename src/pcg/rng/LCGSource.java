package pcg.rng;

import java.math.BigInteger;


abstract public class LCGSource extends SeekableRNG
{
// constants from https://github.com/imneme/pcg-cpp/blob/master/include/pcg_random.hpp
protected static final int mul8 = 141, inc8 = 77;
protected static final int mul16 = 12829, inc16 = 47989;
protected static final int mul32 = 747796405, inc32 = (int) (2891336453L & 0xffffffff);
protected static final long mul64 = 6364136223846793005L, inc64 = 1442695040888963407L;
protected static final BigInteger mul128 = new BigInteger( "47026247687942121848144207491837523525" ), inc128 = new BigInteger( "117397592171526113268558934119004209487" );


  protected LCGSource()
  {
  }


  public static LCGSource create()
  {
    return create( 64, 64 );
  }


  public static LCGSource create( int outputBits )
  {
    return create( outputBits, outputBits );
  }


  public static LCGSource create( int outputBits, int stateBits )
  {
    if (8 == outputBits)
      return new LCGSource8();
    if (16 == outputBits)
      return new LCGSource16();
    if (32 == outputBits)
      return new LCGSource32();
    if (64 == outputBits)
      return new LCGSource64();

    return null;
  }


  @Override
  public void setState( byte[] b )
  {
  }


  @Override
  public void seek( long position )
  {
  }


  @Override
  public void advance( long amount )
  {
  }


  @Override
  public ISeekableRNG deepCopy()
  {
    return null;
  }


  @Override
  protected void deepCopy( ISeekableRNG target )
  {
  }


  @Override
  public int next32()
  {
    return 0;
  }
}


class LCGSource8 extends LCGSource
{
long state, stateMask;


  LCGSource8()
  {
    state = 1;
    stateMask = 0xff;
  }


  protected void next()
  {
    state = (state * mul8 + inc8) & stateMask;
  }


  @Override
  public byte next8()
  {
    return 0;
  }

  
}


class LCGSource16 extends LCGSource
{
long state, stateMask;

  protected void next()
  {
    state = (state * mul16 + inc16) & stateMask;
  }
  
}


class LCGSource32 extends LCGSource
{
long state, stateMask;

  protected void next()
  {
    state = (state * mul32 + inc32) & stateMask;
  }
  
}


class LCGSource64 extends LCGSource
{
long state, stateMask;

  protected void next()
  {
    state = (state * mul64 + inc64) & stateMask;
  }

}

