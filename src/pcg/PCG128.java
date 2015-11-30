package pcg;

import java.io.Serializable;
import java.math.BigInteger;

import rngtools.ISeekableRNG;


/*
 * PCG Random Number Generation for Java.
 *
 * Copyright 2015 Charles Griffiths <cs.griffiths@yahoo.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For additional information about the PCG random number generation scheme,
 * including its license and other licensing options, visit
 *
 *       http://www.pcg-random.org
 */
/*
 * This code is derived from the canonical C++ PCG implementation by
 * Melissa O'Neill <oneill@pcg-random.org>. The C++ version has many
 * additional features and is preferable if you can use C++ in your project.
 */


// TODO: 128 bits of output at once to a stream
// TODO: adjust values in next64()
public final class PCG128 extends PCGKernel implements Serializable
{
private static final long serialVersionUID = 1L;

public final static BigInteger mul128 = new BigInteger( "47026247687942121848144207491837523525" ),
                               inc128 = new BigInteger( "117397592171526113268558934119004209487" ),
                               max256 = BigInteger.valueOf( 2 ).pow( 256 ).subtract( BigInteger.ONE );

private BigInteger state = BigInteger.ONE, inc = inc128;


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
    state = state.multiply( mul128 ).add( inc ).and( max256 );

  int rotate = state.shiftRight( 122 ).intValue() & 0x3f;
  BigInteger shifted = state.xor( state.shiftRight( 128 ) );

    shifted = shifted.xor( shifted.shiftRight( 64 ));

    return shifted.shiftRight( rotate ).xor( shifted.shiftLeft( 64-rotate ) ).longValue();
  }


  @Override
  protected ISeekableRNG deepCopy( ISeekableRNG target )
  {
    if (target instanceof PCG128)
    {
    PCG128 pcg = (PCG128) target;

      pcg.state = state;
      pcg.inc = inc;
    }

    return target;
  }

  
  @Override
  public ISeekableRNG deepCopy()
  {
    return deepCopy( new PCG128() );
  }
}

