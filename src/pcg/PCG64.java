package pcg;

import java.io.Serializable;
import java.math.BigInteger;


/*
 * This code is derived from the canonical C++ PCG implementation by
 * Melissa O'Neill <oneill@pcg-random.org>. The C++ version has many
 * additional features and is preferable if you can use C++ in your project.
 */


public final class PCG64 extends PCGKernel implements Serializable
{
private static final long serialVersionUID = 1L;

public final static BigInteger mul128 = new BigInteger( "47026247687942121848144207491837523525" ),
                               inc128 = new BigInteger( "117397592171526113268558934119004209487" ),
                               max128 = BigInteger.valueOf( 2 ).pow( 128 ).subtract( BigInteger.ONE );

private BigInteger state = BigInteger.ONE, inc = inc128;


  public PCG64()
  {
  }


  @Override
  public void setState( long state )
  {
    setState( BigInteger.valueOf( state ));
  }


  public void setState( BigInteger state )
  {
    this.state = state;
  }


  @Override
  public void setStream()
  {
    inc = inc128;
  }


  @Override
  public void setStream( long inc )
  {
    setStream( BigInteger.valueOf( inc ));
  }


  public void setStream( BigInteger inc )
  {
    this.inc = inc;
  }


  @Override
  public byte next8()
  {
    return (byte) (next64() >> 58);
  }


  @Override
  public short next16()
  {
    return (short) (next64() >> 48);
  }


  @Override
  public int next32()
  {
    return (int) (next64() >> 32);
  }


  @Override
  public long next64()
  {
    state = state.multiply( mul128 ).add( inc ).and( max128 );

  int rotate = state.shiftRight( 122 ).intValue();
  BigInteger shifted = state.xor( state.shiftRight( 64 ) );

    return shifted.shiftRight( rotate ).xor( shifted.shiftLeft( 64-rotate ) ).longValue();
  }
}

