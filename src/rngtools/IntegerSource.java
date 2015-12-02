package rngtools;

import java.math.BigInteger;


public class IntegerSource extends RNGBitStream
{
  protected IntegerSource() {}
  protected IntegerSource( ISeekableRNG rng )
  {
    super( rng );
  }


  public IntegerSource create()
  {
    return null;
  }

  public IntegerSource create( int maxBits )
  {
    return null;
  }
}


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
  public ISeekableRNG deepCopy()
  {
    return deepCopy( new BigIntegerRNGSource() );
  }


  @Override
  protected ISeekableRNG deepCopy( ISeekableRNG target )
  {
    // TODO Auto-generated method stub
    return null;
  }
  
}

