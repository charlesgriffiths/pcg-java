package pcg;

import java.io.Serializable;
import java.math.BigInteger;


public class PCG64 extends PCGKernel implements Serializable
{
private static final long serialVersionUID = 1L;

public final static BigInteger mul128 = new BigInteger( "47026247687942121848144207491837523525" ),
                               inc128 = new BigInteger( "117397592171526113268558934119004209487" ),
                               max128 = BigInteger.valueOf( 2 ).pow( 128 ).subtract( BigInteger.ONE );

BigInteger state = BigInteger.ONE, inc = inc128;


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
    state = state.multiply( mul128 ).add( inc ).mod( max128 );

  int rotate = state.shiftRight( 122 ).intValue();
  BigInteger shifted = state.xor( state.shiftRight( 64 ) );

    return shifted.shiftLeft( rotate ).xor( shifted.shiftRight( 64-rotate ) ).longValue();
  }
}

