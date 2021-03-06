package rngtools;

import java.math.BigInteger;
import java.util.Arrays;


abstract public class IntegerSourceRNG extends RNG
{

  public static IntegerSourceRNG create( int stateSize )
  {
    switch( stateSize )
    {
      case 1:
        return new ByteRNGEntire( (byte) 0 );

      case 2:
        return new ShortRNGEntire( (short) 0 );

      case 4:
        return new IntRNGEntire( 0 );

      case 8:
        return new LongRNGEntire( 0L );
    }

    return new BigIntegerRNGEntire( stateSize );
  }
}


class ByteRNGEntire extends IntegerSourceRNG
{
protected byte state;


  ByteRNGEntire( byte state ) { this.state = state; }

  @Override
  public void setState( byte[] b )
  {
    state = (byte) get( b, 0, blockSize() );
  }

  @Override
  public int blockSize()
  {
    return 1;
  }
  
  @Override
  protected long nextBlock()
  {
    return state++ & 0xff;
  }


  @Override
  public IRNG deepCopy()
  {
    return new ByteRNGEntire( state );
  }
}


class ShortRNGEntire extends IntegerSourceRNG
{
protected short state;


  ShortRNGEntire( short state ) { this.state = state; }

  @Override
  public void setState( byte[] b )
  {
    state = (short) get( b, 0, blockSize() );
  }

  @Override
  public int blockSize()
  {
    return 2;
  }
  
  @Override
  protected long nextBlock()
  {
    return state++ & 0xffff;
  }


  @Override
  public IRNG deepCopy()
  {
    return new ShortRNGEntire( state );
  }
}


class IntRNGEntire extends IntegerSourceRNG
{
protected int state;


  IntRNGEntire( int state ) { this.state = state; }

  @Override
  public void setState( byte[] b )
  {
    state = (int) get( b, 0, blockSize() );
  }

  @Override
  public int blockSize()
  {
    return 4;
  }
  
  @Override
  protected long nextBlock()
  {
    return state++ & 0xffffffff;
  }


  @Override
  public IRNG deepCopy()
  {
    return new IntRNGEntire( state );
  }
}


class LongRNGEntire extends IntegerSourceRNG
{
protected long state;


  LongRNGEntire( long state ) { this.state = state; }

  @Override
  public void setState( byte[] b )
  {
    state = get( b, 0, blockSize() );
  }


  @Override
  public int blockSize()
  {
    return 8;
  }


  @Override
  protected long nextBlock()
  {
    return state++;
  }


  @Override
  public IRNG deepCopy()
  {
    return new LongRNGEntire( state );
  }
}


class BigIntegerRNGEntire extends IntegerSourceRNG
{
static final BigInteger TWO = BigInteger.valueOf( 2 );

protected int stateSize;
protected BigInteger state, max;


  BigIntegerRNGEntire( int stateSize )
  {
    this.stateSize = stateSize;
    state = BigInteger.ZERO;
    max = TWO.pow( stateSize * 8 ).subtract( BigInteger.ONE );
  }


  BigIntegerRNGEntire( int stateSize, BigInteger state, BigInteger max )
  {
    this.stateSize = stateSize;
    this.state = state;
    this.max = max;
  }


  @Override
  public void setState( byte[] b )
  {
    state = new BigInteger( b ).and( max ).abs();
  }


  @Override
  public int blockSize()
  {
    return stateSize;
  }


  @Override
  protected long nextBlock()
  {
  long ret = state.longValue();

    state = state.add( BigInteger.ONE ).and( max );

    return ret;
  }


  @Override
  public byte[] next()
  {
  byte ret[] = state.toByteArray();

    state = state.add( BigInteger.ONE ).and( max );

    if (0 == ret[0] && ret.length > 1)
      ret = Arrays.copyOfRange( ret, 1, ret.length );

    return ret;
  }


  @Override
  public IRNG deepCopy()
  {
    return new BigIntegerRNGEntire( stateSize, state, max );
  }
}

