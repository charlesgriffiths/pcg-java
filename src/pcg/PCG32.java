package pcg;

import java.io.Serializable;

import rngtools.IRNG;


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


public final class PCG32 extends PCGKernel implements Serializable
{
private static final long serialVersionUID = 1L;

public static final long mul64 = 6364136223846793005L, inc64 = 1442695040888963407L;

private long state = 1, inc = inc64;


  public PCG32()
  {
  }


  public PCG32( long seed )
  {
    setState( seed );
  }


  @Override
  public void setState( long seed )
  {
    state = seed;
  }


  @Override
  public void setState( byte[] b )
  {
    setState( get( b, 0, b.length ));
  }


  @Override
  public void setStream()
  {
    setStream( inc64 );
  }


  @Override
  public void setStream( long inc )
  {
    this.inc = inc;
  }


  @Override
  public int blockSize()
  {
    return 4;
  }

/* This output function in java is meant to serve the same purpose as the following:
 * 
// *Really* minimal PCG32 code / (c) 2014 M.E. O'Neill / pcg-random.org
// Licensed under Apache License 2.0 (NO WARRANTY, etc. see website)

typedef struct { uint64_t state;  uint64_t inc; } pcg32_random_t;

uint32_t pcg32_random_r(pcg32_random_t* rng)
{
    uint64_t oldstate = rng->state;
    // Advance internal state
    rng->state = oldstate * 6364136223846793005ULL + (rng->inc|1);
    // Calculate output function (XSH RR), uses old state for max ILP
    uint32_t xorshifted = ((oldstate >> 18u) ^ oldstate) >> 27u;
    uint32_t rot = oldstate >> 59u;
    return (xorshifted >> rot) | (xorshifted << ((-rot) & 31));
}

 Similar functionality may be obtained by setting inc to 1 with setStream( 1L );
*/

  @Override
  protected long nextBlock()
  {
    state = state * mul64 + inc;

    return Integer.rotateRight( (int) (((state >>> 18) ^ state) >>> 27), (int) (state >>> 59) );
  }


  @Override
  public boolean seek( long offset )
  {
    setState( 1 + stateoffset( offset, mul64, inc ));

    return true;
  }


  @Override
  protected IRNG deepCopy( IRNG target )
  {
    if (target instanceof PCG32)
    {
    PCG32 pcg = (PCG32) target;

      pcg.state = state;
      pcg.inc = inc;
    }

    return target;
  }


  @Override
  public IRNG deepCopy()
  {
    return deepCopy( new PCG32() );
  }
}

