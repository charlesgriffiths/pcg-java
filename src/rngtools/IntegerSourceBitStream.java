package rngtools;

import java.math.BigInteger;


abstract public class IntegerSourceBitStream extends BitStream
{
  protected IntegerSourceBitStream() {}


  public static IntegerSourceBitStream create()
  {
    return new BigIntegerBitStreamBitwise();
  }


  public static IntegerSourceBitStream createAdder( int addPower )
  {
    return new BigIntegerBitStreamBitwiseAdder( addPower );
  }


  public static IntegerSourceBitStream create( int maxBits )
  {
    if (32==maxBits)
    {
      return new IntBitStreamBitwise();
    }

    return null;
  }
}


abstract class IntBitStream extends IntegerSourceBitStream
{
protected int state;
protected int position;


  IntBitStream()
  {
    advanceState();
    state = 0;
  }


  protected IntBitStream( int state, int position )
  {
    this.state = state;
    this.position = position;
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


class IntBitStreamBitwise extends IntBitStream
{
protected int maxPosition;


  IntBitStreamBitwise() {}

  protected IntBitStreamBitwise( int state, int position, int maxPosition )
  {
    super( state, position );
    this.maxPosition = maxPosition;
  }


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
    return new IntBitStreamBitwise( state, position, maxPosition );
  }
}


abstract class BigIntegerBitStream extends IntegerSourceBitStream
{
protected BigInteger state = BigInteger.ZERO;
protected int position = 0;


  BigIntegerBitStream()
  {
  }


  protected BigIntegerBitStream( BigInteger state, int position )
  {
    this.state = state;
    this.position = position;
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


class BigIntegerBitStreamBitwise extends BigIntegerBitStream
{
protected int maxPosition = 1;

  BigIntegerBitStreamBitwise() {}
  

  BigIntegerBitStreamBitwise( BigInteger state, int position, int maxPosition )
  {
    super( state, position );
    this.maxPosition = maxPosition;
  }


  @Override
  void advanceState()
  {
    state = state.add( BigInteger.ONE );
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
    return new BigIntegerBitStreamBitwise( state, position, maxPosition );
  }
}


// this rng has a bias toward '1' bits
class BigIntegerBitStreamBitwiseAdder extends BigIntegerBitStream
{
protected BigInteger count = BigInteger.ZERO, add, max;
protected int maxPosition = 1, addPower;

  BigIntegerBitStreamBitwiseAdder( int addPower )
  {
    this.addPower = addPower;
    add = BigInteger.valueOf( addPower );
    max = BigInteger.valueOf( 2 ).pow( maxPosition ).subtract( BigInteger.ONE );
  }
  

  BigIntegerBitStreamBitwiseAdder( BigInteger state, int position, BigInteger count, int maxPosition, BigInteger add, int addPower )
  {
    super( state, position );
    this.count = count;
    this.maxPosition = maxPosition;
    this.add = add;
    this.addPower = addPower;
    max = BigInteger.valueOf( 2 ).pow( maxPosition ).subtract( BigInteger.ONE );
  }


  protected void adjustAdd( BigInteger target )
  {
    while( target.compareTo( add ) > 0)
      add = add.multiply( BigInteger.valueOf( addPower ));
  }


  @Override
  void advanceState()
  {
    count = count.add( BigInteger.ONE );

    if (count.testBit( maxPosition ))
    {
      adjustAdd( max );
      count = BigInteger.ZERO;
      maxPosition++;
      max = BigInteger.valueOf( 2 ).pow( maxPosition ).subtract( BigInteger.ONE );
    }

    state = state.add( add ).and( max );

    position = maxPosition-1;
  }


  @Override
  public IBitStream deepCopy()
  {
    return new BigIntegerBitStreamBitwiseAdder( state, position, count, maxPosition, add, addPower );
  }
}

