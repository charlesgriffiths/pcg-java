package rngtools;

import java.math.BigInteger;


abstract public class IntegerSourceByteStream extends ByteStream
{
  protected IntegerSourceByteStream() {}


  public static IntegerSourceByteStream create()
  {
    return new BigIntegerByteStream();
  }


  public static IntegerSourceByteStream create( int maxBytes )
  {
    return null;
  }
}


class BigIntegerByteStream extends IntegerSourceByteStream
{
protected BigInteger state;
protected byte data[];
protected int position;


  BigIntegerByteStream()
  {
    state = BigInteger.ZERO;
    data = state.toByteArray();
    position = 0;
  }

  protected BigIntegerByteStream( BigInteger state, byte data[], int position )
  {
    this.state = state;
    this.data = data;
    this.position = position;
  }


  @Override
  public int next()
  {
    if (position >= data.length)
    {
      state = state.add( BigInteger.ONE );
      data = state.toByteArray();
      position = 0;
      if (0 == data[0]) position = 1;
    }

    return data[position++] & 0xff;
  }


  @Override
  public IByteStream deepCopy()
  {
    return new BigIntegerByteStream( state, data, position );
  }
}

