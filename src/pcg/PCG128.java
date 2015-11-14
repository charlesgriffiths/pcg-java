package pcg;

import java.io.Serializable;
import java.math.BigInteger;


// TODO: 128 bits of output at once to a stream
// TODO: adjust values in next64()
public class PCG128 extends PCGKernel implements Serializable
{
private static final long serialVersionUID = 1L;

public final static BigInteger mul128 = new BigInteger( "47026247687942121848144207491837523525" ),
                               inc128 = new BigInteger( "117397592171526113268558934119004209487" ),
                               max256 = BigInteger.valueOf( 2 ).pow( 256 ).subtract( BigInteger.ONE );

BigInteger state = BigInteger.ONE, inc = inc128;


  public PCG128()
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
    state = state.multiply( mul128 ).add( inc ).mod( max256 );

  int rotate = state.shiftRight( 122 ).intValue() & 0x3f;
  BigInteger shifted = state.xor( state.shiftRight( 64 ) );

    return shifted.shiftLeft( rotate ).xor( shifted.shiftRight( 64-rotate ) ).longValue();
  }
}

