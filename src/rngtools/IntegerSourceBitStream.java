package rngtools;

import java.math.BigInteger;


abstract public class IntegerSourceBitStream extends BitStream
{
  protected IntegerSourceBitStream() {}


  public static BitStream create()
  {
    return null;
  }


  public static BitStream create( int maxBits )
  {
    if (32==maxBits)
    {
      return BitStream.create( new IntBitStreamBitwise() );
    }
    return null;
  }


  @Override
  public IBitStream deepCopy()
  {
    // TODO Auto-generated method stub
    return null;
  }
}


class IntBitStreamEntire extends IntBitStream
{
  @Override
  void advanceState()
  {
    state++;
    position = 31;
  }

  @Override
  public IBitStream deepCopy()
  {
    // TODO Auto-generated method stub
    return null;
  }
  
}


class IntBitStreamBitwise extends IntBitStream
{
int maxPosition = 1;

  @Override
  void advanceState()
  {
    if (state == (1L<<maxPosition)-1)
    {
      state = 0;
      maxPosition++;

      if (maxPosition > 31)
      {
        maxPosition = 1;
      }
    }
    else
    {
      state++;
    }

    position = maxPosition-1;
  }


  @Override
  public IBitStream deepCopy()
  {
    // TODO Auto-generated method stub
    return null;
  }
}


abstract class IntBitStream extends BitStream
{
int state;
int position;

  IntBitStream()
  {
    advanceState();
    state = 0;
  }


  abstract void advanceState();


  @Override
  public boolean next()
  {
    if (position < 0)
      advanceState();

    return 0 != (state & (1<<position--));
  }
}

abstract class BigIntBitStream extends BitStream
{
BigInteger state;
int position;

  BigIntBitStream()
  {
    advanceState();
    state = BigInteger.ZERO;
  }


  abstract void advanceState();


  @Override
  public boolean next()
  {
    if (position < 0)
      advanceState();

    return state.testBit( position-- );
  }
}


class BigIntBitStreamBitwise extends BigIntBitStream
{
int maxPosition = 1;

  @Override
  void advanceState()
  {
    state.add( BigInteger.ONE );
    if (state.testBit( maxPosition ))
    {
      state = BigInteger.ZERO;
      maxPosition++;
    }

    position = maxPosition-1;
  }

  @Override
  public IBitStream deepCopy()
  {
    // TODO Auto-generated method stub
    return null;
  }
}

/*
class IntegerRNGSource extends SeekableRNG
{
int state;

  @Override
  public void setState( byte[] b )
  {
    state = 0;
    for (int i=0; i<b.length && i<4; i++)
      state = state<<8 | (b[i] & 0xff);
  }


  @Override
  public boolean seek( long position )
  {
    state = (int) position;
    return true;
  }


  @Override
  public void advance( long amount )
  {
    seek( state + amount );
  }

  
  @Override
  public int next32()
  {
    return state++;
  }


  @Override
  public ISeekableRNG deepCopy()
  {
    return deepCopy( new IntegerRNGSource() );
  }


  @Override
  protected ISeekableRNG deepCopy( ISeekableRNG target )
  {
    if (target instanceof IntegerRNGSource)
    {
    IntegerRNGSource ret = (IntegerRNGSource) target;

      ret.state = state;
    }

    return target;
  }
  
}
*/

/*
class BigIntegerRNGSource extends SeekableRNG
{
final BigInteger TWO = BigInteger.valueOf( 2 );
BigInteger state;
int position = 0;  // an infinitely long period would require a BigInteger position and infinite memory


  BigIntegerRNGSource()
  {
    state = BigInteger.ZERO;
  }


  @Override
  public void setState( byte[] b )
  {
    state = new BigInteger( b ).abs();
  }


  @Override
  public boolean seek( long position )
  {
    state = BigInteger.ZERO;
    advance( position );

    return true;
  }


  // this version of advance produces many bits while advancing the size of state
  // quite slowly, but it is only one variation and perhaps not the best
  @Override
  public void advance( long amount )
  {
    if (amount > position)
    {
      amount -= position;
    }
    else
    {
      position -= amount;
      amount = 0;
    }

    while (amount > 0)
    {
    int size = state.bitLength();
    BigInteger biAmount = BigInteger.valueOf( amount );
    BigInteger biggerState = TWO.pow( size ), distance = biggerState.subtract( state );

      if (distance.compareTo( biAmount ) >= 0)
      {
        amount = 0;
        state.add( biAmount );
      }
      else
      {
        amount -= distance.longValue();
        state = biggerState;
      }
    }
  }


  public boolean next()
  {
    if (0 == position)
    {
      state = state.add( BigInteger.ONE );
      position = state.bitLength();
    }

    return state.testBit( position-- );
  }


  @Override
  public int next32()
  {
    // TODO Auto-generated method stub
    return 0;
  }


  @Override
  public IRNG deepCopy()
  {
    return deepCopy( new BigIntegerRNGSource() );
  }


  @Override
  protected IRNG deepCopy( IRNG target )
  {
    // TODO Auto-generated method stub
    return null;
  }
  
}
*/