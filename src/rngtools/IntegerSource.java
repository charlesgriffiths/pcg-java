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
BigInteger state;
int position = 0;  // an infinitely long period would require a BigInteger position and infinite memory


  BigIntegerRNGSource()
  {
    state = BigInteger.ZERO;
  }


  @Override
  public void setState( byte[] b )
  {
    
  }


  @Override
  public boolean seek( long position )
  {
    // TODO Auto-generated method stub
    return false;
  }


  @Override
  public void advance( long amount )
  {
    // TODO Auto-generated method stub
    
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
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  protected ISeekableRNG deepCopy( ISeekableRNG target )
  {
    // TODO Auto-generated method stub
    return null;
  }
  
}

