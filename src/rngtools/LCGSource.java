package rngtools;

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
  public boolean seek( long position )
  {
    return false;
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
  protected ISeekableRNG deepCopy( ISeekableRNG target )
  {
    return target;
  }


  @Override
  public int next32()
  {
    return 0;
  }
}


class LCGSourceLong extends LCGSource
{
long state, stateMask;
int shift;


  LCGSourceLong( long seed, long mask )
  {
    state = seed & mask;
    stateMask = mask;
    shift = 0;
  }


  LCGSourceLong( long seed, long mask, int shiftOutput )
  {
    state = seed & mask;
    stateMask = mask;
    shift = shiftOutput;
  }
}


final class LCGSource8 extends LCGSourceLong
{
  LCGSource8()
  {
    super( 1, 0xff );
  }


  protected void next()
  {
    state = (state * mul8 + inc8) & stateMask;
  }


  @Override
  public byte next8()
  {
    next();

    return (byte) (state >> shift);
  }


  @Override
  public short next16()
  {
    return (short) (((int)next8() << 8) | ((int)next8() & 0xffff));
  }


  @Override
  public int next32()
  {
    return (short) (((int)next16() << 16) | ((int)next16() & 0xffffffff));
  }
}


final class LCGSource16 extends LCGSourceLong
{
  LCGSource16()
  {
    super( 1, 0xffff );
  }


  protected void next()
  {
    state = (state * mul16 + inc16) & stateMask;
  }


  @Override
  public byte next8()
  {
    return (byte) (next32() >> 24);
  }


  @Override
  public short next16()
  {
    next();

    return (short) (state >> shift);
  }


  @Override
  public int next32()
  {
    return (short) (((int)next16() << 16) | ((int)next16() & 0xffffffff));
  }
}


final class LCGSource32 extends LCGSourceLong
{
  LCGSource32()
  {
    super( 1, 0xffffffff );
  }


  protected void next()
  {
    state = (state * mul32 + inc32) & stateMask;
  }


  @Override
  public int next32()
  {
    next();

    return (int) (state >> shift);
  }
}


final class LCGSource64 extends LCGSourceLong
{
  LCGSource64()
  {
    super( 1, 0xffffffffffffffffL );
  }


  protected void next()
  {
    state = (state * mul64 + inc64) & stateMask;
  }


  @Override
  public int next32()
  {
    return (int) (next64() >> 32);
  }


  @Override
  public long next64()
  {
    next();

    return (long) (state >> shift);
  }
}

